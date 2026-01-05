package com.gb.dun.aop;

import com.alibaba.fastjson.JSON;
import com.gb.aliyun.dingding.SendRebootUtil;
import com.gb.common.constant.UrlConstant;
import com.gb.dun.entity.bo.PolicyInfo;
import com.gb.dun.entity.bo.PolicyResult;
import com.gb.dun.entity.vo.DunResponseVO;
import com.gb.dun.log.DunLogHandle;
import com.gb.mq.dun.*;
import com.gb.utils.JsonUtil;
import com.gb.utils.enumeration.SystemSourceEnum;
import com.gb.utils.exception.BusinessException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

/**
 * <p>
 * 盾日志切面
 * </p>
 *
 * @author sunx
 * @since 2021-12-26
 */
@Aspect
@Component
@Slf4j
@Setter(onMethod_ = {@Autowired})
@Order(2)
public class DunLogAop {

    private DunLogHandle dunLogHandle;

    @Pointcut("@annotation(com.gb.dun.annotation.DunLog)")
    public void writeLog() {
    }

    @AfterReturning(value = "writeLog()", returning = "dunResponseVO")
    public void afterReturning(JoinPoint joinPoint, DunResponseVO dunResponseVO) {
        writeLog(joinPoint, dunResponseVO);
    }

    @AfterThrowing(value = "writeLog()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("MQ-推送盾-请求参数：【{}】,异常信息：", JsonUtil.json(joinPoint), e);
    }

    /**
     * 写日志
     *
     * @param joinPoint
     * @param dunResponseVO
     * @author sunx
     * @since 2021-03-18
     */
    private void writeLog(JoinPoint joinPoint, DunResponseVO dunResponseVO) {
        DunPushEvent event = (DunPushEvent) joinPoint.getArgs()[0];
        String castId = dunResponseVO.getCastId();
        switch (event.getPushType()) {
            case DUN_RISKVIEW_TYPE:
                DunRiskSysReviewEvent dunRiskSysReviewEvent = (DunRiskSysReviewEvent) event;
                castId = dunRiskSysReviewEvent.getCastInsuranceId();
                dunLogHandle.logDunRiskView(dunRiskSysReviewEvent, dunResponseVO.getRiskControlNumber(), dunResponseVO.getOptionType(), JSON.toJSONString(dunResponseVO.getReqObject()), dunResponseVO.getRespJson(), dunResponseVO.getErrorMsg());
                break;
            case DUN_ASSOCIATE_ORDER_TYPE:
                DunRoAssociateOrderEvent roAssociateOrderEvent = (DunRoAssociateOrderEvent) event;
                castId = roAssociateOrderEvent.getPolicyNumber();
                dunLogHandle.logDunAssociateOrder(roAssociateOrderEvent, JSON.toJSONString(dunResponseVO.getReqObject()), dunResponseVO.getRespJson());
                break;
            case DUN_UNDERWRITING_TYPE:
                DunRoUnderwritingRefusalEvent roUnderwritingRefusalEvent = (DunRoUnderwritingRefusalEvent) event;
                castId = roUnderwritingRefusalEvent.getPolicyNumber();
                dunLogHandle.logUnderwritingRefusal(roUnderwritingRefusalEvent, JSON.toJSONString(dunResponseVO.getReqObject()), dunResponseVO.getRespJson());
                break;
            case DUN_SURRENDER_TYPE:
                DunRoSurrenderRecordEvent roSurrenderRecordEvent = (DunRoSurrenderRecordEvent) event;
                castId = roSurrenderRecordEvent.getPolicyNumber();
                dunLogHandle.logDunSurrenderRecord(roSurrenderRecordEvent, JSON.toJSONString(dunResponseVO.getReqObject()), dunResponseVO.getRespJson());
                break;
            case DUN_INSURANCE_INFO_TYPE:
                //保司保单文件信息
                DunInsuranceInfoEvent dunInsuranceInfoEvent = (DunInsuranceInfoEvent) event;
                castId = dunInsuranceInfoEvent.getCastInsuranceId();
                dunLogHandle.logDunInsuranceInfo(dunInsuranceInfoEvent, JSON.toJSONString(dunResponseVO.getReqObject()), dunResponseVO.getRespJson());
                break;
            case DUN_UNDERWRITING_REFUSAL_RESULTS_TYPE:
                //保司承保详细结果
                DunRoUnderwritingRefusalResultEvent dunRoUnderwritingRefusalResultEvent = (DunRoUnderwritingRefusalResultEvent) event;
                castId = dunRoUnderwritingRefusalResultEvent.getCastInsuranceId();
                dunLogHandle.logDunRoUnderwritingRefusalResult(dunRoUnderwritingRefusalResultEvent, JSON.toJSONString(dunResponseVO.getReqObject()), dunResponseVO.getRespJson());
                break;
            default:
                break;
        }
        if (Objects.nonNull(dunResponseVO) && StringUtils.isNotBlank(dunResponseVO.getErrorMsg())) {
            dunResponseVO.setCastId(castId);
            String title = event.getPushType().getCode();
            SendRebootUtil.DingDingParams dingDingParams = new SendRebootUtil.DingDingParams();
            dingDingParams.setReqTitle("通知" + SystemSourceEnum.GB_D.getDesc() + "-" + title);
            if (Objects.nonNull(dunResponseVO.getReqObject())) {
                dingDingParams.setReqObject(dunResponseVO.getReqObject());
            } else {
                dingDingParams.setReqObject(event);
            }
            dingDingParams.setListParams(new HashMap<String, String>(2) {{
                put("url", UrlConstant.getDunUrl(event.getPushType().getDunUrl()));
                put("投保单号", dunResponseVO.getCastId());
            }});
            if (Objects.nonNull(dunResponseVO.getMqObject())) {
                dingDingParams.setMqTitle(title);
                dingDingParams.setMqObject(dunResponseVO.getMqObject());
            }
            dingDingParams.setRespTitle(SystemSourceEnum.GB_D.getDesc());
            dingDingParams.setRespJson(dunResponseVO.getRespJson());
            dingDingParams.setErrorMsg(dunResponseVO.getErrorMsg());
            dingDingParams.setAtMobileList(SendRebootUtil.ModuleEnum.getMobiles(null, SendRebootUtil.ModuleEnum.INTERNAL));
            SendRebootUtil.sendDingNotice(dingDingParams);
            throw new BusinessException(dunResponseVO.getErrorMsg());
        }
    }
}
