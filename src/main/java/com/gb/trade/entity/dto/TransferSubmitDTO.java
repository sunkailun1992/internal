package com.gb.trade.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * @author:     	lixinyao
 * @since:   	    2022-11-25 09:05:58
 * @description:	TODO  转账
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = true)
public class TransferSubmitDTO implements Serializable {

    private static final long serialVersionUID = 5609958773094303269L;

    /**
     * 记账字账簿编号
     */
    @NotEmpty
    private String channelAccountCode;

    /**
     * 商品信息
     */
    @NotEmpty
    private String goodsInfo;

    /**
     * 交易手续费（默认0。00）
     */
    @NotEmpty
    private BigDecimal feeAmount;

    /**
     * 商户后台t通知url
     */
    @NotEmpty
    private String notifyUrl;

    /**
     * 商户前台t通知url
     */
    @NotEmpty
    private String returnUrl;

    /**
     * 线上交易标志（0：线上，1：线下）
     */
    @NotEmpty
    private String onlineFlag;
    /**
     * 备注说明
     */
    private String remarks;

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
    private String oppAccountName;

    /**
     * 对方银行名称
     */
    @NotEmpty
    private String oppBankName;

    /**
     * 对方银行编号
     */
    @NotEmpty
    private String oppBankNo;

    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;

    /**
     * 银行编号
     */
    private String tradeInfo;

    /**
     * 对方城市编号
     */
    private String oppCityCode;

    /**
     * 联行号
     */
    private String openBranch;

    /**
     * 支行信息
     */
    private String bankBranchName;

    /**
     * 订单号
     */
    @NotEmpty
    private String orderNo;

    /**
     * 交易码
     */
    @NotEmpty
    private String transCode;

}
