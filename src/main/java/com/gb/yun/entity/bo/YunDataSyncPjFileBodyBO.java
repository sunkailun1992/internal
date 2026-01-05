package com.gb.yun.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 云数据同步项目附件体BO
 * </p>
 *
 * @author sunx
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="云数据同步项目附件体BO", description="云数据同步项目附件体BO")
public class YunDataSyncPjFileBodyBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "projectName", value = "项目名称")
    private String projectName;

    @ApiModelProperty(name = "reqProjectFileJson", value = "项目附件请求JSON报文")
    private String reqProjectFileJson;

    @ApiModelProperty(name = "respProjectTextJson", value = "项目附件响应JSON报文")
    private String respProjectFileJson;

    @ApiModelProperty(value = "项目附件处理类型")
    private String type;

    @ApiModelProperty(value = "失败消息")
    private String errorMsg;
}
