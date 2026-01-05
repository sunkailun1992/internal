package com.gb.dun.aop;

import com.gb.dun.entity.bo.PolicyInfo;
import com.gb.mq.dun.*;
import com.gb.utils.DataSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 盾数据源切面
 * </p>
 *
 * @author sunx
 * @since 2021-12-26
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class DataSourceAop {

    /**
     * 对所有DynamicDataSource的注解类实现切点
     */
    @Pointcut("within(@com.gb.dun.annotation.DynamicDataSource *)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        DunPushEvent event = (DunPushEvent) joinPoint.getArgs()[0];
        String castId = getCastId(event);
        DataSourceUtil.setDataSource(castId);
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        DataSourceUtil.clear();
    }

    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrow(JoinPoint joinPoint, Throwable e) {
        DataSourceUtil.clear();
    }

    /**
     * 获得投保ID
     *
     * @param event 盾MQ推送
     * @author sunx
     * @since 2021-03-18
     */
    private String getCastId(DunPushEvent event) {
        String castId = StringUtils.EMPTY;
        switch (event.getPushType()) {
            case DUN_RISKVIEW_TYPE:
                DunRiskSysReviewEvent riskSysReviewEvent = (DunRiskSysReviewEvent) event;
                castId = riskSysReviewEvent.getCastInsuranceId();
                break;
            case DUN_ASSOCIATE_ORDER_TYPE:
                DunRoAssociateOrderEvent roAssociateOrderEvent = (DunRoAssociateOrderEvent) event;
                castId = roAssociateOrderEvent.getPolicyNumber();
                break;
            case DUN_UNDERWRITING_TYPE:
                DunRoUnderwritingRefusalEvent roUnderwritingRefusalEvent = (DunRoUnderwritingRefusalEvent) event;
                castId = roUnderwritingRefusalEvent.getPolicyNumber();
                break;
            case DUN_SURRENDER_TYPE:
                DunRoSurrenderRecordEvent roSurrenderRecordEvent = (DunRoSurrenderRecordEvent) event;
                castId = roSurrenderRecordEvent.getPolicyNumber();
                break;
            case DUN_INSURANCE_INFO_TYPE:
                DunInsuranceInfoEvent dunInsuranceInfoEvent = (DunInsuranceInfoEvent)event;
                castId = dunInsuranceInfoEvent.getCastInsuranceId();
                break;
            case DUN_UNDERWRITING_REFUSAL_RESULTS_TYPE:
                DunRoUnderwritingRefusalResultEvent dunRoUnderwritingRefusalResultEvent = (DunRoUnderwritingRefusalResultEvent)event;
                castId = dunRoUnderwritingRefusalResultEvent.getCastInsuranceId();
                break;
            default:
                break;
        }
        return castId;
    }
}
