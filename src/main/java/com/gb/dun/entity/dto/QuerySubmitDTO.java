package com.gb.dun.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName QuerySubmitDTO
 * @Author yyl
 * @Date 2022-09-07 14:50:43
 * @Description QuerySubmitDTO
 * @Version 1.0
 */
@Data
public class QuerySubmitDTO implements Serializable {

    private static final long serialVersionUID = -4427134666963181489L;

    @ApiModelProperty(value = "问卷id")
    private String questionnaireId;

    @ApiModelProperty(value = "问卷名称")
    private String questionnaireName;

    @ApiModelProperty(value = "风险分类id", example = "1")
    private String riskCategoryId;

    @ApiModelProperty(value = "保司code")
    private String insuranceCode;

    @ApiModelProperty(value = "地区code")
    private String areaCode;

    @ApiModelProperty(value = "问卷调查问题选项集合")
    private List<QuestionnaireQuestionChoice> questionnaireQuestionChoices;
}
