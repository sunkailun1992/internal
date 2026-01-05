package com.gb.trade.log.service;

import com.gb.trade.log.entity.TransFerSubmitLog;
import com.gb.trade.log.entity.TransFerSubmitNotifyLog;

/**
 * 创建支付单
 * @author lixinyao
 * @DateTime 2021/11/05  上午11:09
 */
public interface TransFerSubmitNotifyLogService {
    /**
     * 创建支付单
     *
     * @param transFerSubmitNotifyLog
     */
    void insert(TransFerSubmitNotifyLog transFerSubmitNotifyLog);

}
