package com.gb.dun.log.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 退保信息日志
 * </p>
 *
 * @author wgs
 * @since 2021-04-21 17:39
 */
@Data
@ApiModel(value = "退保信息日志实体类")
@SuppressWarnings(value = "all")
public class DunRoSurrenderRecordLog implements Serializable {

    @Id
    @ApiModelProperty(name = "_id", value = "序列")
    private ObjectId _id;

    @ApiModelProperty(name = "policyNumber", value = "投保单号")
    private String policyNumber;

    @ApiModelProperty(name = "riskControlNumber", value = "风控订单ID")
    private String riskControlNumber;

    @ApiModelProperty(name = "ip", value = "访问IP")
    private String ip;

    @ApiModelProperty(name = "innerReqJson", value = "内部请求JSON报文")
    private String innerReqJson;

    @ApiModelProperty(name = "reqJson", value = "请求JSON报文")
    private String reqJson;

    @ApiModelProperty(name = "respJson", value = "响应JSON报文")
    private String respJson;

    @ApiModelProperty(name = "createName", value = "操作人")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyDateTime;

    @ApiModelProperty(name = "modifyName", value = "修改人")
    private String modifyName;

    @ApiModelProperty(name = "operationContent", value = "备注")
    private String description;

}
