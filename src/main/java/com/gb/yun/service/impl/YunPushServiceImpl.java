package com.gb.yun.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gb.common.constant.CommonConstant;
import com.gb.common.entity.*;
import com.gb.mq.yun.YunSyncDataEvent;
import com.gb.utils.enumeration.WordCodeEnum;
import com.gb.utils.exception.ParameterNullException;
import com.gb.yun.enmus.YunObjectTypeEnum;
import com.gb.yun.enmus.YunSyncDataTypeEnum;
import com.gb.yun.entity.vo.*;
import com.gb.yun.service.YunCommonService;
import com.gb.yun.service.YunPushService;
import com.gb.yun.service.YunService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.gb.yun.enmus.YunFunctionEnum.OBJECT_QUERY;


/**
 * 工保云推送服务接口实现类
 *
 * @author: sunx
 * @Date: 2021/03/22 23:30
 * @descript: 工保云推送服务接口实现类
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
@SuppressWarnings(value = "all")
public class YunPushServiceImpl implements YunPushService {

    private YunCommonService yunCommonService;

    private YunService yunService;

    @Override
    public Map<String, Object> queryProjectInfo(Project project) {
        //1、项目字段的业务类型组装
        FieldProjectRequestVO txtProjectRequestVO = null;
        List<ProjectTextFieldValue> txtList = project.getProjectTextFieldValueList();
        if (CollectionUtils.isNotEmpty(txtList)) {
            txtProjectRequestVO = new FieldProjectRequestVO();
            txtProjectRequestVO.setObjectCode("xmjbxx");
            txtProjectRequestVO.setProjectName(project.getCode());
            for (ProjectTextFieldValue txtField : txtList) {
                //地址
                String address = txtField.getAreaCode();
                if (StringUtils.isBlank(address)) {
                    address = StringUtils.isBlank(txtField.getCityCode()) ? txtField.getProvinceCode() : txtField.getCityCode();
                }
                //根据code获取对应的类型
                buildTxtProjectRequestVO(txtProjectRequestVO, txtField.getValue(), txtField.getCode(), address);
                txtProjectRequestVO.setMasterDataId(txtField.getMasterCode());
                txtProjectRequestVO.setCategoryId(txtField.getClassificationCode());
            }
        }
        //2、项目文件的业务类型组装
        FileProjectRequestVO fileProjectRequestVO = null;
        List<ProjectFileFieldValue> fileList = project.getProjectFileFieldValueList();
        if (CollectionUtils.isNotEmpty(fileList)) {
            fileProjectRequestVO = new FileProjectRequestVO();
            fileProjectRequestVO.setObjectCode("project_attachment");
            fileProjectRequestVO.setProjectName(project.getCode());
            for (ProjectFileFieldValue fileField : fileList) {
                //根据code获取对应的类型
                buildFileProjectRequestVO(fileProjectRequestVO, fileField.getCode(), fileField.getAddress());
                fileProjectRequestVO.setMasterDataId(fileField.getMasterCode());
                fileProjectRequestVO.setCategoryId(fileField.getClassificationCode());
            }
        }
        Map<String, Object> projectMap = Maps.newHashMap();
        projectMap.put(CommonConstant.TXT_PROJECT, txtProjectRequestVO);
        projectMap.put(CommonConstant.FILE_PROJECT, fileProjectRequestVO);
        return projectMap;
    }

    @Override
    public Map<String, Object> queryEnterpriseInfo(List<Enterprise> enterprisList) {
        List<FieldEnterpriseRequestVO> txtEnterpriseRequestVOList = Lists.newArrayList();
        List<FileEnterpriseRequestVO> fileEnterpriseRequestVOList = Lists.newArrayList();
        for (Enterprise enterprise : enterprisList) {
            //1、企业字段的业务类型组装
            List<EnterpriseTextFieldValue> txtList = enterprise.getEnterpriseTextFieldValueList();
            if (CollectionUtils.isNotEmpty(txtList)) {
                FieldEnterpriseRequestVO txtEnterpriseRequestVO = new FieldEnterpriseRequestVO();
                txtEnterpriseRequestVO.setObjectCode("business_information");
                for (EnterpriseTextFieldValue txtField : txtList) {
                    //地址
                    String address = txtField.getAreaCode();
                    if (StringUtils.isBlank(address)) {
                        address = StringUtils.isBlank(txtField.getCityCode()) ? txtField.getProvinceCode() : txtField.getCityCode();
                    }
                    //根据code获取对应的类型
                    buildTxtEnterpriseRequestVO(txtEnterpriseRequestVO, txtField.getValue(), txtField.getCode(), address);
                    txtEnterpriseRequestVO.setMasterDataId(txtField.getMasterCode());
                    txtEnterpriseRequestVO.setCategoryId(txtField.getClassificationCode());
                }
                txtEnterpriseRequestVOList.add(txtEnterpriseRequestVO);
            }
            //2、企业附件的业务类型组装
            List<EnterpriseFileFieldValue> fileList = enterprise.getEnterpriseFileFieldValueList();
            if (CollectionUtils.isNotEmpty(fileList)) {
                FileEnterpriseRequestVO fileEnterpriseRequestVO = new FileEnterpriseRequestVO();
                fileEnterpriseRequestVO.setObjectCode("bo_image_data_info");
                for (EnterpriseFileFieldValue fileField : fileList) {
                    fileEnterpriseRequestVO.setEnterpriseName(enterprise.getName());
                    //根据code获取对应的类型
                    buildFileEnterpriseRequestVO(fileEnterpriseRequestVO, fileField.getCode(), fileField.getAddress());
                    fileEnterpriseRequestVO.setMasterDataId(fileField.getMasterCode());
                    fileEnterpriseRequestVO.setCategoryId(fileField.getClassificationCode());
                }
                fileEnterpriseRequestVOList.add(fileEnterpriseRequestVO);
            }
        }
        Map<String, Object> enterpriseMap = Maps.newHashMap();
        enterpriseMap.put(CommonConstant.TXT_ENTERPRISE, txtEnterpriseRequestVOList);
        enterpriseMap.put(CommonConstant.FILE_ENTERPRISE, fileEnterpriseRequestVOList);
        return enterpriseMap;
    }

    @Override
    public Map<String, String> pushCloud(String linkId, String linkName, YunObjectTypeEnum objectTypeEnum, Object txtProject) throws Exception {
        //1、先查询请求参数是否已经存在
        Map<String, Object> requestMap = JSONObject.parseObject(JSON.toJSONString(txtProject));
        Object findObj = yunCommonService.functionSend(OBJECT_QUERY, new ApiRequestVO() {{
            setLinkId(linkId);
            setLinkName(linkName);
            setObjectCode((String) requestMap.get("objectCode"));
            setCreditCode((String) requestMap.get("credit_code"));
            setEnterpriseName((String) requestMap.get("enterpriseName"));
            setProjectName((String) requestMap.get("projectName"));
            setCategoryId((String) requestMap.get("categoryId"));
            setMasterDataId((String) requestMap.get("masterDataId"));
        }});
        boolean isUpdate = Objects.nonNull(findObj);
        YunSyncDataTypeEnum typeEnum = getYunSyncDataTypeEnum(objectTypeEnum, isUpdate);
        //2、如果项目信息的linkId和linkName不存在，则推送云新增，否则推送云修改
        Map<String, Object> resultMap = Maps.newHashMap();
        String reqObjJson = StringUtils.EMPTY;
        if (!isUpdate) {
            Object reqObj = buildObject(typeEnum, txtProject, linkName, linkId, null);
            reqObjJson = JSON.toJSONString(reqObj);
            log.debug("【工保云】{}到云：{}", typeEnum.getName(), reqObjJson);
            resultMap = yunCommonService.validateProcess(new Function<String, String>() {
                @SneakyThrows
                @Override
                public String apply(String token) {
                    return yunService.fieldInsert(token, reqObj);
                }
            });
        } else {
            List<Map<String, Object>> resultList = JSON.parseObject(JSON.toJSONString(findObj), new TypeReference<List<Map<String, Object>>>() {
            });
            Object reqObj = buildObject(typeEnum, txtProject, linkName, linkId, (String) resultList.get(0).get("id"));
            reqObjJson = JSON.toJSONString(reqObj);
            log.debug("【工保云】{}到云：{}", typeEnum.getName(), reqObjJson);
            resultMap = yunCommonService.validateProcess(new Function<String, String>() {
                @SneakyThrows
                @Override
                public String apply(String token) {
                    return yunService.fieldUpdate(token, reqObj);
                }
            });
        }
        log.debug("【工保云】{}到云结束。请求参数：{}，工保云响应结果：{}", typeEnum.getName(), reqObjJson, JSON.toJSONString(resultMap));
        String errorMsg = StringUtils.EMPTY;
        if (!StringUtils.equals((String) resultMap.get(CommonConstant.STATUS), CommonConstant.SUCCESS)) {
            log.error("【工保云】{}异常：{}", typeEnum.getName(), resultMap.get(CommonConstant.MSG_YUN));
            errorMsg = String.format("【工保云】%s异常", typeEnum.getName());
            if (Objects.nonNull(resultMap.get(CommonConstant.MSG_YUN)) && StringUtils.isNotBlank(String.valueOf(resultMap.get(CommonConstant.MSG_YUN)))) {
                errorMsg = errorMsg + "：" + resultMap.get(CommonConstant.MSG_YUN);
            }
        }
        Map<String, String> returnMap = Maps.newHashMap();
        returnMap.put("typeCode", typeEnum.getCode());
        returnMap.put("errorMsg", errorMsg);
        returnMap.put("resultMap", JSON.toJSONString(resultMap));
        return returnMap;
    }

    /**
     * 获取推送工保云的业务类型
     *
     * @param objectTypeEnum: 对象类型
     * @param isUpdate:       是否是更新操作
     * @return YunSyncDataTypeEnum
     * @author 孙凯伦
     * @since 2021-04-20
     */
    private YunSyncDataTypeEnum getYunSyncDataTypeEnum(YunObjectTypeEnum objectTypeEnum, boolean isUpdate) {
        YunSyncDataTypeEnum dataTypeEnum = null;
        switch (objectTypeEnum) {
            case PROJECT:
                if (isUpdate) {
                    dataTypeEnum = YunSyncDataTypeEnum.PJ_TXT_UPDATE;
                } else {
                    dataTypeEnum = YunSyncDataTypeEnum.PJ_TXT_ADD;
                }
                break;
            case ENTERPRISE:
                if (isUpdate) {
                    dataTypeEnum = YunSyncDataTypeEnum.EN_TXT_UPATE;
                } else {
                    dataTypeEnum = YunSyncDataTypeEnum.EN_TXT_ADD;
                }
                break;
            case PJ_FILE:
                if (isUpdate) {
                    dataTypeEnum = YunSyncDataTypeEnum.PJ_FILE_UPDATE;
                } else {
                    dataTypeEnum = YunSyncDataTypeEnum.PJ_FILE_ADD;
                }
                break;
            case EN_FILE:
                if (isUpdate) {
                    dataTypeEnum = YunSyncDataTypeEnum.EN_FILE_UPDATE;
                } else {
                    dataTypeEnum = YunSyncDataTypeEnum.EN_FILE_ADD;
                }
                break;
            default:
                break;
        }
        return dataTypeEnum;
    }

    @Override
    public void validateParams(YunSyncDataEvent event) {
        if (Objects.isNull(event)) {
            throw new ParameterNullException("请求参数不能为空！");
        }
        if (StringUtils.isBlank(event.getContentId())) {
            throw new ParameterNullException("投保内容ID不能为空！");
        }
        if (CollectionUtils.isEmpty(event.getEnterprisList())) {
            throw new ParameterNullException("企业列表不能为空！");
        }

    }

    /**
     * 组织推送工保云的对象
     *
     * @param typeEnum: 对象类型
     * @param reqObj:   请求对象Obj
     * @param linkName: 关联对象名称
     * @param linkId:   关联对象序列
     * @param id:       工保云序列
     * @return Object
     * @author 孙凯伦
     * @since 2021-04-20
     */
    private Object buildObject(YunSyncDataTypeEnum typeEnum, Object reqObj, String linkName, String linkId, String id) {
        Object obj = reqObj;
        switch (typeEnum) {
            case EN_TXT_UPATE:
            case EN_TXT_ADD:
                FieldEnterpriseRequestVO txtEnterprise = (FieldEnterpriseRequestVO) reqObj;
                if (StringUtils.isNotBlank(id)) {
                    txtEnterprise.setId(id);
                }
                if (StringUtils.isNotBlank(linkId)) {
                    txtEnterprise.setLinkId(linkId);
                }
                if (StringUtils.isNotBlank(linkName)) {
                    txtEnterprise.setLinkName(linkName);
                }
                obj = txtEnterprise;
                break;
            case PJ_TXT_UPDATE:
            case PJ_TXT_ADD:
                FieldProjectRequestVO txtProject = (FieldProjectRequestVO) reqObj;
                if (StringUtils.isNotBlank(id)) {
                    txtProject.setId(id);
                }
                if (StringUtils.isNotBlank(linkId)) {
                    txtProject.setLinkId(linkId);
                }
                if (StringUtils.isNotBlank(linkName)) {
                    txtProject.setLinkName(linkName);
                }
                obj = txtProject;
                break;
            case EN_FILE_UPDATE:
            case EN_FILE_ADD:
                FileEnterpriseRequestVO fileEnterprise = (FileEnterpriseRequestVO) reqObj;
                if (StringUtils.isNotBlank(id)) {
                    fileEnterprise.setId(id);
                }
                if (StringUtils.isNotBlank(linkId)) {
                    fileEnterprise.setLinkId(linkId);
                }
                if (StringUtils.isNotBlank(linkName)) {
                    fileEnterprise.setLinkName(linkName);
                }
                obj = fileEnterprise;
                break;
            case PJ_FILE_UPDATE:
            case PJ_FILE_ADD:
                FileProjectRequestVO fileProject = (FileProjectRequestVO) reqObj;
                if (StringUtils.isNotBlank(id)) {
                    fileProject.setId(id);
                }
                if (StringUtils.isNotBlank(linkId)) {
                    fileProject.setLinkId(linkId);
                }
                if (StringUtils.isNotBlank(linkName)) {
                    fileProject.setLinkName(linkName);
                }
                obj = fileProject;
                break;
            default:
                break;
        }
        return obj;
    }

    /**
     * 组织项目附件VO
     *
     * @param fileProjectRequestVO: 项目附件VO
     * @param code:                 项目附件CODE
     * @param address:              项目附件地址
     * @return void
     * @author sunx
     * @since 2021-03-18
     */
    private void buildFileProjectRequestVO(FileProjectRequestVO fileProjectRequestVO, String code, String address) {
        //根据code获取对应的类型
        WordCodeEnum wordCodeEnum = WordCodeEnum.getByCode(code);
        switch (wordCodeEnum) {
            case BIDDING_DOCUMENT:
                if (StringUtils.isNotBlank(address)) {
                    fileProjectRequestVO.setBiddingDocument(address);
                }
                break;
            case BIDDING_NOTICE:
                if (StringUtils.isNotBlank(address)) {
                    fileProjectRequestVO.setBiddingNotice(address);
                }
                break;
            case CONSTRUCTION_CONTRACT:
                if (StringUtils.isNotBlank(address)) {
                    fileProjectRequestVO.setConstructionContract(address);
                }
                break;
            case WORKERS:
                if (StringUtils.isNotBlank(address)) {
                    fileProjectRequestVO.setWorkers(address);
                }
                break;
            case SCHEDULE_FORM:
                if (StringUtils.isNotBlank(address)) {
                    fileProjectRequestVO.setScheduleForm(address);
                }
                break;
            case OW_STRUCTURE_CERTIFICATE:
                if (StringUtils.isNotBlank(address)) {
                    fileProjectRequestVO.setOwStructureCertificate(address);
                }
                break;
            default:
                log.debug("code码值：【" + code + "】，未知项目附件类型");
                break;
        }
    }

    /**
     * 组织项目字段VO
     *
     * @param txtProjectRequestVO: 项目字段VO
     * @param value:               项目字段值
     * @param code:                项目字段CODE
     * @param address:             地址
     * @return void
     * @author sunx
     * @since 2021-03-18
     */
    private void buildTxtProjectRequestVO(FieldProjectRequestVO txtProjectRequestVO, String value, String code, String address) {
        //根据code获取对应的类型
        WordCodeEnum wordCodeEnum = WordCodeEnum.getByCode(code);
        switch (wordCodeEnum) {
            case BID_OPEN_TIME:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setBidOpenTime(DateUtil.parse(value, DatePattern.NORM_DATE_PATTERN));
                }
                break;
            case TENDER_AMOUNT:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setTenderAmount(new BigDecimal(value));
                }
                break;
            case PROJECT_CODE:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setXmbh(value);
                }
                break;
            case PROJECT_NAME:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setProjectName(value);
                }
                break;
            case BDBH:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setBdbh(value);
                }
                break;
            case BDMC:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setBdmc(value);
                }
                break;
            case PROJECT_ADD:
                //项目注册地址：不要以工保云的界面操作为准，直接填写码值，不然影响到工保盾那边的操作
                if (StringUtils.isNotBlank(address)) {
                    txtProjectRequestVO.setProjectAdd(address);
                }
                break;
            case ZBR:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setZbr(value);
                }
                break;
            case ZBLXR:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setZbrlxr(value);
                }
                break;
            case ZBRPHONE:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setZbrPhone(value);
                }
                break;
            case TENDEREE_CODE:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setTendereeCode(value);
                }
                break;
            case TENDERDAY:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setTenderDay(Integer.parseInt(value));
                }
                break;
            case ZBRDZ:
                //招标人地址：不要以工保云的界面操作为准，直接填写码值，不然影响到工保盾那边的操作
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setZbrdz(value);
                }
                break;
            case START_DATE:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setStartDate(DateUtil.parse(value, DatePattern.NORM_DATE_PATTERN));
                }
                break;
            case END_DATE:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setEndDate(DateUtil.parse(value, DatePattern.NORM_DATE_PATTERN));
                }
                break;
            case PROJECY_TIME:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setProjectTime(Integer.parseInt(value));
                }
                break;
            case CLOSE_TIME:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setCloseTime(DateUtil.parse(value, DatePattern.NORM_DATE_PATTERN));
                }
                break;
            case CONTRACT_NO:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setContractNo(value);
                }
                break;
            case CONTRACT_DATE:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setContractDate(DateUtil.parse(value, DatePattern.NORM_DATE_PATTERN));
                }
                break;
            case PROJECT_DOCUMENT:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setProjectDocument(value);
                }
                break;
            case PROJECT_CONT:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setProjectCont(value);
                }
                break;
            case PROJECT_CONST_NATURE:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setProjectConstNature(value);
                }
                break;
            case EG_COST:
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setEgCost(value);
                }
                break;
            case PROJECT_POSSESSION:
                //项目属地：不要以工保云的界面操作为准，直接填写码值，不然影响到工保盾那边的操作
                if (StringUtils.isNotBlank(address)) {
                    txtProjectRequestVO.setProjectPossesion(address);
                }
                break;
            case CONTRACTING_TYPE:
                //承包属性
                if (StringUtils.isNotBlank(value)) {
                    txtProjectRequestVO.setContractingType(value);
                }
                break;
            default:
                log.debug("code码值：【" + code + "】，未知项目字段类型");
                break;
        }
    }

    /**
     * 组织企业字段VO
     *
     * @param txtEnterpriseRequestVO: 企业字段VO
     * @param value:                  项目字段值
     * @param code:                   项目字段CODE
     * @param address:                地址
     * @return FieldProjectRequestVO
     * @author sunx
     * @since 2021-03-18
     */
    private void buildTxtEnterpriseRequestVO(FieldEnterpriseRequestVO txtEnterpriseRequestVO, String value, String code, String address) {
        //根据code获取对应的类型
        WordCodeEnum wordCodeEnum = WordCodeEnum.getByCode(code);
        switch (wordCodeEnum) {
            case ENTERPRISE_NAME:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setEnterpriseName(value);
                }
                break;
            case ENTERPRISE_CODE:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setCreditCode(value);
                }
                break;
            case REG_ADDRESS:
                //企业注册地址：不要以工保云的界面操作为准，直接填写码值，不然影响到工保盾那边的操作
                if (StringUtils.isNotBlank(address)) {
                    txtEnterpriseRequestVO.setRegAddress(address);
                }
                break;
            case LEGAL_PERSON:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setLegalPerson(value);
                }
                break;
            case BUSINESS_ADDRESS:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setBusinessAddress(value);
                }
                break;
            case NATURE_BUSINESS:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setNatureBusiness(value);
                }
                break;
            case ORGAINZE_CODE:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setOrganizationCode(value);
                }
                break;
            case REGISTRATION_AUTHORITY:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setRegistrationAuthority(value);
                }
                break;
            case PERSONNEL_SIZE:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setPersonnelSize(Integer.parseInt(value));
                }
                break;
            case INDUSTRY:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setIndustry(value);
                }
                break;
            case CONTACTS:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setContacts(value);
                }
                break;
//            case CONTACT_NUMBER:
//                if(StringUtils.isNotBlank(value)){
//                    txtEnterpriseRequestVO.setContactNumber(value);
//                }
//                break;
            case REGISTERED_CAPITAL:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setRegisteredCapital(new BigDecimal(value));
                }
                break;
            case FAREN_CODE:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setFarenCode(value);
                }
                break;
            case OWNERSHIP_STRUCTURE:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setOwnershipStructure(value);
                }
                break;
            case DETAIL_ADDRESS:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setDetailAddress(value);
                }
                break;
            case BUSINESS_STATUS:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setBusinessStatus(value);
                }
                break;
            case REG_CAPITAL:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setRegCapital(new BigDecimal(value));
                }
                break;
            case NATURE:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setNature(value);
                }
                break;
            case APPLICANT_BUSINESS:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setApplicantBusiness(value);
                }
                break;
            case BRIEF_INTRODUCTION:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setBriefIntroduction(value);
                }
                break;
            case CS_QUALIFICATION_LEVEL:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setCsQualificationLevel(value);
                }
                break;
            case ENTERPRISES_ECONOMIC_NATURE:
                if (StringUtils.isNotBlank(value)) {
                    txtEnterpriseRequestVO.setEnterprisesEconomicNature(value);
                }
                break;
            default:
                log.debug("code码值：【" + code + "】，未知企业字段类型");
                break;
        }
    }

    /**
     * 组织企业附件VO
     *
     * @param fileEnterpriseRequestVO: 企业附件VO
     * @param code:                    企业附件CODE
     * @param address:                 企业附件地址
     * @return void
     * @author sunx
     * @since 2021-03-18
     */
    private void buildFileEnterpriseRequestVO(FileEnterpriseRequestVO fileEnterpriseRequestVO, String code, String address) {
        //根据code获取对应的类型
        WordCodeEnum wordCodeEnum = WordCodeEnum.getByCode(code);
        switch (wordCodeEnum) {
            case CREDIT_REPORT_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setCreditReport(address);
                }
                break;
            case FINANCIAL_REPORT_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setFinancialAuditReport(address);
                }
                break;
            case BUSINESS_LICENSE_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setBusinessLicenseInfo(address);
                }
                break;
            case PROJECT_PERFORMAMCE_LIST_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setProjectPerformanceList(address);
                }
                break;
            case FINANCIAL_ONE_REPORT:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setFinancialReportsOneYear(address);
                }
                break;
            case CORPORATE_ID_CARD_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setCorporateIdCardInfo(address);
                }
                break;
            case CORPORATE_ID_CARD_SIDE:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setCorporateIdCardInfoSide(address);
                }
                break;
            case ENTERPRISE__EVOLUTION_FORM_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setEnterpriseEvolutionForm(address);
                }
                break;
            case POWER_ATTORNEY_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setPowerAttorneyInfo(address);
                }
                break;
            case ARTICLES_OF_ASSOCIATION_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setArticlesOfAssociation(address);
                }
                break;
            case RELEVANT_ASSERT_CERTIFICATE_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setRelevantAssetCertificate(address);
                }
                break;
            case BANK_FLOW_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setBankFlowInfo(address);
                }
                break;
            case QUALIFE_CERT_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setQualifeCertInfo(address);
                }
                break;
            case TAX_CERT_ONE_YEAR_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setTaxCertOneYear(address);
                }
                break;
            case ENTERPRISE_QUALIFE_FORM_INFO:
                if (StringUtils.isNotBlank(address)) {
                    fileEnterpriseRequestVO.setEnterpriseQualifeForm(address);
                }
                break;
            default:
                log.debug("code码值：【" + code + "】，未知企业附件类型");
                break;
        }
    }
}