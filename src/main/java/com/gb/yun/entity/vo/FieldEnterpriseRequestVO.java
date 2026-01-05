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
 * 企业字段请求VO对象
 * </p>
 *
 * @author sunx
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="企业字段请求VO对象", description="企业字段请求VO对象")
public class FieldEnterpriseRequestVO implements Serializable {

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
    private String masterDataId;

    @ApiModelProperty(value = "分类ID")
    private String categoryId;

    @ApiModelProperty(value = "序列")
    @JSONField(name = "id")
    private String id;

    @ApiModelProperty(value = "企业名称")
    @JSONField(name = "enterpris_name")
    private String enterpriseName;

    @ApiModelProperty(value = "统一社会信用代码")
    @JSONField(name = "credit_code")
    private String creditCode;

    @ApiModelProperty(value = "法人")
    @JSONField(name = "legal_person")
    private String legalPerson;

    @ApiModelProperty(hidden = true, value = "注册地址")
    @JSONField(name = "Reg_address")
    private String regAddress;

    @ApiModelProperty(value = "详细地址")
    @JSONField(name = "Detail_address")
    private String detailAddress;

    @ApiModelProperty(value = "营业状态")
    @JSONField(name = "Business_status")
    private String businessStatus;

    @ApiModelProperty(value = "登记状态")
    @JSONField(name = "Registration_status")
    private String registrationStatus;

    @ApiModelProperty(hidden = true, value = "注册资本")
    @JSONField(name = "reg_capital")
    private BigDecimal regCapital;

    @ApiModelProperty(value = "实缴资本")
    @JSONField(name = "Paid_capital")
    private BigDecimal paidCapital;

    @ApiModelProperty(value = "成立时间")
    @DateTimeFormat(pattern = "yyyy/MM/dd", iso = DateTimeFormat.ISO.DATE)
    private Date establishTime;

    @ApiModelProperty(value = "核准日期")
    @DateTimeFormat(pattern = "yyyy/MM/dd", iso = DateTimeFormat.ISO.DATE)
    private Date approvalDate;

    @ApiModelProperty(value = "注册时间")
    @DateTimeFormat(pattern = "yyyy/MM/dd", iso = DateTimeFormat.ISO.DATE)
    private Date registrationTime;

    @ApiModelProperty(value = "营业期限起")
    @DateTimeFormat(pattern = "yyyy/MM/dd", iso = DateTimeFormat.ISO.DATE)
    private Date businesStermStart;

    @ApiModelProperty(value = "营业期限止")
    @DateTimeFormat(pattern = "yyyy/MM/dd", iso = DateTimeFormat.ISO.DATE)
    private Date businesStermEnd;

    @ApiModelProperty(hidden = true, value = "营业期限")
    @JSONField(name = "Busines_sterm")
    private String businesSterm;

    @ApiModelProperty(value = "性质")
    @JSONField(name = "nature")
    private String nature;

    @ApiModelProperty(value = "组织机构代码")
    @JSONField(name = "Organization_code")
    private String organizationCode;

    @ApiModelProperty(value = "登记机关")
    @JSONField(name = "registration_authority")
    private String registrationAuthority;

    @ApiModelProperty(value = "曾用名")
    @JSONField(name = "Name_used")
    private String nameUsed;

    @ApiModelProperty(value = "参保人数")
    @JSONField(name = "insured_persons")
    private Integer insuredPersons;

    @ApiModelProperty(value = "人员规模")
    @JSONField(name = "Personnel_size")
    private Integer personnelSize;

    @ApiModelProperty(value = "经营地址")
    @JSONField(name = "Business_address")
    private String businessAddress;

    @ApiModelProperty(value = "经营范围")
    @JSONField(name = "Nature_Business")
    private String natureBusiness;

    @ApiModelProperty(value = "所属行业")
    @JSONField(name = "Industry")
    private String industry;

    @ApiModelProperty(value = "行业生命周期")
    @JSONField(name = "Industry_life")
    private String industryLife;

    @ApiModelProperty(value = "联系人")
    @JSONField(name = "contacts")
    private String contacts;

    @ApiModelProperty(value = "移动电话")
    @JSONField(name = "mobile")
    private String mobile;

    @ApiModelProperty(value = "联系电话")
    @JSONField(name = "contact_number")
    private String contactNumber;

    @ApiModelProperty(value = "邮箱")
    @JSONField(name = "mailbox")
    private String mailbox;

    @ApiModelProperty(value = "官网")
    @JSONField(name = "Official_website")
    private String officialWebsite;

    @ApiModelProperty(value = "简介")
    @JSONField(name = "brief_introduction")
    private String briefIntroduction;

    @ApiModelProperty(value = "企业类型2")
    @JSONField(name = "Enterprise_type")
    private String enterpriseType2;

    @ApiModelProperty(value = "施工资质等级")
    @JSONField(name = "cs_qualification_level")
    private String csQualificationLevel;

    @ApiModelProperty(value = "企业经济性质")
    @JSONField(name = "enterprises_economic_nature")
    private String enterprisesEconomicNature;

    @ApiModelProperty(value = "投保人企业性质")
    @JSONField(name = "applicant_business")
    private String applicantBusiness;

    @ApiModelProperty(value = "经济性质")
    @JSONField(name = "economic_nature")
    private String economicNature;

    @ApiModelProperty(value = "股权结构")
    @JSONField(name = "ownership_structure")
    private String ownershipStructure;

    @ApiModelProperty(value = "控股方经济性质")
    @JSONField(name = "controlling_party")
    private String controllingParty;

    @ApiModelProperty(value = "最终受益人")
    @JSONField(name = "Ultimate_beneficiary")
    private String ultimateBeneficiary;

    @ApiModelProperty(value = "法人代表身份证号")
    @JSONField(name = "faren_code")
    private String farenCode;

    @ApiModelProperty(value = "法人代表身份证有效期起")
    @DateTimeFormat(pattern = "yyyy/MM/dd", iso = DateTimeFormat.ISO.DATE)
    private Date farenDateStart;

    @ApiModelProperty(value = "法人代表身份证有效期止")
    @DateTimeFormat(pattern = "yyyy/MM/dd", iso = DateTimeFormat.ISO.DATE)
    private Date farenDateEnd;

    @ApiModelProperty(hidden = true, value = "法人代表身份证有效期")
    @JSONField(name = "faren_date")
    private String farenDate;

    @ApiModelProperty(value = "行政级别")
    @JSONField(name = "Administrative_level")
    private String administrativeLevel;

    @ApiModelProperty(value = "注册资本")
    @JSONField(name = "registered_capital")
    private BigDecimal registeredCapital;

    @ApiModelProperty(value = "被保险人企业性质")
    @JSONField(name = "insured_nature")
    private String insuredNature;

    @ApiModelProperty(value = "举办单位")
    @JSONField(name = "Organizer")
    private String organizer;

    @ApiModelProperty(value = "宗旨和业务范围")
    @JSONField(name = "Purpose_business")
    private String purposeBusiness;

    @ApiModelProperty(value = "资金来源")
    @JSONField(name = "Funding_sources")
    private String fundingSources;

    @ApiModelProperty(value = "开办资金")
    @JSONField(name = "Start_fund")
    private BigDecimal startFund;

    @ApiModelProperty(value = "注册省名称")
    private String regProvinceName;

    @ApiModelProperty(value = "注册市名称")
    private String regCityName;

    @ApiModelProperty(value = "注册区名称")
    private String regAreaName;

}

