package com.gb.rpc;

import com.gb.utils.Json;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: ranyang
 * @Date: 2021/3/15 11:05
 * @descript: 投保项目相关查询
 */
@Component
@FeignClient(value = "insurance")
public interface InsuranceRpc {

    /**
     * 同步企业
     *
     * @param synchronizingEnterpriseData
     * @return
     */
    @PostMapping(value = "/enterprise/saveOrUpdateYunEnterpriseData")
    Optional<Json> saveOrUpdateYunEnterpriseData(@RequestBody List<Map> synchronizingEnterpriseData);

    /**
     * 同步项目
     *
     * @param synchronizingProjectData
     * @return
     */
    @PostMapping(value = "/project/saveOrUpdateYunProjectData")
    Optional<Json> saveOrUpdateYunProjectData(@RequestBody List<Map> synchronizingProjectData);
}
