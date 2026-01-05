package com.gb.yunv2.entity.vo;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.common.entity.EnterpriseTextFieldValue;
import com.gb.yunv2.entity.constant.YunV2Constant;
import com.gb.yunv2.entity.enums.enterprise.YunV2EnterpriseEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: com.gb.newYun-> YunV2ProjectEntityVo
 * @author: 王一飞
 * @createDate: 2021-12-23 2:20 下午
 * @description: 企业查询返回数据
 */
@Data
@Component
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class YunV2EnterpriseEntityVo {
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
     * 企业传输主数据模型--企业的建筑业企业资质、勘查、监理、设计资质
     */
    private YunV2EnterpriseQualificationInformationEntityVo enterpriseQualificationInformation;

    /**
     * 企业传输主数据模型--企业的基本账户信息
     */
    private YunV2EnterpriseBasicEnterpriseAccountInformationEntityVo basicEnterpriseAccountInformation;

    /**
     * 企业传输主数据模型--企业的文件信息
     */
    private YunV2EnterpriseImageDataEntityVo enterpriseImageData;

    /**
     * 企业传输主数据模型--企业基本信息
     */
    private YunV2EnterpriseMasterDataEntityVo enterpriseMasterData;


    /**
     * @createAuthor: 王一飞
     * @title: 组织 返回 主数据
     * @createDate: 2021/12/27 10:33 下午
     */
    public static List getResultJsonData(String send) {
        JSONObject resultObject = JSONObject.parseObject(send);

        //  成功
        if (! resultObject.get(YunV2Constant.RESULT_STATUS).equals(YunV2Constant.RESULT_STATUS_SUCCESS)) {
            return ListUtil.empty();
        }

        //  获取data外层数据
        String resultDataJson = Convert.toStr(JSONObject.parseObject(Convert.toStr(resultObject.get(YunV2Constant.RESULT_DATA)))
                .get(YunV2Constant.RESULT_CONTENT));
        return JSONArray.parseArray(resultDataJson)
                .stream()
                .map(enterpriseEntityJson -> {
                    //  组装外层对象
                    YunV2EnterpriseEntityVo newYunProjectEntityVo = JSONObject.parseObject(
                            JSON.toJSONString(enterpriseEntityJson), YunV2EnterpriseEntityVo.class);

                    JSONObject enterpriseJsonObject = JSON.parseObject(JSON.toJSONString(enterpriseEntityJson));

                    //  组装里层对象--企业的建筑业企业资质、勘查、监理、设计资质
                    YunV2EnterpriseQualificationInformationEntityVo qualificationInformationEntityVo =
                            JSON.parseObject(
                                    JSON.toJSONString(enterpriseJsonObject.get(YunV2Constant.ENTERPRISE_QUALIFICATION_INFORMATION_MODEL)),
                                    YunV2EnterpriseQualificationInformationEntityVo.class);
                    newYunProjectEntityVo.setEnterpriseQualificationInformation(qualificationInformationEntityVo);

                    //  组装里层对象--企业的基本账户信息
                    YunV2EnterpriseBasicEnterpriseAccountInformationEntityVo basicEnterpriseAccountInformationEntityVo =
                            JSON.parseObject(
                                    JSON.toJSONString(enterpriseJsonObject.get(YunV2Constant.ENTERPRISE_BASIC_ENTERPRISE_ACCOUNT_INFORMATION_MODEL)),
                                    YunV2EnterpriseBasicEnterpriseAccountInformationEntityVo.class);
                    newYunProjectEntityVo.setBasicEnterpriseAccountInformation(basicEnterpriseAccountInformationEntityVo);

                    //  组装里层对象--企业基本信息
                    YunV2EnterpriseMasterDataEntityVo enterpriseMasterDataEntityVo =
                            JSON.parseObject(
                                    JSON.toJSONString(enterpriseJsonObject.get(YunV2Constant.ENTERPRISE_MASTERDATA_MODEL)),
                                    YunV2EnterpriseMasterDataEntityVo.class);
                    newYunProjectEntityVo.setEnterpriseMasterData(enterpriseMasterDataEntityVo);

                    //  组装里层对象--企业的文件信息
                    YunV2EnterpriseImageDataEntityVo enterpriseImageDataEntityVo =
                            JSON.parseObject(
                                    JSON.toJSONString(enterpriseJsonObject.get(YunV2Constant.ENTERPRISE_IMAGE_DATA_MODEL)),
                                    YunV2EnterpriseImageDataEntityVo.class);
                    newYunProjectEntityVo.setEnterpriseImageData(enterpriseImageDataEntityVo);

                    return newYunProjectEntityVo;
                })
                .collect(Collectors.toList());
    }


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