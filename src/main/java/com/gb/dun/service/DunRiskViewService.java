package com.gb.dun.service;


import com.gb.dun.entity.bo.RiskOrderBO;
import com.gb.dun.entity.vo.RiskOrderVO;
import com.gb.mq.dun.DunRiskSysReviewEvent;

/**
 * 工保盾风控审核业务处理接口
 * @author: sunx
 * @Date: 2021/03/22 23:30
 * @descript: 工保盾风控审核业务处理接口
 */
public interface DunRiskViewService {


    /**
     * 组装风控审核请求订单VO
     * @param riskOrderBO 风控审核订单BO
     * @return map
     */
    RiskOrderVO organizeRiskOrderVO(RiskOrderBO riskOrderBO);

    /**
     * 组装RiskOrderBO
     * @param riskSysReviewEvent: 风控推送事件类
     * @author rangyang
     * @since 2021-03-18
     * @return RiskOrderBO
     */
    RiskOrderBO buildRiskOrderBO(DunRiskSysReviewEvent riskSysReviewEvent);




}
