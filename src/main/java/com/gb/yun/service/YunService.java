package com.gb.yun.service;

import com.gb.yun.entity.vo.ApiRequestVO;


/**
 * <p>
 * 工保云 服务类接口
 * </p>
 *
 * @author sunx
 * @since 2021-03-15
 */
public interface YunService {

    /**
     * 第三方登录
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     * @throws Exception
     */
    String loginThirdparty() throws Exception;

    /**
     * 业务对象查询
     * @param dto: Api请求VO对象
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     * @throws Exception
     */
    String findAll(ApiRequestVO dto) throws Exception;

    /**
     * 字段新增
     * @param token:  token
     * @param dto:  字段请求VO对象
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-24
     * @throws Exception
     */
    String fieldInsert(String token, Object dto) throws Exception;

    /**
     * 字段更新
     * @param token:  token
     * @param dto:  字段请求VO对象
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-24
     * @throws Exception
     */
    String fieldUpdate(String token, Object dto) throws Exception;

    /**
     * 区划代码树
     * @param token
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-24
     * @throws Exception
     */
    String treeData(String token) throws Exception;
}