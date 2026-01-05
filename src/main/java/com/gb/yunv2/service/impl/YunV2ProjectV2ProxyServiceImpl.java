package com.gb.yunv2.service.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gb.yunv2.entity.to.YunV2ProjectFileTo;
import com.gb.yunv2.entity.to.YunV2ProjectTextTo;
import com.gb.yunv2.entity.to.YunV2ProjectTo;
import com.gb.yunv2.entity.vo.YunV2ProjectEntityVo;
import com.gb.yunv2.entity.constant.YunV2Constant;
import com.gb.yunv2.entity.enums.YunV2BoEnum;
import com.gb.yunv2.service.YunV2ProxyService;
import com.gb.yunv2.utils.YunV2AssemblyDataUtil;
import com.gb.utils.OkhttpUtils;
import com.gb.utils.enumeration.HttpType;
import com.gb.utils.enumeration.HttpWay;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @className: com.gb.newYun.service-> YunV2ProjectV2ProxyService
 * @author: 王一飞
 * @createDate: 2021-12-27 10:43 上午
 * @description: 云查询   项目信息
 */
@Slf4j
@Service
public class YunV2ProjectV2ProxyServiceImpl implements YunV2ProxyService {

    /**
     * @createAuthor: 王一飞
     * @title: 查询 项目
     * @createDate: 2021/12/27 1:57 下午
     */
    @Override
    public Object select(JSONObject requestJsonObject, Boolean returnType) {
        //  组装 查询云数据结构
        Map<String, Object> paramMap = getAssemblyDataMap(Boolean.TRUE, requestJsonObject, Boolean.FALSE);

        //  查询接口
        String send = StringUtils.EMPTY;
        try {
            send = OkhttpUtils.send(new Request.Builder(), HttpWay.POST,
                    YunV2Constant.getYunUrl(YunV2Constant.GBY_SELECT_URl),
                    JSON.toJSONString(paramMap), HttpType.JSON).string();
            log.debug("查询云接口获取项目信息,请求地址={},paramMap={},返回结果={},组装主体数据后的结果={}", YunV2Constant.getYunUrl(YunV2Constant.GBY_SELECT_URl), paramMap, send, YunV2ProjectEntityVo.getResultJsonData(send));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询云接口错误,参数：{}   结果：{}", JSON.toJSONString(paramMap), send);
        }

        //  组织 返回数据结构
        return YunV2AssemblyDataUtil.getResultJson(returnType, Boolean.TRUE, send, YunV2ProjectEntityVo.getResultJsonData(send), paramMap,
                requestJsonObject.getInteger(YunV2BoEnum.请求页行数.getName()), requestJsonObject.getInteger(YunV2BoEnum.请求页码数.getName()));
    }


    @Override
    public Object addAndUpdate(JSONObject requestJsonObject) {
        YunV2ProjectTextTo projectTextTo = Convert.convert(YunV2ProjectTextTo.class,
                JSON.parse(JSON.toJSONString(requestJsonObject.get(YunV2BoEnum.字段.getName()))));
        //  判断项目名称是否存在
        if (StringUtils.isBlank(projectTextTo.getProjectName())) {
            log.error("向云上报数据参数错误，不存在项目名称，参数：{}", requestJsonObject.toJSONString());
        }
        //  判断项目属地是否存在（工保云唯一标识：标段内部编号（由项目所在地区编码+各地区公共资源交易中心生成的标段编号组成））
        if (StringUtils.isBlank(projectTextTo.getProjectProvince())) {
            log.error("向云上报数据参数错误，不存在项目属地，参数：{}", requestJsonObject.toJSONString());
        }

        //  组装 云项目 数据结构
        Map<String, Object> paramMap = getAssemblyDataMap(Boolean.FALSE, requestJsonObject, null);

        //  调用 上报数据 接口
        String send = StringUtils.EMPTY;
        try {
            send = OkhttpUtils.send(new Request.Builder(), HttpWay.POST,
                    YunV2Constant.getYunUrl(YunV2Constant.GBY_ADD_AND_UPDATE_URL),
                    JSON.toJSONString(paramMap), HttpType.JSON).string();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上报云接口错误,参数：{}   结果：{}", JSON.toJSONString(paramMap), send);
        }

        //  组织 返回数据结构
        return YunV2AssemblyDataUtil.getResultJson(Boolean.FALSE,
                Boolean.FALSE, send, null, paramMap, null, null);
    }


    /**
     * @param requestJsonObject : 查询参数数据
     * @param dataDictionary:   是否为数据字典查询
     * @createAuthor: 王一飞
     * @title: 组装 云项目 数据结构
     * @createDate: 2022/1/6 3:14 下午
     */
    private Map<String, Object> getAssemblyDataMap(Boolean control, JSONObject requestJsonObject, Boolean dataDictionary) {
        Object data = new Object();
        try {
            JSONObject textJsonObject = (JSONObject) JSON.parse(JSON.toJSONString(requestJsonObject.get(YunV2BoEnum.字段.getName())));
            JSONObject fileJsonObject = (JSONObject) JSON.parse(JSON.toJSONString(requestJsonObject.get(YunV2BoEnum.附件.getName())));
            data = YunV2ProjectTo.getAssemblyData(
                    control,
                    Objects.isNull(textJsonObject) || textJsonObject.isEmpty() ? null : Convert.convert(YunV2ProjectTextTo.class, textJsonObject),
                    Objects.isNull(fileJsonObject) || fileJsonObject.isEmpty() ? null : Convert.convert(YunV2ProjectFileTo.class, fileJsonObject));
        } catch (Exception e) {
            log.error("查询云项目数据组装失败,参数:{}", requestJsonObject.toJSONString());
            e.printStackTrace();
        }
        return YunV2AssemblyDataUtil.getParamMap(
                control, YunV2Constant.PROJECT_DATA_MODEL, requestJsonObject, data, dataDictionary,
                requestJsonObject.getInteger(YunV2BoEnum.请求页行数.getName()), requestJsonObject.getInteger(YunV2BoEnum.请求页码数.getName()));
    }
}
