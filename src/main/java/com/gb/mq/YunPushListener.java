package com.gb.mq;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.gb.bean.RabbitConfig;
import com.gb.common.constant.CommonConstant;
import com.gb.common.constant.RedisConstant;
import com.gb.mq.yun.YunSyncDataEvent;
import com.gb.utils.RedisUtils;
import com.gb.utils.exception.PreventRepeatException;
import com.gb.yun.entity.bo.*;
import com.gb.yun.entity.vo.FieldEnterpriseRequestVO;
import com.gb.yun.entity.vo.FieldProjectRequestVO;
import com.gb.yun.entity.vo.FileEnterpriseRequestVO;
import com.gb.yun.entity.vo.FileProjectRequestVO;
import com.gb.yun.log.YunLogHandle;
import com.gb.yun.service.YunPushService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.gb.yun.enmus.YunObjectTypeEnum.*;

/**
 * 云推送监听接收
 *
 * @author sunkailun
 * @DateTime 2021/3/4  10:52 上午
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@Slf4j
@Component
@Setter(onMethod_ = {@Autowired})
@RabbitListener(queues = RabbitConfig.YUN_SYNCDATA_Q)
@SuppressWarnings(value = "all")
public class YunPushListener {

    private YunPushService yunPushService;

    private YunLogHandle yunLogHandle;

    private StringRedisTemplate stringRedisTemplate;

    @RabbitHandler
    public void yunSyncDataHandler(YunSyncDataEvent event) throws Exception {
        String mqReqJson = JSON.toJSONString(event);
        log.debug("收到云数据同步MQ消息：【消息内容：{}】", mqReqJson);
        YunDataSyncResultBO bo = new YunDataSyncResultBO();
        try {
            //1、幂等校验、基本值校验
            String idempotentKey = idempotentCheck(event.getContentId(), mqReqJson);
            yunPushService.validateParams(event);
            //2、获取项目信息
            if (Objects.nonNull(event.getProject())) {
                Map<String, Object> projectMap = yunPushService.queryProjectInfo(event.getProject());
                if (MapUtils.isNotEmpty(projectMap)) {
                    //2.1、项目字段信息获取
                    FieldProjectRequestVO txtProject = (FieldProjectRequestVO) projectMap.get(CommonConstant.TXT_PROJECT);
                    if (Objects.nonNull(txtProject)) {
                        //2.1.1、设置项目信息的linkId和linkName
                        String linkId = SecureUtil.md5(txtProject.getProjectName());
                        String linkName = txtProject.getProjectName();
                        //2.1.2、同步项目字段信息到工保云
                        Map<String, String> returnMap = yunPushService.pushCloud(linkId, linkName, PROJECT, txtProject);
                        bo.setPjTxtBodyBO(buildPjTxtBodyBO(txtProject, returnMap));
                    }
                    //2.2、项目附件信息获取
                    FileProjectRequestVO fileProject = (FileProjectRequestVO) projectMap.get(CommonConstant.FILE_PROJECT);
                    if (Objects.nonNull(fileProject)) {
                        //2.2.1、查询项目附件信息的linkId和linkName，如果项目附件信息的linkId和linkName不存在，则推送云新增，否则推送云修改
                        String linkId = SecureUtil.md5(fileProject.getProjectName());
                        String linkName = fileProject.getProjectName();
                        //2.2.2、同步项目附件信息到工保云
                        Map<String, String> returnMap = yunPushService.pushCloud(linkId, linkName, PJ_FILE, txtProject);
                        bo.setPjFileBodyBO(buildPjFileBodyBO(fileProject, returnMap));
                    }
                }
            }
            //3、获取企业信息
            Map<String, Object> enterpriseMap = yunPushService.queryEnterpriseInfo(event.getEnterprisList());
            List<YunDataSyncEnTxtBodyBO> enTxtBodyList = Lists.newArrayList();
            List<YunDataSyncEnFileBodyBO> enFileBodyList = Lists.newArrayList();
            if (MapUtils.isNotEmpty(enterpriseMap)) {
                //3.1、企业字段信息获取
                List<FieldEnterpriseRequestVO> txtEnterpriseList = (List<FieldEnterpriseRequestVO>) enterpriseMap.get(CommonConstant.TXT_ENTERPRISE);
                Set<String> keySet = Sets.newHashSet();
                for (FieldEnterpriseRequestVO txtEnterprise : txtEnterpriseList) {
                    if (Objects.isNull(txtEnterprise)) {
                        continue;
                    }
                    String key = txtEnterprise.getCreditCode() + txtEnterprise.getEnterpriseName();
                    if (StringUtils.isBlank(key)) {
                        log.debug("【工保云】同步企业信息，存在一条【统一社会信用代码与企业名称】为空的数据，无须同步！该数据为：{}", JSON.toJSONString(txtEnterprise));
                        continue;
                    }
                    if (!keySet.add(key)) {
                        continue;
                    }
                    //3.1.1、设置企业信息的linkId和linkName
                    String linkId = SecureUtil.md5(txtEnterprise.getEnterpriseName());
                    String linkName = txtEnterprise.getEnterpriseName();
                    //3.1.2、同步企业字段信息到工保云
                    Map<String, String> returnMap = yunPushService.pushCloud(linkId, linkName, ENTERPRISE, txtEnterprise);
                    YunDataSyncEnTxtBodyBO txtBodyBO = buildEnTxtBodyBO(txtEnterprise, returnMap);
                    enTxtBodyList.add(txtBodyBO);
                }
                keySet.clear();
                //3.2、企业附件信息获取
                List<FileEnterpriseRequestVO> fileEnterpriseList = (List<FileEnterpriseRequestVO>) enterpriseMap.get(CommonConstant.FILE_ENTERPRISE);
                for (FileEnterpriseRequestVO fileEnterprise : fileEnterpriseList) {
                    if (Objects.isNull(fileEnterprise)) {
                        continue;
                    }
                    String key = fileEnterprise.getEnterpriseName();
                    if (StringUtils.isBlank(key)) {
                        log.debug("【工保云】同步企业附件信息，存在一条【企业名称】为空的数据，无须同步！该数据为：{}", JSON.toJSONString(fileEnterprise));
                        continue;
                    }
                    if (!keySet.add(key)) {
                        continue;
                    }
                    //3.2.1、查询企业附件信息的linkId和linkName，如果企业附件信息的linkName不存在，则推送云新增，否则推送云修改
                    String linkId = SecureUtil.md5(fileEnterprise.getEnterpriseName());
                    String linkName = fileEnterprise.getEnterpriseName();
                    //3.2.2、同步企业附件到工保云
                    Map<String, String> returnMap = yunPushService.pushCloud(linkId, linkName, EN_FILE, fileEnterprise);
                    YunDataSyncEnFileBodyBO fileBodyBO = buildEnFileBodyBO(fileEnterprise, returnMap);
                    enFileBodyList.add(fileBodyBO);
                }
            }
            bo.setEnTxtBodyBOList(enTxtBodyList);
            bo.setEnFileBodyBOList(enFileBodyList);
            //4、放入缓存，用于mq业务幂等校验
            RedisUtils.add(stringRedisTemplate, idempotentKey, mqReqJson, RedisConstant.EXPIRE, TimeUnit.MINUTES);
        } catch (PreventRepeatException e) {
            log.error(e.getMessage());
            bo.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            log.error("工保云同步数据异常：", e);
            String errorMsg = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "工保云同步数据业务处理异常！";
            bo.setErrorMsg(errorMsg);
            throw e;
        } finally {
            //记录日志
            yunLogHandle.log(mqReqJson, event.getContentId(), event.getCreateName(), bo);
            log.debug("云数据同步MQ消息消费结束.");
        }
    }

    /**
     * 组织云数据同步企业字段体BO
     *
     * @param txtEnterprise: 请求参数
     * @param returnMap:     同步结果
     * @return YunDataSyncEnTxtBodyBO
     * @author 孙凯伦
     * @since 2021-04-20
     */
    private YunDataSyncEnTxtBodyBO buildEnTxtBodyBO(FieldEnterpriseRequestVO txtEnterprise, Map<String, String> returnMap) {
        YunDataSyncEnTxtBodyBO txtBodyBO = new YunDataSyncEnTxtBodyBO();
        txtBodyBO.setReqEnterpriseTxtJson(JSON.toJSONString(txtEnterprise));
        txtBodyBO.setEnterpriseName(txtEnterprise.getEnterpriseName());
        txtBodyBO.setRespEnterpriseTxtJson(returnMap.get("resultMap"));
        txtBodyBO.setType(returnMap.get("typeCode"));
        txtBodyBO.setErrorMsg(returnMap.get("errorMsg"));
        return txtBodyBO;
    }

    /**
     * 组织云数据同步企业附件体BO
     *
     * @param fileEnterprise: 请求参数
     * @param returnMap:      同步结果
     * @return YunDataSyncEnFileBodyBO
     * @author 孙凯伦
     * @since 2021-04-20
     */
    private YunDataSyncEnFileBodyBO buildEnFileBodyBO(FileEnterpriseRequestVO fileEnterprise, Map<String, String> returnMap) {
        YunDataSyncEnFileBodyBO fileBodyBO = new YunDataSyncEnFileBodyBO();
        fileBodyBO.setEnterpriseName(fileEnterprise.getEnterpriseName());
        fileBodyBO.setReqEnterpriseFileJson(JSON.toJSONString(fileEnterprise));
        fileBodyBO.setRespEnterpriseFileJson(returnMap.get("resultMap"));
        fileBodyBO.setType(returnMap.get("typeCode"));
        fileBodyBO.setErrorMsg(returnMap.get("errorMsg"));
        return fileBodyBO;
    }

    /**
     * 组织云数据同步项目字段体BO
     *
     * @param txtProject: 请求参数
     * @param returnMap:  同步结果
     * @return YunDataSyncPjTxtBodyBO
     * @author 孙凯伦
     * @since 2021-04-20
     */
    private YunDataSyncPjTxtBodyBO buildPjTxtBodyBO(FieldProjectRequestVO txtProject, Map<String, String> returnMap) {
        YunDataSyncPjTxtBodyBO txtBodyBO = new YunDataSyncPjTxtBodyBO();
        txtBodyBO.setProjectName(txtProject.getProjectName());
        txtBodyBO.setReqProjectTextJson(JSON.toJSONString(txtProject));
        txtBodyBO.setRespProjectTextJson(returnMap.get("resultMap"));
        txtBodyBO.setType(returnMap.get("typeCode"));
        txtBodyBO.setErrorMsg(returnMap.get("errorMsg"));
        return txtBodyBO;
    }

    /**
     * 组织云数据同步项目附件体BO
     *
     * @param fileProject: 请求参数
     * @param returnMap:   同步结果
     * @return YunDataSyncPjFileBodyBO
     * @author 孙凯伦
     * @since 2021-04-20
     */
    private YunDataSyncPjFileBodyBO buildPjFileBodyBO(FileProjectRequestVO fileProject, Map<String, String> returnMap) {
        YunDataSyncPjFileBodyBO fileBodyBO = new YunDataSyncPjFileBodyBO();
        fileBodyBO.setProjectName(fileProject.getProjectName());
        fileBodyBO.setReqProjectFileJson(JSON.toJSONString(fileProject));
        fileBodyBO.setRespProjectFileJson(returnMap.get("resultMap"));
        fileBodyBO.setType(returnMap.get("typeCode"));
        fileBodyBO.setErrorMsg(returnMap.get("errorMsg"));
        return fileBodyBO;
    }

    /**
     * MQ消息幂等校验
     *
     * @param contentId: 投保内容ID
     * @param mqReqJson: mq请求参数JSON
     * @return String
     * @author 孙凯伦
     * @since 2021-04-20
     */
    private String idempotentCheck(String contentId, String mqReqJson) {
        RedisConstant s = new RedisConstant();
        String key = RedisConstant.GBY + contentId;
        if (StringUtils.isNotBlank(RedisUtils.get(stringRedisTemplate, key))) {
            throw new PreventRepeatException("【工保云重复同步】：投保内容ID【" + contentId + "】，云数据同步，消息已消费！【消息内容：" + mqReqJson + "】");
        }
        return key;
    }

}
