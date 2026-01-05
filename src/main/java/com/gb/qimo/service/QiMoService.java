package com.gb.qimo.service;

import com.gb.qimo.entity.PotentialCustomer;

import java.util.List;
import java.util.Map;

/**
 * 七陌云 用户信息拉取接口
 *
 * @author lijh
 * @date 2021/6/2
 */
public interface QiMoService {

    /**
     * 保存客户线索信息
     * @param params
     * @return List<PotentialCustomer>
     */
    List<PotentialCustomer> saveCustomerInfo(Map<String, Object> params);

    /**
     * 拉取七陌云客户信息
     *
     * @param params
     * @param url
     * @return
     * @throws Exception
     */
    Map<String, Object> sendQimoyun(Map<String, Object> params, String url) throws Exception;
}
