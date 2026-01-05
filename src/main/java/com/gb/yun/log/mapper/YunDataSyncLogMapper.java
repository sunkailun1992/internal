package com.gb.yun.log.mapper;

import cn.hutool.core.date.DateUtil;
import com.gb.yun.log.entity.YunDataSyncLog;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * 云数据同步操作日志Mapper
 * @author sunx
 * @DateTime 2018/4/23  下午3:07
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@Repository
@Setter(onMethod_ = {@Autowired})
public class YunDataSyncLogMapper {
    /**
     * 由springboot自动注入，默认配置会产生mongoTemplate这个bean
     */
    private MongoTemplate mongoTemplate;

    /**
     * 操作日志,插入数据
     *
     * @param yunDataSyncLog:
     * @return void
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:22
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public void insert(YunDataSyncLog yunDataSyncLog) {
        mongoTemplate.insert(yunDataSyncLog);
    }

    /**
     * 操作日志,id查询
     *
     * @param id:
     * @return com.entity.mongodb.user.OperationLog
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:23
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public YunDataSyncLog select(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)), YunDataSyncLog.class);
    }

    /**
     * 操作日志,查询所有记录
     *
     * @param :
     * @return java.util.List<com.entity.mongodb.user.OperationLog>
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:25
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public List<YunDataSyncLog> selectAll() {
        return mongoTemplate.findAll(YunDataSyncLog.class);
    }

    /**
     * 操作日志,日期区间删除
     *
     * @return void
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:25
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public void delete(Date createDateStart, Date createDateEnd) {
        Query query = new Query(Criteria.where("createDate").gte(DateUtil.offsetHour(createDateStart, 8)).lte(DateUtil.offsetHour(createDateEnd, 8)));
        mongoTemplate.remove(query, YunDataSyncLog.class);
    }

    /**
     * 操作日志，集合查询
     *
     * @param createDateStart:
     * @param createDateEnd:
     * @param pageable:
     * @return java.util.List<com.entity.mongodb.user.OperationLog>
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:25
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public List<YunDataSyncLog> selectList(Date createDateStart, Date createDateEnd, YunDataSyncLog yunDataSyncLog, Pageable pageable) {
        Criteria criteria = new Criteria();
        //操作人名称查询
        if (StringUtils.isNotBlank(yunDataSyncLog.getCreateName())) {
            criteria.and("createName").is(yunDataSyncLog.getCreateName());
        }
        //投保内容ID查询
        if (StringUtils.isNotBlank(yunDataSyncLog.getContentId())) {
            criteria.and("contentId").is(yunDataSyncLog.getContentId());
        }
        //时间区间查询
        if (createDateStart != null && createDateEnd != null) {
            criteria.and("createDateTime").gte(DateUtil.offsetHour(createDateStart, 8)).lte(DateUtil.offsetHour(createDateEnd, 8));
        }
        //模糊查询
        Query query = new Query(criteria).with(pageable);
        List<YunDataSyncLog> list = mongoTemplate.find(query, YunDataSyncLog.class);
        return list;
    }

    /**
     * 操作日志，查询总数
     *
     * @param yunDataSyncLog:
     * @param pageable:
     * @return java.util.List<com.entity.mongodb.user.OperationLog>
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:25
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public Long selectTotal(Date createDateStart, Date createDateEnd, YunDataSyncLog yunDataSyncLog, Pageable pageable) {
        Criteria criteria = new Criteria();
        //操作人名称查询
        if (StringUtils.isNotBlank(yunDataSyncLog.getCreateName())) {
            criteria.and("createName").is(yunDataSyncLog.getCreateName());
        }
        //投保内容ID查询
        if (StringUtils.isNotBlank(yunDataSyncLog.getContentId())) {
            criteria.and("contentId").is(yunDataSyncLog.getContentId());
        }
        //时间区间查询
        if (createDateStart != null && createDateEnd != null) {
            criteria.and("createDateTime").gte(DateUtil.offsetHour(createDateStart, 8)).lte(DateUtil.offsetHour(createDateEnd, 8));
        }
        //模糊查询
        Query query = new Query(criteria);
        Long total = mongoTemplate.count(query, YunDataSyncLog.class);
        return total;
    }

    /**
     * 操作日志，批量新增
     *
     * @param yunDataSyncLog:
     * @return void
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:25
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public void insertAll(List<YunDataSyncLog> yunDataSyncLog) {
        mongoTemplate.insertAll(yunDataSyncLog);
    }
}
