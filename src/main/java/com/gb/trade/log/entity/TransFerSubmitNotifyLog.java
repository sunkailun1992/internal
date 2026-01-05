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

/**
 * <p>
 * 转账通知
 * </p>
 *
 * @author lixinyao
 * @since 2022-12-01
 */
@Data
@ApiModel(value = "转账通知")
public class TransFerSubmitNotifyLog implements Serializable {


    @Id
    @ApiModelProperty(name = "_id", value = "序列")
    private ObjectId _id;

    @Indexed
    @ApiModelProperty(name = "payNo", value = "支付流水号")
    private String payNo;

    @ApiModelProperty(name = "reqJson", value = "交易平台支付成功回调入参")
    private String reqJson;

    @ApiModelProperty(name = "mqJson", value = "mq数据")
    private String mqJson;

    @ApiModelProperty(name = "respJson", value = "工保网返回值出参")
    private String respJson;

    @ApiModelProperty(name = "msg", value = "异常信息")
    private String msg;

    @ApiModelProperty(name = "title", value = "标题")
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDateTime;

}
