package com.gb.yunv2.entity.to;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.yunv2.entity.constant.YunV2Constant;
import com.gb.yunv2.entity.enums.enterprise.YunV2EnterpriseEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @className: com.gb.newYun.entity.Bo-> YunV2EnterpriseTo
 * @author: 王一飞
 * @createDate: 2021-12-28 10:27 下午
 * @description: 企业传输
 */
@Data
public class YunV2EnterpriseTo {

    private YunV2EnterpriseTextTo text;
    private YunV2EnterpriseFileTo file;


    /**
     * @createAuthor: 王一飞
     * @title: 组织主体 数据结构
     * @createDate: 2021/12/24 3:25 下午
     */
    public static Object getAssemblyData(Boolean control, YunV2EnterpriseTextTo enterpriseText, YunV2EnterpriseFileTo enterpriseFile) {
        Map<String, Object> paramMap = new HashMap<>(4);

        //  向param对象添加 企业基本信息
        Map<String, Object> enterpriseMasterDataMap = new HashMap<>(4);

        //  向param对象添加 企业影像资料
        Map<String, Object> enterpriseImageDataMap = new HashMap<>(4);

        //  向param对象添加 企业的建筑业企业资质、勘查、监理、设计资质
        Map<String, Object> enterpriseQualificationInformationMap = new HashMap<>(4);

        //  向param对象添加 企业基本账户信息
        Map<String, Object> basicEnterpriseAccountInformationMap = new HashMap<>(4);

        //  字段
        if (Objects.nonNull(enterpriseText)) {
            Arrays.stream(enterpriseText
                    .getClass()
                    .getDeclaredFields())
                    .filter(text -> Objects.nonNull(text.getAnnotation(JsonProperty.class)))
                    .peek(text -> {
                        String code = text.getAnnotation(JsonProperty.class).value();
                        String yunBoGrouping = YunV2EnterpriseEnum.getYunEnterpriseEnumByYunCode(code).getYunBoGrouping();

                        if (StringUtils.isBlank(yunBoGrouping)) {
                            //  没有分组，为外层数据
                            String value = YunV2EnterpriseTo.getOrSetYunAssemblyData(Boolean.TRUE, code, null, enterpriseText);
                            if (StringUtils.isNotBlank(value)) {
                                paramMap.put(code, value);
                            }
                        } else {
                            //  根据分组添加里层数据
                            String textValue;
                            switch (yunBoGrouping) {
                                case YunV2Constant.ENTERPRISE_MASTERDATA_MODEL:
                                    textValue = YunV2EnterpriseTextTo.getOrSetYunAssemblyData(Boolean.TRUE, code, null, enterpriseText);
                                    if (StringUtils.isNotBlank(textValue)) {
                                        enterpriseMasterDataMap.put(code, textValue);
                                    }
                                    break;
                                case YunV2Constant.ENTERPRISE_QUALIFICATION_INFORMATION_MODEL:
                                    textValue = YunV2EnterpriseTextTo.getOrSetYunAssemblyData(Boolean.TRUE, code, null, enterpriseText);
                                    if (StringUtils.isNotBlank(textValue)) {
                                        enterpriseQualificationInformationMap.put(code, textValue);
                                    }
                                    break;
                                case YunV2Constant.ENTERPRISE_BASIC_ENTERPRISE_ACCOUNT_INFORMATION_MODEL:
                                    textValue = YunV2EnterpriseTextTo.getOrSetYunAssemblyData(Boolean.TRUE, code, null, enterpriseText);
                                    if (StringUtils.isNotBlank(textValue)) {
                                        basicEnterpriseAccountInformationMap.put(code, textValue);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    })
                    .collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(enterpriseMasterDataMap)) {
            paramMap.put(YunV2Constant.ENTERPRISE_MASTERDATA_MODEL, enterpriseMasterDataMap);
        }
        if (CollectionUtils.isNotEmpty(enterpriseQualificationInformationMap)) {
            paramMap.put(YunV2Constant.ENTERPRISE_QUALIFICATION_INFORMATION_MODEL, enterpriseQualificationInformationMap);
        }
        if (CollectionUtils.isNotEmpty(basicEnterpriseAccountInformationMap)) {
            paramMap.put(YunV2Constant.ENTERPRISE_BASIC_ENTERPRISE_ACCOUNT_INFORMATION_MODEL, basicEnterpriseAccountInformationMap);
        }

        //  附件
        if (Objects.nonNull(enterpriseFile)) {
            Arrays.stream(enterpriseFile
                    .getClass()
                    .getDeclaredFields())
                    .filter(file -> Objects.nonNull(file.getAnnotation(JsonProperty.class)))
                    .peek(file -> {
                        String code = file.getAnnotation(JsonProperty.class).value();
                        String yunBoGrouping = YunV2EnterpriseEnum.getYunEnterpriseEnumByYunCode(code).getYunBoGrouping();
                        if (StringUtils.isNotBlank(yunBoGrouping)) {
                            switch (yunBoGrouping) {
                                case YunV2Constant.ENTERPRISE_IMAGE_DATA_MODEL:
                                    String fileValue = YunV2EnterpriseFileTo.getOrSetAssemblyData(Boolean.TRUE, code, null, enterpriseFile);
                                    if (StringUtils.isNotBlank(fileValue)) {
                                        enterpriseImageDataMap.put(code, fileValue);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    })
                    .collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(enterpriseImageDataMap)) {
            paramMap.put(YunV2Constant.ENTERPRISE_IMAGE_DATA_MODEL, enterpriseImageDataMap);
        }

        //  查询 和 上传，参数层级不一致
        if (control) {
            Map<String, Object> dataMap = new HashMap<>(1);
            dataMap.put(YunV2Constant.REQUEST_DATA_PARAM, paramMap);
            return dataMap;
        } else {
            return paramMap;
        }
    }


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
            case 企业名称:
                if (isGetBoolean) {
                    resultString = enterpriseText.getCorporateName();
                } else {
                    enterpriseText.setCorporateName(value);
                }
                break;
            case 统一社会信用代码:
                if (isGetBoolean) {
                    resultString = enterpriseText.getUnifySocialCreditCodes();
                } else {
                    enterpriseText.setUnifySocialCreditCodes(value);
                }
                break;
            default:
                break;
        }
        return resultString;
    }
}
