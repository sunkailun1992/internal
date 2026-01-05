package com.gb.yun.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * <p>
 * 项目附件请求VO对象
 * </p>
 *
 * @author sunx
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="项目附件请求VO对象", description="项目附件请求VO对象")
public class FileProjectRequestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "token")
    public String token;

    @ApiModelProperty(value = "项目名称")
    @JSONField(name = "project_name")
    private String projectName;

    @ApiModelProperty(value = "业务对象代码")
    @JSONField(name = "objectCode")
    private String objectCode;

    @ApiModelProperty(value = "关联数据ID")
    @JSONField(name = "linkId")
    private String linkId;

    @ApiModelProperty(value = "关联数据名称")
    @JSONField(name = "linkName")
    private String linkName;

    @ApiModelProperty(value = "序列")
    @JSONField(name = "id")
    private String id;

    @ApiModelProperty(value = "主数据ID")
    private String masterDataId;

    @ApiModelProperty(value = "分类ID")
    private String categoryId;

    @ApiModelProperty(value = "招标文件")
    @JSONField(name = "bidding_document")
    private String biddingDocument;

    @ApiModelProperty(value = "中标通知书")
    @JSONField(name = "bidding_notice")
    private String biddingNotice;

    @ApiModelProperty(value = "施工合同")
    @JSONField(name = "construction_contract")
    private String constructionContract;

    @ApiModelProperty(hidden = true, value = "农民工花名册")
    @JSONField(name = "workers")
    private String workers;

    @ApiModelProperty(value = "用工计划表")
    @JSONField(name = "schedule_form")
    private String scheduleForm;

    @ApiModelProperty(value = "股权结构证明")
    @JSONField(name = "ow_structure_certificate")
    private String owStructureCertificate;

}

