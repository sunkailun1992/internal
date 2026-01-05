package com.gb.mq.dun;

import com.gb.common.entity.EnterpriseFileFieldValue;
import com.gb.common.entity.EnterpriseTextFieldValue;
import com.gb.common.entity.ProjectFileFieldValue;
import com.gb.common.entity.ProjectTextFieldValue;
import com.gb.dun.enmus.DunPushTypeEnum;
import com.gb.dun.entity.bo.InsuranceBO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 推送到工保盾风控审核事件
 * @author: ranyang
 * @Date: 2021/03/29 19:18
 * @descript: 推送到工保盾风控审核事件
 */
@Data
@NoArgsConstructor
@Validated
@EqualsAndHashCode(callSuper = false)
public class DunRiskSysReviewEvent extends DunPushEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 投保id
     */
    @NotNull(message = "投保ID不能为空")
    private String castInsuranceId;

    /**
     * 保险机构id
     */
    private String insuranceAgencyId;


    /**
     * 保险机构名称
     */
    private String insuranceAgencyName;

    /**
     * 风险(险种)分类id
     */
    private Long riskCategoryId;

    /**
     * 1险种名称
     */
    private String riskCategoryName;
    

    /**
     * 费率
     */
    private BigDecimal rate;

    /**
     * 保费
     */
    private BigDecimal premium;

    /**
     * 保险金额
     */
    private BigDecimal insuranceAmount;

    /**
     * 省编码
     */
    private String provinceCode;
    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区编码
     */
    private String areaCode;

    /**
     * 保险开始时间
     */
    private String startTime;

    /**
     * 保险结束时间
     */
    private String endTime;

    /**
     * 创建人（投保人）
     */
    @NotNull(message = "创建人不能为空！")
    private String createName;

    /**
     * 方案类型id
     */
    @NotNull(message = "方案类型ID不能为空")
    private String planId;

    /**
     * 方案类型值id
     */
    @NotNull(message = "方案类型值ID不能为空")
    private String planValueId;

    /**
     * 内容id
     */
    private String contentId;

    /**
     * 投保人类型id
     */
    @NotNull(message = "投保人类型ID不能为空")
    private String policyHolderId;

    /**
     * 投保人类型值id
     */
    @NotNull(message = "投保人类型值ID不能为空")
    private String policyHolderValueId;

    /**
     * 投保企业id
     */
    @NotNull(message = "投保企业ID不能为空")
    private String castEntepriseId;

    /**
     * 项目字段
     */
    private List<ProjectTextFieldValue> projectTxtList;
    /**
     * 投保企业字段
     */
    private List<EnterpriseTextFieldValue> enterpriseTxtList;
    /**
     * 被保企业字段
     */
    private List<EnterpriseTextFieldValue> insuranceEnterpriseTxtList;

    /**
     * 查询所有项目附件
     */
    private List<ProjectFileFieldValue> projectFileList;
    /**
     * 查询所有企业附件
     */
    private List<EnterpriseFileFieldValue> enterpriseFileList;

    /**
     * 是否风控【工保盾2.0.0】
     */
    private Boolean riskControl;

    /**
     * 是否续保客户【工保盾2.0.0】
     */
    private Boolean renewalCustomers;

    /**
     * 保险公司详细信息【工保盾2.0.0】
     */
    @NotNull(message = "保险公司详细信息不能为空")
    private List<InsuranceBO> insurances;


    /**
     * 投保企业名称(建设单位名称)
     */
    private String castEnterpriseName;

    /**
     * 保险期限
     */
    private Integer insuranceDay;

    @Override
    public void setPushType() {
        this.pushType = DunPushTypeEnum.DUN_RISKVIEW_TYPE;

    }
}
