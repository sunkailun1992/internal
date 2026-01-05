package com.gb.yun.log.service;

import com.gb.yun.log.entity.YunDataSyncLog;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * 云数据同步操作日志接口
 * @author sunx
 * @DateTime 2018/7/16  上午11:09
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
public interface YunDataSyncLogService {
    /**
     * 操作日志，插入
     * @author      sunx
     * @DateTime    2018/7/16  上午11:12
     * @email       376253703@qq.com
     * @phone       13777579028
     * @param log:
     * @return      void
     */
    void insert(YunDataSyncLog log);

    /**
     * 操作日志，单条查询
     * @author      sunx
     * @DateTime    2018/7/16  上午11:12
     * @email       376253703@qq.com
     * @phone       13777579028
     * @param id: 
     * @return      com.entity.mongodb.log.Log
     */
    YunDataSyncLog select(String id);

    /**
     * 时间删除
     * @author      sunx
     * @DateTime    2018/7/16  上午11:12
     * @email       376253703@qq.com
     * @phone       13777579028
     * @param createDateStart:
     * @param createDateEnd:
     * @return      void
     */
    void delete(Date createDateStart, Date createDateEnd);

}
