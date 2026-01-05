package com.gb.yunv2.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.common.entity.ProjectTextFieldValue;
import com.gb.yunv2.entity.enums.project.YunV2ProjectEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @className: com.gb.newYun-> YunV2ProjectEntityVo
 * @author: 王一飞
 * @createDate: 2021-12-23 2:20 下午
 * @description: 项目返回数据
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class YunV2ProjectMasterDataEntityVo {
    /**
     * 开标时间
     */
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
    private String bidInviter;

    /**
     * 招标人统一社会信用代码
     */
    private String inviterUniformSocialCreditCode;

    /**
     * 招标联系人
     */
    @JsonProperty("inviter_contact")
    private String inviterContact;

    /**
     * 招标联系人手机号
     */
    private String inviterContactTelephone;

    /**
     * 招标联系⼈联系方式
     */
    @JsonProperty("inviter_contact_way")
    private String inviterContactWay;

    /**
     * 招标人地址
     */
    private String inviterProvince;

    /**
     * 招标人详细地址
     */
    private String inviterAddress;

    /**
     * 投标截止时间
     */
    private String tenderDeadline;

    /**
     * 投标有效期
     */
    private String bidValidityPeriod;

    /**
     * 工程建设性质
     */
    private String constructionNature;

    /**
     * 工程类型
     */
    private String constructionType;

    /**
     * 项目交易类型
     */
    private String projectExecutionMode;

    /**
     * 项目中标金额
     */
    private String winnningBidAmount;

    /**
     * 项目负责人
     */
    private String projectPrincipal;

    /**
     * 投标保证金缴纳截止时间
     */
    private String tenderBondPaymentDeadline;

    /**
     * 资金来源
     */
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
    private String winConstructionUnitUniformSocialCreditCode;

    /**
     * 建设单位社会统一信用代码
     */
    private String developmentOrganizationUniformSocialCreditCode;

    /**
     * 投标保证金金额
     */
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
    private String constructionCost;

    /**
     * 反担保承包方式
     */
    private String counterGuaranteeContractingMode;

    /**
     * 立项文号
     */
    private String projectDocumentNo;

    /**
     * 中标结果公示发布时间
     */
    private String publicityOfBidWinningResultsTime;

    /**
     * 招标公告发布时间
     */
    private String announcementOfBiddingTime;

    /**
     * 招标公告链接
     */
    private String biddingAnnouncementLink;

    /**
     * 中标公告链接
     */
    private String bidWinningAnnouncementLink;


    /**
     * @param yunCode               :yun字段标识
     * @param yunProjectEntityVo :数据来源
     * @createAuthor: 王一飞
     * @title: 类型转换，云数据结构 转化为 网数据结构（用于 下拉云数据并向网同步数据）
     * @createDate: 2022/1/10 2:58 下午
     * @description:
     * @return:
     */
    public static ProjectTextFieldValue convertToTextFieldValue(String yunCode, YunV2ProjectEntityVo yunProjectEntityVo) {
        YunV2ProjectMasterDataEntityVo projectMasterData = yunProjectEntityVo.getProjectMasterData();
        if (Objects.isNull(projectMasterData)) {
            return null;
        }
        ProjectTextFieldValue projectTextFieldValue = new ProjectTextFieldValue();

        YunV2ProjectEnum yunProjectEnum = YunV2ProjectEnum.getYunProjectEnumByYunCode(yunCode);
        projectTextFieldValue.setCode(yunProjectEnum.getWangCode());
        switch (yunProjectEnum) {
            case 招标联系人联系方式:
                projectTextFieldValue.setValue(projectMasterData.getInviterContactWay());
                break;
            case 招标联系人:
                projectTextFieldValue.setValue(projectMasterData.getInviterContact());
                break;
            case 项目内容:
                projectTextFieldValue.setValue(projectMasterData.getProjectContent());
                break;
            case 计划开工日期:
                projectTextFieldValue.setValue(projectMasterData.getScheduledStartTime());
                break;
            case 计划竣工日期:
                projectTextFieldValue.setValue(projectMasterData.getScheduledCompletionTime());
                break;
            case 工期:
                projectTextFieldValue.setValue(projectMasterData.getConstructionPeriod());
                break;
            case 工程合同编号:
                projectTextFieldValue.setValue(projectMasterData.getProjectContractNo());
                break;
            case 合同签订日期:
                projectTextFieldValue.setValue(projectMasterData.getContractAwardPeriod());
                break;
            case 项目详细地址:
                projectTextFieldValue.setValue(projectMasterData.getProjectAddress());
                break;
            case 项目地址:
                projectTextFieldValue.setValue(projectMasterData.getProjectProvince());
                break;
            case 建设单位:
                projectTextFieldValue.setValue(projectMasterData.getDevelopmentOrganization());
                break;
            case 中标施工单位:
                projectTextFieldValue.setValue(projectMasterData.getWinConstructionUnit());
                break;
            default:
                break;
        }
        return StringUtils.isNotBlank(projectTextFieldValue.getValue()) ? projectTextFieldValue : null;
    }
}