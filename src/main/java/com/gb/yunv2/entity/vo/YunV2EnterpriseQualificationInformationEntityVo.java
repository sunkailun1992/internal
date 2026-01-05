package com.gb.yunv2.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gb.common.entity.EnterpriseTextFieldValue;
import com.gb.yunv2.entity.enums.enterprise.YunV2EnterpriseEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @className: com.gb.newYun.entity.Vo-> YunV2EnterpriseQualificationInformationEntityVo
 * @author: 王一飞
 * @createDate: 2021-12-29 11:02 上午
 * @description: 企业传输主数据模型--企业的建筑业企业资质、勘查、监理、设计资质
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class YunV2EnterpriseQualificationInformationEntityVo {
    /**
     * 资质名称
     */
    private String qualifactionName;
    /**
     * 资质编号
     */
    private String qulificationNumber;
    /**
     * 发证日期
     */
    private String licenceIssued;
    /**
     * 资质有效期
     */
    private String qualificationTerm;


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
        YunV2EnterpriseQualificationInformationEntityVo enterpriseQualificationInformation = yunEnterpriseEntityVo.getEnterpriseQualificationInformation();
        if (Objects.isNull(enterpriseQualificationInformation)) {
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
