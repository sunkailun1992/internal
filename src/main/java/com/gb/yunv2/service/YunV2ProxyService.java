package com.gb.yunv2.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @className: com.gb.newYun.service-> YunV2ProxyService
 * @author: 王一飞
 * @createDate: 2021-12-27 10:45 上午
 * @description: 查询
 */
public interface YunV2ProxyService<T> {
    /**
     * 查询
     *
     * @param requestJsonObject :请求参数
     * @param returnType        :返回数据结构(默认false，返回工保网数据结构)，当为true时，返回List<Object>
     * @createAuthor: 王一飞
     * @title: 查询
     * @createDate: 2021/12/27 10:48 上午
     * @description:
     * @return: 结果
     */
    T select(JSONObject requestJsonObject, Boolean returnType);

    /**
     * 上报
     *
     * @param parseObject
     * @createAuthor: 王一飞
     * @title: 上报数据
     * @createDate: 2021/12/27 10:48 上午
     * @description:
     * @return: 结果
     */
    T addAndUpdate(JSONObject parseObject);
}
