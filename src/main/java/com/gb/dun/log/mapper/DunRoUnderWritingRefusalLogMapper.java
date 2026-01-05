package com.gb.dun.log.mapper;

import cn.hutool.core.date.DateUtil;
import com.gb.dun.log.entity.DunRoUnderwritingRefusalLog;
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
 * 承保结果信息操作日志
 * @author sunkailun
 * @DateTime 2018/4/23  下午3:07
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@Repository
public class DunRoUnderWritingRefusalLogMapper {
    /**
     * 由springboot自动注入，默认配置会产生mongoTemplate这个bean
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 操作日志,插入数据
     *
     * @param dunRoUnderwritingRefusalLog:
     * @return void
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:22
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public void insert(DunRoUnderwritingRefusalLog dunRoUnderwritingRefusalLog) {
        mongoTemplate.insert(dunRoUnderwritingRefusalLog);
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
    public DunRoUnderwritingRefusalLog select(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)), DunRoUnderwritingRefusalLog.class);
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
    public List<DunRoUnderwritingRefusalLog> selectAll() {
        return mongoTemplate.findAll(DunRoUnderwritingRefusalLog.class);
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
        Query query = new Query(Criteria.where("createDateTime").gte(DateUtil.offsetHour(createDateStart, 8)).lte(DateUtil.offsetHour(createDateEnd, 8)));
        mongoTemplate.remove(query, DunRoUnderwritingRefusalLog.class);
    }

    /**
     * 操作日志，集合查询
     *
     * @param dunRoUnderwritingRefusalLog:
     * @param pageable:
     * @return java.util.List<com.entity.mongodb.user.OperationLog>
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:25
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public List<DunRoUnderwritingRefusalLog> selectList(Date createDateStart, Date createDateEnd, DunRoUnderwritingRefusalLog dunRoUnderwritingRefusalLog, Pageable pageable) {
        Criteria criteria = new Criteria();
        //操作人名称查询
        if (StringUtils.isNotBlank(dunRoUnderwritingRefusalLog.getCreateName())) {
            criteria.and("createName").is(dunRoUnderwritingRefusalLog.getCreateName());
        }
        //投保单号查询
        if (StringUtils.isNotBlank(dunRoUnderwritingRefusalLog.getPolicyNumber())) {
            criteria.and("policyNumber").is(dunRoUnderwritingRefusalLog.getPolicyNumber());
        }
        //风控单号查询
        if (StringUtils.isNotBlank(dunRoUnderwritingRefusalLog.getRiskControlNumber())) {
            criteria.and("riskControlNumber").is(dunRoUnderwritingRefusalLog.getRiskControlNumber());
        }

        //时间区间查询
        if (createDateStart != null && createDateEnd != null) {
            criteria.and("createDateTime").gte(DateUtil.offsetHour(createDateStart, 8)).lte(DateUtil.offsetHour(createDateEnd, 8));
        }
        //模糊查询
        Query query = new Query(criteria).with(pageable);
        List<DunRoUnderwritingRefusalLog> list = mongoTemplate.find(query, DunRoUnderwritingRefusalLog.class);
        return list;
    }

    /**
     * 操作日志，查询总数
     *
     * @param dunRoUnderwritingRefusalLog:
     * @param pageable:
     * @return java.util.List<com.entity.mongodb.user.OperationLog>
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:25
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public Long selectTotal(Date createDateStart, Date createDateEnd, DunRoUnderwritingRefusalLog dunRoUnderwritingRefusalLog, Pageable pageable) {
        Criteria criteria = new Criteria();
        //操作人名称查询
        if (StringUtils.isNotBlank(dunRoUnderwritingRefusalLog.getCreateName())) {
            criteria.and("createName").is(dunRoUnderwritingRefusalLog.getCreateName());
        }
        //投保单号查询
        if (StringUtils.isNotBlank(dunRoUnderwritingRefusalLog.getPolicyNumber())) {
            criteria.and("policyNumber").is(dunRoUnderwritingRefusalLog.getPolicyNumber());
        }
        //风控单号查询
        if (StringUtils.isNotBlank(dunRoUnderwritingRefusalLog.getRiskControlNumber())) {
            criteria.and("riskControlNumber").is(dunRoUnderwritingRefusalLog.getRiskControlNumber());
        }

        //时间区间查询
        if (createDateStart != null && createDateEnd != null) {
            criteria.and("createDateTime").gte(DateUtil.offsetHour(createDateStart, 8)).lte(DateUtil.offsetHour(createDateEnd, 8));
        }
        //模糊查询
        Query query = new Query(criteria);
        Long total = mongoTemplate.count(query, DunRoUnderwritingRefusalLog.class);
        return total;
    }

    /**
     * 操作日志，批量新增
     *
     * @param dunRoUnderwritingRefusalLog:
     * @return void
     * @author sunkailun
     * @DateTime 2018/5/23  上午10:25
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    public void insertAll(List<DunRoUnderwritingRefusalLog> dunRoUnderwritingRefusalLog) {
        mongoTemplate.insertAll(dunRoUnderwritingRefusalLog);
    }
}
