package com.gb.yunv2.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @className: com.gb.newYun.entity.enums-> YunV2BoEnum
 * @author: 王一飞
 * @createDate: 2022-01-06 2:31 下午
 * @description: 传输数据 枚举获取
 */
@Getter
@AllArgsConstructor
public enum YunV2BoEnum {
    /**
     * 字段类型
     */
    类型("type"),
    字段("text"),
    附件("file"),

    请求页行数("size"),
    请求页码数("current"),

    无法识别("无法识别");

    private String name;

    /**
     * 获取对应的控制器
     */
    public static YunV2BoEnum getYunEnumByName(String name) {
        Optional<YunV2BoEnum> value = Arrays.stream(YunV2BoEnum.values())
                .filter(x -> x.getName().equals(name))
                .findFirst();
        return value.orElse(无法识别);
    }
}
