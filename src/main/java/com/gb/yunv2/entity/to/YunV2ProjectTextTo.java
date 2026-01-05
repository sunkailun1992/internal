package com.gb.yunv2.entity.to;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.common.entity.Project;
import com.gb.common.entity.ProjectTextFieldValue;
import com.gb.rpc.component.RpcComponent;
import com.gb.yunv2.entity.enums.project.YunV2ProjectEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * @className: com.gb.newYun.entity.Bo-> YunV2ProjectTextTo
 * @author: 王一飞
 * @createDate: 2021-12-28 12:35 下午
 * @description: 项目传输--字段
 */
@Data
public class YunV2ProjectTextTo {
    /**
     * 标段内部编号
     */
    @JsonProperty("project_internal_number")
    private String projectInternalNumber;

    /**
     * 项⽬编号
     */
    @JsonProperty("project_number")
    private String projectNumber;

    /**
     * 项目名称
     */
    @JsonProperty("project_name")
    private String projectName;

    /**
     * 标段编号
     */
    @JsonProperty("bid_number")
    private String bidNumber;

    /**
     * 标段名称
     */
    @JsonProperty("bid_name")
    private String bidName;

    /**
     * 是否云自定义  1自定义 0否
     */
    @JsonProperty("whether_bid_number_is_customized")
    private String whetherBidNumberIsCustomized;
    /**
     * 开标时间
     */
    @JsonProperty("bid_openingtime")
    private String bidOpeningtime;

    /**
     * 项目地址
     */
    @JsonProperty("project_province")
    private String projectProvince;

    /**
     * 项目详细地址
     */
    @JsonProperty("project_address")
    private String projectAddress;

    /**
     * 招标人
     */
    @JsonProperty("bid_inviter")
    private String bidInviter;

    /**
     * 招标人统一社会信用代码
     */
    @JsonProperty("inviter_uniform_social_credit_code")
    private String inviterUniformSocialCreditCode;

    /**
     * 招标联系人
     */
    @JsonProperty("inviter_contact")
    private String inviterContact;

    /**
     * 招标联系人手机号
     */
    @JsonProperty("inviter_contact_telephone")
    private String inviterContactTelephone;

    /**
     * 招标联系⼈联系方式
     */
    @JsonProperty("inviter_contact_way")
    private String inviterContactWay;

    /**
     * 招标人地址
     */
    @JsonProperty("inviter_province")
    private String inviterProvince;

    /**
     * 招标人详细地址
     */
    @JsonProperty("inviter_address")
    private String inviterAddress;

    /**
     * 投标截止时间
     */
    @JsonProperty("tender_deadline")
    private String tenderDeadline;

    /**
     * 投标有效期
     */
    @JsonProperty("bid_validity_period")
    private String bidValidityPeriod;

    /**
     * 工程建设性质
     */
    @JsonProperty("construction_nature")
    private String constructionNature;

    /**
     * 工程类型
     */
    @JsonProperty("construction_type")
    private String constructionType;

    /**
     * 项目交易类型
     */
    @JsonProperty("project_execution_mode")
    private String projectExecutionMode;

    /**
     * 项目中标金额
     */
    @JsonProperty("winnning_bid_amount")
    private String winnningBidAmount;

    /**
     * 项目负责人
     */
    @JsonProperty("project_principal")
    private String projectPrincipal;

    /**
     * 投标保证金缴纳截止时间
     */
    @JsonProperty("tender_bond_payment_deadline")
    private String tenderBondPaymentDeadline;

    /**
     * 资金来源
     */
    @JsonProperty("capital_source")
    private String capitalSource;

    /**
     * 工期
     */
    @JsonProperty("construction_period")
    private String constructionPeriod;

    /**
     * 项目内容
     */
    @JsonProperty("project_content")
    private String projectContent;

    /**
     * 计划开工日期
     */
    @JsonProperty("scheduled_start_time")
    private String scheduledStartTime;

    /**
     * 计划竣工日期
     */
    @JsonProperty("scheduled_completion_time")
    private String scheduledCompletionTime;

    /**
     * 建设单位
     */
    @JsonProperty("development_organization")
    private String developmentOrganization;

    /**
     * 中标施工单位
     */
    @JsonProperty("win_construction_unit")
    private String winConstructionUnit;

    /**
     * 中标施工单位统一社会信用代码
     */
    @JsonProperty("win_construction_unit_uniform_social_credit_code")
    private String winConstructionUnitUniformSocialCreditCode;

    /**
     * 建设单位社会统一信用代码
     */
    @JsonProperty("development_organization_uniform_social_credit_code")
    private String developmentOrganizationUniformSocialCreditCode;

    /**
     * 投标保证金金额
     */
    @JsonProperty("bid_bond")
    private String bidBond;

    /**
     * 工程合同编号
     */
    @JsonProperty("project_contract_no")
    private String projectContractNo;

    /**
     * 合同签订日期
     */
    @JsonProperty("contract_award_period")
    private String contractAwardPeriod;

    /**
     * 工程造价
     */
    @JsonProperty("construction_cost")
    private String constructionCost;

    /**
     * 反担保承包方式
     */
    @JsonProperty("counter_guarantee_contracting_mode")
    private String counterGuaranteeContractingMode;

    /**
     * 立项文号
     */
    @JsonProperty("project_document_no")
    private String projectDocumentNo;

    /**
     * 中标结果公示发布时间
     */
    @JsonProperty("publicity_of_bid_winning_results_time")
    private String publicityOfBidWinningResultsTime;

    /**
     * 招标公告发布时间
     */
    @JsonProperty("announcement_of_bidding_time")
    private String announcementOfBiddingTime;

    /**
     * 招标公告链接
     */
    @JsonProperty("bidding_announcement_link")
    private String biddingAnnouncementLink;

    /**
     * 中标公告链接
     */
    @JsonProperty("bid_winning_announcement_link")
    private String bidWinningAnnouncementLink;

    /**
     * RPC
     */
    public static RpcComponent rpcComponent;

    public static RpcComponent rpcComponentBean() {
        if (Objects.isNull(rpcComponent)) {
            rpcComponent = SpringUtil.getBean("rpcComponent");
        }
        return rpcComponent;
    }


    /**
     * @param isGetBoolean:          true-get，false-set
     * @param projectText:           原始对象
     * @param projectTextFieldValue: set转换数据来源
     * @createAuthor: 王一飞
     * @title: 组织主体 数据结构
     * @createDate: 2021/12/24 3:25 下午
     */
    public static String getOrSetAssemblyData(Boolean isGetBoolean, YunV2ProjectEnum projectEnum, YunV2ProjectTextTo projectText, ProjectTextFieldValue projectTextFieldValue) {
        String resultString = StringUtils.EMPTY;
        String value = Objects.nonNull(projectTextFieldValue) ? projectTextFieldValue.getValue() : StringUtils.EMPTY;
        switch (projectEnum) {
            case 项目地址:
                if (isGetBoolean) {
                    resultString = projectText.getProjectProvince();
                } else {
                    projectText.setProjectProvince(projectTextFieldValue.getAreaCode());
                }
                break;
            case 项目详细地址:
                if (isGetBoolean) {
                    resultString = projectText.getProjectAddress();
                } else {
                    projectText.setProjectAddress(value);
                }
                break;
            case 招标联系人:
                if (isGetBoolean) {
                    resultString = projectText.getInviterContact();
                } else {
                    projectText.setInviterContact(value);
                }
                break;
            case 招标联系人联系方式:
                if (isGetBoolean) {
                    resultString = projectText.getInviterContactWay();
                } else {
                    projectText.setInviterContactWay(value);
                }
                break;
            case 工期:
                if (isGetBoolean) {
                    resultString = projectText.getConstructionPeriod();
                } else {
                    projectText.setConstructionPeriod(value);
                }
                break;
            case 项目内容:
                if (isGetBoolean) {
                    resultString = projectText.getProjectContent();
                } else {
                    projectText.setProjectContent(value);
                }
                break;
            case 计划开工日期:
                if (isGetBoolean) {
                    resultString = projectText.getScheduledStartTime();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
                    try {
                        value = DateUtil.date(sdf.parse(value).getTime()).toDateStr();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    projectText.setScheduledStartTime(value);
                }
                break;
            case 计划竣工日期:
                if (isGetBoolean) {
                    resultString = projectText.getScheduledCompletionTime();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
                    try {
                        value = DateUtil.date(sdf.parse(value).getTime()).toDateStr();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    projectText.setScheduledCompletionTime(value);
                }
                break;
            case 建设单位:
                if (isGetBoolean) {
                    resultString = projectText.getDevelopmentOrganization();
                } else {
                    projectText.setDevelopmentOrganization(value);
                }
                break;
            case 中标施工单位:
                if (isGetBoolean) {
                    resultString = projectText.getWinConstructionUnit();
                } else {
                    projectText.setWinConstructionUnit(value);
                }
                break;
            case 工程合同编号:
                if (isGetBoolean) {
                    resultString = projectText.getProjectContractNo();
                } else {
                    projectText.setProjectContractNo(value);
                }
                break;
            case 合同签订日期:
                if (isGetBoolean) {
                    resultString = projectText.getContractAwardPeriod();
                } else {
                    projectText.setContractAwardPeriod(value);
                }
                break;
            default:
                break;
        }
        return resultString;
    }

    public static YunV2ProjectTextTo convert(Project project) {
        //  过滤空数组
        List<ProjectTextFieldValue> projectTextFieldValueList = project.getProjectTextFieldValueList();
        YunV2ProjectTextTo projectTextTo = new YunV2ProjectTextTo();
        if (CollectionUtils.isEmpty(projectTextFieldValueList)) {
            return projectTextTo;
        }

        for (ProjectTextFieldValue projectTextFieldValue : projectTextFieldValueList) {
            //  过滤空数据
            String value = projectTextFieldValue.getValue();
            String code = projectTextFieldValue.getCode();
            YunV2ProjectEnum projectEnum = YunV2ProjectEnum.getYunProjectEnumByWangCode(code);
            if (StringUtils.isBlank(value) || YunV2ProjectEnum.无法识别.equals(projectEnum)) {
                continue;
            }
            //  转化并set数据
            YunV2ProjectTextTo.getOrSetAssemblyData(
                    Boolean.FALSE, YunV2ProjectEnum.getYunProjectEnumByWangCode(projectTextFieldValue.getCode()), projectTextTo, projectTextFieldValue);
            YunV2ProjectTo.getOrSetAssemblyData(
                    Boolean.FALSE, YunV2ProjectEnum.getYunProjectEnumByWangCode(projectTextFieldValue.getCode()), projectTextTo, projectTextFieldValue);
        }
        return projectTextTo;
    }
}
