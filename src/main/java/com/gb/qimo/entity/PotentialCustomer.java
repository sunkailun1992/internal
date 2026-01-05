package com.gb.qimo.entity;

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
import java.util.Date;

/**
 * <p>
 * 潜在客户表
 * </p>
 *
 * @author lijh
 * @since 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`potential_customer`")
@ApiModel(value = "PotentialCustomer对象", description = "潜在客户表")
public class PotentialCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序列")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "来源id")
    private String potentialCustomerSourceId;

    @ApiModelProperty(value = "咨询用户id")
    private String userId;

    @ApiModelProperty(value = "咨询用户名称")
    private String userName;

    @ApiModelProperty(value = "经纪人id")
    private String agentUserId;

    @ApiModelProperty(value = "经纪人名称")
    private String agentUserName;

    @ApiModelProperty(value = "客服id")
    private String customerServiceId;

    @ApiModelProperty(value = "客服名称")
    private String customerServiceName;

    @ApiModelProperty(value = "险种分类id")
    private String dangerPlantedCategoryId;

    @ApiModelProperty(value = "险种分类名称")
    private String dangerPlantedCategoryName;

    @ApiModelProperty(value = "险种id")
    private String dangerPlantedId;

    @ApiModelProperty(value = "险种名称")
    private String dangerPlantedName;

    @ApiModelProperty(value = "咨询产品id")
    private String spuId;

    @ApiModelProperty(value = "省id")
    private String provinceCode;

    @ApiModelProperty(value = "省name")
    private String provinceName;

    @ApiModelProperty(value = "市id")
    private String cityCode;

    @ApiModelProperty(value = "市name")
    private String cityName;

    @ApiModelProperty(value = "区id")
    private String areaCode;

    @ApiModelProperty(value = "区name")
    private String areaName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "意向（0：未知，1：无意向，2：低意向，3：中意向，4：高意向）")
    private Integer intention;

    @ApiModelProperty(value = "有效性（0：待定，1：无效，2：有效）")
    private Integer effectiveness;

    @ApiModelProperty(value = "有效时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime effectivenessDateTime;

    @ApiModelProperty(value = "联系（0：未联系，1：已联系）")
    private Boolean contact;

    @ApiModelProperty(value = "预约联系时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date appointmentContactDateTime;

    @ApiModelProperty(value = "联系时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime contactDateTime;

    @ApiModelProperty(value = "现场到访（0：未到访，1：已到访）")
    private Boolean siteVisit;

    @ApiModelProperty(value = "预约现场到访时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime appointmentSiteVisitDateTime;

    @ApiModelProperty(value = "到访时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime siteVisitDateTime;

    @ApiModelProperty(value = "分配（0：未分配，1：已分配）")
    private Boolean allocation;

    @ApiModelProperty(value = "分配时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime allocationDateTime;

    @ApiModelProperty(value = "成单（0：未成单，1：已成单）")
    private Boolean clinchDeal;

    @ApiModelProperty(value = "结果内容")
    private String resultsContent;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "提交时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime submitTime;

    @ApiModelProperty(value = "处理时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime processingTime;

    @ApiModelProperty(value = "完成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime completeTime;

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

    @ApiModelProperty(value = "类型（0：产品咨询，1：经纪人咨询）")
    private Integer type;

    @ApiModelProperty(value = "状态（-1：取消，0：待处理，1：处理中，2：已完成，3：关闭）")
    private Integer state;

    @ApiModelProperty(value = "标签（0: 上午，1：下午）")
    private Integer label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @TableField(exist = false)
    @ApiModelProperty(value = "数据来源")
    private String sourceQuery;

    @TableField(exist = false)
    @ApiModelProperty(value = "数据状态")
    private String statusQuery;

}
