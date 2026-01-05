package com.gb.common.service;

import com.gb.utils.enumeration.HttpType;
import com.gb.utils.enumeration.HttpWay;

import java.util.Map;


/**
 * 三方通用接口
 *
 * @author: sunx
 * @Date: 2021/03/22 23:30
 * @descript: 三方通用接口
 */
public interface CommonService {

    /**
     * 通用发送方法
     *
     * @param token:    token
     * @param httpWay:  请求方式
     * @param url:      请求url
     * @param params:   请求参数
     * @param httpType: 请求参数格式
     * @param headerMap: 请求头
     * @return String
     * @author sunx
     * @since 2021-03-18
     * @throws Exception
     */
    String send(String token, HttpWay httpWay, String url, Map<String, Object> params, HttpType httpType, Map<String, String> headerMap) throws Exception;

    /**
     * 七陌云发送方法
     *
     * @param httpWay       请求方式
     * @param url           请求url
     * @param params        请求参数
     * @param httpType      请求参数格式
     * @param authorization 请求头
     * @throws Exception
     * @return String
     */
    String sendQimo(String authorization, HttpWay httpWay, String url, Map<String, Object> params, HttpType httpType) throws Exception;
}
