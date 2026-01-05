package com.gb.dun.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName QuestionnaireQuestionChoice
 * @Author yyl
 * @Date 2022-09-07 14:54:10
 * @Description QuestionnaireQuestionChoice
 * @Version 1.0
 */
@Data
public class QuestionnaireQuestionChoice implements Serializable {

    private static final long serialVersionUID = 203822571899477057L;

    @ApiModelProperty(value = "问题id")
    private String questionnaireQuestionId;

    @ApiModelProperty(value = "选项id")
    private String questionnaireOptionId;
}
