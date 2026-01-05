package com.gb.trade.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @author:     	lixinyao
 * @since:   	    2022-11-25 09:05:58
 * @description:	TODO  订单与支付绑定关系
 */
@Data
public class PayRelationBindBO implements Serializable {


    private static final long serialVersionUID = -5366509810492723L;
    /**
     * 平台交易流水
     */
    @NotEmpty(message = "平台交易流水不能为空")
    private String tradeNo;

    /**
     * 支付流水号
     */
    @NotEmpty(message = "支付流水号不能为空")
    private String payNo;

    /**
     * 交易码
     */
    @NotEmpty(message = "交易码不能为空")
    private String transCode;

}
