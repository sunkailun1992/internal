package com.gb.yun.service;

import com.gb.yun.enmus.YunFunctionEnum;
import com.gb.yun.enmus.YunSyncDataTypeEnum;

import java.util.Map;
import java.util.function.Function;


/**
 * <p>
 * 工保云 服务类接口
 * </p>
 *
 * @author sunx
 * @since 2021-03-15
 */
public interface YunCommonService {

    /**
     * 功能转发
     *
     * @param funcEnum 功能类型
     * @param reqObj 请求参数
     * @return Object
     * @author sunx
     * @since 2021-03-17
     * @throws Exception
     */
    Object functionSend(YunFunctionEnum funcEnum, Object reqObj) throws Exception;

    /**
     * 通用处理的过程校验【token】
     * @return Map<String, Object>
     * @author sunx
     * @since 2021-03-18
     * @param function
     * @throws Exception
     */
    Map<String, Object> validateProcess(Function<String, String> function) throws Exception;

    /**
     * 推送到云新增
     *
     * @param  isAllReturn; 是否结果全返回
     * @param  typeEnum; 工保云同步数据类型枚举
     * @param  obj 字段对象
     * @author sunx
     * @since 2021-03-18
     * @return Object
     * @throws Exception
     */
    Object sendCloud(boolean isAllReturn, YunSyncDataTypeEnum typeEnum, Object obj) throws Exception;
}