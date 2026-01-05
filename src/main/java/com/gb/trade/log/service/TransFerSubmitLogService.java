package com.gb.trade.log.service;

import com.gb.trade.log.entity.CreatePayOrderLog;
import com.gb.trade.log.entity.TransFerSubmitLog;

/**
 * 创建支付单
 * @author lixinyao
 * @DateTime 2021/11/05  上午11:09
 */
public interface TransFerSubmitLogService {
    /**
     * 创建支付单
     *
     * @param transFerSubmitLog
     */
    void insert(TransFerSubmitLog transFerSubmitLog);

}
