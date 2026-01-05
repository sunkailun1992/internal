package com.gb.yunv2.utils;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.yunv2.entity.constant.YunV2Constant;
import com.gb.yunv2.utils.signature.ApiSignUtil;
import com.gb.utils.Json;
import com.gb.utils.enumeration.ReturnCode;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @className: com.gb.newYun.utils.signature-> YunV2AssemblyDataUtil
 * @author: 王一飞
 * @createDate: 2021-12-28 11:18 下午
 * @description: 组装数据
 */
@Slf4j
public class YunV2AssemblyDataUtil {
    /**
     * @param control:           true：查询，false：修改
     * @param modelName:         查询模型名称
     * @param requestJsonObject: request查询数据json
     * @param data:              查询主体
     * @param dataDictionary:    是否为数据字典查询
     * @createAuthor: 王一飞
     * @title: 组装 查询云数据
     * @createDate: 2021/12/27 3:32 下午
     */
    public static Map<String, Object> getParamMap(Boolean control,
                                                  String modelName,
                                                  JSONObject requestJsonObject,
                                                  Object data,
                                                  Boolean dataDictionary,
                                                  Integer pageSize,
                                                  Integer pageCurrent) {
        //  组装数据
        Map<String, Object> paramMap = Maps.newHashMap();
        //  接口版本号
        paramMap.put(YunV2Constant.API_VERSION, YunV2Constant.API_VERSION_VALUE);
        //  模型名称
        paramMap.put(YunV2Constant.MODEL_NAME, modelName);
        //  数据来源
        paramMap.put(YunV2Constant.DATA_SOURCE, YunV2Constant.DATA_SOURCE_VALUE);

        //  查询
        String sign;
        if (control) {
            Map<String, Object> dataMap = Convert.toMap(String.class, Object.class, data);
            //  是否为数据字典查询
            if (Objects.nonNull(dataDictionary)) {
                dataMap.put(YunV2Constant.REQUEST_DATA_DICTIONARY, dataDictionary);
            }
            //  页码：默认为1
            if (Objects.nonNull(pageSize)) {
                dataMap.put(YunV2Constant.REQUEST_DATA_PAGE_SIZE, pageSize);
            }
            //  页面大小：默认为100
            if (Objects.nonNull(pageCurrent)) {
                dataMap.put(YunV2Constant.REQUEST_DATA_PAGE_CURRENT, pageCurrent);
            }
            //  查询主体
            paramMap.put(YunV2Constant.REQUEST_DATA, dataMap);
            //  签名
            sign = ApiSignUtil.encode(dataMap, YunV2Constant.PUBLIC_KEY);
        } else {
            //  查询主体
            paramMap.put(YunV2Constant.REQUEST_DATA, data);
            //  签名
            sign = ApiSignUtil.encode(data, YunV2Constant.PUBLIC_KEY);
        }
        //  签名
        paramMap.put(YunV2Constant.SIGN, sign);
        log.info("查询云数据组装签名成功,工保网参数:{},签名:{}", requestJsonObject.toJSONString(), sign);
        return paramMap;
    }


    /**
     * @param returnType  :返回数据结构(默认false，返回工保网数据结构)，当为true时，返回List<Object>
     * @param control     :        true：查询，false：修改
     * @param send        ：云          返回数据
     * @param resultData  ：主体数据
     * @param paramMap    ：请求参数
     * @param pageSize    ：页数
     * @param pageCurrent ：当前页
     * @return
     * @createAuthor: 王一飞
     * @title: 组织 返回数据结构
     * @createDate: 2021/12/27 10:33 下午
     */
    public static Object getResultJson(Boolean returnType,
                                       Boolean control, String send, List resultData, Map paramMap,
                                       Integer pageSize, Integer pageCurrent) {
        if (returnType) {
            return resultData;
        }

        JSONObject resultObject = JSONObject.parseObject(send);
        Json<IPage> resultJson = new Json<>(resultObject.get(YunV2Constant.RESULT_STATUS).equals(YunV2Constant.RESULT_STATUS_SUCCESS)
                ?
                ReturnCode.成功 : ReturnCode.调用第三方服务出错);

        //  成功
        if (control && resultObject.get(YunV2Constant.RESULT_STATUS).equals(YunV2Constant.RESULT_STATUS_SUCCESS)) {
            resultJson.setObj(new Page() {{
                if (CollectionUtils.isNotEmpty(resultData)) {
                    setRecords(resultData);
                    setTotal(resultData.size());
                }
                //  页面大小:默认为100
                setSize(Objects.isNull(pageSize) ? 100 : pageSize);
                //  页码:默认为1
                setCurrent(Objects.isNull(pageCurrent) ? 1 : pageCurrent);
            }});
        }

        //  失败
        if (! resultObject.get(YunV2Constant.RESULT_STATUS).equals(YunV2Constant.RESULT_STATUS_SUCCESS)) {
            resultJson.setErrorMessage(String.valueOf(resultObject.get(YunV2Constant.RESULT_MSG_YUN)));
            resultJson.setCode(String.valueOf(resultObject.get(YunV2Constant.RESULT_ERROR_CODE)));
            log.error(control ? "查询" : "上报" + "云错误,工保网参数:{}", JSON.toJSONString(paramMap));
        }

        resultJson.setTimestamp(LocalDateTime.now());
        return resultJson;
    }
}
