package com.gb.yun.log;

import cn.hutool.core.net.NetUtil;
import com.alibaba.fastjson.JSON;
import com.gb.yun.enmus.YunSyncDataTypeEnum;
import com.gb.yun.entity.bo.YunDataSyncResultBO;
import com.gb.yun.log.entity.YunDataSyncLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 日志处理类
 * @author yyl
 */
@Component
@Slf4j
public class YunLogHandle {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存日志的方法
     */
    public void saveLog(Object obj) {
        try {
            //插入日志
            mongoTemplate.insert(obj);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【保存日志】err: {}", e.getMessage());
        }
    }

    /**
     * 填写日志
     *
     * @param mqReqJson； 请求参数
     * @param contentId; 投保内容ID
     * @param createName; 操作人
     * @param bo  云数据同步结果BO
     * @return void
     */
    @Async
    public void log(String mqReqJson, String contentId, String createName, YunDataSyncResultBO bo) {
        try{
            YunDataSyncLog log = new YunDataSyncLog();
            log.setIp(NetUtil.getLocalhostStr());
            log.setContentId(StringUtils.defaultString(contentId));
            log.setCreateDateTime(new Date());
            log.setCreateName(StringUtils.defaultString(createName));
            log.setMqReqJson(mqReqJson);
            String value = StringUtils.EMPTY;
            if(CollectionUtils.isNotEmpty(bo.getEnTxtBodyBOList())){
                value = JSON.toJSONString(bo.getEnTxtBodyBOList());
            }
            log.setEnterpriseTxtJson(value);
            value = StringUtils.EMPTY;
            if(CollectionUtils.isNotEmpty(bo.getEnFileBodyBOList())){
                value = JSON.toJSONString(bo.getEnFileBodyBOList());
            }
            log.setEnterpriseFileJson(value);
            value = StringUtils.EMPTY;
            if(null != bo.getPjTxtBodyBO()){
                value = JSON.toJSONString(bo.getPjTxtBodyBO());
            }
            log.setProjectTextJson(value);
            value = StringUtils.EMPTY;
            if(null != bo.getPjFileBodyBO()){
                value = JSON.toJSONString(bo.getPjFileBodyBO());
            }
            log.setProjectFileJson(value);
            log.setErrorMsg(StringUtils.defaultString(bo.getErrorMsg()));
            mongoTemplate.insert(log);
        }catch (Exception e){
            log.error("工保云数据同步日志记录异常：{}", e);
        }
    }

    /**
     * 根据操作类型获取处理结果
     *
     * @param optionType 操作类型
     * @return String
     */
    private String getDealResult(String optionType){
        String result = StringUtils.EMPTY;
        if(null == optionType){
            return  result;
        }
        YunSyncDataTypeEnum typeEnum = YunSyncDataTypeEnum.getByCode(optionType);
        switch (typeEnum){
            case EN_TXT_ADD:
            case EN_FILE_ADD:
            case PJ_TXT_ADD:
            case PJ_FILE_ADD:
                result = "add";
                break;
            case EN_TXT_UPATE:
            case EN_FILE_UPDATE:
            case PJ_TXT_UPDATE:
            case PJ_FILE_UPDATE:
                result = "update";
                break;
            default:
                break;
        }
        return result;
    }
}
