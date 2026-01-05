package com.gb.dun.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName QuestionnaireOption
 * @Author yyl
 * @Date 2022-09-05 14:08:51
 * @Description QuestionnaireOption
 * @Version 1.0
 */
@Data
public class QuestionnaireOption implements Serializable {

    private static final long serialVersionUID = -8071240280768103001L;

    private String id;

    @ApiModelProperty(value = "问卷问题表id", example = "1")
    private String questionId;

    @ApiModelProperty(value = "选项code")
    private String optionCode;

    @ApiModelProperty(value = "选项名称")
    private String optionName;

    @ApiModelProperty(value = "选项值")
    private String optionVaule;

    @ApiModelProperty(value = "排序字段", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "默认值")
    private String defultVaule;
}
