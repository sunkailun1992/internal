package com.gb.trade.controller;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.gb.trade.entity.bo.BasePayPlatformBO;
import com.gb.trade.entity.bo.CreatePayOrderBO;
import com.gb.trade.entity.bo.TransferSubmitBO;
import com.gb.trade.entity.vo.BasePayPlatformVO;
import com.gb.trade.entity.vo.CreatePayOrderVO;
import com.gb.trade.entity.vo.TransferSubmitVO;
import com.gb.trade.service.CreatePayOrderService;
import com.gb.trade.service.TransFerSubmitService;
import com.gb.utils.Json;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.enumeration.ReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.gb.constants.TradePlatConstants.SUCCESS_CODE;
import static com.gb.constants.TradePlatConstants.SUCCESS_RESP_CODE;

/**
 * 交易平台接口
 *
 * @author lxy
 * @since 2022-11-25
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/tradePlat")
@Api(tags = "交易平台接口")
public class TradePlatformController {

	private CreatePayOrderService createPayOrderService;
	private TransFerSubmitService transFerSubmitService;

	/**
	 * 创建支付订单
	 *
	 * @return com.utils.Json
	 * @author lxy
	 * @since 2022-11-25
	 */
	@Methods(methodsName = "创建支付订单", methods = "createPayOrder")
	@ApiOperation(value = "创建支付订单", httpMethod = "POST", notes = "创建支付订单", response = Json.class)
	@PostMapping("/createPayOrder")
	public Json<CreatePayOrderVO> createPayOrder(@Validated CreatePayOrderBO createPayOrder) {
		log.debug("创建支付订单入参：【{}】", JSON.toJSONString(createPayOrder));
		CreatePayOrderVO payOrder = createPayOrderService.createPayOrder(createPayOrder);
		return new Json(ReturnCode.成功, payOrder);
	}

	/**
	 * 创建支付订单回调通知
	 *
	 * @return com.utils.Json
	 * @author lxy
	 * @since 2022-11-25
	 */
	@Methods(methodsName = "用户支付成功通知", methods = "createPayOrderNotify")
	@ApiOperation(value = "用户支付成功通知", httpMethod = "POST", notes = "用户支付成功通知", response = Json.class)
	@PostMapping("/createPayOrderNotify")
	public Map<String,Object> createPayOrderNotify(@Validated @RequestBody BasePayPlatformBO payOrder) {
		log.debug("用户支付成功通知入参：【{}】",JSON.toJSONString(payOrder));
		Map<String,Object> payOrderNotify = createPayOrderService.createPayOrderNotify(payOrder);
		return payOrderNotify;
	}
	/**
	 * 转账
	 *
	 * @return com.utils.Json
	 * @author lxy
	 * @since 2022-11-25
	 */
	@Methods(methodsName = "转账", methods = "transFerSubmit")
	@ApiOperation(value = "转账", httpMethod = "POST", notes = "转账", response = Json.class)
	@PostMapping("/transFerSubmit")
	public Json<TransferSubmitVO> transFerSubmit(@Validated TransferSubmitBO transferSubmitBO) {
		log.debug("转账入参：【{}】", JSON.toJSONString(transferSubmitBO));
		if(!transferSubmitBO.getIfRefund()){
			Assert.isTrue(StringUtils.isNotEmpty(transferSubmitBO.getPayNo())&&
					StringUtils.isNotEmpty(transferSubmitBO.getTradeNo()),"转账必填参数不能为空");
		}
		TransferSubmitVO payOrder = transFerSubmitService.transFerSubmit(transferSubmitBO);
		return new Json(ReturnCode.成功, payOrder);
	}


	/**
	 * 转账回调通知
	 *
	 * @return com.utils.Json
	 * @author lxy
	 * @since 2022-11-25
	 */
	@Methods(methodsName = "转账回调通知", methods = "transFerSubmitNotify")
	@ApiOperation(value = "转账回调通知", httpMethod = "POST", notes = "转账回调通知", response = Json.class)
	@PostMapping("/transFerSubmitNotify")
	public Map<String,Object> transFerSubmitNotify(@Validated @RequestBody BasePayPlatformBO payOrder) {
		log.debug("转账回调通知入参：【{}】",JSON.toJSONString(payOrder));
		Map<String,Object> payOrderNotify = transFerSubmitService.transFerSubmitNotify(payOrder);
		return payOrderNotify;
	}

	/**
	 * 默认回调通知
	 *
	 * @return com.utils.Json
	 * @author lxy
	 * @since 2022-11-25
	 */
	@Methods(methodsName = "默认回调通知", methods = "defaultNotify")
	@ApiOperation(value = "默认回调通知", httpMethod = "POST", notes = "默认回调通知", response = Json.class)
	@PostMapping("/defaultNotify")
	public Map<String,Object> defaultNotify(@Validated @RequestBody BasePayPlatformBO payOrder) {
		log.debug("默认回调通知入参：【{}】",JSON.toJSONString(payOrder));
		Map<String,Object> payOrderNotify = new HashMap<>(1);
		payOrderNotify.put("code",SUCCESS_RESP_CODE);
		return payOrderNotify;
	}


	/**
	 * 退汇回调通知
	 *
	 * @return com.utils.Json
	 * @author lxy
	 * @since 2022-11-25
	 */
	@Methods(methodsName = "退汇回调通知", methods = "refundNotify")
	@ApiOperation(value = "退汇回调通知", httpMethod = "POST", notes = "退汇回调通知", response = Json.class)
	@PostMapping("/refundNotify")
	public Map<String,Object> refundNotify(@Validated @RequestBody BasePayPlatformBO payOrder) {
		log.debug("退汇回调通知入参：【{}】",JSON.toJSONString(payOrder));
		Map<String,Object> payOrderNotify = transFerSubmitService.refundNotify(payOrder);
		return payOrderNotify;
	}

}