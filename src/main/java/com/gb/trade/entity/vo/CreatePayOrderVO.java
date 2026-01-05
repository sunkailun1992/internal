package com.gb.trade.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: lixinyao
 * @since: 2022-11-25 09:05:58
 * @description: TODO 创建支付订单出参
 */
@Data
public class CreatePayOrderVO implements Serializable {


	private static final long serialVersionUID = 3371894400663407501L;
	/**
	 * 银行（渠道）支付流水
	 */
	private String channelSerialNo;

	/**
	 * 联行号
	 */
	private String opneBranch;

	/**
	 * 线下支付码
	 */
	@NotEmpty
	private String payCode;

	/**
	 * 支付流水号
	 */
	@NotEmpty
	private String payNo;

	/**
	 * 交易金额
	 */
	@NotEmpty
	private String tradeAmount;

	/**
	 * 平台交易流水
	 */
	@NotEmpty
	private String tradeNo;

	/**
	 * 交易状态（00：待支付，01：成功，02：失败，03：处理中，04：关闭/取消）
	 */
	@NotEmpty
	private String tradeStatus;

	/**
	 * 交易类型（00：支付，01：转账，02：退汇）
	 */
	@NotEmpty
	private String tradeType;

	/**
	 * 支最后更新时间
	 */
	@NotEmpty
	private String updateTime;

	/**
	 * 渠道编号
	 */
	@NotEmpty
	private String channelCode;

	/**
	 * 渠道名称
	 */
	@NotEmpty
	private String channelName;

	/**
	 * 创建时间
	 */
	@NotEmpty
	private String createTime;

	/**
	 * 客户名称
	 */
	private String custName;

	/**
	 * 客户号
	 */
	private String payAccount;



}
