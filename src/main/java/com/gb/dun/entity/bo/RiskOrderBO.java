package com.gb.dun.entity.bo;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * 风控审核BO
 * @author yyl
 */
@Data
public class RiskOrderBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 风控单号
     */
    @NotBlank(message = "风控单号不能为空")
    private String riskControlNumber;

    /**
     * 投保单号
     */
    @NotBlank(message = "投保单号不能为空")
    private String policyNumber;

    /**
     * 保险机构id
     */
    @NotBlank(message = "保险机构id不能为空")
    private String insuranceAgencyId;

    /**
     * 保险机构名称
     */
    @NotBlank(message = "保险机构名称不能为空")
    private String insuranceAgencyName;

    /**
     * 风险业务类型（1：保前风控业务）
     */
    @NotNull(message = "风险业务类型不能为空")
    private Integer riskBusinessType;

    /**
     * 风险分类id
     */
    @NotNull(message = "风险分类名称不能为空")
    private String riskCategoryName;

    /**
     * 保险模式（1：共保体，0：非共保体）
     */
    @NotNull(message = "保险模式不能为空")
    private Boolean insuranceModel;

    /**
     * 其他投保人
     */
    private String otherPolicyHolder;

    /**
     * 费率
     */
    @NotNull(message = "费率不能为空")
    private BigDecimal rate;

    /**
     * 保费
     */
    @NotNull(message = "保费不能为空")
    private BigDecimal premium;

    /**
     * 省
     */
    @NotBlank(message = "省编码不能为空")
    private String provinceCode;

    /**
     * 市
     */
    @NotBlank(message = "市编码不能为空")
    private String cityCode;

    /**
     * 区
     */
    @NotBlank(message = "区编码不能为空")
    private String areaCode;

    /**
     * 开始时间【不需要时分秒】
     * TODO:时间格式正则只限制了位数，相信工保网
     */
    @Pattern(regexp = "^(\\d{4})(-|\\/)(\\d{2})\\2(\\d{2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$", message = "保险开始时间格式不对")
    @NotBlank(message = "保险开始时间不能为空")
    private String startTime;

    /**
     * 结束时间【不需要时分秒】
     */
    @Pattern(regexp = "^(\\d{4})(-|\\/)(\\d{2})\\2(\\d{2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$", message = "保险结束时间格式不对")
    @NotBlank(message = "保险结束时间不能为空")
    private String endTime;

    /**
     * 投保人属性名
     */
    @NotBlank(message = "投保人属性名不能为空")
    private String policyHolderTypeName;

    /**
     * 投保人属性值
     */
    @NotBlank(message = "投保人属性值不能为空")
    private String policyHolderAttrName;

    /**
     * 产品属性名
     */
    @NotBlank(message = "产品属性名不能为空")
    private String productSpecName;

    /**
     * 产品属性值
     */
    @NotBlank(message = "产品属性值不能为空")
    private String productAttributeName;

    /**
     * 工保网的企业id
     */
    @NotBlank(message = "工保网的企业id不能为空")
    private String outEnerpriseId;

    /**
     * 反担保措施（1：无，2：保证反担保，3：现金反担保，4：抵押反担保，5：其他）
     * TODO：目前还不能确定有值，默认1
     */
    @NotNull(message = "反担保措施不能为空")
    private Integer counterGuaranteeMeasures;

    /**
     * 创建人
     */
    @NotBlank(message = "创建人不能为空")
    private String createName;

    /**
     * 业务发生时间 (工保网的create_time)
     */
    @NotNull(message = "业务发生时间不能为空")
    private Long businessTime;

    /**
     * 更新的时候产生的版本号
     */
    @NotNull(message = "数据版本时间不能为空")
    private Long version;

    /**
     * 项目编码
     */
    @NotNull(message = "项目编码不能为空")
    private CloudBO projectCode;

    /**
     * 项目名称
     */
    @NotNull(message = "项目名称不能为空")
    private CloudBO projectName;

    /**
     * 投保人id（社会信用代码）
     */
    @NotNull(message = "投保人id（社会信用代码）不能为空")
    private CloudBO policyHolderId;

    /**
     * 投保人
     */
    @NotNull(message = "投保人不能为空")
    private CloudBO policyHolder;

    /**
     * 财务报表资料路径
     */
    @NotNull(message = "财务报表资料路径不能为空")
    private String financialStatementsFilepaths;

    /**
     * 征信报告资料路径
     */
    @NotNull(message = "征信报告资料路径不能为空")
    private String creditReportFilepaths;

    /**
     * 保险金额
     */
    @NotNull(message = "保险金额不能为空")
    private CloudBO insuranceAmount;

    /**
     * 被保险人
     */
    private CloudBO theInsured;

    /**
     * 开标时间
     */
    private CloudBO timeOfBidOpening;

    /**
     * 项目报告附件
     */
    @NotEmpty(message = "项目报告附件不能为空")
    private List<ProjectReportBO> projectList;

    /**
     * 企业报告附件
     */
    @NotEmpty(message = "企业报告附件不能为空")
    private List<ProjectReportBO> enterpriseList;

    /**
     * 工程用途属性名【工保盾2.0.0】
     */
    private String estateDevName;

    /**
     * 工程用途属性值【工保盾2.0.0】
     */
    private String estateDevValue;

    /**
     * 项目工期，日历天【工保盾2.0.0】
     */
    private CloudBO projectDuration;

    /**
     * 项目性质【工保盾2.0.0】
     */
    private CloudBO projectNature;

    /**
     * 中标资质可以给企业资质使用【工保盾2.0.0】
     */
    private CloudBO bidQualification;

    /**
     * 企业类型【工保盾2.0.0】
     */
    private CloudBO enterpriseType;

    /**
     * 企业注册资本【工保盾2.0.0】
     */
    private CloudBO enterpriseRegisteredCapital;

    /**
     * 是否续保客户【工保盾2.0.0】
     */
    private Boolean renewalCustomers;

    /**
     * 工程项目类型【工保盾2.0.0】
     */
    private CloudBO projectType;

    /**
     * 工程地址【工保盾2.0.0】
     */
    private CloudBO projectAddr;

    /**
     * 工程详细地址【工保盾2.0.0】
     */
    private CloudBO projectAddrDetail;

    /**
     * 工程合同价/中标价【工保盾2.0.0】
     */
    private CloudBO projectContractPrice;

    /**
     * 是否风控【工保盾2.0.0】
     */
    private Boolean riskControl;

    /**
     * 保险公司详细信息【工保盾2.0.0】
     */
    private List<InsuranceBO> insurances;

    /**
     * 保险期限
     */
    private String insurancePeriod;

    /**
     * 建设单位名称
     */
    private String constructionName;

    /**
     * 计划开工日期
     */
    private String projectStartDate;


    @Override
    public String toString() {

        TreeMap<String, String> treeMap = new TreeMap<>();

        try {
            Class<?> forName = this.getClass();

            for (Field field : forName.getDeclaredFields()){

                Object value = field.get(this);
                if (value == null){
                    continue;
                }
                //判断属性是不是List<ProjectReportBO>
                if (("java.util.List<" + ProjectReportBO.class.getTypeName() + ">").equals(field.getGenericType().getTypeName())){

                    StringBuffer sb = new StringBuffer();
                    List<ProjectReportBO> list = (List<ProjectReportBO>)value;
                    //list对象排序
                    list.sort(Comparator.comparing(ProjectReportBO::getCreditReportFilepaths)
                            .thenComparing(ProjectReportBO::getDataFileName));

                    for (ProjectReportBO projectReportBO : list) {
                        sb.append(ObjectUtils.toString(projectReportBO, String::new));
                    }

                    treeMap.put(field.getName(), sb.toString());
                } else if (("java.util.List<" + InsuranceBO.class.getTypeName() + ">").equals(field.getGenericType().getTypeName())){

                    StringBuffer sb = new StringBuffer();
                    List<InsuranceBO> list = (List<InsuranceBO>)value;
                    //list对象排序
                    list.sort(Comparator.comparing(InsuranceBO::getInsuranceCode)
                            .thenComparing(InsuranceBO::getChildPolicyNumber));

                    for (InsuranceBO insuranceBO : list) {
                        sb.append(ObjectUtils.toString(insuranceBO, String::new));
                    }

                    treeMap.put(field.getName(), sb.toString());
                } else {
                    treeMap.put(field.getName(), StringUtils.trim(ObjectUtils.toString(value, String::new)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer();

        for (String value : treeMap.values()){
            sb.append(value);
        }

        return sb.toString();
    }

}