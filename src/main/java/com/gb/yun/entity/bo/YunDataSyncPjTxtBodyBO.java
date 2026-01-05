package com.gb.yun.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 云数据同步项目字段BO
 * </p>
 *
 * @author sunx
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="云数据同步项目字段BO", description="云数据同步项目字段BO")
public class YunDataSyncPjTxtBodyBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "projectName", value = "项目名称")
    private String projectName;

    @ApiModelProperty(name = "reqProjectTextJson", value = "项目字段请求JSON报文")
    private String reqProjectTextJson;

    @ApiModelProperty(name = "respProjectTextJson", value = "项目字段响应JSON报文")
    private String respProjectTextJson;

    @ApiModelProperty(value = "项目字段处理类型")
    private String type;

    @ApiModelProperty(value = "失败消息")
    private String errorMsg;
}
