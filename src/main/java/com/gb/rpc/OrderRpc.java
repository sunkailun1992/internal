package com.gb.rpc;

import com.gb.utils.Json;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author sunkailun
 * @DateTime 2020/1/8  11:04 上午
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@FeignClient(value = "order")
public interface OrderRpc {
    /**
     * 新增保费支付订单
     * @param map
     * @return Optional<Json>
     */
    @PostMapping(value = "/order/save")
    Optional<Json> createInsuranceFeeOrder(@RequestParam Map<String, Object> map);

    /**
     * 新增保费支付订单
     * @param map
     * @return Json
     */
    @PostMapping(value = "/order/selectOne")
    Json selectOne(@RequestParam Map<String, String> map);
}
