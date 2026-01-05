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
 * @description:	TODO  创建支付订单
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = true)
public class PaymentDTO implements Serializable {


    private static final long serialVersionUID = -7448664837752754064L;
    /**
     * 渠道编号
     */
    @NotEmpty
    private String channelCode;
    /**
     * 记账字账簿编号
     */
    @NotEmpty
    private String channelAccountCode;
    /**
     * 交易手续费（默认0。00）
     */
    @NotEmpty
    private BigDecimal feeAmount;
    /**
     * 商品信息
     */
    @NotEmpty
    private String goodsInfo;
    /**
     * 线上交易标志（0：线上，1：线下）
     */
    @NotEmpty
    private String onlineFlag;
    /**
     * 商户订单号
     */
    @NotEmpty
    private String orderNo;

    /**
     * 备注说明
     */
    private String remarks;

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
     * 交易金额
     */
    @NotEmpty
    private BigDecimal tradeAmount;

    /**
     * 交易信息（商品名称）
     */
    private String tradeInfo;

    /**
     * 交易码
     */
    @NotEmpty
    private String transCode;


}
