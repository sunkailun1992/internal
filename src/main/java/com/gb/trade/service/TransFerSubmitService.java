package com.gb.trade.service;

import com.gb.trade.entity.bo.BasePayPlatformBO;
import com.gb.trade.entity.bo.TradeAndPayRelationBO;
import com.gb.trade.entity.bo.TransferSubmitBO;
import com.gb.trade.entity.vo.BasePayPlatformVO;
import com.gb.trade.entity.vo.TransferSubmitVO;

import java.util.Map;

/**
 * 交易平台支付转账
 * @author lixinyao
 */
public interface TransFerSubmitService {
	/**
	 * 转账
	 * @param transferSubmitBO
	 * @return
	 */
	TransferSubmitVO transFerSubmit(TransferSubmitBO transferSubmitBO);

	/**
	 * 转账回调
	 * @param payOrder
	 * @return
	 */
	Map<String,Object> transFerSubmitNotify(BasePayPlatformBO payOrder);

	/**
	 * 退汇回调
	 * @param payOrder
	 * @return
	 */
	Map<String,Object> refundNotify(BasePayPlatformBO payOrder);


	/**
	 * 交易支付关系绑定
	 * @param tradeAndPayRelationBO
	 * @return
	 */
	BasePayPlatformVO tradeRelation(TradeAndPayRelationBO tradeAndPayRelationBO);
}
