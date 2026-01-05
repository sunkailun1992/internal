package com.gb.yun.enmus;

import com.gb.utils.exception.ParameterNullException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 云功能枚举类
 * @author: ranyang
 * @Date: 2021/03/30 09:05
 * @descript:
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public enum YunFunctionEnum {
    XZQH_TREE("xzqh_tree","行政区划树"),
    OBJECT_QUERY("object_query","对象查询"),
    DICDATA_QUERY("dicData_query", "数据字典查询"),
    DICDATA_INSERT("dicData_insert", "数据字典插入");
    /**
     * 云功能代码
     */
    private String code;
    /**
     * 云功能名称
     */
    private String name;

    /**
     * 根据云功能代码获取枚举类型
     */
    public static YunFunctionEnum getByCode(String code) throws Exception {
        if(StringUtils.isBlank(code)){
            throw new ParameterNullException("云功能码不能为空！");
        }
        Optional<YunFunctionEnum> codeEnum = Arrays.stream(YunFunctionEnum.values()).filter(x -> code.equals(x.getCode())).findFirst();
        return codeEnum.orElseThrow(() -> new ParameterNullException("该功能不存在！"));
    }

}
