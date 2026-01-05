package com.gb.yunv2.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.common.entity.EnterpriseTextFieldValue;
import com.gb.yunv2.entity.enums.enterprise.YunV2EnterpriseEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @className: com.gb.newYun.entity.Vo-> YunV2EnterpriseMasterDataEntityVo
 * @author: 王一飞
 * @createDate: 2021-12-29 11:23 上午
 * @description: 企业基本信息
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class YunV2EnterpriseMasterDataEntityVo {
    /**
     * 企业名称
     */
    private String corporateName;
    /**
     * 统一社会信用代码
     */
    private String unifySocialCreditCodes;
    /**
     * 法定代表人
     */
    @JsonProperty("legal_representative")
    private String legalRepresentative;
    /**
     * 法定代表人身份证号
     */
    @JsonProperty("id_number_of_the_legal_representative")
    private String idNumberOfTheLegalRepresentative;
    /**
     * 企业经济类型
     */
    @JsonProperty("the_enterprise_economic_types")
    private String theEnterpriseEconomicTypes;
    /**
     * 成立日期
     */
    private String dateOfEstablishment;
    /**
     * 注册资本
     */
    @JsonProperty("register_capital_amount")
    private String registerCapitalAmount;
    /**
     * 实缴资本
     */
    private String paidInCapital;
    /**
     * 营业期限
     */
    @JsonProperty("operating_period")
    private String operatingPeriod;
    /**
     * 登记机关
     */
    private String registrationAuthority;
    /**
     * 核准日期
     */
    private String dataOfApproval;
    /**
     * 注册地址
     */
    @JsonProperty("registered_address")
    private String registeredAddress;
    /**
     * 经营范围
     */
    private String businessScope;
    /**
     * 联系手机号
     */
    @JsonProperty("telephone")
    private String telephone;
    /**
     * 联系人
     */
    @JsonProperty("contact_person")
    private String contactPerson;


    /**
     * @param yunCode                  :yun字段标识
     * @param yunEnterpriseEntityVo :数据来源
     * @createAuthor: 王一飞
     * @title: 类型转换，云数据结构 转化为 网数据结构（用于 下拉云数据并向网同步数据）
     * @createDate: 2022/1/10 2:58 下午
     * @description:
     * @return:
     */
    public static EnterpriseTextFieldValue convertToTextFieldValue(String yunCode, YunV2EnterpriseEntityVo yunEnterpriseEntityVo) {
        YunV2EnterpriseMasterDataEntityVo enterpriseMasterData = yunEnterpriseEntityVo.getEnterpriseMasterData();
        if (Objects.isNull(enterpriseMasterData)) {
            return null;
        }
        EnterpriseTextFieldValue enterpriseTextFieldValue = new EnterpriseTextFieldValue();

        YunV2EnterpriseEnum yunEnterpriseEnum = YunV2EnterpriseEnum.getYunEnterpriseEnumByYunCode(yunCode);
        enterpriseTextFieldValue.setCode(yunEnterpriseEnum.getWangCode());
        switch (yunEnterpriseEnum) {
            case 企业名称:
                enterpriseTextFieldValue.setValue(yunEnterpriseEntityVo.getCorporateName());
                break;
            case 统一社会信用代码:
                enterpriseTextFieldValue.setValue(yunEnterpriseEntityVo.getUnifySocialCreditCodes());
                break;
            default:
                break;
        }
        return StringUtils.isNotBlank(enterpriseTextFieldValue.getValue()) ? enterpriseTextFieldValue : null;
    }
}
