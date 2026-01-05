package com.gb.dun.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName QuestionnaireDTO
 * @Author yyl
 * @Date 2022-09-05 11:22:07
 * @Description QuestionnaireDTO
 * @Version 1.0
 */
@Data
public class QuestionnaireDTO implements Serializable {

    private static final long serialVersionUID = -2196908858279710588L;

    @ApiModelProperty(value = "风险分类id", example = "1")
    private String riskCategoryId;

    @ApiModelProperty(value = "投保人类型（3：投标方/施工企业，4：招标方/建设单位/企业）")
    private String policyHolderTypeName;

    /**
     * 投保人属性值
     */
    @ApiModelProperty(value = "投保人属性值")
    private String policyHolderAttrName;

    /**
     * 产品属性名
     */
    @ApiModelProperty(value = "产品属性名")
    private String productSpecName;

    /**
     * 产品属性值
     */
    @ApiModelProperty(value = "产品属性值")
    private String productAttributeName;

    @ApiModelProperty(value = "保司code")
    private String insuranceCode;

}
