package com.gb.rpc.impl;

import com.gb.log.service.RpcLogService;
import com.gb.qimo.entity.PotentialCustomer;
import com.gb.rpc.CustomerRpc;
import com.gb.utils.Json;
import com.gb.utils.exception.RpcException;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ClassName InsuranceRpcImp
 * @Description 保险rpc熔断
 * @Author 孙凯伦
 * @Mobile 13777579028
 * @Email 376253703@qq.com
 * @Time 2021/6/15 6:22 下午
 */
@Component
public class CustomerRpcImp implements FallbackFactory<CustomerRpc> {

    /**
     * rpc日志
     */
    @Autowired
    private RpcLogService rpcLogService;
    /**
     * 接收服务
     */
    private String receiveServer = "customer";

    @Override
    public CustomerRpc create(Throwable throwable) {
        return new CustomerRpc() {
            @Override
            public Json selectCustomerSourceOne(Map<String, String> map) {
                rpcLogService.rpcLog(receiveServer, "/potential-customer-source/selectOne", map, throwable.getMessage());
                throw new RpcException(throwable.getMessage());
            }

            @Override
            public Json saveCustomerAll(List<PotentialCustomer> potentialCustomers) {
                rpcLogService.rpcLog(receiveServer, "/potential-customer/saveAll", potentialCustomers, throwable.getMessage());
                throw new RpcException(throwable.getMessage());
            }

            @Override
            public Json selectOneRecentlyClues(Map<String, Object> map) {
                rpcLogService.rpcLog(receiveServer, "/potential-customer/selectOneRecentlyClues", map, throwable.getMessage());
                throw new RpcException(throwable.getMessage());
            }

            @Override
            public Json saveAllBiInfo(List<PotentialCustomer> potentialCustomers) {
                rpcLogService.rpcLog(receiveServer, "/potential-customer/saveAllBiInfo", potentialCustomers, throwable.getMessage());
                throw new RpcException(throwable.getMessage());
            }
        };
    }
}
