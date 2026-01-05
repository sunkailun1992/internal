package com.gb.trade.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @author:     	lixinyao
 * @since:   	    2022-11-25 09:05:58
 * @description:	TODO  交易支付绑定关系
 */
@Data
public class TradeAndPayRelationBO implements Serializable {


    private static final long serialVersionUID = 7593650557032816183L;
    /**
     * 平台交易流水号
     */
    @NotEmpty
    private String tradeNo;

    /**
     * 支付流水号
     */
    @NotEmpty
    private String payNo;

    /**
     * 交易码
     */
    private String transCode;




}
