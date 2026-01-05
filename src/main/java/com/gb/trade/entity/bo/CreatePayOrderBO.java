package com.gb.trade.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @author:     	lixinyao
 * @since:   	    2022-11-25 09:05:58
 * @description:	TODO  创建支付订单
 */
@Data
public class CreatePayOrderBO implements Serializable {

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
     * 交易金额
     */
    @NotEmpty(message = "交易金额不能为空")
    private String tradeAmount;

    /**
     * 交易信息（商品名称）
     */
    private String tradeInfo;

}
