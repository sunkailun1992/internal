package com.gb.trade.log.service.impl;

import com.gb.trade.log.entity.CreatePayOrderLog;
import com.gb.trade.log.entity.TransFerSubmitLog;
import com.gb.trade.log.mapper.CreatePayOrderLogMapper;
import com.gb.trade.log.mapper.TransFerSubmitLogMapper;
import com.gb.trade.log.service.CreatePayOrderLogService;
import com.gb.trade.log.service.TransFerSubmitLogService;
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
public class TransFerSubmitLogServiceImpl implements TransFerSubmitLogService {

    private TransFerSubmitLogMapper transFerSubmitLogMapper;

    /**
     * 新增操作日志
     *
     * @param transFerSubmitLog
     */
    @Async
    @Override
    public void insert(TransFerSubmitLog transFerSubmitLog ) {
        transFerSubmitLogMapper.insert(transFerSubmitLog);
    }


}
