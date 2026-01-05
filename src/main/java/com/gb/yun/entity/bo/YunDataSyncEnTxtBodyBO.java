package com.gb.yun.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 云数据同步企业字段体BO
 * </p>
 *
 * @author sunx
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="云数据同步企业字段体BO", description="云数据同步企业字段体BO")
public class YunDataSyncEnTxtBodyBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "enterpriseName", value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(name = "reqEnterpriseTxtJson", value = "企业字段请求JSON报文")
    private String reqEnterpriseTxtJson;

    @ApiModelProperty(name = "respEnterpriseTxtJson", value = "企业字段响应JSON报文")
    private String respEnterpriseTxtJson;

    @ApiModelProperty(value = "企业字段处理类型")
    private String type;

    @ApiModelProperty(value = "失败消息")
    private String errorMsg;
}
