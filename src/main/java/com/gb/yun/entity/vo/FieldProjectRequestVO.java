package com.gb.yun.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * <p>
 * 项目字段请求VO对象
 * </p>
 *
 * @author sunx
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="项目字段请求VO对象", description="项目字段请求VO对象")
public class FieldProjectRequestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "token")
    public String token;

    @ApiModelProperty(value = "业务对象代码")
    @JSONField(name = "objectCode")
    private String objectCode;

    @ApiModelProperty(value = "关联数据ID")
    @JSONField(name = "linkId")
    private String linkId;

    @ApiModelProperty(value = "关联数据名称")
    @JSONField(name = "linkName")
    private String linkName;

    @ApiModelProperty(value = "主数据ID")
    @JSONField(name = "masterDataId")
    private String masterDataId;

    @ApiModelProperty(value = "分类ID")
    @JSONField(name = "categoryId")
    private String categoryId;

    @ApiModelProperty(value = "序列")
    @JSONField(name = "id")
    private String id;

    @ApiModelProperty(value = "项目编号")
    @JSONField(name = "xmbh")
    private String xmbh;

    @ApiModelProperty(value = "标段编号")
    @JSONField(name = "bdbh")
    private String bdbh;

    @ApiModelProperty(value = "项目名称")
    @JSONField(name = "project_name")
    private String projectName;

    @ApiModelProperty(value = "标段名称")
    @JSONField(name = "bdmc")
    private String bdmc;

    @ApiModelProperty(value = "项目地址省名称")
    private String projectProvinceName;

    @ApiModelProperty(value = "项目地址市名称")
    private String projectCityName;

    @ApiModelProperty(value = "项目地址区名称")
    private String projectAreaName;

    @ApiModelProperty(hidden = true, value = "项目地址")
    @JSONField(name = "project_add")
    private String projectAdd;

    @ApiModelProperty(value = "招标人")
    @JSONField(name = "zbr")
    private String zbr;

    @ApiModelProperty(value = "招标联系人")
    @JSONField(name = "zbrlxr")
    private String zbrlxr;

    @ApiModelProperty(value = "招标人联系电话")
    @JSONField(name = "zbr_phone")
    private String zbrPhone;

    @ApiModelProperty(value = "招标人地址省名称")
    private String zbrdzProvinceName;

    @ApiModelProperty(value = "招标人地址市名称")
    private String zbrdzCityName;

    @ApiModelProperty(value = "招标人地址区名称")
    private String zbrdzAreaName;

    @ApiModelProperty(hidden = true, value = "招标人地址")
    @JSONField(name = "zbrdz")
    private String zbrdz;

    @ApiModelProperty(value = "开标日期")
    @DateTimeFormat(pattern = "yyyy/MM/dd", iso = DateTimeFormat.ISO.DATE)
    private Date bidOpenDate;

    @ApiModelProperty(value = "发布日期")
    @DateTimeFormat(pattern = "yyyy/MM/dd", iso = DateTimeFormat.ISO.DATE)
    private Date publicDate;

    @ApiModelProperty(value = "招标方式")
    @JSONField(name = "bidding_method")
    private String biddingMethod;

    @ApiModelProperty(value = "招标代理单位")
    @JSONField(name = "zbdldw")
    private String zbdldw;

    @ApiModelProperty(value = "项目工期")
    @JSONField(name = "project_duration")
    private Integer projectDuration ;

    @ApiModelProperty(value = "资金来源")
    @JSONField(name = "fund_source")
    private String fundSource;

    @ApiModelProperty(value = "项目类型")
    @JSONField(name = "project_type")
    private String projectType;

    @ApiModelProperty(value = "项目内容")
    @JSONField(name = "project_cont")
    private String projectCont;

    @ApiModelProperty(value = "项目立项")
    @JSONField(name = "project_approval")
    private String projectApproval;

    @ApiModelProperty(value = "项目总投资")
    @JSONField(name = "investment")
    private String investment;

    @ApiModelProperty(value = "工程类型")
    @JSONField(name = "project_types")
    private String projectTypes;

    @ApiModelProperty(value = "建设性质")
    @JSONField(name = "build_nature")
    private String buildNature;

    @ApiModelProperty(value = "项目性质")
    @JSONField(name = "project_nature")
    private String projectNature;

    @ApiModelProperty(value = "项目建设性质")
    @JSONField(name = "project_const_nature")
    private String projectConstNature;

    @ApiModelProperty(value = "工程造价")
    @JSONField(name = "eg_cost")
    private String egCost;

    @ApiModelProperty(value = "项目复杂性")
    @JSONField(name = "project_complexity")
    private String projectComplexity;

    @ApiModelProperty(value = "招标控制价（最高限价）")
    @JSONField(name = "tender_control_price")
    private BigDecimal tenderControlPrice;


    @ApiModelProperty(value = "保证金金额")
    @JSONField(name = "tender_amount")
    private BigDecimal tenderAmount;

    @ApiModelProperty(value = "计划开工日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date startDate ;

    @ApiModelProperty(value = "计划竣工日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date endDate ;

    @ApiModelProperty(value = "工期")
    @JSONField(name = "project_time")
    private Integer projectTime;

    @ApiModelProperty(value = "工程合同编号")
    @JSONField(name = "contract_no")
    private String contractNo;

    @ApiModelProperty(value = "合同签订日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date contractDate;

    @ApiModelProperty(value = "立项文号")
    @JSONField(name = "project_document")
    private String projectDocument;

    @ApiModelProperty(value = "开标时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date bidOpenTime;

    @ApiModelProperty(value = "保证金缴纳截止时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date closeTime;

    @ApiModelProperty(value = "投标有效期")
    @JSONField(name = "tender_day")
    private Integer tenderDay;

    @ApiModelProperty(value = "招标人统一社会信用代码")
    @JSONField(name = "tenderee_code")
    private String tendereeCode;

    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    @ApiModelProperty(value = "投保内容ID")
    private String contentId;

    @ApiModelProperty(value = "项目属地")
    private String projectPossesion;

    @ApiModelProperty(value = "承包方式")
    private String contractingType;
}
