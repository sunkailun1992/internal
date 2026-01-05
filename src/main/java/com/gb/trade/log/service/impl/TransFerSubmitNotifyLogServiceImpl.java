package com.gb.trade.log.service.impl;

import com.gb.trade.log.entity.TransFerSubmitLog;
import com.gb.trade.log.entity.TransFerSubmitNotifyLog;
import com.gb.trade.log.mapper.TransFerSubmitLogMapper;
import com.gb.trade.log.mapper.TransFerSubmitNotifyLogMapper;
import com.gb.trade.log.service.TransFerSubmitLogService;
import com.gb.trade.log.service.TransFerSubmitNotifyLogService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: lixinyao
 * @Date: 2021/11/05
 */
@Service
@Setter(onMethod_ = {@Autowired})
public class TransFerSubmitNotifyLogServiceImpl implements TransFerSubmitNotifyLogService {

    private TransFerSubmitNotifyLogMapper transFerSubmitNotifyLogMapper;

    /**
     * 新增操作日志
     *
     * @param transFerSubmitNotifyLog
     */
    @Async
    @Override
    public void insert(TransFerSubmitNotifyLog transFerSubmitNotifyLog ) {
        transFerSubmitNotifyLogMapper.insert(transFerSubmitNotifyLog);
    }


}
