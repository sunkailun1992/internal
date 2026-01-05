package com.gb.mq;

import com.alibaba.fastjson.JSON;
import com.gb.bean.RabbitConfig;
import com.gb.dun.service.DunPushService;
import com.gb.mq.dun.DunPushEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 盾推送监听接收
 *
 * @author sunkailun
 * @DateTime 2021/3/4  10:52 上午
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@Slf4j
@Component
@Setter(onMethod_ = {@Autowired})
@RabbitListener(queues = {RabbitConfig.DUN_RISKREVIEW_Q, RabbitConfig.DUN_ASSOCIATEORDER_Q, RabbitConfig.DUN_SURRENDER_Q, RabbitConfig.DUN_UNDERWRITING_Q,
        RabbitConfig.DUN_INSURANCE_NOTICE__Q, RabbitConfig.DUN_UNDERWRITING_RESULTS__Q})
public class DunPushListener {

    private DunPushService dunPushService;

    @RabbitHandler
    public void riskReviewHandler(DunPushEvent dunPushEvent) {
        log.debug("收到对接盾的MQ消息：【消息内容：{}】", JSON.toJSONString(dunPushEvent));
        dunPushService.dealBusiness(dunPushEvent);
        log.debug("对接盾的MQ消息结束。【消息内容：{}】", JSON.toJSONString(dunPushEvent));
    }
}

