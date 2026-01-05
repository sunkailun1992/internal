package com.gb.trade.log.mapper;

import com.gb.trade.log.entity.CreatePayOrderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Author: lixinyao
 * @Date: 2021/11/05
 */
@Repository
public class CreatePayOrderLogMapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入订单日志
     *
     * @param record
     */
    public void insert(CreatePayOrderLog record ) {
        mongoTemplate.insert(record);
    }

}
