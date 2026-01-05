package com.gb.job;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.gb.yunv2.service.YunV2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @className: com.gb.job-> PullYunV2Job
 * @author: 王一飞
 * @createDate: 2022-01-10 11:00 上午
 * @description: 拉取 云信息 并更新 工保网数据
 */
@Component
@Slf4j
public class PullYunV2Job extends JavaProcessor {
    @Resource
    private YunV2Service yunV2Service;

    /**
     * @createAuthor: 王一飞
     * @title: 定时任务：拉取工保云数据，同步工保网
     * @createDate: 2022/1/10 3:51 下午
     * @description:
     * @return:
     */
    @Override
    public ProcessResult process(JobContext jobContext) {

        yunV2Service.pullYunV2();

        return new ProcessResult(true);
    }

}
