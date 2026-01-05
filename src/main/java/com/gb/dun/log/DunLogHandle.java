package com.gb.dun.log;

import cn.hutool.core.net.NetUtil;
import com.alibaba.fastjson.JSON;
import com.gb.dun.entity.dto.QuerySubmitDTO;
import com.gb.dun.log.entity.*;
import com.gb.mq.dun.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 日志处理类
 *
 * @author yyl
 */
@Component
@Slf4j
public class DunLogHandle {

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
     * 风控审核日志
     *
     * @param event             mq请求参数
     * @param riskControlNumber 风控单号
     * @param optionType        操作类型
     * @param reqJson           请求工保盾json
     * @param respJson          工保盾响应json
     * @param errorMsg          错误消息
     */
    @Async
    public void logDunRiskView(DunRiskSysReviewEvent event, String riskControlNumber, String optionType, String reqJson, String respJson, String errorMsg) {
        try {
            DunRiskViewLog log = new DunRiskViewLog();
            log.setOptionType(StringUtils.defaultString(optionType));
            log.setIp(NetUtil.getLocalhostStr());
            log.setCreateDateTime(new Date());
            log.setReqJson(StringUtils.defaultString(reqJson));
            log.setErrorMsg(StringUtils.defaultString(errorMsg));
            log.setRespJson(StringUtils.defaultString(respJson));
            log.setRiskControlNumber(StringUtils.defaultString(riskControlNumber));
            log.setCreateName(StringUtils.defaultString(event.getCreateName()));
            log.setPolicyNumber(StringUtils.defaultString(event.getCastInsuranceId()));
            log.setInnerReqJson(JSON.toJSONString(event));
            mongoTemplate.insert(log);
        } catch (Exception e) {
            log.error("风控审核日志记录异常：{}", e);
        }
    }

    /**
     * 承保结果反馈日志
     *
     * @param event    请求参数
     * @param reqJson  请求工保盾json
     * @param respJson 工保盾响应json
     */
    @Async
    public void logUnderwritingRefusal(DunRoUnderwritingRefusalEvent event, String reqJson, String respJson) {
        try {
            DunRoUnderwritingRefusalLog log = new DunRoUnderwritingRefusalLog();
            log.setCreateDateTime(new Date());
            log.setIp(NetUtil.getLocalhostStr());
            log.setReqJson(StringUtils.defaultString(reqJson));
            log.setRespJson(StringUtils.defaultString(respJson));
            log.setRiskControlNumber(StringUtils.defaultString(event.getRiskControlNumber()));
            log.setCreateName(StringUtils.defaultString(event.getCreateName()));
            log.setPolicyNumber(StringUtils.defaultString(event.getPolicyNumber()));
            log.setInnerReqJson(JSON.toJSONString(event));
            mongoTemplate.insert(log);
        } catch (Exception e) {
            log.error("承保结果反馈日志记录异常：{}", e);
        }
    }

    /**
     * 关联订单信息日志
     *
     * @param event    请求参数
     * @param reqJson  请求工保盾json
     * @param respJson 工保盾响应json
     */
    @Async
    public void logDunAssociateOrder(DunRoAssociateOrderEvent event, String reqJson, String respJson) {
        try {
            DunRoAssicateOrderLog log = new DunRoAssicateOrderLog();
            log.setCreateDateTime(new Date());
            log.setIp(NetUtil.getLocalhostStr());
            log.setReqJson(StringUtils.defaultString(reqJson));
            log.setRespJson(StringUtils.defaultString(respJson));
            log.setRiskControlNumber(StringUtils.defaultString(event.getRiskControlNumber()));
            log.setCreateName(StringUtils.defaultString(event.getCreateName()));
            log.setPolicyNumber(StringUtils.defaultString(event.getPolicyNumber()));
            log.setInnerReqJson(JSON.toJSONString(event));
            mongoTemplate.insert(log);
        } catch (Exception e) {
            log.error("关联订单信息日志记录异常：{}", e);
        }
    }

    /**
     * 退保信息反馈日志
     *
     * @param event    请求参数
     * @param reqJson  请求工保盾json
     * @param respJson 工保盾响应json
     */
    @Async
    public void logDunSurrenderRecord(DunRoSurrenderRecordEvent event, String reqJson, String respJson) {
        try {
            DunRoSurrenderRecordLog log = new DunRoSurrenderRecordLog();
            log.setCreateDateTime(new Date());
            log.setIp(NetUtil.getLocalhostStr());
            log.setReqJson(StringUtils.defaultString(reqJson));
            log.setRespJson(StringUtils.defaultString(respJson));
            log.setRiskControlNumber(StringUtils.defaultString(event.getRiskControlNumber()));
            log.setCreateName(StringUtils.defaultString(event.getCreateName()));
            log.setPolicyNumber(StringUtils.defaultString(event.getPolicyNumber()));
            log.setInnerReqJson(JSON.toJSONString(event));
            mongoTemplate.insert(log);
        } catch (Exception e) {
            log.error("退保信息反馈日志记录异常：{}", e);
        }
    }

    /**
     * 保司承保详细结果反馈日志
     *
     * @param event    请求参数
     * @param reqJson  请求工保盾json
     * @param respJson 工保盾响应json
     */
    @Async
    public void logDunRoUnderwritingRefusalResult(DunRoUnderwritingRefusalResultEvent event, String reqJson, String respJson) {
        try {
            DunRoUnderwritingRefusalResultLog log = new DunRoUnderwritingRefusalResultLog();
            log.setCreateDateTime(new Date());
            log.setIp(NetUtil.getLocalhostStr());
            log.setReqJson(StringUtils.defaultString(reqJson));
            log.setRespJson(StringUtils.defaultString(respJson));
            log.setRiskControlNumber(StringUtils.defaultString(event.getRiskControlNumber()));
            log.setCreateName(StringUtils.defaultString(event.getCreateName()));
            log.setPolicyNumber(StringUtils.defaultString(event.getCastInsuranceId()));
            log.setInnerReqJson(JSON.toJSONString(event));
            mongoTemplate.insert(log);
        } catch (Exception e) {
            log.error("保司承保详细结果反馈日志记录异常：{}", e);
        }
    }

    /**
     * 保司文件信息反馈日志
     *
     * @param event    请求参数
     * @param reqJson  请求工保盾json
     * @param respJson 工保盾响应json
     */
    @Async
    public void logDunInsuranceInfo(DunInsuranceInfoEvent event, String reqJson, String respJson) {
        try {
            DunInsuranceInfoLog log = new DunInsuranceInfoLog();
            log.setCreateDateTime(new Date());
            log.setIp(NetUtil.getLocalhostStr());
            log.setReqJson(StringUtils.defaultString(reqJson));
            log.setRespJson(StringUtils.defaultString(respJson));
            log.setRiskControlNumber(StringUtils.defaultString(event.getRiskControlNumber()));
            log.setCreateName(StringUtils.defaultString(event.getCreateName()));
            log.setPolicyNumber(StringUtils.defaultString(event.getPolicyNumber()));
            log.setInnerReqJson(JSON.toJSONString(event));
            mongoTemplate.insert(log);
        } catch (Exception e) {
            log.error("保司文件信息反馈日志记录异常：{}", e);
        }
    }

    @Async
    public void logDunQuestionnaireCommitLog(QuerySubmitDTO querySubmitDTO, String reqJson, String respJson) {
        try {
            DunQuestionnaireCommitLog log = new DunQuestionnaireCommitLog();
            log.setCreateDateTime(new Date());
            log.setIp(NetUtil.getLocalhostStr());
            log.setReqJson(StringUtils.defaultString(reqJson));
            log.setRespJson(StringUtils.defaultString(respJson));
            mongoTemplate.insert(log);
        } catch (Exception e) {
            log.error("智能问卷校验反馈日志记录异常：{}", e);
        }
    }
}
