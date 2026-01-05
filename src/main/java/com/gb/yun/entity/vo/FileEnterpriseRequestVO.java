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
 * 企业附件请求VO对象
 * </p>
 *
 * @author sunx
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="企业附件请求VO对象", description="企业附件请求VO对象")
public class FileEnterpriseRequestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "token")
    public String token;

    @ApiModelProperty(value = "企业名称")
    @JSONField(name = "enterpris_name")
    private String enterpriseName;

    @ApiModelProperty(value = "业务对象代码")
    @JSONField(name = "objectCode")
    private String objectCode;

    @ApiModelProperty(value = "序列")
    @JSONField(name = "id")
    private String id;

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

    @ApiModelProperty(value = "营业执照")
    @JSONField(name = "business_license_info")
    private String businessLicenseInfo;

    @ApiModelProperty(value = "法定代表人身份证正面")
    @JSONField(name = "corporate_id_card_info")
    private String corporateIdCardInfo;

    @ApiModelProperty(value = "法定代表人身份证背面")
    @JSONField(name = "corporate_id_card_info_side")
    private String corporateIdCardInfoSide;

    @ApiModelProperty(value = "财务审计报告")
    @JSONField(name = "financial_audit_report_info")
    private String financialAuditReport;

    @ApiModelProperty(value = "近一年及近期财务报告")
    @JSONField(name = "financial_reports_one_year_info")
    private String financialReportsOneYear;

    @ApiModelProperty(value = "工程业绩清单")
    @JSONField(name = "project_performance_list_info")
    private String projectPerformanceList;

    @ApiModelProperty(value = "企业征信报告")
    @JSONField(name = "enterprise_credit_investigation_report_info")
    private String creditReport;

    @ApiModelProperty(value = "企业沿革表")
    @JSONField(name = "enterprise_evolution_form_info")
    private String enterpriseEvolutionForm;

    @ApiModelProperty(value = "法定代表人授权书")
    @JSONField(name = "power_attorney_info")
    private String powerAttorneyInfo;

    @ApiModelProperty(value = "公司章程/简介")
    @JSONField(name = "articles_of_association_info")
    private String articlesOfAssociation;

    @ApiModelProperty(value = "资质等级证书")
    @JSONField(name = "qualife_cert_info")
    private String qualifeCertInfo;

    @ApiModelProperty(value = "银行流水")
    @JSONField(name = "bank_flow_info")
    private String bankFlowInfo;

    @ApiModelProperty(value = "近一年纳税证明")
    @JSONField(name = "tax_cert_one_year_info")
    private String taxCertOneYear;

    @ApiModelProperty(value = "企业资质表")
    @JSONField(name = "enterprise_qualife_form_info")
    private String enterpriseQualifeForm;

    @ApiModelProperty(value = "相关资产证明")
    @JSONField(name = "relevant_asset_certificate_info")
    private String relevantAssetCertificate;
}

