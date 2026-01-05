package com.gb.dun.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 推送盾类型枚举类
 * @author: ranyang
 * @Date: 2021/03/30 09:05
 * @descript:
 */
@Getter
@AllArgsConstructor
public enum DunDataSourceEnum {

    /**
     * 默认数据源
     */
    PRIMARY_DATASOURCE("primary", "默认数据源"),

    /**
     * 测试数据源
     */
    TEST_DATASOURCE("test", "测试数据源");

    /**
     * 码值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

}
