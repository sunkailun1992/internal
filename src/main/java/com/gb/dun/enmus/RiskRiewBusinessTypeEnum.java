package com.gb.dun.enmus;

import lombok.Getter;

/**
 * 风控业务类型枚举类
 * @author: sunx
 * @Date: 2021/03/30 09:05
 * @descript:
 */
@SuppressWarnings("ALL")
@Getter
public enum RiskRiewBusinessTypeEnum {

    /**
     * 保前风控业务
     */
    RISKRIEW_BEFORE_INSURANCE(1, "保前风控业务"),

    /**
     * 保后风控业务
     */
    RISKRIEW_AFTER_INSURANCE(2, "保后风控业务");

    /**
     * 风控业务类型代码
     */
    private Integer code;

    /**
     * 风控业务类型名称
     */
    private String desc;


    /**
     * 有参构造
     */
    RiskRiewBusinessTypeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
