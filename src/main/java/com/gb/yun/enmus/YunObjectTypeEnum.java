package com.gb.yun.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 推送云数据同步类型枚举类
 * @author: ranyang
 * @Date: 2021/03/30 09:05
 * @descript:
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
@Slf4j
public enum YunObjectTypeEnum {
    PROJECT("project","项目"),
    ENTERPRISE("enterprise","企业"),
    PJ_FILE("pj_file","项目附件"),
    EN_FILE("en_file","企业附件");

    /**
     * 对象类型码
     */
    private String code;

    /**
     * 对象类型名称
     */
    private String name;

}
