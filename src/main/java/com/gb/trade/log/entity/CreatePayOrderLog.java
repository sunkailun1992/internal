package com.gb.trade.log.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 创建支付单
 * </p>
 *
 * @author lixinyao
 * @since 2022-12-01
 */
@Data
@ApiModel(value = "创建支付单")
public class CreatePayOrderLog implements Serializable {


    @Id
    @ApiModelProperty(name = "_id", value = "序列")
    private ObjectId _id;

    @ApiModelProperty(name = "orderNo", value = "商户订单号")
    private String orderNo;

    @ApiModelProperty(name = "createPayOrder", value = "接口入参")
    private String createPayOrder;

    @ApiModelProperty(name = "paymentDTO", value = "sign验签参数")
    private String paymentDTO;

    @ApiModelProperty(name = "basePayPlatformBO", value = "交易平台入参")
    private String basePayPlatformBO;

    @ApiModelProperty(name = "basePayPlatformVO", value = "交易平台出参")
    private String basePayPlatformVO;

    @ApiModelProperty(name = "msg", value = "异常信息")
    private String msg;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDateTime;

}
