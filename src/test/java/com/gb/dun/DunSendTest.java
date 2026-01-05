package com.gb.dun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gb.SpringTest;
import com.gb.bean.GongBaoConfig;
import com.gb.bean.RabbitConfig;
import com.gb.dun.entity.dto.QuestionnaireDTO;
import com.gb.dun.entity.vo.QuestionnaireRespVO;
import com.gb.dun.service.QuestionnaireService;
import com.gb.mq.trade.PaySuccessNotifyEvent;
import com.gb.trade.entity.bo.BasePayPlatformBO;
import com.gb.utils.JsonUtil;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @ClassName DunSendTest
 * @Author yyl
 * @Date 2022-09-06 14:09:08
 * @Description DunSendTest
 * @Version 1.0
 */
public class DunSendTest extends SpringTest {

    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send() throws Exception {
        QuestionnaireDTO questionnaireDTO = new QuestionnaireDTO();
        questionnaireDTO.setInsuranceCode("C036");
        questionnaireDTO.setPolicyHolderAttrName("3");
        questionnaireDTO.setPolicyHolderTypeName("1");
        questionnaireDTO.setProductAttributeName("2");
        questionnaireDTO.setProductSpecName("2");
        questionnaireDTO.setRiskCategoryId("3373752497105836035L");
        QuestionnaireRespVO questionnaireVO = questionnaireService.queryQuestionnaireContent(questionnaireDTO);
        System.out.println(JsonUtil.json(questionnaireVO));
    }

    @Test
    public void send1() throws Exception {
        String ss="{\"oppAccount\":\"\",\"payFinishTime\":\"20221130144703\",\"remark\":\"L2X56W\",\"bankName\":\"平安银行杭州分行营业部\",\"payAccount\":\"4560913116376287132\",\"openBranch\":\"0\",\"payNo\":\"20221130144703364P10000000054296\",\"feeAmount\":0,\"payAmount\":0.01,\"oppAccountName\":\"工保科技（浙江）有限公司\",\"transCode\":\"N000001\",\"payAccountName\":\"工保网开发环境账户\",\"channelAccountCode\":\"10000000113899DAZ58\",\"payStatus\":\"01\",\"channelCode\":\"999999\"}";
        PaySuccessNotifyEvent paySuccessNotifyEvent=JsonUtil.bean(ss, PaySuccessNotifyEvent.class);

        JSON.toJSONString(paySuccessNotifyEvent);

        JSON.toJSONString(paySuccessNotifyEvent,SerializerFeature.MapSortField);
        //rabbitTemplate.convertAndSend(RabbitConfig.TRADE_CREATE_PAY_ORDER_NOTICE, paySuccessNotifyEvent);
    }




}
