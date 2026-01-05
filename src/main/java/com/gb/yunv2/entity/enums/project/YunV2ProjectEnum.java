package com.gb.yunv2.entity.enums.project;

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
public enum YunV2ProjectEnum {
    项目编号("project_number", "项目编号", null, "xmbh", "项目编号"),
    项目名称("project_name", "项目名称", null, "project_name", "项目名称"),
    标段内部编号("project_internal_number", "标段内部编号", null, "", ""),
    标段名称("bid_name", "标段名称", null, "", ""),
    标段编号("bid_number", "标段编号", null, "", ""),

    是否自定义("whether_bid_number_is_customized", "标段编号是否是自定义", YunV2Constant.PROJECT_MASTER_DATA, "", ""),

    招标联系人联系方式("inviter_contact_way", "招标联系⼈联系方式", YunV2Constant.PROJECT_MASTER_DATA, "zbr_phone", "招标人联系电话"),
    招标联系人("inviter_contact", "招标联系人", YunV2Constant.PROJECT_MASTER_DATA, "zbrlxr", "招标联系人"),
    项目内容("project_content", "项目内容", YunV2Constant.PROJECT_MASTER_DATA, "project_cont", "项目内容"),
    计划开工日期("scheduled_start_time", "计划开工日期", YunV2Constant.PROJECT_MASTER_DATA, "start_date", "计划开工日期"),
    计划竣工日期("scheduled_completion_time", "计划竣工日期", YunV2Constant.PROJECT_MASTER_DATA, "end_date", "计划竣工日期"),
    工期("construction_period", "工期", YunV2Constant.PROJECT_MASTER_DATA, "project_time", "工期"),
    工程合同编号("project_contract_no", "工程合同编号", YunV2Constant.PROJECT_MASTER_DATA, "contract_no", "工程合同编号"),
    合同签订日期("contract_award_period", "合同签订日期", YunV2Constant.PROJECT_MASTER_DATA, "contract_date", "合同签订日期"),
    项目详细地址("project_address", "项目详细地址", YunV2Constant.PROJECT_MASTER_DATA, "project_add", "项目地址"),
    项目地址("project_province", "项目地址", YunV2Constant.PROJECT_MASTER_DATA, "project_possession", "项目属地"),
    建设单位("development_organization", "建设单位", YunV2Constant.PROJECT_MASTER_DATA, "project_construction_unit", "建设单位"),
    中标施工单位("win_construction_unit", "中标施工单位", YunV2Constant.PROJECT_MASTER_DATA, "project_construction_in_unit", "施工单位"),

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
     * 获取对应的平台类型
     */
    public static YunV2ProjectEnum getYunProjectEnumByYunCode(String yunCode) {
        Optional<YunV2ProjectEnum> value = Arrays.stream(YunV2ProjectEnum.values())
                .filter(x -> x.getYunCode().equals(yunCode))
                .findFirst();
        return value.orElse(无法识别);
    }


    /**
     * 获取对应的平台类型
     */
    public static YunV2ProjectEnum getYunProjectEnumByWangCode(String wangCode) {
        Optional<YunV2ProjectEnum> value = Arrays.stream(YunV2ProjectEnum.values())
                .filter(x -> x.getWangCode().equals(wangCode))
                .findFirst();
        return value.orElse(无法识别);
    }
}
