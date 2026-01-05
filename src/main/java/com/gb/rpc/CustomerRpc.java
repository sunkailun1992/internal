package com.gb.rpc;

import com.gb.qimo.entity.PotentialCustomer;
import com.gb.rpc.impl.CustomerRpcImp;
import com.gb.utils.Json;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * CRM-RPC
 * @author lijh
 * @date 2021/6/4
 */
@FeignClient(value = "customer", fallbackFactory = CustomerRpcImp.class)
public interface CustomerRpc {

    /**
     * 获取客户线索来源信息
     *
     * @param map
     * @return
     */
    @GetMapping(value = "/potential-customer-source/selectOne")
    Json selectCustomerSourceOne(@RequestParam Map<String, String> map);


    /**
     * 批量添加线索
     *
     * @param potentialCustomers
     * @return
     */
    @PostMapping(value = "/potential-customer/saveAll")
    Json saveCustomerAll(@RequestBody List<PotentialCustomer> potentialCustomers);

    /**
     * 查询线索数据
     *
     * @param map
     * @return
     */
    @GetMapping(value = "/potential-customer/selectOneRecentlyClues")
    Json selectOneRecentlyClues(@RequestParam Map<String, Object> map);

    /**
     * 批量添加BI线索
     *
     * @param potentialCustomers
     * @return
     */
    @PostMapping(value = "/potential-customer/saveAllBiInfo")
    Json saveAllBiInfo(@RequestBody List<PotentialCustomer> potentialCustomers);
}
