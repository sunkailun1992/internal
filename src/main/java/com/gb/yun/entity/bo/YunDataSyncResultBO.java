package com.gb.yun.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 云数据同步结果BO
 * </p>
 *
 * @author 孙凯伦
 * @since 2021-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="云数据同步结果BO", description="云数据同步结果BO")
public class YunDataSyncResultBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "pjTxtBodyBO", value = "项目字段BO")
    private YunDataSyncPjTxtBodyBO pjTxtBodyBO;

    @ApiModelProperty(name = "pjFileBodyBO", value = "项目附件BO")
    private YunDataSyncPjFileBodyBO pjFileBodyBO;

    @ApiModelProperty(name = "enTxtBodyBOList", value = "企业字段列表")
    private List<YunDataSyncEnTxtBodyBO> enTxtBodyBOList;

    @ApiModelProperty(name = "enFileBodyBOList", value = "企业附件列表")
    private List<YunDataSyncEnFileBodyBO> enFileBodyBOList;

    @ApiModelProperty(value = "失败消息")
    private String errorMsg;
}
