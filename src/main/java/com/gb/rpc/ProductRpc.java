package com.gb.rpc;

import com.gb.utils.Json;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

/**
 * @author: ranyang
 * @Date: 2021/3/15 11:05
 * @descript: 产品相关查询
 */
@FeignClient(value = "product")
public interface ProductRpc {

    /**
     * 查询方案类型表
     * @param map
     * @return
     */
    @GetMapping(value = "/product-plan/selectOne")
    Optional<Json> selectProductPlan(@RequestParam Map<String, String> map);

    /**
     * 查询方案类型值表
     * @param map
     * @return
     */
    @GetMapping(value = "/product-plan-value/selectOne")
    Optional<Json> selectProductPlanValue(@RequestParam Map<String, String> map);

    /**
     * 查询投保人类型表
     * @param map
     * @return
     */
    @GetMapping(value = "/product-policy-holder/selectOne")
    Optional<Json> selectProductPolicyHolder(@RequestParam Map<String, String> map);

    /**
     * 查询投保人类型值表
     * @param map
     * @return
     */
    @GetMapping(value = "/product-policy-holder-value/selectOne")
    Optional<Json> selectProductPolicyHolderValue(@RequestParam Map<String, String> map);

    /**
     * 根据区码获取省市区
     * @param map
     * @return
     */
    @GetMapping(value = "/area/selectAreaCode")
    Optional<Json> selectAreaCode(@RequestParam Map<String, String> map);

    /**
     * 查询product_danger_planted 险种表, 单条
     * @param map
     * @return
     */
    @GetMapping(value = "/product-danger-planted/selectOne")
    Optional<Json> selectProductDangerPlantedOne(@RequestParam Map<String, String> map);
}
