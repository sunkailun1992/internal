package com.gb.yun.entity.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 云字典数据查询BO
 * </p>
 *
 * @author sunx
 * @since 2021-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="云字典数据查询BO", description="云字典数据查询BO")
public class YunDicDataQueryBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "linkId", value = "关联ID")
    private String linkId;

    @ApiModelProperty(name = "masterDataId", value = "主数据对象ID")
    private String masterDataId;

    @ApiModelProperty(name = "dataCode", value = "查询数据对象码值")
    private String dataCode;

    @ApiModelProperty(name = "父类ID", value = "父类ID")
    private String fatherId;

    @ApiModelProperty(name = "分类ID", value = "分类ID")
    private String categoryId;

    @ApiModelProperty(name = "是否是主数据", value = "是否是主数据")
    private Boolean isMasterData;

    @ApiModelProperty(name = "工保云TOKEN", value = "工保云TOKEN")
    private String yunToken;

}
