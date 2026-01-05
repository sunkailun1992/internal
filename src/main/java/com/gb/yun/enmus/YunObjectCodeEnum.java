package com.gb.yun.enmus;

import com.gb.utils.exception.ParameterNullException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 云业务对象枚举
 * @author: ranyang
 * @Date: 2021/03/30 09:05
 * @descript:
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public enum YunObjectCodeEnum {
    XZQH_CASCAD("bo_qhdm","行政区划级联关系"),
    BUSINESS_INFORMATION("business_information","工商信息"),
    XMJBXX("xmjbxx","项目基本信息"),
    PROJECT_ATTACH("project_attachment","项目附件"),
    ENTERPRISE_ATTACH("bo_image_data_info","企业附件");

    /**
     * 云业务对象代码
     */
    private String code;
    /**
     * 云业务对象名称
     */
    private String name;

    /**
     * 根据云业务对象代码获取枚举类型
     */
    public static YunObjectCodeEnum getByCode(String code) throws Exception {
        if(StringUtils.isBlank(code)){
            throw new ParameterNullException("请求参数业务对象不能为空！");
        }
        Optional<YunObjectCodeEnum> codeEnum = Arrays.stream(YunObjectCodeEnum.values()).filter(x -> code.equals(x.getCode())).findFirst();
        return codeEnum.orElseThrow(()-> new ParameterNullException("该业务对象不存在！"));
    }

}
