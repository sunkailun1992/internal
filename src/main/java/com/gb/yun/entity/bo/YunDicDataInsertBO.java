package com.gb.yun.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 云字典数据录入BO
 * </p>
 *
 * @author sunx
 * @since 2021-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="云字典数据录入BO", description="云字典数据录入BO")
public class YunDicDataInsertBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "linkId", value = "项目字段响应JSON报文")
    private String linkId;

    @ApiModelProperty(name = "masterDataId", value = "项目附件响应JSON报文")
    private String masterDataId;

    @ApiModelProperty(name = "dataCode", value = "查询数据对象码值")
    private String dataCode;

    @ApiModelProperty(name = "父类ID", value = "父类ID")
    private String fatherId;

    @ApiModelProperty(name = "工保云TOKEN", value = "工保云TOKEN")
    private String yunToken;
}
