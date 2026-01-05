package com.gb.trade.log.service;

import com.gb.trade.log.entity.CreatePayOrderLog;
import com.gb.trade.log.entity.CreatePayOrderNotifyLog;

/**
 * 创建支付单
 * @author lixinyao
 * @DateTime 2021/11/05  上午11:09
 */
public interface CreatePayOrderNotifyLogService {
    /**
     * 创建支付单回调
     *
     * @param createPayOrderNotifyLog
     */
    void insert(CreatePayOrderNotifyLog createPayOrderNotifyLog);

}
