package com.gb.dun.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 盾风控审核诶下枚举类
 * @author: sunx
 * @Date: 2021/03/30 09:05
 * @descript:
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public enum DunRiskViewEnum {
    RISKVIEW_A("RISKVIEW_A","风控审核新增"),
    RISKVIEW_C("RISKVIEW_C", "风控审核回调"),
    RISKVIEW_U("RISKVIEW_U", "风控审核更新");

    /**
     * 码值
     */
    private String code;

    /**
     * 含义
     */
    private String desc;

}
