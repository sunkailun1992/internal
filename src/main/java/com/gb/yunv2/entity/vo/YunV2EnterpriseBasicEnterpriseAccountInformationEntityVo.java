package com.gb.yunv2.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gb.common.entity.EnterpriseTextFieldValue;
import com.gb.yunv2.entity.enums.enterprise.YunV2EnterpriseEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @className: com.gb.newYun.entity.Vo-> YunV2EnterpriseBasicEnterpriseAccountInformationEntityVo
 * @author: 王一飞
 * @createDate: 2021-12-29 11:05 上午
 * @description: 企业的基本账户信息
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class YunV2EnterpriseBasicEnterpriseAccountInformationEntityVo {
    /**
     * 基本存款账户的账户名称
     */
    private String theAccountNameOfTheBasicDepositAccount;
    /**
     * 基本存款账户的开户银行
     */
    private String bankOfDeposit;
    /**
     * 基本存款账户的银行账号
     */
    private String bankAccount;


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
        YunV2EnterpriseBasicEnterpriseAccountInformationEntityVo basicEnterpriseAccountInformation = yunEnterpriseEntityVo.getBasicEnterpriseAccountInformation();
        if (Objects.isNull(basicEnterpriseAccountInformation)) {
            return null;
        }
        EnterpriseTextFieldValue enterpriseTextFieldValue = new EnterpriseTextFieldValue();

        YunV2EnterpriseEnum yunEnterpriseEnum = YunV2EnterpriseEnum.getYunEnterpriseEnumByYunCode(yunCode);
        enterpriseTextFieldValue.setCode(yunEnterpriseEnum.getWangCode());
        switch (yunEnterpriseEnum) {
            default:
                break;
        }
        return StringUtils.isNotBlank(enterpriseTextFieldValue.getValue()) ? enterpriseTextFieldValue : null;
    }
}
