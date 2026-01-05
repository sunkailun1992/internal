package com.gb.yun.log.service.impl;

import com.gb.yun.log.entity.YunDataSyncLog;
import com.gb.yun.log.mapper.YunDataSyncLogMapper;
import com.gb.yun.log.service.YunDataSyncLogService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *  云数据同步操作日志实现类
 * @author sunx
 * @DateTime 2018/7/16  上午11:09
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@Service
@Setter(onMethod_ = {@Autowired})
public class YunDataSyncLogServiceImpl implements YunDataSyncLogService {

    private YunDataSyncLogMapper yunDataSyncLogMapper;

    /**
     * 操作日志，新增
     *
     * @param log:
     * @return void
     * @author sunx
     * @DateTime 2018/7/16  上午11:15
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @Async
    @Override
    public void insert(YunDataSyncLog log) {
        yunDataSyncLogMapper.insert(log);
    }


    /**
     * 操作日志，单条查询
     *
     * @param id:
     * @return com.entity.mongodb.log.Log
     * @author sunx
     * @DateTime 2018/7/16  上午11:15
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @Override
    public YunDataSyncLog select(String id) {
        return yunDataSyncLogMapper.select(id);
    }

    /**
     * 操作日志，删除
     *
     * @param createDateStart:
     * @param createDateEnd:
     * @return void
     * @author sunx
     * @DateTime 2018/7/16  上午11:16
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @Override
    public void delete(Date createDateStart, Date createDateEnd) {
        yunDataSyncLogMapper.delete(createDateStart, createDateEnd);
    }

}
