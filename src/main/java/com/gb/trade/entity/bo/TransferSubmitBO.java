package com.gb.trade.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @author:     	lixinyao
 * @since:   	    2022-11-25 09:05:58
 * @description:	TODO  创建支付订单
 */
@Data
public class TransferSubmitBO implements Serializable {

    /**
     * 商品信息
     */
    @NotEmpty(message = "商品信息不能为空")
    private String goodsInfo;

    /**
     * 备注说明
     */
    private String remarks;

    /**
     * 商户订单号
     */
    @NotEmpty
    private String orderNo;


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
    @NotEmpty(message = "对方账户不能为空")
    private String oppAccount;

    /**
     * 对方账户名
     */
    private String oppAccountName;

    /**
     * 对方银行名称
     */
    private String oppBankName;

    /**
     * 对方银行编号
     */
    private String oppBankNo;

    /**
     * 对方城市编号
     */
    private String oppCityCode;

    /**
     * 交易金额
     */
    @NotEmpty(message = "交易金额不能为空")
    private String tradeAmount;

    /**
     * 交易信息（商品名称）
     */
    private String tradeInfo;

    /**
     * 联行号
     */
    private String openBranch;

    /**
     * 支行信息
     */
    private String bankBranchName;
    /**
     * 平台交易流水号
     */
    private String tradeNo;

    /**
     * 支付流水号
     */
    private String payNo;

    /**
     * 是否退款（true：是，false：转账）
     */
    @NotNull(message = "是否退款不能为空")
    private Boolean ifRefund;



}
