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
 * 风控审核日志
 * </p>
 *
 * @author wgs
 * @since 2021-04-21 17:39
 */
@Data
@ApiModel(value = "风控审核日志实体类")
@SuppressWarnings(value = "all")
public class DunRiskViewLog implements Serializable {

    @Id
    @ApiModelProperty(name = "_id", value = "序列")
    private ObjectId _id;

    /**
     * 操作类型：
     * 风控审核新增("RISKVIEW_A"),
     * 风控审核回调("RISKVIEW_C"),
     * 风控审核更新("RISKVIEW_U");
     */
    @ApiModelProperty(name = "optionType", value = "操作类型")
    private String optionType;

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

    @ApiModelProperty(name = "description", value = "备注")
    private String description;

    @ApiModelProperty(name = "errorMsg", value = "错误消息")
    private String errorMsg;

}
