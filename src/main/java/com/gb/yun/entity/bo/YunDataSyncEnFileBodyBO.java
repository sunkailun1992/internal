package com.gb.yun.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 云数据同步企业附件体BO
 * </p>
 *
 * @author 孙凯伦
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="云数据同步企业附件体BO", description="云数据同步企业附件体BO")
public class YunDataSyncEnFileBodyBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "enterpriseName", value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(name = "reqEnterpriseFileJson", value = "企业字附件请求JSON报文")
    private String reqEnterpriseFileJson;

    @ApiModelProperty(name = "respEnterpriseFileJson", value = "企业附件响应JSON报文")
    private String respEnterpriseFileJson;

    @ApiModelProperty(value = "企业附件处理类型")
    private String type;

    @ApiModelProperty(value = "失败消息")
    private String errorMsg;
}
