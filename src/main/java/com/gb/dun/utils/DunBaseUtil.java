package com.gb.dun.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gb.common.constant.UrlConstant;
import com.gb.dun.enmus.DunDataSourceEnum;
import com.gb.dun.enmus.DunPushTypeEnum;
import com.gb.dun.entity.vo.DunRespBaseVO;
import com.gb.utils.DynamicSourceTtl;
import com.gb.utils.JsonUtil;
import com.gb.utils.OkhttpUtils;
import com.gb.utils.enumeration.HttpType;
import com.gb.utils.enumeration.HttpWay;
import com.gb.utils.enumeration.SystemSourceEnum;
import com.gb.utils.exception.BusinessException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @ClassName DunBaseUtil
 * @Author yyl
 * @Date 2022-09-05 17:06:05
 * @Description DunBaseUtil
 * @Version 1.0
 */
@Slf4j
public class DunBaseUtil {

    /**
     * 工保盾交互基础方法
     * @param dunPushTypeEnum
     * @param param
     * @param headerMap
     * @return
     */
    public static String sendDun(DunPushTypeEnum dunPushTypeEnum, String param, Map<String, String> headerMap) {
        String env = StringUtils.equals(DynamicSourceTtl.get(), DynamicSourceTtl.SLAVE_DATASOURCE) ? DunDataSourceEnum.TEST_DATASOURCE.getCode() : DunDataSourceEnum.PRIMARY_DATASOURCE.getCode();
        headerMap.put("env", env);
        Request.Builder builder = new Request.Builder();
        builder.addHeader("Gongbao-Origin", SystemSourceEnum.GB_N.getCode());
        for (Map.Entry<String, String> header : headerMap.entrySet()) {
            builder.addHeader(header.getKey(), header.getValue());
        }
        String url = UrlConstant.getDunUrl(dunPushTypeEnum.getDunUrl());
        try {
            log.info("工保盾{}API【{}】，访问开始！请求参数：{}，请求头参数：{}", dunPushTypeEnum.getCode(), url, param, JsonUtil.json(headerMap));
            //接口请求
            String respJson = OkhttpUtils.send(builder, HttpWay.POST, url, param, HttpType.JSON).string();
            log.info("工保盾{}API【{}】，访问完毕！请求参数：{}，请求头参数：{}，响应结果：{}", dunPushTypeEnum.getCode(), url, param, JsonUtil.json(headerMap), respJson);
            DunRespBaseVO dunRespBaseVO = JsonUtil.bean(respJson, DunRespBaseVO.class);
            if (dunRespBaseVO == null) {
                throw new BusinessException("工保盾" + dunPushTypeEnum.getCode() + "API【" + url + "】，访问异常！");
            }
            if (!dunRespBaseVO.isStatus()) {
                throw new BusinessException("工保盾:" + dunRespBaseVO.getMessage());
            }
            return respJson;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("工保盾{}API【{}】，访问异常：", dunPushTypeEnum.getCode(), url, e);
            throw new BusinessException("工保盾" + dunPushTypeEnum.getCode() + "API【" + url + "】，访问异常！");
        }
    }

    /**
     * 工保盾交互基础方法
     * @param dunPushTypeEnum
     * @param param
     * @return
     */
    public static String sendDun(DunPushTypeEnum dunPushTypeEnum, String param) {
        Map<String, String> headerMap = Maps.newHashMap();
        return sendDun(dunPushTypeEnum, param, headerMap);
    }


    public static <T> T jsonStringToObject(String str, TypeReference<T> valueTypeRef) {
        ObjectMapper mapper = JsonUtil.getJsonMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(str, valueTypeRef);
        } catch (JsonProcessingException e) {
            log.error("json类型转换失败");
            throw new BusinessException("json类型转换失败");
        }
    }

}
