package com.gb.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.gb.common.service.CommonService;
import com.gb.utils.JsonUtil;
import com.gb.utils.OkhttpUtils;
import com.gb.utils.enumeration.HttpType;
import com.gb.utils.enumeration.HttpWay;
import com.gb.utils.enumeration.SystemSourceEnum;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 三方通用接口实现类
 *
 * @author: sunx
 * @Date: 2021/03/22 23:30
 * @descript: 三方通用接口实现类
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public String send(String token, HttpWay httpWay, String url, Map<String, Object> params, HttpType httpType, Map<String, String> headerMap) throws Exception {
        Request.Builder builder = new Request.Builder();
        builder.addHeader("Gongbao-Origin", SystemSourceEnum.GB_N.getCode());
        if (StringUtils.isNotBlank(token)) {
            builder.addHeader("Authorization", token);
            builder.addHeader("content", SystemSourceEnum.GB_N.getCode());
        }
        if(MapUtils.isNotEmpty(headerMap)){
            log.debug("headerMap：", JSON.toJSONString(headerMap));
            for (Map.Entry<String, String> header: headerMap.entrySet()){
                builder.addHeader(header.getKey(), header.getValue());
            }
        }
        String requestJson = JsonUtil.json(params);
        String respJson = OkhttpUtils.send(builder, httpWay, url, requestJson, httpType).string();
        log.debug("【调用第三方接口】builder信息：{}，调用方式：{}，调用地址：{}，请求参数：{}，响应结果：{}", builder.toString(), httpWay.getType(), url, requestJson, respJson);
        return respJson;
    }

    @Override
    public String sendQimo(String authorization, HttpWay httpWay, String url, Map<String, Object> params, HttpType httpType) throws Exception {
        Request.Builder builder = new Request.Builder();
        builder.addHeader("Authorization", authorization);

        return OkhttpUtils.send(builder, httpWay, url, JsonUtil.json(params), httpType).string();
    }
}