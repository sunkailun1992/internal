package com.gb.yunv2.entity.to;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.common.entity.Enterprise;
import com.gb.common.entity.EnterpriseTextFieldValue;
import com.gb.yunv2.entity.enums.enterprise.YunV2EnterpriseEnum;
import com.gb.yunv2.entity.enums.enterprise.YunV2EnterprisesEconomicNatureEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @className: com.gb.newYun.entity.Bo-> YunV2EnterpriseTextTo
 * @author: 王一飞
 * @createDate: 2021-12-28 10:28 下午
 * @description: 企业传输--字段
 */
@Data
public class YunV2EnterpriseTextTo {
    /**
     * 企业名称
     */
    @JsonProperty("corporate_name")
    private String corporateName;
    /**
     * 统一社会信用代码
     */
    @JsonProperty("unify_social_credit_codes")
    private String unifySocialCreditCodes;
    /**
     * 注册地址
     */
    @JsonProperty("registered_address")
    private String registeredAddress;
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
     * 法定代表人身份证号
     */
    @JsonProperty("id_number_of_the_legal_representative")
    private String idNumberOfTheLegalRepresentative;
    /**
     * 法定代表人
     */
    @JsonProperty("legal_representative")
    private String legalRepresentative;
    /**
     * 企业经济类型
     */
    @JsonProperty("the_enterprise_economic_types")
    private String theEnterpriseEconomicTypes;
    /**
     * 注册资本
     */
    @JsonProperty("register_capital_amount")
    private String registerCapitalAmount;


    /**
     * @param isGetBoolean:   true-get，false-set
     * @param yunCode:        云code
     * @param value:          set的value值
     * @param enterpriseText: 原始对象
     * @createAuthor: 王一飞
     * @title: 组织主体 数据结构
     * @createDate: 2021/12/24 3:25 下午
     */
    public static String getOrSetYunAssemblyData(Boolean isGetBoolean, String yunCode, String value, YunV2EnterpriseTextTo enterpriseText) {
        String resultString = StringUtils.EMPTY;
        switch (YunV2EnterpriseEnum.getYunEnterpriseEnumByYunCode(yunCode)) {
            case 注册地址:
                if (isGetBoolean) {
                    resultString = enterpriseText.getRegisteredAddress();
                } else {
                    enterpriseText.setRegisteredAddress(value);
                }
                break;
            case 联系手机号:
                if (isGetBoolean) {
                    resultString = enterpriseText.getTelephone();
                } else {
                    enterpriseText.setTelephone(value);
                }
                break;
            case 联系人:
                if (isGetBoolean) {
                    resultString = enterpriseText.getContactPerson();
                } else {
                    enterpriseText.setContactPerson(value);
                }
                break;
            case 法定代表人身份证号:
                if (isGetBoolean) {
                    resultString = enterpriseText.getIdNumberOfTheLegalRepresentative();
                } else {
                    enterpriseText.setIdNumberOfTheLegalRepresentative(value);
                }
                break;
            case 法定代表人:
                if (isGetBoolean) {
                    resultString = enterpriseText.getLegalRepresentative();
                } else {
                    enterpriseText.setLegalRepresentative(value);
                }
                break;
            case 企业经济类型:
                if (isGetBoolean) {
                    resultString = YunV2EnterprisesEconomicNatureEnum.getYunEnterpriseEnumByWaningCode(enterpriseText.getTheEnterpriseEconomicTypes()).getYunCode();
                } else {
                    enterpriseText.setTheEnterpriseEconomicTypes(value);
                }
                break;
            case 注册资本:
                if (isGetBoolean) {
                    resultString = enterpriseText.getRegisterCapitalAmount();
                } else {
                    enterpriseText.setRegisterCapitalAmount(value);
                }
                break;
            default:
                break;
        }
        return resultString;
    }


    /**
     * @param enterprise: 参数数据
     * @createAuthor: 王一飞
     * @title: 监听网数据, 并向云推送数据，进行格式转换
     * @createDate: 2022/1/7 2:14 下午
     * @description:
     * @return: YunV2EnterpriseTextTo.class
     */
    public static YunV2EnterpriseTextTo convert(Enterprise enterprise) {
        //  过滤空数组
        List<EnterpriseTextFieldValue> enterpriseTextFieldValueList = enterprise.getEnterpriseTextFieldValueList();
        YunV2EnterpriseTextTo enterpriseTextTo = new YunV2EnterpriseTextTo();
        if (CollectionUtils.isEmpty(enterpriseTextFieldValueList)) {
            return enterpriseTextTo;
        }

        for (EnterpriseTextFieldValue enterpriseTextFieldValue : enterpriseTextFieldValueList) {
            //  过滤空数据
            String value = enterpriseTextFieldValue.getValue();
            String code = enterpriseTextFieldValue.getCode();
            YunV2EnterpriseEnum enterpriseEnum = YunV2EnterpriseEnum.getYunEnterpriseEnumByWaningCode(code);
            if (StringUtils.isBlank(value) || YunV2EnterpriseEnum.无法识别.equals(enterpriseEnum)) {
                continue;
            }
            //  转化并set数据
            YunV2EnterpriseTextTo.getOrSetYunAssemblyData(
                    Boolean.FALSE, enterpriseEnum.getYunCode(), value, enterpriseTextTo);
            YunV2EnterpriseTo.getOrSetYunAssemblyData(
                    Boolean.FALSE, enterpriseEnum.getYunCode(), value, enterpriseTextTo);
        }

        return enterpriseTextTo;
    }
}
