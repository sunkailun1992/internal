package com.gb.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.gb.bean.RabbitConfig;
import com.gb.common.constant.RedisConstant;
import com.gb.common.entity.Enterprise;
import com.gb.common.entity.Project;
import com.gb.mq.yun.YunSyncDataEvent;
import com.gb.yunv2.entity.to.YunV2EnterpriseFileTo;
import com.gb.yunv2.entity.to.YunV2EnterpriseTextTo;
import com.gb.yunv2.entity.to.YunV2ProjectFileTo;
import com.gb.yunv2.entity.to.YunV2ProjectTextTo;
import com.gb.yunv2.entity.enums.YunV2BoEnum;
import com.gb.yunv2.service.impl.YunV2EnterpriseV2ProxyServiceImpl;
import com.gb.yunv2.service.impl.YunV2ProjectV2ProxyServiceImpl;
import com.gb.utils.RedisUtils;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.PreventRepeatException;
import com.gb.yun.entity.bo.YunDataSyncResultBO;
import com.gb.yun.log.YunLogHandle;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : 王一飞
 * @title: 监听接收的数据 推送到云
 * @createDate: 2022/1/7 10:03 上午
 */
@Slf4j
@Component
@Setter(onMethod_ = {@Resource})
@RabbitListener(queues = RabbitConfig.YUN_SYNCDATA_Q)
public class YunV2PushListener {

    private YunLogHandle yunLogHandle;

    private StringRedisTemplate stringRedisTemplate;

    private YunV2EnterpriseV2ProxyServiceImpl yunV2EnterpriseProxyService;

    private YunV2ProjectV2ProxyServiceImpl yunV2ProjectProxyService;

    @RabbitHandler
    public void yunSyncDataHandler(YunSyncDataEvent event) {
        //  校验参数
        String mqReqJson = JSON.toJSONString(event);
        if (Objects.isNull(event) || StringUtils.isBlank(event.getContentId())) {
            throw new ParameterNullException("推送云：参数错误[" + mqReqJson + "]");
        }
        //  记录信息
        YunDataSyncResultBO bo = new YunDataSyncResultBO();
        log.debug("收到推送云数据同步MQ消息：【消息内容：{}】", mqReqJson);
        try {
            //  幂等校验
            String idempotentKey = idempotentCheck(event.getContentId(), mqReqJson);
            //  推送企业数据
            pushEnterpriseData(event);
            //  推送项目数据
            pushProjectData(event);
            //  放入缓存，用于mq业务幂等校验
            RedisUtils.add(stringRedisTemplate, idempotentKey, mqReqJson, RedisConstant.EXPIRE, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("工保云同步数据异常：", e);
            bo.setErrorMsg(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "工保云同步数据业务处理异常！");
            throw e;
        } finally {
            //  记录日志
            yunLogHandle.log(mqReqJson, event.getContentId(), event.getCreateName(), bo);
            log.debug("云数据同步MQ消息消费结束.");
        }
    }


    /**
     * @createAuthor: 王一飞
     * @title: 推送企业数据
     * @createDate: 2022/1/7 3:13 下午
     * @description:
     * @return:
     */
    private void pushEnterpriseData(YunSyncDataEvent event) {
        //  同步企业
        List<Enterprise> enterpriseList = event.getEnterprisList();
        if (CollectionUtils.isEmpty(enterpriseList)) {
            return;
        }
        //  投保企业同步
        Enterprise castEnterprise = enterpriseList.stream().findFirst().get();
        HashMap<String, Object> castEnterpriseMap = new HashMap<String, Object>(2) {{
            YunV2EnterpriseTextTo yunV2EnterpriseTextTo = YunV2EnterpriseTextTo.convert(castEnterprise);
            put(YunV2BoEnum.字段.getName(), yunV2EnterpriseTextTo);
            YunV2EnterpriseFileTo yunV2EnterpriseFileTo = YunV2EnterpriseFileTo.convert(castEnterprise);
            put(YunV2BoEnum.附件.getName(), yunV2EnterpriseFileTo);
        }};
        yunV2EnterpriseProxyService.addAndUpdate(JSONObject.parseObject(JSON.toJSONString(castEnterpriseMap)));

        //  被保企业同步
        Enterprise insuredEnterprise = enterpriseList.stream().skip(enterpriseList.size() - 1).findFirst().get();
        HashMap<String, Object> insuredEnterpriseMap = new HashMap<String, Object>(2) {{
            YunV2EnterpriseTextTo yunV2EnterpriseTextTo = YunV2EnterpriseTextTo.convert(insuredEnterprise);
            put(YunV2BoEnum.字段.getName(), yunV2EnterpriseTextTo);
            YunV2EnterpriseFileTo yunV2EnterpriseFileTo = YunV2EnterpriseFileTo.convert(insuredEnterprise);
            put(YunV2BoEnum.附件.getName(), yunV2EnterpriseFileTo);
        }};
        yunV2EnterpriseProxyService.addAndUpdate(JSONObject.parseObject(JSON.toJSONString(insuredEnterpriseMap)));
    }


    /**
     * @createAuthor: 王一飞
     * @title: 推送企业数据
     * @createDate: 2022/1/7 3:13 下午
     * @description:
     * @return:
     */
    private Object pushProjectData(YunSyncDataEvent event) {
        //  同步项目
        Project project = event.getProject();
        if (Objects.isNull(project)) {
            return new Object();
        }
        HashMap<String, Object> projectMap = new HashMap<String, Object>(2) {{
            YunV2ProjectTextTo yunV2ProjectTextTo = YunV2ProjectTextTo.convert(project);
            put(YunV2BoEnum.字段.getName(), yunV2ProjectTextTo);
            YunV2ProjectFileTo yunV2ProjectFileTo = YunV2ProjectFileTo.convert(project);
            put(YunV2BoEnum.附件.getName(), yunV2ProjectFileTo);
        }};
        return yunV2ProjectProxyService.addAndUpdate(JSONObject.parseObject(JSON.toJSONString(projectMap)));
    }


    /**
     * @param contentId: 投保内容ID
     * @param mqReqJson: mq请求参数JSON
     * @createAuthor: 王一飞
     * @title: MQ消息幂等校验
     * @createDate: 2022/1/7 11:13 上午
     */
    private String idempotentCheck(String contentId, String mqReqJson) {
        String key = "newYun" + contentId;
        if (StringUtils.isNotBlank(RedisUtils.get(stringRedisTemplate, key))) {
            throw new PreventRepeatException("【工保云重复同步】：投保内容ID【" + contentId + "】，云数据同步，消息已消费！【消息内容：" + mqReqJson + "】");
        }
        return key;
    }

}
