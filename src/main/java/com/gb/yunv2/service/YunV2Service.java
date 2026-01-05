package com.gb.yunv2.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.common.entity.EnterpriseFileFieldValue;
import com.gb.common.entity.EnterpriseTextFieldValue;
import com.gb.common.entity.ProjectTextFieldValue;
import com.gb.rpc.InsuranceRpc;
import com.gb.utils.CreditCodeUtil;
import com.gb.utils.enumeration.WordCodeEnum;
import com.gb.yunv2.entity.enums.YunV2BoEnum;
import com.gb.yunv2.entity.enums.YunV2Enum;
import com.gb.yunv2.entity.vo.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ljh
 * @date 2022/7/29 9:45 上午
 */
@Slf4j
@Service
public class YunV2Service {

    @Resource
    private InsuranceRpc insuranceRpc;

    public void pullYunV2() {
        //  查询 企业
        List<Map> beUpdatedEnterpriseList = synchronizingEnterpriseData(1000);
        log.debug("同步企业数量={}", beUpdatedEnterpriseList.size());
        if (CollectionUtils.isNotEmpty(beUpdatedEnterpriseList)) {
            // 集合分片
            List<List<Map>> newList = Lists.partition(beUpdatedEnterpriseList, 100);
            // 打印分片集合
            newList.forEach(i -> {
                //  同步 企业
                insuranceRpc.saveOrUpdateYunEnterpriseData(i);
            });
        }


        //  查询 项目
        List<Map> beUpdatedProjectList = synchronizingProjectData(1000);
        log.debug("同步项目数量={}", beUpdatedProjectList.size());
        //  过滤项目重复的数据
        List<Map> filterBeUpdatedProjectList = filterBeUpdatedProjectList(beUpdatedProjectList);
        if (CollectionUtils.isNotEmpty(filterBeUpdatedProjectList)) {
            // 集合分片
            List<List<Map>> newList = Lists.partition(filterBeUpdatedProjectList, 100);
            // 打印分片集合
            newList.forEach(i -> {
                //  同步 项目
                insuranceRpc.saveOrUpdateYunProjectData(i);
            });
        }
    }


    /**
     * @createAuthor: 王一飞
     * @title: 过滤项目重复的数据
     * @createDate: 2022/1/13 1:45 下午
     * @description:
     * @return:
     */
    public List<Map> filterBeUpdatedProjectList(List<Map> beUpdatedProjectList) {
        //  根据项目名称进行分组
        Map<String, List<ProjectTextFieldValue>> projectNameExistMap = beUpdatedProjectList
                .stream()
                .filter(beUpdatedProject -> {
                    if (Objects.isNull(beUpdatedProject)) {
                        return Boolean.FALSE;
                    }
                    List<ProjectTextFieldValue> projectTextFieldValues = JSON.parseArray(JSON.toJSONString(
                            beUpdatedProject.get(YunV2BoEnum.字段.getName())), ProjectTextFieldValue.class);
                    if (CollectionUtils.isEmpty(projectTextFieldValues)) {
                        return Boolean.FALSE;
                    }
                    Optional<String> optional = projectTextFieldValues
                            .stream()
                            .filter(projectTextFieldValue ->
                                    WordCodeEnum.PROJECT_NAME.getCode().equals(projectTextFieldValue.getCode()))
                            .map(ProjectTextFieldValue::getValue)
                            .findFirst();
                    return optional.isPresent();
                })
                .map(beUpdatedProject -> {
                    List<ProjectTextFieldValue> projectTextFieldValues = JSON.parseArray(JSON.toJSONString(
                            beUpdatedProject.get(YunV2BoEnum.字段.getName())), ProjectTextFieldValue.class);
                    return projectTextFieldValues
                            .stream()
                            .filter(projectTextFieldValue ->
                                    WordCodeEnum.PROJECT_NAME.getCode().equals(projectTextFieldValue.getCode()))
                            .findFirst()
                            .get();
                })
                .collect(Collectors.groupingBy(ProjectTextFieldValue::getValue));

        //  过滤项目重复的数据
        List<Map> newList = beUpdatedProjectList
                .stream()
                .filter(map -> {
                    //  取出字段
                    Optional<String> projectNameOptional = JSON.parseArray(JSON.toJSONString(
                                    map.get(YunV2BoEnum.字段.getName())), ProjectTextFieldValue.class)
                            .stream()
                            .filter(text -> WordCodeEnum.PROJECT_NAME.getCode().equals(text.getCode()))
                            .map(ProjectTextFieldValue::getValue)
                            .findFirst();
                    if (!projectNameOptional.isPresent()) {
                        return false;
                    }
                    return projectNameExistMap.containsKey(projectNameOptional.get())
                            &&
                            projectNameExistMap.get(projectNameOptional.get()).size() <= 1;
                })
                .collect(Collectors.toList());
        return newList;
    }


    /**
     * @createAuthor: 王一飞
     * @title: 同步 企业
     * @createDate: 2022/1/10 4:44 下午
     * @description:
     * @return:
     */
    public List<Map> synchronizingEnterpriseData(Integer pageSize) {
        //  企业信息code
        List<String> enterpriseJsonPropertyValues = getJsonPropertyValue(YunV2EnterpriseEntityVo.class);
        //  企业基本信息code
        List<String> enterpriseMasterDataJsonPropertyValues =
                getJsonPropertyValue(YunV2EnterpriseMasterDataEntityVo.class);
        //  企业的基本账户信息code
        List<String> enterpriseBasicEnterpriseAccountInformationJsonPropertyValues =
                getJsonPropertyValue(YunV2EnterpriseBasicEnterpriseAccountInformationEntityVo.class);
        //  企业的建筑业企业资质、勘查、监理、设计资质code
        List<String> enterpriseQualificationInformationJsonPropertyValues =
                getJsonPropertyValue(YunV2EnterpriseQualificationInformationEntityVo.class);
        //  企业的文件信息
        List<String> enterpriseImageDataJsonPropertyValues = getJsonPropertyValue(YunV2EnterpriseImageDataEntityVo.class);

        //  查询企业
        HashMap<String, Object> enterpriseParam = new HashMap<String, Object>(2) {{
            put(YunV2BoEnum.类型.getName(), YunV2Enum.enterprise.getName());
            put(YunV2BoEnum.请求页行数.getName(), pageSize);
        }};
        Object enterpriseResult = new YunV2ProxyContext(JSON.toJSONString(enterpriseParam))
                .select(JSON.toJSONString(enterpriseParam), Boolean.TRUE);
        log.debug("拉取云数据,查询企业返回结果={}", JSON.toJSONString(enterpriseResult));
        //  转换企业类型
        return JSON.parseArray(JSON.toJSONString(enterpriseResult), YunV2EnterpriseEntityVo.class)
                .stream()
                .filter(newYunEnterpriseEntityVo ->
                        StringUtils.isNotBlank(newYunEnterpriseEntityVo.getUnifySocialCreditCodes())
                                &&
                                CreditCodeUtil.isCreditCode(newYunEnterpriseEntityVo.getUnifySocialCreditCodes()))
                .map(newYunEnterpriseEntityVo -> convertEnterpriseTextFieldValue(newYunEnterpriseEntityVo,
                        enterpriseJsonPropertyValues,
                        enterpriseMasterDataJsonPropertyValues,
                        enterpriseBasicEnterpriseAccountInformationJsonPropertyValues,
                        enterpriseQualificationInformationJsonPropertyValues,
                        enterpriseImageDataJsonPropertyValues))
                .collect(Collectors.toList());
    }


    /**
     * @param enterpriseEntityVo                                            ：获取数据来源
     * @param enterpriseJsonPropertyValues                                  ：企业信息code
     * @param enterpriseMasterDataJsonPropertyValues                        ：企业基本信息code
     * @param enterpriseBasicEnterpriseAccountInformationJsonPropertyValues ：企业的基本账户信息code
     * @param enterpriseQualificationInformationJsonPropertyValues          ：企业的建筑业企业资质、勘查、监理、设计资质code
     * @param enterpriseImageDataJsonPropertyValues                         ：企业的文件信息
     * @createAuthor: 王一飞
     * @title: 类型转换
     * @createDate: 2022/1/10 2:51 下午
     * @description:
     * @return:
     */
    public Map convertEnterpriseTextFieldValue(YunV2EnterpriseEntityVo enterpriseEntityVo,
                                               List<String> enterpriseJsonPropertyValues,
                                               List<String> enterpriseMasterDataJsonPropertyValues,
                                               List<String> enterpriseBasicEnterpriseAccountInformationJsonPropertyValues,
                                               List<String> enterpriseQualificationInformationJsonPropertyValues,
                                               List<String> enterpriseImageDataJsonPropertyValues) {
        Map<String, Object> resultMap = new HashMap<>(16);


        //  赋值字段
        List<EnterpriseTextFieldValue> enterpriseTextFieldValues = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(enterpriseJsonPropertyValues)) {
            //  企业信息code
            enterpriseTextFieldValues = enterpriseJsonPropertyValues
                    .stream()
                    .map(propertyValue -> YunV2EnterpriseEntityVo.convertToTextFieldValue(propertyValue, enterpriseEntityVo))
                    .collect(Collectors.toList());
        }
        List<EnterpriseTextFieldValue> enterpriseMasterDataTextFieldValues = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(enterpriseMasterDataJsonPropertyValues)) {
            //  企业基本信息code
            enterpriseMasterDataTextFieldValues = enterpriseMasterDataJsonPropertyValues
                    .stream()
                    .map(propertyValue -> YunV2EnterpriseMasterDataEntityVo.convertToTextFieldValue(propertyValue, enterpriseEntityVo))
                    .collect(Collectors.toList());
        }
        List<EnterpriseTextFieldValue> enterpriseBasicEnterpriseAccountInformationMasterDataTextFieldValues = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(enterpriseBasicEnterpriseAccountInformationJsonPropertyValues)) {
            //  企业的基本账户信息code
            enterpriseBasicEnterpriseAccountInformationMasterDataTextFieldValues = enterpriseBasicEnterpriseAccountInformationJsonPropertyValues
                    .stream()
                    .map(propertyValue -> YunV2EnterpriseBasicEnterpriseAccountInformationEntityVo.convertToTextFieldValue(propertyValue, enterpriseEntityVo))
                    .collect(Collectors.toList());
        }
        List<EnterpriseTextFieldValue> enterpriseQualificationInformationTextFieldValues = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(enterpriseQualificationInformationJsonPropertyValues)) {
            //  企业的建筑业企业资质、勘查、监理、设计资质code
            enterpriseQualificationInformationTextFieldValues = enterpriseQualificationInformationJsonPropertyValues
                    .stream()
                    .map(propertyValue -> YunV2EnterpriseQualificationInformationEntityVo.convertToTextFieldValue(propertyValue, enterpriseEntityVo))
                    .collect(Collectors.toList());
        }
        enterpriseTextFieldValues.addAll(enterpriseMasterDataTextFieldValues);
        enterpriseTextFieldValues.addAll(enterpriseBasicEnterpriseAccountInformationMasterDataTextFieldValues);
        enterpriseTextFieldValues.addAll(enterpriseQualificationInformationTextFieldValues);
        resultMap.put(YunV2BoEnum.字段.getName(), enterpriseTextFieldValues.stream().filter(Objects::nonNull).collect(Collectors.toList()));


        //  赋值附件
        List<EnterpriseFileFieldValue> enterpriseImageDataFileFieldValues = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(enterpriseImageDataJsonPropertyValues)) {
            //  企业的建筑业企业资质、勘查、监理、设计资质code
            enterpriseImageDataFileFieldValues = enterpriseImageDataJsonPropertyValues
                    .stream()
                    .map(propertyValue -> YunV2EnterpriseImageDataEntityVo.convertToTextFieldValue(propertyValue, enterpriseEntityVo))
                    .collect(Collectors.toList());
        }
        resultMap.put(YunV2BoEnum.附件.getName(), enterpriseImageDataFileFieldValues.stream().filter(Objects::nonNull).collect(Collectors.toList()));

        return resultMap;
    }


    /**
     * @createAuthor: 王一飞
     * @title: 查询 项目
     * @createDate: 2022/1/10 4:53 下午
     * @description:
     * @return:
     */
    public List<Map> synchronizingProjectData(Integer pageSize) {
        //  项目信息code
        List<String> projectJsonPropertyValues = getJsonPropertyValue(YunV2ProjectEntityVo.class);
        //  项目基本信息code
        List<String> projectMasterDataJsonPropertyValues = getJsonPropertyValue(YunV2ProjectMasterDataEntityVo.class);

        //  查询项目
        HashMap<String, Object> projectParam = new HashMap<String, Object>(2) {{
            put(YunV2BoEnum.类型.getName(), YunV2Enum.project.getName());
            put(YunV2BoEnum.请求页行数.getName(), pageSize);
        }};
        Object projectResult = new YunV2ProxyContext(JSON.toJSONString(projectParam))
                .select(JSON.toJSONString(projectParam), Boolean.TRUE);
        List<YunV2ProjectEntityVo> yunV2ProjectEntityVos = JSON.parseArray(JSON.toJSONString(projectResult), YunV2ProjectEntityVo.class);
        log.debug("拉取云数据,查询项目返回结果={}", yunV2ProjectEntityVos);

        //  转换项目类型
        return checkProject(yunV2ProjectEntityVos).stream()
                .map(newYunProjectEntityVo -> convertEnterpriseTextFieldValue(newYunProjectEntityVo,
                        projectJsonPropertyValues,
                        projectMasterDataJsonPropertyValues))
                .collect(Collectors.toList());
    }

    /**
     * 校验项目信息
     * 为云自定义的字段。 设置项目编号和标段编号为null
     *
     * @param yunV2ProjectEntityVos
     * @return
     */
    public List<YunV2ProjectEntityVo> checkProject(List<YunV2ProjectEntityVo> yunV2ProjectEntityVos) {
        String whetherBidNumberIsCustomized = "1";
        for (YunV2ProjectEntityVo yunV2ProjectEntityVo : yunV2ProjectEntityVos) {
            if (whetherBidNumberIsCustomized.equals(yunV2ProjectEntityVo.getWhetherBidNumberIsCustomized())) {
                log.info("为云自定义，修改前：{}", yunV2ProjectEntityVo.getProjectNumber());
                yunV2ProjectEntityVo.setProjectNumber(null);
                yunV2ProjectEntityVo.setBidNumber(null);
            }
        }
        return yunV2ProjectEntityVos;
    }


    /**
     * @param yunV2ProjectEntityVo                ：获取数据来源
     * @param projectJsonPropertyValues           ：项目信息code
     * @param projectMasterDataJsonPropertyValues ：项目基本信息code
     * @createAuthor: 王一飞
     * @title: 类型转换
     * @createDate: 2022/1/10 4:52 下午
     * @description:
     * @return:
     */
    public Map convertEnterpriseTextFieldValue(YunV2ProjectEntityVo yunV2ProjectEntityVo,
                                               List<String> projectJsonPropertyValues,
                                               List<String> projectMasterDataJsonPropertyValues) {
        Map<String, Object> resultMap = new HashMap<>(16);

        //  赋值字段
        List<ProjectTextFieldValue> projectTextFieldValues = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(projectJsonPropertyValues)) {
            //  项目信息code
            projectTextFieldValues = projectJsonPropertyValues
                    .stream()
                    .map(propertyValue -> YunV2ProjectEntityVo.convertToTextFieldValue(propertyValue, yunV2ProjectEntityVo))
                    .collect(Collectors.toList());
        }
        List<ProjectTextFieldValue> projectMasterDataTextFieldValues = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(projectMasterDataJsonPropertyValues)) {
            //  项目基本信息code
            projectMasterDataTextFieldValues = projectMasterDataJsonPropertyValues
                    .stream()
                    .map(propertyValue -> YunV2ProjectMasterDataEntityVo.convertToTextFieldValue(propertyValue, yunV2ProjectEntityVo))
                    .collect(Collectors.toList());
        }
        projectTextFieldValues.addAll(projectMasterDataTextFieldValues);
        resultMap.put(YunV2BoEnum.字段.getName(), projectTextFieldValues.stream().filter(Objects::nonNull).collect(Collectors.toList()));

        //  赋值附件
        resultMap.put(YunV2BoEnum.附件.getName(), new ArrayList<>());

        return resultMap;
    }


    /**
     * @createAuthor: 王一飞
     * @title: 获取类的code值（与网相对应，需要一一匹配）
     * @createDate: 2022/1/10 3:55 下午
     * @description:
     * @return:
     */
    public List<String> getJsonPropertyValue(Class clazz) {
        return Arrays.stream(clazz
                        .getDeclaredFields())
                .filter(jsonPropertyValue -> Objects.nonNull(jsonPropertyValue.getAnnotation(JsonProperty.class)))
                .map(jsonPropertyValue -> jsonPropertyValue.getAnnotation(JsonProperty.class).value())
                .collect(Collectors.toList());
    }
}
