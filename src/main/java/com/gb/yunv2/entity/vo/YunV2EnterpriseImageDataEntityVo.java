package com.gb.yunv2.entity.vo;

import cn.hutool.core.convert.Convert;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.common.entity.EnterpriseFileFieldValue;
import com.gb.yunv2.entity.enums.enterprise.YunV2EnterpriseEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.*;

import java.util.Objects;

/**
 * @className: com.gb.newYun.entity.Vo-> YunV2EnterpriseImageDataEntityVo
 * @author: 王一飞
 * @createDate: 2021-12-29 11:18 上午
 * @description: 企业的文件信息
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class YunV2EnterpriseImageDataEntityVo {
    /**
     * 企业营业执照扫描件的
     */
    @JsonProperty("enterprise_scanned_copy_of_business_license")
    private String enterpriseScannedCopyOfBusinessLicense;
    /**
     * 法人身份证正反面扫描件
     */
    private String scannedCopyOfFrontAndBackOfLegalPersonIdCard;
    /**
     * 企业资质影像文件扫描件
     */
    @JsonProperty("scanned_image_file_of_enterprise_qualification")
    private String scannedImageFileOfEnterpriseQualification;
    /**
     * 企业授权委托书
     */
    @JsonProperty("enterprise_power_of_attorney")
    private String enterprisePowerOfAttorney;
    /**
     * 经办人身份证复印件
     */
    private String copyOfIdCardOfHandler;
    /**
     * 开户许可证
     */
    @JsonProperty("account_opening_permit")
    private String accountOpeningPermit;

    private static final Pattern pattern = Pattern
            .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+$");


    /**
     * @param yunCode               :yun字段标识
     * @param yunEnterpriseEntityVo :数据来源
     * @createAuthor: 王一飞
     * @title: 类型转换，云数据结构 转化为 网数据结构（用于 下拉云数据并向网同步数据）
     * @createDate: 2022/1/10 2:58 下午
     * @description:
     * @return:
     */
    public static EnterpriseFileFieldValue convertToTextFieldValue(String yunCode, YunV2EnterpriseEntityVo yunEnterpriseEntityVo) {
        YunV2EnterpriseImageDataEntityVo enterpriseImageData = yunEnterpriseEntityVo.getEnterpriseImageData();
        if (Objects.isNull(enterpriseImageData)) {
            return null;
        }
        EnterpriseFileFieldValue enterpriseFileFieldValue = new EnterpriseFileFieldValue();

        YunV2EnterpriseEnum yunEnterpriseEnum = YunV2EnterpriseEnum.getYunEnterpriseEnumByYunCode(yunCode);
        enterpriseFileFieldValue.setCode(yunEnterpriseEnum.getWangCode());
        switch (yunEnterpriseEnum) {
            case 企业营业执照扫描件的:
                if (StringUtils.isNotEmpty(enterpriseImageData.getEnterpriseScannedCopyOfBusinessLicense()) &&
                        pattern.matcher(enterpriseImageData.getEnterpriseScannedCopyOfBusinessLicense()).matches()) {
                    enterpriseFileFieldValue.setAddress(enterpriseImageData.getEnterpriseScannedCopyOfBusinessLicense());
                }
                break;
            case 企业资质影像文件扫描件:
                if (StringUtils.isNotEmpty(enterpriseImageData.getScannedImageFileOfEnterpriseQualification()) &&
                        pattern.matcher(enterpriseImageData.getScannedImageFileOfEnterpriseQualification()).matches()) {
                    enterpriseFileFieldValue.setAddress(enterpriseImageData.getScannedImageFileOfEnterpriseQualification());
                }
                break;
            case 企业授权委托书:
                if (StringUtils.isNotEmpty(enterpriseImageData.getEnterprisePowerOfAttorney()) &&
                        pattern.matcher(enterpriseImageData.getEnterprisePowerOfAttorney()).matches()) {
                    enterpriseFileFieldValue.setAddress(enterpriseImageData.getEnterprisePowerOfAttorney());
                }
                break;
            case 开户许可证:
                if (StringUtils.isNotEmpty(enterpriseImageData.getAccountOpeningPermit()) &&
                        pattern.matcher(enterpriseImageData.getAccountOpeningPermit()).matches()) {
                    enterpriseFileFieldValue.setAddress(enterpriseImageData.getAccountOpeningPermit());
                }
                break;
            default:
                break;
        }
        return StringUtils.isNotBlank(enterpriseFileFieldValue.getAddress()) ? enterpriseFileFieldValue : null;
    }

}
