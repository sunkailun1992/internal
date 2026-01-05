package com.gb.yunv2.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @className: com.gb.newYun.entity.enums-> YunV2Enum
 * @author: 王一飞
 * @createDate: 2021-12-27 10:37 上午
 * @description: 控制器
 */
@Getter
@AllArgsConstructor
public enum YunV2Enum {
    /**
     * 控制器
     */
    project("project"),
    enterprise("enterprise"),
    无法识别("无法识别");

    private String name;

    /**
     * 获取对应的控制器
     */
    public static YunV2Enum getYunEnumByName(String name) {
        Optional<YunV2Enum> value = Arrays.stream(YunV2Enum.values())
                .filter(x -> x.getName().equals(name))
                .findFirst();
        return value.orElse(无法识别);
    }
}
