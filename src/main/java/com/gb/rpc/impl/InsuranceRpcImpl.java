package com.gb.rpc.impl;

import com.gb.log.service.RpcLogService;
import com.gb.rpc.InsuranceRpc;
import com.gb.utils.Json;
import com.gb.utils.enumeration.ReturnCode;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ljh
 * @date 2022/7/29 9:08 上午
 */
@Slf4j
@Component
public class InsuranceRpcImpl implements FallbackFactory<InsuranceRpc> {

    /**
     * rpc日志
     */
    @Autowired
    private RpcLogService rpcLogService;
    /**
     * 接收服务
     */
    private final String receiveServer = "insurance";

    @Override
    public InsuranceRpc create(Throwable throwable) {
        return new InsuranceRpc() {

            @Override
            public Optional<Json> saveOrUpdateYunEnterpriseData(List<Map> synchronizingEnterpriseData) {
                rpcLogService.rpcLog(receiveServer, "/enterprise/saveOrUpdateYunEnterpriseData", synchronizingEnterpriseData, throwable.getMessage());
                log.error("[Insurance服务]拉取工保云企业信息同步数据到工保网异常 msg: {},  map:{}", throwable.getMessage(), synchronizingEnterpriseData);
                return Optional.of(new Json(ReturnCode.RPC服务出错));
            }

            @Override
            public Optional<Json> saveOrUpdateYunProjectData(List<Map> synchronizingProjectData) {
                rpcLogService.rpcLog(receiveServer, "/project/saveOrUpdateYunProjectData", synchronizingProjectData, throwable.getMessage());
                log.error("[Insurance服务]拉取工保云项目信息同步数据到工保网异常 msg: {},  map:{}", throwable.getMessage(), synchronizingProjectData);
                return Optional.of(new Json(ReturnCode.RPC服务出错));
            }
        };
    }
}
