package com.gb.trade.log.mapper;

import com.gb.trade.log.entity.TransFerSubmitLog;
import com.gb.trade.log.entity.TransFerSubmitNotifyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Author: lixinyao
 * @Date: 2021/11/05
 */
@Repository
public class TransFerSubmitNotifyLogMapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入订单日志
     *
     * @param record
     */
    public void insert(TransFerSubmitNotifyLog record ) {
        mongoTemplate.insert(record);
    }

}
