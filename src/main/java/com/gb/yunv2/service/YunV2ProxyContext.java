package com.gb.yunv2.service;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.gb.yunv2.entity.enums.YunV2BoEnum;
import com.gb.yunv2.entity.enums.YunV2Enum;
import com.gb.yunv2.service.impl.YunV2EnterpriseV2ProxyServiceImpl;
import com.gb.yunv2.service.impl.YunV2ProjectV2ProxyServiceImpl;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

/**
 * @className: com.gb.newYun.service-> YunV2ProxyContext
 * @author: 王一飞
 * @createDate: 2021-12-27 6:30 下午
 * @description:
 */
public class YunV2ProxyContext<T> {
    private YunV2ProxyService<T> yunProxyService;

    public YunV2ProxyContext(String param) {
        JSONObject requestJsonObject = JSONObject.parseObject(param);
        switch (YunV2Enum.getYunEnumByName(Convert.toStr(requestJsonObject.get(YunV2BoEnum.类型.getName())))) {
            case project:
                yunProxyService = new YunV2ProjectV2ProxyServiceImpl();
                break;
            case enterprise:
                yunProxyService = new YunV2EnterpriseV2ProxyServiceImpl();
                break;
            default:
                break;
        }
    }

    public T select(String param, @RequestParam(defaultValue = "false", required = false) Boolean returnType) {
        if (Objects.isNull(returnType)) {
            returnType = Boolean.FALSE;
        }
        return yunProxyService.select(JSONObject.parseObject(param), returnType);
    }

    public T select(String param) {
        return yunProxyService.select(JSONObject.parseObject(param), Boolean.FALSE);
    }

    public T addAndUpdate(String param) {
        return yunProxyService.addAndUpdate(JSONObject.parseObject(param));
    }
}
