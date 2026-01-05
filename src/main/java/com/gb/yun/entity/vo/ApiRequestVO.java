package com.gb.yun.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * Api请求VO对象
 * </p>
 *
 * @author sunx
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Api请求VO对象", description="Api请求VO对象")
public class ApiRequestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //--------------------------------------公共部分--------------------------------------

    @ApiModelProperty(value = "关联名称")
    private String linkName;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "业务对象代码")
    @NotBlank(message = "业务对象不能为空")
    private String objectCode;

    @ApiModelProperty(value = "业务对象ID")
    private String businessObjectId;

    //-------------------------------------------------------------------------------------

    @ApiModelProperty(value = "统一社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "企业ID")
    private String linkId;

    @ApiModelProperty(value = "主数据ID")
    private String masterDataId;

    @ApiModelProperty(value = "分类ID")
    private String categoryId;

    @ApiModelProperty(value = "起始页")
    private Integer offset;

    @ApiModelProperty(value = "分页显示数量")
    private Integer pageSize;

    //--------------------------------------bo_qhdm--------------------------------------

    @ApiModelProperty(value = "父类ID")
    private String fatherId;

    @ApiModelProperty(value = "编码值")
    private String value;

    @ApiModelProperty(value = "名称")
    private String name;


}
