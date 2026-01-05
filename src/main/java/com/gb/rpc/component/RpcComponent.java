package com.gb.rpc.component;

import com.alibaba.fastjson.JSON;
import com.gb.rpc.OrderRpc;
import com.gb.rpc.ProductRpc;
import com.gb.rpc.UserRpc;
import com.gb.rpc.enums.RpcTypeEnum;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.Json;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * @author: ranyang
 * @Date: 2021/03/26 14:12
 * @descript:
 */
@Slf4j
@Component
public class RpcComponent {
    @Resource
    private ProductRpc productRpc;
    @Resource
    private UserRpc userRpc;
    @Resource
    private OrderRpc orderRpc;

    /**
     * rpc调用，返回单个
     * @param params
     * @param queryType
     * @param returnType
     * @param <T>
     * @return
     */
    public <T> T rpcQuery(Map<String, String> params, RpcTypeEnum queryType, Class<T> returnType) {
        Optional<Json> result = Optional.empty();
        try{
            switch (queryType) {
                case product_plan:
                    result = productRpc.selectProductPlan(params);
                    break;
                case product_plan_value:
                    result = productRpc.selectProductPlanValue(params);
                    break;
                case product_policy_holder:
                    result = productRpc.selectProductPolicyHolder(params);
                    break;
                case product_policy_holder_value:
                    result = productRpc.selectProductPolicyHolderValue(params);
                    break;
                case product_area_select:
                    result = productRpc.selectAreaCode(params);
                    break;
                case product_risk_category_id:
                    result = productRpc.selectProductDangerPlantedOne(params);
                    break;
                default:
                    break;
            }
            if(returnType.getTypeName().equals(Map.class.getTypeName())) {
                return (T)Json.get(result.get());
            }
            if (result.isPresent() && result.get().getSuccess()) {
                return GeneralConvertor.convertor(Json.get(result.get()), returnType);
            }
        }catch (Exception e){
            log.error("远程RPC【{}】调用【请求参数：{}】异常【响应参数：{}】：", queryType.toString(), JSON.toJSONString(params), JSON.toJSONString(result), e);
            throw new RpcException("RPC【" + queryType + "】调用异常");
        }
        String tipMsg = String.format("RPC【%s】调用失败", queryType);
        String msg = result.isPresent() ? String.format(tipMsg + ", 返回【%s】", result.get().getMsg()) : tipMsg;
        throw new BusinessException(msg);
    }

}
