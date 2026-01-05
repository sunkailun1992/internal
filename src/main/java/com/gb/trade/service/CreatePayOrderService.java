package com.gb.trade.service;

import com.gb.trade.entity.bo.BasePayPlatformBO;
import com.gb.trade.entity.bo.CreatePayOrderBO;
import com.gb.trade.entity.vo.BasePayPlatformVO;
import com.gb.trade.entity.vo.CreatePayOrderVO;

import java.util.Map;

/**
 * 交易平台支付
 * @author lixinyao
 */
public interface CreatePayOrderService {
	/**
	 * 创建支付订单
	 * @param createPayOrder
	 * @return
	 */
	CreatePayOrderVO createPayOrder(CreatePayOrderBO createPayOrder);


	/**
	 * 支付订单回调
	 * @param payOrder
	 * @return
	 */
	Map<String,Object> createPayOrderNotify(BasePayPlatformBO payOrder);
}
