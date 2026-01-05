package com.gb.yunv2.entity.enums.enterprise;

import com.gb.yunv2.entity.constant.YunV2Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @className: com.gb.newYun.entity.enmus-> YunV2ProjectEnum
 * @author: 王一飞
 * @createDate: 2021-12-23 2:49 下午
 * @description: 云字段与网字段 关联关系
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public enum YunV2EnterpriseEnum {
    /**
     * 云字段与网字段
     */
    企业名称("corporate_name", "企业名称", null, "enterpris_name", "企业名称"),
    统一社会信用代码("unify_social_credit_codes", "统一社会信用代码", null, "credit_code", "统一社会信用代码"),

    注册地址("registered_address", "注册地址", YunV2Constant.ENTERPRISE_MASTERDATA_MODEL, "Reg_address", "注册地址"),
    联系手机号("telephone", "联系手机号", YunV2Constant.ENTERPRISE_MASTERDATA_MODEL, "mobile", "联系电话"),
    联系人("contact_person", "联系人", YunV2Constant.ENTERPRISE_MASTERDATA_MODEL, "contacts", "联系人"),
    法定代表人身份证号("id_number_of_the_legal_representative", "法定代表人身份证号", YunV2Constant.ENTERPRISE_MASTERDATA_MODEL, "faren_code", "法定代表人身份证号"),
    法定代表人("legal_representative", "法定代表人", YunV2Constant.ENTERPRISE_MASTERDATA_MODEL, "legal_person", "法定代表人"),
    企业经济类型("the_enterprise_economic_types", "企业经济类型", YunV2Constant.ENTERPRISE_MASTERDATA_MODEL, "enterprises_economic_nature", "企业经济性质"),
    注册资本("register_capital_amount", "注册资本", YunV2Constant.ENTERPRISE_MASTERDATA_MODEL, "registered_capital", "注册资本"),

    企业营业执照扫描件的("enterprise_scanned_copy_of_business_license", "企业营业执照扫描件的", YunV2Constant.ENTERPRISE_IMAGE_DATA_MODEL, "business_license_info", "营业执照信息"),
    开户许可证("account_opening_permit", "开户许可证", YunV2Constant.ENTERPRISE_IMAGE_DATA_MODEL, "permit", "开户许可证"),
    企业授权委托书("enterprise_power_of_attorney", "企业授权委托书", YunV2Constant.ENTERPRISE_IMAGE_DATA_MODEL, "auth_report", "授权书"),
    企业资质影像文件扫描件("scanned_image_file_of_enterprise_qualification", "企业资质影像文件扫描件", YunV2Constant.ENTERPRISE_IMAGE_DATA_MODEL, "qualife_cert_info", "资质等级证书信息"),

    无法识别("无法识别", "无法识别", "无法识别", "无法识别", "无法识别");

    //  云code
    private String yunCode;
    //  云名称
    private String yunName;
    //  云传输业务对象
    private String yunBoGrouping;
    //  网code
    private String wangCode;
    //  网名称
    private String wangName;


    /**
     * 根据云code 获取对应的类型
     */
    public static YunV2EnterpriseEnum getYunEnterpriseEnumByYunCode(String yunCode) {
        Optional<YunV2EnterpriseEnum> value = Arrays.stream(YunV2EnterpriseEnum.values())
                .filter(x -> x.getYunCode().equals(yunCode))
                .findFirst();
        return value.orElse(无法识别);
    }


    /**
     * 根据网code 获取对应的类型
     */
    public static YunV2EnterpriseEnum getYunEnterpriseEnumByWaningCode(String wangCode) {
        Optional<YunV2EnterpriseEnum> value = Arrays.stream(YunV2EnterpriseEnum.values())
                .filter(x -> x.getWangCode().equals(wangCode))
                .findFirst();
        return value.orElse(无法识别);
    }
}
