package com.gb.dun.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.gb.bean.GongBaoConfig;
import com.gb.common.constant.CommonConstant;
import com.gb.common.entity.EnterpriseFileFieldValue;
import com.gb.common.entity.EnterpriseTextFieldValue;
import com.gb.common.entity.ProjectFileFieldValue;
import com.gb.common.entity.ProjectTextFieldValue;
import com.gb.common.service.CommonService;
import com.gb.dun.enmus.RiskRiewBusinessTypeEnum;
import com.gb.dun.entity.bo.CloudBO;
import com.gb.dun.entity.bo.ContentFieldBO;
import com.gb.dun.entity.bo.ProjectReportBO;
import com.gb.dun.entity.bo.RiskOrderBO;
import com.gb.dun.entity.vo.RiskOrderVO;
import com.gb.dun.service.DunRiskViewService;
import com.gb.mq.dun.DunRiskSysReviewEvent;
import com.gb.rpc.component.RpcComponent;
import com.gb.rpc.enums.RpcTypeEnum;
import com.gb.utils.RsaUtils;
import com.gb.utils.enumeration.WordCodeEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 工保盾风控审核业务处理接口实现类
 *
 * @author: sunx
 * @Date: 2021/03/22 23:30
 * @descript: 保盾风控审核业务处理接口实现类
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
@SuppressWarnings(value = "all")
public class DunRiskViewServiceImpl implements DunRiskViewService {

    private CommonService commonService;

    private RpcComponent rpcComponent;

    @Override
    public RiskOrderVO organizeRiskOrderVO(RiskOrderBO riskOrderBO) {
        RiskOrderVO vo = new RiskOrderVO();
        if (Objects.isNull(riskOrderBO.getRiskBusinessType())) {
            riskOrderBO.setRiskBusinessType(RiskRiewBusinessTypeEnum.RISKRIEW_BEFORE_INSURANCE.getCode());
        }
        vo.setRiskOrderBO(riskOrderBO);
        vo.setAppId(GongBaoConfig.gongbaoDunAppId);
        //2、获取签名
        String sign = RsaUtils.generateSignOnly(vo.getRiskOrderBO().toString(), GongBaoConfig.privateKey);
        vo.setSign(sign);
        return vo;
    }

    @Override
    public RiskOrderBO buildRiskOrderBO(DunRiskSysReviewEvent riskSysReviewEvent) {
        RiskOrderBO riskOrderBO = new RiskOrderBO();
        BeanUtils.copyProperties(riskSysReviewEvent, riskOrderBO);
        riskOrderBO.setInsurancePeriod(riskSysReviewEvent.getInsuranceDay().toString());
        riskOrderBO.setConstructionName(riskSysReviewEvent.getCastEnterpriseName());
        riskOrderBO.setPolicyNumber(riskSysReviewEvent.getCastInsuranceId());
        riskOrderBO.setBusinessTime(System.currentTimeMillis());
        BigDecimal tenderAmount = (null == riskSysReviewEvent.getInsuranceAmount() ? BigDecimal.ZERO : riskSysReviewEvent.getInsuranceAmount());
        riskOrderBO.setInsuranceAmount(buildCloudBO(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, String.valueOf(tenderAmount)));
        Map<String, String> productParam = Maps.newHashMap();
        //1、获取产品种类名称
        productParam.put("id", riskSysReviewEvent.getPlanId());
        String result = (String) rpcComponent.rpcQuery(productParam, RpcTypeEnum.product_plan, Map.class).get(CommonConstant.DUN_CODE);
        log.debug("产品方案类型名称获取的dunCode为：{}", result);
        riskOrderBO.setProductSpecName(result);

        //2、获取产品属性名称
        productParam.clear();
        productParam.put("id", riskSysReviewEvent.getPlanValueId());
        result = (String) rpcComponent.rpcQuery(productParam, RpcTypeEnum.product_plan_value, Map.class).get(CommonConstant.DUN_CODE);
        log.debug("产品方案类型值获取的dunCode为：{}", result);
        riskOrderBO.setProductAttributeName(result);
        riskOrderBO.setOutEnerpriseId(riskSysReviewEvent.getCastEntepriseId());

        // 3、查询投保人属性
        productParam.clear();
        productParam.put("id", riskSysReviewEvent.getPolicyHolderId());
        result = (String) rpcComponent.rpcQuery(productParam, RpcTypeEnum.product_policy_holder, Map.class).get(CommonConstant.DUN_CODE);
        log.debug("投保人类型名称获取的dunCode为：{}", result);
        riskOrderBO.setPolicyHolderTypeName(result);

        // 4、查询投保人属性名称
        productParam.clear();
        productParam.put("id", riskSysReviewEvent.getPolicyHolderValueId());
        result = (String) rpcComponent.rpcQuery(productParam, RpcTypeEnum.product_policy_holder_value, Map.class).get(CommonConstant.DUN_CODE);
        log.debug("投保人类型值获取的dunCode为：{}", result);
        riskOrderBO.setPolicyHolderAttrName(result);

        // 5、组装企业附件信息
        if (CollectionUtils.isNotEmpty(riskSysReviewEvent.getEnterpriseFileList())) {
            List<ProjectReportBO> enterpriseReportBOList = Lists.newArrayList();
            for (EnterpriseFileFieldValue fileFieldValue : riskSysReviewEvent.getEnterpriseFileList()) {
                WordCodeEnum codeEnum = WordCodeEnum.getByCode(fileFieldValue.getCode());
                switch (codeEnum) {
                    case FINANCIAL_REPORT_INFO:
                        // 财务报表资料路径
                        riskOrderBO.setFinancialStatementsFilepaths(fileFieldValue.getAddress());
                        break;
                    case CREDIT_REPORT_INFO:
                        // 征信报告资料路径
                        riskOrderBO.setCreditReportFilepaths(fileFieldValue.getAddress());
                        break;
                    default:
                        break;
                }
                enterpriseReportBOList.add(buildEnterpriseReportBOList(fileFieldValue));
            }
            // 企业报告附件 给所有企业附件
            riskOrderBO.setEnterpriseList(enterpriseReportBOList);
        }

        // 6、组装投保企业文本信息
        for (EnterpriseTextFieldValue txtFieldValue : riskSysReviewEvent.getEnterpriseTxtList()) {
            WordCodeEnum codeEnum = WordCodeEnum.getByCode(txtFieldValue.getCode());
            CloudBO cloudBO = buildCloudBO(txtFieldValue.getMasterCode(), txtFieldValue.getClassificationCode(), txtFieldValue.getCode(), txtFieldValue.getValue());
            switch (codeEnum) {
                case ENTERPRISE_CODE:
                    // 投保企业社会信用代码
                    riskOrderBO.setPolicyHolderId(cloudBO);
                    break;
                case ENTERPRISE_NAME:
                    // 投保企业名称
                    riskOrderBO.setPolicyHolder(cloudBO);
                    break;
                case ENTERPRISES_ECONOMIC_NATURE:
                    // 企业类型【工保盾2.0.0】
                    if (StringUtils.isNotBlank(txtFieldValue.getValue())) {
                        if (StringUtils.isNotBlank(txtFieldValue.getContent())) {
                            List<ContentFieldBO> contentFieldBOList = JSON.parseArray(txtFieldValue.getContent(), ContentFieldBO.class);
                            String enterprisesTypeName = contentFieldBOList.stream().filter(c -> com.alibaba.druid.util.StringUtils.equals(c.getValue(), txtFieldValue.getValue())).findFirst().get().getLabel();
                            cloudBO.setValue(enterprisesTypeName);
                        }
                    }
                    riskOrderBO.setEnterpriseType(cloudBO);
                    break;
                case REGISTERED_CAPITAL:
                    // 企业注册资本【工保盾2.0.0】
                    riskOrderBO.setEnterpriseRegisteredCapital(cloudBO);
                    break;
                case CS_QUALIFICATION_LEVEL:
                    // 中标资质可以给企业资质使用【工保盾2.0.0】
                    riskOrderBO.setBidQualification(cloudBO);
                    break;
                default:
                    break;
            }
        }
        // 7、组装项目文本信息
        String projectName = StringUtils.EMPTY;
        if (CollectionUtils.isNotEmpty(riskSysReviewEvent.getProjectTxtList())) {
            for (ProjectTextFieldValue txtProjectValue : riskSysReviewEvent.getProjectTxtList()) {
                WordCodeEnum codeEnum = WordCodeEnum.getByCode(txtProjectValue.getCode());
                CloudBO cloudBO = buildCloudBO(txtProjectValue.getMasterCode(), txtProjectValue.getClassificationCode(), txtProjectValue.getCode(), txtProjectValue.getValue());
                switch (codeEnum) {
                    case BID_OPEN_TIME:
                        // 开标时间
                        riskOrderBO.setTimeOfBidOpening(cloudBO);
                        break;
                    case PROJECT_NAME:
                        // 项目名称
                        projectName = cloudBO.getValue();
                        riskOrderBO.setProjectName(cloudBO);
                        // TODO:暂无projectCode 使用projectName
                        CloudBO projectCodeBO = new CloudBO();
                        BeanUtils.copyProperties(cloudBO, projectCodeBO);
                        projectCodeBO.setFactorCode(WordCodeEnum.PROJECT_CODE.getCode());
                        riskOrderBO.setProjectCode(projectCodeBO);
                        break;
                    case PROJECY_TIME:
                        // 项目工期【工保盾2.0.0】
                        riskOrderBO.setProjectDuration(cloudBO);
                        break;
                    case PROJECT_CONST_NATURE:
                        // 项目性质【工保盾2.0.0】
                        riskOrderBO.setProjectNature(cloudBO);
                        break;
                    case PROJECT_ADD:
                        // 工程详细地址【工保盾2.0.0】
                        riskOrderBO.setProjectAddrDetail(cloudBO);
                        break;
                    case PROJECT_POSSESSION:
                        // 工程地址【工保盾2.0.0】
                        riskOrderBO.setProjectAddr(cloudBO);
                        break;
                    case EG_COST:
                        // 工程合同价/中标价【工保盾2.0.0】
                        riskOrderBO.setProjectContractPrice(cloudBO);
                        break;
                    case PROJECT_TYPES:
                        // 工程项目类型【工保盾2.0.0】
                        riskOrderBO.setProjectType(cloudBO);
                        if (StringUtils.isNotBlank(txtProjectValue.getValue())) {
                            riskOrderBO.setEstateDevValue(txtProjectValue.getValue());
                            if (StringUtils.isNotBlank(txtProjectValue.getContent())) {
                                List<ContentFieldBO> contentFieldBOList = JSON.parseArray(txtProjectValue.getContent(), ContentFieldBO.class);
                                String estateDevName = contentFieldBOList.stream().filter(c -> com.alibaba.druid.util.StringUtils.equals(c.getValue(), txtProjectValue.getValue())).findFirst().get().getLabel();
                                riskOrderBO.setEstateDevName(estateDevName);

                            }
                        }
                        break;
                    case START_DATE:
                        riskOrderBO.setProjectStartDate(DateUtil.parse(txtProjectValue.getValue()).toDateStr());
                        break;
                    default:
                        break;
                }
            }
        }
        // 8、组装项目附件信息
        if (CollectionUtils.isNotEmpty(riskSysReviewEvent.getProjectFileList())) {
            List<ProjectReportBO> projectReportBOList = Lists.newArrayList();
            for (ProjectFileFieldValue fileFieldValue : riskSysReviewEvent.getProjectFileList()) {
                projectReportBOList.add(buildProjectReportBOList(fileFieldValue));
            }
            // 项目报告附件 给所有项目附件
            riskOrderBO.setProjectList(projectReportBOList);
        }

        // 9、组装被保企业的文本信息
        if (CollectionUtils.isNotEmpty(riskSysReviewEvent.getInsuranceEnterpriseTxtList())) {
            for (EnterpriseTextFieldValue enterpriseTextFieldValue : riskSysReviewEvent.getInsuranceEnterpriseTxtList()) {
                WordCodeEnum codeEnum = WordCodeEnum.getByCode(enterpriseTextFieldValue.getCode());
                if (WordCodeEnum.ENTERPRISE_NAME.equals(codeEnum)) {
                    CloudBO cloudBO = buildCloudBO(enterpriseTextFieldValue.getMasterCode(), enterpriseTextFieldValue.getClassificationCode(), enterpriseTextFieldValue.getCode(), enterpriseTextFieldValue.getValue());
                    // 被保险人（被保险企业）
                    riskOrderBO.setTheInsured(cloudBO);
                }
            }
        } else {
            CloudBO cloudBO = buildCloudBO(null, null, null, projectName + "依法招收的农民工");
            // 被保险人（被保险企业）
            riskOrderBO.setTheInsured(cloudBO);
        }
        return riskOrderBO;
    }

    /**
     * 组装企业附件资料
     *
     * @param enterpriseFileFieldValue; 企业附件字段表
     * @return ProjectReportBO
     */
    private ProjectReportBO buildEnterpriseReportBOList(EnterpriseFileFieldValue enterpriseFileFieldValue) {
        return buildProjectReportBO(enterpriseFileFieldValue.getMasterCode(), enterpriseFileFieldValue.getClassificationCode(), enterpriseFileFieldValue.getCode(), enterpriseFileFieldValue.getKey(), enterpriseFileFieldValue.getAddress());
    }

    /**
     * 组装项目附件资料
     *
     * @param projectFileFieldValue; 项目附件字段表
     * @return ProjectReportBO
     */
    private ProjectReportBO buildProjectReportBOList(ProjectFileFieldValue projectFileFieldValue) {
        return buildProjectReportBO(projectFileFieldValue.getMasterCode(), projectFileFieldValue.getClassificationCode(), projectFileFieldValue.getCode(), projectFileFieldValue.getKey(), projectFileFieldValue.getAddress());
    }

    /**
     * 组装ProjectReportBO
     *
     * @param masterCode;         主数据码值
     * @param classificationCode; 分类码值
     * @param code;               因子编码
     * @param key；因子编码对应的值
     * @param address;            附件地址
     * @return ProjectReportBO
     */
    private ProjectReportBO buildProjectReportBO(String masterCode, String classificationCode, String code, String key, String address) {
        //1、项目资料名称
        CloudBO dataFileName = buildCloudBO(masterCode, classificationCode, code, key);
        //2、项目资料路径
        CloudBO creditReportFilepaths = buildCloudBO(masterCode, classificationCode, code, address);
        //3、组织项目资料BO
        ProjectReportBO projectReportBO = new ProjectReportBO();
        projectReportBO.setDataFileName(dataFileName);
        projectReportBO.setCreditReportFilepaths(creditReportFilepaths);
        return projectReportBO;
    }

    /**
     * 组装CloudBO
     *
     * @param masterCode;         主数据码值
     * @param classificationCode; 分类码值
     * @param code;               因子编码
     * @param value；因子编码对应的值
     * @return
     */
    private CloudBO buildCloudBO(String masterCode, String classificationCode, String code, String value) {
        CloudBO cloudBO = new CloudBO();
        cloudBO.setMasterDataId(masterCode);
        cloudBO.setMasterDataCategoryId(classificationCode);
        cloudBO.setFactorCode(code);
        cloudBO.setValue(StringUtils.trim(value));
        return cloudBO;
    }
}