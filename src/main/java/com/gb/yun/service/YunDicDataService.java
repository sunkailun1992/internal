package com.gb.yun.service;

import com.gb.yun.entity.bo.YunDicDataInsertBO;
import com.gb.yun.entity.bo.YunDicDataQueryBO;

/**
 * <p>
 * 工保云数据字典 服务类接口
 * </p>
 *
 * @author sunx
 * @since 2021-03-15
 */
public interface YunDicDataService {

    /**
     * 工保云数据字典查询接口
     *
     * @param bo 请求参数
     * @param token  工保云token
     * @return String
     * @author sunx
     * @since 2021-03-17
     * @throws Exception
     */
    String findList(YunDicDataQueryBO bo, String token) throws Exception;

    /**
     * 工保云数据字典新增接口
     *
     * @param bo 请求参数
     * @param token  工保云token
     * @return String
     * @author sunx
     * @since 2021-03-17
     * @throws Exception
     */
    String insert(YunDicDataInsertBO bo, String token) throws Exception;
}