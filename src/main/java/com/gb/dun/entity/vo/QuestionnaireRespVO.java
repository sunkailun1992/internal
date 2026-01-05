package com.gb.dun.entity.vo;

import com.gb.dun.entity.dto.QuestionnaireNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName QuestionnaireRespVO
 * @Author yyl
 * @Date 2022-09-05 13:53:16
 * @Description QuestionnaireRespVO
 * @Version 1.0
 */
@Data
public class QuestionnaireRespVO implements Serializable {

    private static final long serialVersionUID = -2304994348246785887L;

    private String id;

    @ApiModelProperty(value = "问卷名称")
    private String questionnaireName;

    @ApiModelProperty(value = "问卷描述")
    private String description;

    @ApiModelProperty(value = "风险分类id", example = "1")
    private String riskCategoryId;

    @ApiModelProperty(value = "投保人类型（3：投标方/施工企业，4：招标方/建设单位/企业）", example = "1")
    private Integer policyholderType;

    @ApiModelProperty(value = "产品类型（1：企业版，2：项目版）", example = "1")
    private Integer productType;

    @ApiModelProperty(value = "保司code")
    private String insuranceCode;

    @ApiModelProperty(value = "地区code")
    private String areaCode;

    @ApiModelProperty(value = "问题列表")
    private List<QuestionnaireNode> questions;
}
