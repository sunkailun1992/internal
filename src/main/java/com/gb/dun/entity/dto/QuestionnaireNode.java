package com.gb.dun.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName QuestionnaireNode
 * @Author yyl
 * @Date 2022-09-05 13:56:13
 * @Description QuestionnaireNode
 * @Version 1.0
 */
@Data
public class QuestionnaireNode implements Serializable {

    private static final long serialVersionUID = -8907753543145885602L;

    private String id;

    @ApiModelProperty(value = "问卷调查表id")
    private String questionnaireId;

    @ApiModelProperty(value = "问卷题目")
    private String question;

    @ApiModelProperty(value = "排序字段", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "主数据分类id")
    private String masterDataClassificationId;

    @ApiModelProperty(value = "因子编码")
    private String factorCode;

    @ApiModelProperty(value = "因子名称")
    private String factorName;

    @ApiModelProperty(value = "问题选项集合")
    private List<QuestionnaireOption> questionnaireOptions;
}
