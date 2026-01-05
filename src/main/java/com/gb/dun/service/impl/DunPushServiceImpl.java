package com.gb.dun.service.impl;

import com.alibaba.fastjson.JSON;
import com.gb.bean.GongBaoConfig;
import com.gb.bean.RabbitConfig;
import com.gb.common.constant.UrlConstant;
import com.gb.dun.annotation.DunLog;
import com.gb.dun.annotation.DynamicDataSource;
import com.gb.dun.enmus.DunDataSourceEnum;
import com.gb.dun.enmus.DunPushTypeEnum;
import com.gb.dun.enmus.DunRiskViewEnum;
import com.gb.dun.enmus.RiskRiewBusinessTypeEnum;
import com.gb.dun.entity.bo.*;
import com.gb.dun.entity.vo.DunResponseVO;
import com.gb.dun.service.DunPushService;
import com.gb.dun.service.DunRiskViewService;
import com.gb.mq.dun.*;
import com.gb.utils.DynamicSourceTtl;
import com.gb.utils.OkhttpUtils;
import com.gb.utils.RsaUtils;
import com.gb.utils.enumeration.HttpType;
import com.gb.utils.enumeration.HttpWay;
import com.gb.utils.enumeration.SystemSourceEnum;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


/**
 * 推送盾服务类接口实现类
 *
 * @author yyl
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
@DynamicDataSource
@SuppressWarnings(value = "all")
public class DunPushServiceImpl implements DunPushService {

    private DunRiskViewService dunRiskViewService;

    private RabbitTemplate rabbitTemplate;

    @DunLog
    @Override
    public DunResponseVO dealBusiness(DunPushEvent dunPushEvent) {
        Object objectBO = null;
        DunResponseVO dunResponseVO = new DunResponseVO();
        String voKey = "data";
        try {
            validateParams(dunPushEvent);
            switch (dunPushEvent.getPushType()) {
                case DUN_RISKVIEW_TYPE:
                    dunResponseVO.setOptionType(DunRiskViewEnum.RISKVIEW_A.getCode());
                    RiskOrderBO riskOrderBO = dunRiskViewService.buildRiskOrderBO((DunRiskSysReviewEvent) dunPushEvent);
                    dunResponseVO.setCastId(riskOrderBO.getPolicyNumber());
                    if (Objects.isNull(riskOrderBO.getRiskBusinessType())) {
                        riskOrderBO.setRiskBusinessType(RiskRiewBusinessTypeEnum.RISKRIEW_BEFORE_INSURANCE.getCode());
                    }
                    if (StringUtils.isNotBlank(riskOrderBO.getRiskControlNumber())) {
                        dunResponseVO.setOptionType(DunRiskViewEnum.RISKVIEW_U.getCode());
                        dunResponseVO.setRiskControlNumber(riskOrderBO.getRiskControlNumber());
                    }
                    riskOrderBO.setInsuranceModel(false);
                    riskOrderBO.setCounterGuaranteeMeasures(1);
                    objectBO = riskOrderBO;
                    voKey = getVoKey(objectBO);
                    break;
                case DUN_SURRENDER_TYPE:
                    //退保信息
                    objectBO = new RoSurrenderRecordBO();
                    BeanUtils.copyProperties(dunPushEvent, objectBO);
                    voKey = getVoKey(objectBO);
                    break;
                case DUN_UNDERWRITING_TYPE:
                    //承保结果
                    objectBO = new RoUnderwritingRefusalBO();
                    BeanUtils.copyProperties(dunPushEvent, objectBO);
                    voKey = getVoKey(objectBO);
                    break;
                case DUN_ASSOCIATE_ORDER_TYPE:
                    //关联订单信息
                    objectBO = new RoAssociatedOrderBO();
                    BeanUtils.copyProperties(dunPushEvent, objectBO);
                    voKey = getVoKey(objectBO);
                    break;
                case DUN_INSURANCE_INFO_TYPE:
                    //保司保单文件信息
                    objectBO = new PolicyInfo();
                    BeanUtils.copyProperties(dunPushEvent, objectBO);
                    break;
                case DUN_UNDERWRITING_REFUSAL_RESULTS_TYPE:
                    //保司承保详细结果
                    objectBO = new PolicyResult();
                    BeanUtils.copyProperties(dunPushEvent, objectBO);
                    break;
                default:
                    break;
            }
            Map<String, Object> requestDunMap = Maps.newHashMap();
            requestDunMap.put(voKey, objectBO);
            String objs = objectBO.toString();
            log.debug("加签前参数对象toString={}", objs);
            requestDunMap.put("sign", RsaUtils.generateSignOnly(objs, GongBaoConfig.privateKey));
            requestDunMap.put("appId", GongBaoConfig.gongbaoDunAppId);
            dunResponseVO.setReqObject(requestDunMap);
            //推送工保盾
            String respJson = sendDun(dunPushEvent.getPushType(), JSON.toJSONString(requestDunMap));
            dunResponseVO.setRespJson(respJson);
            //风控审核的时候需要mq投保系统
            if (StringUtils.equals(dunPushEvent.getPushType().getName(), DunPushTypeEnum.DUN_RISKVIEW_TYPE.getName())) {
                Map<String, Object> dataMap = (Map<String, Object>) dealResultJson(respJson, dunPushEvent.getPushType().getCode());
                String riskControlNumber = (String) dataMap.get("riskControlNumber");
                dunResponseVO.setRiskControlNumber(riskControlNumber);
                if (StringUtils.isBlank(riskControlNumber)) {
                    throw new BusinessException("盾风控审核返回风控单号为空！");
                }
                //6、将风控单号返回给投保业务
                DunSyncResponseEvent resultEvent = new DunSyncResponseEvent();
                resultEvent.setCastInsuranceId(dunResponseVO.getCastId());
                resultEvent.setRiskOrderId(riskControlNumber);
                dunResponseVO.setMqObject(resultEvent);
                log.debug("开始推送消息到投保系统，{}", JSON.toJSONString(resultEvent));
                rabbitTemplate.convertAndSend(RabbitConfig.DUN_RISKREVIEW_SYNC_Q, resultEvent);
                log.debug("推送消息到投保系统完毕");
            }
        } catch (Exception e) {
            log.error("{}消息异常：", dunPushEvent.getPushType().getCode(), e);
            String errorMsg = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : dunPushEvent.getPushType().getCode() + "业务处理异常";
            dunResponseVO.setErrorMsg(errorMsg);
            throw e;
        } finally {
            return dunResponseVO;
        }
    }

    /**
     * 获取请求对象key
     *
     * @param objectBO
     * @return
     */
    private String getVoKey(Object objectBO) {
        String typeName = objectBO.getClass().getTypeName();
        String vo = typeName.substring((typeName.lastIndexOf(".") + 1));
        vo = vo.substring(0, 1).toLowerCase() + vo.substring(1);
        return vo;
    }

    /**
     * 发送到工保盾
     *
     * @param dunPushTypeEnum 业务功能枚举类
     * @param reqJson         请求工保盾的参数
     * @return String
     */
    private String sendDun(DunPushTypeEnum dunPushTypeEnum, String reqJson) {
        Map<String, String> headerMap = Maps.newHashMap();
        String env = StringUtils.equals(DynamicSourceTtl.get(), DynamicSourceTtl.SLAVE_DATASOURCE) ? DunDataSourceEnum.TEST_DATASOURCE.getCode() : DunDataSourceEnum.PRIMARY_DATASOURCE.getCode();
        headerMap.put("env", env);
        if (StringUtils.equals(dunPushTypeEnum.getCode(), DunPushTypeEnum.DUN_RISKVIEW_TYPE.getCode())) {
            headerMap.put("api_version", GongBaoConfig.dunVersion);
        }
        Request.Builder builder = new Request.Builder();
        builder.addHeader("Gongbao-Origin", SystemSourceEnum.GB_N.getCode());
        for (Map.Entry<String, String> header : headerMap.entrySet()) {
            builder.addHeader(header.getKey(), header.getValue());
        }
        String url = UrlConstant.getDunUrl(dunPushTypeEnum.getDunUrl());
        try {
            log.debug("工保盾{}API【{}】，访问开始！请求参数：{}，请求头参数：{}", dunPushTypeEnum.getCode(), url, reqJson, JSON.toJSONString(headerMap));
            String respJson = OkhttpUtils.send(builder, HttpWay.POST, url, reqJson, HttpType.JSON).string();
            log.debug("工保盾{}API【{}】，访问完毕！请求参数：{}，请求头参数：{}，响应结果：{}", dunPushTypeEnum.getCode(), url, reqJson, JSON.toJSONString(headerMap), respJson);
            return respJson;
        } catch (Exception e) {
            log.error("工保盾{}API【{}】，访问异常：", dunPushTypeEnum.getCode(), url, e);
            throw new BusinessException("工保盾" + dunPushTypeEnum.getCode() + "API【" + url + "】，访问异常！");
        }
    }

    /**
     * 工保盾返回结果解析
     *
     * @param respJson 盾返回结果
     * @param desc     功能描述
     * @return Object
     */
    private Object dealResultJson(String respJson, String desc) {
        log.debug("开始解析盾{}的返回结果：{}", desc, respJson);
        Map<String, Object> resultMap = JSON.parseObject(respJson, Map.class);
        if (MapUtils.isEmpty(resultMap)) {
            throw new BusinessException("盾" + desc + "业务处理异常，无任何返回！");
        }
        Object error = (null == resultMap.get("error")) ? resultMap.get("message") : resultMap.get("error");
        Object dataObject = resultMap.get("data");
        boolean isycFlag = ((null != error) && (!StringUtils.equals(String.valueOf(error), "成功")));
        if (isycFlag || null == dataObject) {
            String errorMsg = (null == error) ? (desc + "异常") : (String) error;
            throw new BusinessException(errorMsg);
        }
        Object resultObj = resultMap.get("data");
        log.debug("解析完毕：{}，返回给请求方结果：{}", desc, resultObj);
        return resultObj;
    }

    /**
     * 对请求参数值进行校验
     *
     * @author yyl
     */
    private void validateParams(Object obj) throws Exception {
        try {
            String result = StringUtils.EMPTY;
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                String message = (null == (f.getAnnotation(NotNull.class)))
                        ? (null == f.getAnnotation(NotBlank.class) ? StringUtils.EMPTY : f.getAnnotation(NotBlank.class).message())
                        : f.getAnnotation(NotNull.class).message();
                if (StringUtils.isBlank(message)) {
                    continue;
                }
                if (null == f.get(obj)) {
                    throw new ParameterNullException(message);
                }
            }
        } catch (Exception e) {
            log.error("参数校验异常：", e);
            throw e;
        }
    }
}