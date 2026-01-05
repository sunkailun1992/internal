package com.gb.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gb.aliyun.dingding.SendRebootUtil;
import com.gb.bean.GongBaoConfig;
import com.gb.bean.RabbitConfig;
import com.gb.bean.TradeOrderConfig;
import com.gb.constants.TradePlatConstants;
import com.gb.mq.trade.PaySuccessNotifyEvent;
import com.gb.trade.entity.bo.BasePayPlatformBO;
import com.gb.trade.entity.bo.CreatePayOrderBO;
import com.gb.trade.entity.dto.PaymentDTO;
import com.gb.trade.entity.vo.BasePayPlatformVO;
import com.gb.trade.entity.vo.CreatePayOrderVO;
import com.gb.trade.log.entity.CreatePayOrderLog;
import com.gb.trade.log.entity.CreatePayOrderNotifyLog;
import com.gb.trade.log.service.CreatePayOrderLogService;
import com.gb.trade.log.service.CreatePayOrderNotifyLogService;
import com.gb.trade.service.CreatePayOrderService;
import com.gb.trade.utils.RSAUtil;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.JsonUtil;
import com.gb.utils.OkhttpUtils;
import com.gb.utils.enumeration.HttpType;
import com.gb.utils.enumeration.HttpWay;
import com.gb.utils.exception.BusinessException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.gb.constants.TradePlatConstants.*;
import static com.gb.constants.TransCode.TRA_TRANSCODE_TRADE_CREATE_PAY;


/**
 * @author lixinyao
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class CreatePayOrderServiceImpl implements CreatePayOrderService {

	private RabbitTemplate rabbitTemplate;
	private TradeOrderConfig tradeOrderConfig;
	private CreatePayOrderLogService createPayOrderLogService;
	private CreatePayOrderNotifyLogService createPayOrderNotifyLogService;

	/**
	 * 创建支付订单
	 *
	 * @param createPayOrder
	 * @return
	 */
	@Override
	public CreatePayOrderVO createPayOrder(CreatePayOrderBO createPayOrder) {
		CreatePayOrderLog createPayOrderLog = new CreatePayOrderLog();
		//1。组装参数
		PaymentDTO bo = getObject(createPayOrder);
		//2。获取签名
		String encrypt = RSAUtil.encrypt(JsonUtil.json(bo), GongBaoConfig.privateKey);
		//3。发送获取数据
		try {
			BasePayPlatformBO basePayPlatformBO = new BasePayPlatformBO();
			basePayPlatformBO.setSign(encrypt);
			basePayPlatformBO.setSignType(SIGN_TYPE);
			basePayPlatformBO.setVersion(VERSION);
			basePayPlatformBO.setCustNo(tradeOrderConfig.getCustNo());
			basePayPlatformBO.setPlatNo(tradeOrderConfig.getPlatNo());
			basePayPlatformBO.setBody(JSON.parseObject(JsonUtil.json(bo)));
			Request.Builder builder = new Request.Builder();
			createPayOrderLog.setBasePayPlatformBO(JsonUtil.json(basePayPlatformBO));
			log.debug("【交易平台-创建交易支付订单参数】：{}", JsonUtil.json(basePayPlatformBO));
			String json = OkhttpUtils.send(builder, HttpWay.POST, tradeOrderConfig.getCreatePayOrderUrl(), JsonUtil.json(basePayPlatformBO), HttpType.JSON).string();
			log.debug("【交易平台-{}】响应结果：{}", "创建交易支付订单", json);
			BasePayPlatformVO resultMap = JSONObject.parseObject(json, BasePayPlatformVO.class);
			createPayOrderLog.setBasePayPlatformVO(json);
			if (resultMap == null) {
				throw new BusinessException("【交易平台响应结果】" + "创建交易支付订单" + "，交易平台响应为空！");
			}
			if (!StringUtils.equals(resultMap.getRespCode(), SUCCESS_CODE)) {
				throw new BusinessException("交易平台响应失败:" + resultMap.getRespMsg());
			}
			return GeneralConvertor.convertor(resultMap.getBody(),CreatePayOrderVO.class);
		} catch (Exception e) {
			log.error("调用交易平台创建支付订单异常，{}", e.getMessage());
			createPayOrderLog.setMsg(e.getMessage());
			throw new BusinessException("调用交易平台数据异常");
		} finally {
			createPayOrderLog.setOrderNo(createPayOrder.getOrderNo());
			createPayOrderLog.setCreatePayOrder(JsonUtil.json(createPayOrder));
			createPayOrderLog.setCreateDateTime(LocalDateTime.now().plusHours(8));
			createPayOrderLogService.insert(createPayOrderLog);
			SendRebootUtil.DingDingParams dingDingParams = new SendRebootUtil.DingDingParams();
			dingDingParams.setReqObject(JsonUtil.bean(createPayOrderLog.getBasePayPlatformBO(),BasePayPlatformBO.class));
			dingDingParams.setReqTitle("交易平台-创建支付订单");
			dingDingParams.setRespJson(createPayOrderLog.getBasePayPlatformVO());
			dingDingParams.setErrorMsg(createPayOrderLog.getMsg());
			dingDingParams.setAtMobileList(Arrays.asList("@18667943303"));
			dingDingParams.setRespTitle("交易平台响应");
			SendRebootUtil.sendDingNotice(dingDingParams);
		}
	}

	@Override
	public Map<String,Object> createPayOrderNotify(BasePayPlatformBO payOrder) {
		//响应实体类
		Map<String,Object> basePayPlatformVO = new HashMap<>(4);
		CreatePayOrderNotifyLog logInfo = new CreatePayOrderNotifyLog();
		logInfo.setReqJson(JsonUtil.json(payOrder));
		PaySuccessNotifyEvent paySuccessNotifyEvent = JsonUtil.bean(JsonUtil.json(payOrder.getBody()), PaySuccessNotifyEvent.class);
		logInfo.setPayNo(paySuccessNotifyEvent == null ? null : paySuccessNotifyEvent.getPayNo());
		//Boolean aBoolean = RSAUtil.check(JSON.toJSONString(payOrder.getBody(), SerializerFeature.MapSortField), payOrder.getSign(), tradeOrderConfig.getPublicKey());
//		if (!aBoolean) {
//			basePayPlatformVO.put(TradePlatConstants.SUCCESS,false);
//			basePayPlatformVO.put("msg","验签失败");
//			return basePayPlatformVO;
//		}
		try {
			log.debug("发送用户支付成功mq，{}", JsonUtil.json(payOrder.getBody()));
			//发送mq
			rabbitTemplate.convertAndSend(RabbitConfig.TRADE_CREATE_PAY_ORDER_NOTICE, paySuccessNotifyEvent);
			basePayPlatformVO.put("code",SUCCESS_RESP_CODE);
			basePayPlatformVO.put(TradePlatConstants.SUCCESS,true);
			return basePayPlatformVO;
		} catch (Exception e) {
			logInfo.setMsg(e.getMessage());
			log.error("用户支付成功回调失败，mess={}", e.getMessage());
			throw e;
		} finally {
			logInfo.setMqJson(JsonUtil.json(paySuccessNotifyEvent));
			logInfo.setRespJson(JsonUtil.json(basePayPlatformVO));
			logInfo.setCreateDateTime(LocalDateTime.now().plusHours(8L));
			createPayOrderNotifyLogService.insert(logInfo);
			SendRebootUtil.DingDingParams dingDingParams = new SendRebootUtil.DingDingParams();
			dingDingParams.setReqObject(payOrder);
			dingDingParams.setReqTitle("交易平台-用户支付成功回调");
			dingDingParams.setRespJson(JsonUtil.json(basePayPlatformVO));
			dingDingParams.setErrorMsg(logInfo.getMsg());
			dingDingParams.setAtMobileList(Arrays.asList("@18667943303"));
			dingDingParams.setRespTitle("工保网响应");
			SendRebootUtil.sendDingNotice(dingDingParams);

		}
	}

	private PaymentDTO getObject(CreatePayOrderBO createPayOrder) {
		PaymentDTO payment = new PaymentDTO();
		payment.setChannelCode(tradeOrderConfig.getChannelCode());
		payment.setChannelAccountCode(tradeOrderConfig.getChannelAccountCode());
		payment.setFeeAmount(new BigDecimal("0.00"));
		payment.setGoodsInfo(createPayOrder.getGoodsInfo());
		payment.setOnlineFlag(OFFLINEFLAG);
		payment.setOrderNo(createPayOrder.getOrderNo());
		payment.setRemarks(createPayOrder.getRemarks());
		payment.setNotifyUrl(tradeOrderConfig.getDefaultNoticeUrl());
		payment.setReturnUrl(tradeOrderConfig.getDefaultNoticeUrl());
		payment.setTradeAmount(new BigDecimal(createPayOrder.getTradeAmount()).setScale(2, RoundingMode.HALF_UP));
		payment.setTradeInfo(createPayOrder.getTradeInfo());
		payment.setTransCode(TRA_TRANSCODE_TRADE_CREATE_PAY);
		return payment;
	}


}
