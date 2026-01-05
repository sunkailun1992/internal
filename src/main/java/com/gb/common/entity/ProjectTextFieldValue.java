package com.gb.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 项目字段值表
 * </p>
 *
 * @author ranyang
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`project_text_field_value`")
@ApiModel(value = "ProjectTextFieldValue对象", description = "项目字段值表")
public class ProjectTextFieldValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序列")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "项目字段id")
    private String projectTextFieldId;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "名称")
    @TableField("`key`")
    private String key;

    @ApiModelProperty(value = "主数据编码")
    private String masterCode;

    @ApiModelProperty(value = "分类编码")
    private String classificationCode;

    @ApiModelProperty(value = "字段编码")
    private String code;

    @ApiModelProperty(value = "多选集合json")
    private String content;

    @ApiModelProperty(value = "后缀单位或集合json")
    private String unit;

    @ApiModelProperty(value = "后缀单位的值")
    private String unitValue;

    @ApiModelProperty(value = "省")
    private String provinceCode;

    @ApiModelProperty(value = "市")
    private String cityCode;

    @ApiModelProperty(value = "区")
    private String areaCode;

    @ApiModelProperty(value = "输入框值内容")
    private String value;

    @ApiModelProperty(value = "长度（0：半行，1：整行）")
    private Integer length;

    @ApiModelProperty(value = "验证类型（0：统一社会信用编码，1：身份证，2：邮箱，3：座机，4：手机号，5：网站）")
    private Integer validation;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyDateTime;

    @ApiModelProperty(value = "修改人")
    private String modifyName;

    @ApiModelProperty(value = "删除状态（0：未删除，1：删除）")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty(value = "类型（-1：其他，0：输入框，1：大输入框，2：整数数字框，3：小数点数字框，4：年月日日期，5：年月日时分秒日期，6：单选框，7：复选框，8：下拉框，9：地址框）")
    private Integer type;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @TableField(exist = false)
    @ApiModelProperty(value = "显示字段")
    private String fields;

    @TableField(exist = false)
    @ApiModelProperty(value = "排序规则(0:desc,1:asc)")
    private Boolean collation;

    @TableField(exist = false)
    @ApiModelProperty(value = "排序字段")
    private String collationFields = "create_date_time";

    @TableField(exist = false)
    @ApiModelProperty(value = "模糊查询")
    private String query;

    @TableField(exist = false)
    @ApiModelProperty(value = "指定code")
    private String codeIn;

}
