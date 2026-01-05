package com.gb.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gb.aliyun.dingding.SendRebootUtil;
import com.gb.bean.GongBaoConfig;
import com.gb.bean.RabbitConfig;
import com.gb.bean.TradeOrderConfig;
import com.gb.constants.TradePlatConstants;
import com.gb.mq.trade.RefundNotifyEvent;
import com.gb.trade.entity.bo.*;
import com.gb.trade.entity.dto.TransferSubmitDTO;
import com.gb.trade.entity.vo.BasePayPlatformVO;
import com.gb.mq.trade.TransferNotifyEvent;
import com.gb.trade.entity.vo.TransferSubmitVO;
import com.gb.trade.log.entity.TransFerSubmitLog;
import com.gb.trade.log.entity.TransFerSubmitNotifyLog;
import com.gb.trade.log.service.TransFerSubmitLogService;
import com.gb.trade.log.service.TransFerSubmitNotifyLogService;
import com.gb.trade.service.TransFerSubmitService;
import com.gb.trade.utils.RSAUtil;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.JsonUtil;
import com.gb.utils.OkhttpUtils;
import com.gb.utils.RsaUtils;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.gb.constants.TradePlatConstants.*;
import static com.gb.constants.TransCode.TRA_TRANSCODE_TRADE_PAY_RELATE_CREATE;
import static com.gb.constants.TransCode.TRA_TRANSCODE_TRANSFER_BY_CHANNEL_ACCOUNT_CODE;


/**
 * @author lixinyao
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TransFerSubmitServiceImpl implements TransFerSubmitService {

	private RabbitTemplate rabbitTemplate;
	private TradeOrderConfig tradeOrderConfig;
	private TransFerSubmitLogService transFerSubmitLogService;
	private TransFerSubmitNotifyLogService transFerSubmitNotifyLogService;

	private TransferSubmitDTO getObject(TransferSubmitBO transferSubmitBO) {
		TransferSubmitDTO transfer = new TransferSubmitDTO();
		transfer.setChannelAccountCode(tradeOrderConfig.getChannelAccountCode());
		transfer.setGoodsInfo(transferSubmitBO.getGoodsInfo());
		transfer.setFeeAmount(new BigDecimal("0.00"));
		transfer.setNotifyUrl(tradeOrderConfig.getTransferNoticeUrl());
		//todo 设置退汇路径
		transfer.setReturnUrl(tradeOrderConfig.getDefaultNoticeUrl());
		transfer.setOnlineFlag(OFFLINEFLAG);
		transfer.setRemarks(transferSubmitBO.getRemarks());
		transfer.setPayAccount(transferSubmitBO.getPayAccount());
		transfer.setPayAccountName(transferSubmitBO.getPayAccountName());
		transfer.setOppAccountName(transferSubmitBO.getOppAccountName());
		transfer.setOppAccount(transferSubmitBO.getOppAccount());
		transfer.setOppBankName(transferSubmitBO.getOppBankName());
		transfer.setOppBankNo(transferSubmitBO.getOppBankNo());
		transfer.setTradeAmount(new BigDecimal(transferSubmitBO.getTradeAmount()).setScale(2, RoundingMode.HALF_UP));
		transfer.setTradeInfo(transferSubmitBO.getTradeInfo());
		transfer.setOppCityCode(transferSubmitBO.getOppCityCode());
		transfer.setOpenBranch(transferSubmitBO.getOpenBranch());
		transfer.setBankBranchName(transferSubmitBO.getBankBranchName());
		transfer.setOrderNo(transferSubmitBO.getOrderNo());
		transfer.setTransCode(TRA_TRANSCODE_TRANSFER_BY_CHANNEL_ACCOUNT_CODE);
		return transfer;
	}

	/**
	 * 转账
	 * @param transferSubmitBO
	 * @return
	 */
	@Override
	public TransferSubmitVO transFerSubmit(TransferSubmitBO transferSubmitBO) {
		TransFerSubmitLog transLog=new TransFerSubmitLog();
		//退款则不走绑定
		if(!transferSubmitBO.getIfRefund()){
			//建立绑定关系成功与否不影响主业务流程
			BasePayPlatformVO basePayPlatformVO = tradeRelation(new TradeAndPayRelationBO() {{
				setPayNo(transferSubmitBO.getPayNo());
				setTradeNo(transferSubmitBO.getTradeNo());
				setTransCode(TRA_TRANSCODE_TRADE_PAY_RELATE_CREATE);
			}});
		}
		//1。组装参数
		TransferSubmitDTO bo = getObject(transferSubmitBO);
		transLog.setSign(JsonUtil.json(bo));
		//2。获取签名
		String sign = RSAUtil.encrypt(JsonUtil.json(bo), GongBaoConfig.privateKey);
		//3。发送获取数据
		try {
			BasePayPlatformBO basePayPlatformBO = new BasePayPlatformBO();
			basePayPlatformBO.setSign(sign);
			basePayPlatformBO.setSignType(SIGN_TYPE);
			basePayPlatformBO.setVersion(VERSION);
			basePayPlatformBO.setCustNo(tradeOrderConfig.getCustNo());
			basePayPlatformBO.setPlatNo(tradeOrderConfig.getPlatNo());
			basePayPlatformBO.setBody(JSON.parseObject(JsonUtil.json(bo)));
			Request.Builder builder = new Request.Builder();
			transLog.setRemoteJson(JsonUtil.json(basePayPlatformBO));
			log.debug("【交易平台-转账】调用参数：{}", JsonUtil.json(basePayPlatformBO));
			String json = OkhttpUtils.send(builder, HttpWay.POST, tradeOrderConfig.getTransferUrl(), JsonUtil.json(basePayPlatformBO), HttpType.JSON).string();
			transLog.setRespJson(json);
			log.debug("【交易平台-{}】响应结果：{}", "转账", json);
			BasePayPlatformVO resultMap = JSONObject.parseObject(json, BasePayPlatformVO.class);
			if (resultMap == null) {
				throw new BusinessException("【交易平台响应结果】" + "转账" + "，交易平台响应为空！");
			}
			if (!StringUtils.equals(resultMap.getRespCode(), SUCCESS_CODE)) {
				throw new BusinessException("交易平台响应失败:" + resultMap.getRespMsg());
			}
			return GeneralConvertor.convertor(resultMap.getBody(),TransferSubmitVO.class);
		} catch (Exception e) {
			transLog.setMsg(e.getMessage());
			log.error("转账异常：mess={}",e.getMessage());
			throw new BusinessException("调用交易平台数据异常");
		}finally {
			transLog.setOrderNo(transferSubmitBO.getOrderNo());
			transLog.setReqJson(JsonUtil.json(transferSubmitBO));
			transFerSubmitLogService.insert(transLog);
			SendRebootUtil.DingDingParams dingDingParams = new SendRebootUtil.DingDingParams();
			dingDingParams.setReqObject(JsonUtil.bean(transLog.getRemoteJson(),BasePayPlatformBO.class ));
			dingDingParams.setReqTitle("交易平台-转账");
			dingDingParams.setRespJson(transLog.getRespJson());
			dingDingParams.setErrorMsg(transLog.getMsg());
			dingDingParams.setAtMobileList(Arrays.asList("@18667943303"));
			dingDingParams.setRespTitle("交易平台响应");
			SendRebootUtil.sendDingNotice(dingDingParams);
		}
	}

	/**
	 * 转账通知
	 * @param payOrder
	 * @return
	 */
	@Override
	public Map<String,Object> transFerSubmitNotify(BasePayPlatformBO payOrder) {

		TransFerSubmitNotifyLog transNotifyLog = new TransFerSubmitNotifyLog();
		transNotifyLog.setReqJson(JsonUtil.json(payOrder));

		Map<String,Object> basePayPlatformVO = new HashMap<>(4);
		String sign = payOrder.getSign();
		TransferNotifyEvent transferNotifyEvent =JsonUtil.bean(JsonUtil.json(payOrder.getBody()), TransferNotifyEvent.class);
		transNotifyLog.setPayNo(transferNotifyEvent.getPayNo());
//		Boolean aBoolean = RSAUtil.check(JSON.toJSONString(payOrder.getBody(),SerializerFeature.MapSortField), sign, tradeOrderConfig.getPublicKey());
//		if(!aBoolean){
//			basePayPlatformVO.put("msg","验签失败");
//			basePayPlatformVO.put(TradePlatConstants.SUCCESS,false);
//			return basePayPlatformVO;
//		}
		try{
			transNotifyLog.setMqJson(JsonUtil.json(transferNotifyEvent));
			log.debug("发送用户转账成功mq，{}",JsonUtil.json(payOrder.getBody()));
			//发送mq
			rabbitTemplate.convertAndSend(RabbitConfig.TRADE_TRANSFER_NOTICE, transferNotifyEvent);
			basePayPlatformVO.put("code",TradePlatConstants.SUCCESS_RESP_CODE);
			basePayPlatformVO.put(TradePlatConstants.SUCCESS,true);
			return basePayPlatformVO;
		}catch (Exception e){
			transNotifyLog.setMsg(e.getMessage());
			log.error("转帐通知失败：{}",e.getMessage());
			throw e;
		}finally {
			transNotifyLog.setRespJson(JsonUtil.json(basePayPlatformVO));
			transFerSubmitNotifyLogService.insert(transNotifyLog);
			SendRebootUtil.DingDingParams dingDingParams = new SendRebootUtil.DingDingParams();
			dingDingParams.setReqObject(payOrder);
			dingDingParams.setReqTitle("交易平台-转账通知");
			dingDingParams.setRespJson(transNotifyLog.getRespJson());
			dingDingParams.setErrorMsg(transNotifyLog.getMsg());
			dingDingParams.setAtMobileList(Arrays.asList("@18667943303"));
			dingDingParams.setRespTitle("工保网响应");
			SendRebootUtil.sendDingNotice(dingDingParams);
		}
	}

	@Override
	public Map<String, Object> refundNotify(BasePayPlatformBO payOrder) {

		TransFerSubmitNotifyLog transNotifyLog = new TransFerSubmitNotifyLog();
		transNotifyLog.setReqJson(JsonUtil.json(payOrder));
		transNotifyLog.setTitle("交易平台-退汇通知");

		Map<String,Object> basePayPlatformVO = new HashMap<>(4);
		RefundNotifyEvent refundNotifyEvent =JsonUtil.bean(JsonUtil.json(payOrder.getBody()), RefundNotifyEvent.class);
		transNotifyLog.setPayNo(refundNotifyEvent.getPayNo());
		try{
			transNotifyLog.setMqJson(JsonUtil.json(refundNotifyEvent));
			log.debug("发送用户退汇成功mq，{}",JsonUtil.json(payOrder.getBody()));
			//发送mq
			rabbitTemplate.convertAndSend(RabbitConfig.REFUND_NOTICE, refundNotifyEvent);
			basePayPlatformVO.put("code",TradePlatConstants.SUCCESS_RESP_CODE);
			basePayPlatformVO.put(TradePlatConstants.SUCCESS,true);
			return basePayPlatformVO;
		}catch (Exception e){
			transNotifyLog.setMsg(e.getMessage());
			log.error("退汇通知失败：{}",e.getMessage());
			throw e;
		}finally {
			transNotifyLog.setRespJson(JsonUtil.json(basePayPlatformVO));
			transFerSubmitNotifyLogService.insert(transNotifyLog);
			SendRebootUtil.DingDingParams dingDingParams = new SendRebootUtil.DingDingParams();
			dingDingParams.setReqObject(payOrder);
			dingDingParams.setReqTitle("交易平台-退汇通知");
			dingDingParams.setRespJson(transNotifyLog.getRespJson());
			dingDingParams.setErrorMsg(transNotifyLog.getMsg());
			dingDingParams.setAtMobileList(Arrays.asList("@18667943303"));
			dingDingParams.setRespTitle("工保网响应");
			SendRebootUtil.sendDingNotice(dingDingParams);
		}
	}

	@Override
	public BasePayPlatformVO tradeRelation(TradeAndPayRelationBO tradeAndPayRelationBO) {
		try{
			String sign = RSAUtil.encrypt(JSON.toJSONString(tradeAndPayRelationBO, SerializerFeature.MapSortField), GongBaoConfig.privateKey);
			BasePayPlatformBO basePayPlatformBO = new BasePayPlatformBO();
			basePayPlatformBO.setSign(sign);
			basePayPlatformBO.setSignType(SIGN_TYPE);
			basePayPlatformBO.setVersion(VERSION);
			basePayPlatformBO.setCustNo(tradeOrderConfig.getCustNo());
			basePayPlatformBO.setPlatNo(tradeOrderConfig.getPlatNo());
			basePayPlatformBO.setBody(JSON.parseObject(JsonUtil.json(tradeAndPayRelationBO)));
			Request.Builder builder = new Request.Builder();
			log.debug("【交易平台-绑定关系参数】{}",  JsonUtil.json(basePayPlatformBO));
			String json = OkhttpUtils.send(builder, HttpWay.POST, tradeOrderConfig.getTradeBinding(), JsonUtil.json(basePayPlatformBO), HttpType.JSON).string();
			log.debug("【交易平台-{}】响应结果：{}", "绑定关系", json);
			BasePayPlatformVO resultMap = JSONObject.parseObject(json, BasePayPlatformVO.class);
			if (resultMap == null) {
				throw new BusinessException("【交易平台响应结果】" + "创建绑定关系失败" + "，交易平台响应为空！");
			}
			if (!StringUtils.equals(resultMap.getRespCode(), SUCCESS_CODE)) {
				throw new BusinessException("交易平台响应失败:" + resultMap.getRespMsg());
			}
			return resultMap;

		}catch (Exception e){
			log.error("创建绑定关系失败:{}",e.getMessage());
		}
		return null;
	}
}
