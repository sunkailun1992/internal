package com.gb.rpc.enums;

import lombok.Getter;

/**
 * @author: ranyang
 * @Date: 2021/3/15 15:09
 * @descript: rpc调用枚举
 */
@Getter
@SuppressWarnings("all")
public enum RpcTypeEnum {
    product_plan,
    product_plan_value,
    product_policy_holder_value,
    product_policy_holder,
    product_area_select,
    insurance_enterprise_save_or_update_yun_enterprise_data,
    product_risk_category_id,
    ;
}
