package com.gb.trade.log.service.impl;

import com.gb.trade.log.entity.CreatePayOrderLog;
import com.gb.trade.log.mapper.CreatePayOrderLogMapper;
import com.gb.trade.log.service.CreatePayOrderLogService;
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
public class CreatePayOrderLogServiceImpl implements CreatePayOrderLogService {

    private CreatePayOrderLogMapper createPayOrderLogMapper;

    /**
     * 新增操作日志
     *
     * @param createPayOrderLog
     */
    @Async
    @Override
    public void insert(CreatePayOrderLog createPayOrderLog ) {
        createPayOrderLogMapper.insert(createPayOrderLog);
    }


}
