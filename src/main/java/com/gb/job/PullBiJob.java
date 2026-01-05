package com.gb.job;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.gb.qimo.service.DbOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Bi定时任务
 *
 * @author lijh
 * @date 2021/7/7
 */
@Component
@Slf4j
public class PullBiJob extends JavaProcessor {
    @Resource
    private DbOperation dbOperation;

    @Override
    public ProcessResult process(JobContext jobContext) {
        log.debug("定时拉取BI数据开始");
        return new ProcessResult(dbOperation.conn());

    }
}
