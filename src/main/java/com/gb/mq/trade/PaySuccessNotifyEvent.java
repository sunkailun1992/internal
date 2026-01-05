package com.gb.mq.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: lixinyao
 * @since: 2022-11-25 09:05:58
 * @description: TODO 支付成功回调
 */
@Data
public class PaySuccessNotifyEvent implements Serializable {


	private static final long serialVersionUID = 4298482978797491640L;

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
	 * 交易手续费（默认0。00）
	 */
	@NotEmpty
	private BigDecimal feeAmount;

	/**
	 * 支付流水号
	 */
	@NotEmpty
	private String payNo;

	/**
	 * 创建时间
	 */
	@NotEmpty
	private String createTime;

	/**
	 * 银行流水号
	 */
	@NotEmpty
	private String channelSerNo;

	/**
	 * 备注说明
	 */
	private String remark;

	/**
	 * 支付时间
	 */
	@NotEmpty
	private String payFinishTime;

	/**
	 * 本方账户
	 */
	private String payAccount;

	/**
	 * 本方账户名
	 */
	private String payAccountName;

	/**
	 * 对方账户
	 */
	@NotEmpty
	private String oppAccount;

	/**
	 * 对方账户名
	 */
	@NotEmpty
	private String oppAccountName;

	/**
	 * 支付状态(00:待支付，01：成功，02：失败，03：处理中，04：关闭/取消，05：存疑)
	 */
	@NotEmpty
	private String payStatus;

	/**
	 * 支付金额
	 */
	@NotEmpty
	private BigDecimal payAmount;

	/**
	 * 对方银行名称
	 */
	private String bankName;

	/**
	 * 对方银行编号
	 */
	private String bankNo;

	/**
	 * 联行号
	 */
	private String openBranch;

	/**
	 * 支行信息
	 */
	private String bankBranchName;

	/**
	 * 记账字账簿编号
	 */
	@NotEmpty
	private String channelAccountCode;

	/**
	 * 交易码
	 */
	@NotEmpty
	private String transCode;

}
