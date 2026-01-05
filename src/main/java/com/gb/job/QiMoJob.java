package com.gb.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.gb.common.constant.CommonConstant;
import com.gb.common.constant.UrlConstant;
import com.gb.qimo.service.QiMoService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 七陌云JOB
 *
 * @author lijh
 * @date 2021/6/2
 */
@Component
@Slf4j
public class QiMoJob extends JavaProcessor {
    @Resource
    private QiMoService qiMoService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public ProcessResult process(JobContext jobContext) {
        //设置每次执行时间为每天凌晨1点，
        LocalDate now = LocalDate.now();
        try {
            log.debug("拉取七陌云客户资料开始,当前日期:" + LocalDate.now());
            //获取上次拉取数据的时间
            String lastTime = stringRedisTemplate.opsForValue().get(CommonConstant.QIMOYUN_TIME);
            if (Objects.isNull(lastTime)) {
                //默认获取三个月前数据
                lastTime = LocalDate.now().minusMonths(3).toString();
            }
            Map<String, Object> params = Maps.newHashMap();
            //拉取上次时间 - 当前时间之间的数据
            //获取数据库版本号
            Map<String, Object> templateMap = qiMoService.sendQimoyun(Maps.newHashMap(), UrlConstant.QIMO_GET_TEMPLATE_URL);
            Map<String, Object> resultMap = JSON.parseObject(String.valueOf(templateMap.get("data")), Map.class);
            params.put("version", resultMap.get("version"));
            params.put("endCreateTime", LocalDate.now().toString());
            params.put("beginCreateTime", lastTime);
            log.debug("拉取参数={}", params);
            Map<String, Object> customerInfo = qiMoService.sendQimoyun(params, UrlConstant.QIMO_SELECT_URL);
            qiMoService.saveCustomerInfo(customerInfo);
        } catch (Exception e) {
            log.error("拉取七陌云数据异常" + e);
        } finally {
            stringRedisTemplate.opsForValue().set(CommonConstant.QIMOYUN_TIME, now.toString(), 7, TimeUnit.DAYS);
            log.debug("拉取七陌云客户资料完成,当前日期:" + now);
        }
        return new ProcessResult(true);
    }
}
