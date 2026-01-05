package com.gb.yunv2.entity.to;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.common.entity.Enterprise;
import com.gb.common.entity.EnterpriseFileFieldValue;
import com.gb.yunv2.entity.enums.enterprise.YunV2EnterpriseEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @className: com.gb.newYun.entity.Bo-> YunV2EnterpriseFileTo
 * @author: 王一飞
 * @createDate: 2021-12-28 10:28 下午
 * @description: 企业传输--附件
 */
@Data
public class YunV2EnterpriseFileTo {
    /**
     * 企业营业执照扫描件的
     */
    @JsonProperty("enterprise_scanned_copy_of_business_license")
    private String enterpriseScannedCopyOfBusinessLicense;
    /**
     * 开户许可证
     */
    @JsonProperty("account_opening_permit")
    private String accountOpeningPermit;
    /**
     * 企业授权委托书
     */
    @JsonProperty("enterprise_power_of_attorney")
    private String enterprisePowerOfAttorney;
    /**
     * 企业资质影像文件扫描件
     */
    @JsonProperty("scanned_image_file_of_enterprise_qualification")
    private String scannedImageFileOfEnterpriseQualification;


    /**
     * @param isGetBoolean:   true-get，false-set
     * @param yunCode:        云code
     * @param value:          set的value值
     * @param enterpriseFile: 原始对象
     * @createAuthor: 王一飞
     * @title: 组织主体 数据结构
     * @createDate: 2021/12/24 3:25 下午
     */
    public static String getOrSetAssemblyData(Boolean isGetBoolean, String yunCode, String value, YunV2EnterpriseFileTo enterpriseFile) {
        String resultString = StringUtils.EMPTY;
        switch (YunV2EnterpriseEnum.getYunEnterpriseEnumByYunCode(yunCode)) {
            case 企业营业执照扫描件的:
                if (isGetBoolean) {
                    resultString = enterpriseFile.getEnterpriseScannedCopyOfBusinessLicense();
                } else {
                    enterpriseFile.setEnterpriseScannedCopyOfBusinessLicense(value);
                }
                break;
            case 开户许可证:
                if (isGetBoolean) {
                    resultString = enterpriseFile.getAccountOpeningPermit();
                } else {
                    enterpriseFile.setAccountOpeningPermit(value);
                }
                break;
            case 企业授权委托书:
                if (isGetBoolean) {
                    resultString = enterpriseFile.getEnterprisePowerOfAttorney();
                } else {
                    enterpriseFile.setEnterprisePowerOfAttorney(value);
                }
                break;
            case 企业资质影像文件扫描件:
                if (isGetBoolean) {
                    resultString = enterpriseFile.getScannedImageFileOfEnterpriseQualification();
                } else {
                    enterpriseFile.setScannedImageFileOfEnterpriseQualification(value);
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
     * @return: YunV2EnterpriseFileTo.class
     */
    public static YunV2EnterpriseFileTo convert(Enterprise enterprise) {
        //  过滤空数组
        List<EnterpriseFileFieldValue> enterpriseFileFieldValueList = enterprise.getEnterpriseFileFieldValueList();
        YunV2EnterpriseFileTo enterpriseFileTo = new YunV2EnterpriseFileTo();
        if (CollectionUtils.isEmpty(enterpriseFileFieldValueList)) {
            return enterpriseFileTo;
        }
        //  过滤空数据
        for (EnterpriseFileFieldValue enterpriseFileFieldValue : enterpriseFileFieldValueList) {
            String value = enterpriseFileFieldValue.getAddress();
            String code = enterpriseFileFieldValue.getCode();
            YunV2EnterpriseEnum enterpriseEnum = YunV2EnterpriseEnum.getYunEnterpriseEnumByWaningCode(code);
            if (StringUtils.isBlank(value) || YunV2EnterpriseEnum.无法识别.equals(enterpriseEnum)) {
                continue;
            }
            //  转化并set数据
            YunV2EnterpriseFileTo.getOrSetAssemblyData(
                    Boolean.FALSE, enterpriseEnum.getYunCode(), value, enterpriseFileTo);
        }
        return enterpriseFileTo;
    }
}
