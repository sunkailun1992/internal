package com.gb.yunv2.controller;

import com.gb.yunv2.service.YunV2ProxyContext;
import com.gb.utils.Json;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.RequestRequired;
import com.gb.yunv2.service.YunV2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @className: com.gb.newYun.utils-> YunV2Controller
 * @author: 王一飞
 * @createDate: 2021-12-23 11:42 上午
 * @description: 工保云2.0控制器
 */
@Slf4j
@RequestRequired
@RestController
@RequestMapping("/yunV2")
@Api(tags = "工保云2.0控制器")
public class YunV2Controller {

    @Resource
    private YunV2Service yunV2Service;

    /**
     * @param param: json数据
     * @createAuthor: 王一飞
     * @title: 查询
     * @createDate: 2021/12/29 2:27 下午
     * @description:
     * @return: Json：工保网数据格式
     */
    @Methods(methodsName = "查询", methods = "select")
    @ApiOperation(value = "查询", httpMethod = "GET", notes = "业务对象查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "json数据", dataType = "String", required = true)
    })
    @PostMapping("/select")
    public Json<Object> select(@RequestBody String param) throws Exception {
        YunV2ProxyContext<Json> yunV2ProxyContext = new YunV2ProxyContext(param);
        return yunV2ProxyContext.select(param);
    }


    /**
     * @param param: json数据
     * @createAuthor: 王一飞
     * @title: 新增或修改
     * @createDate: 2021/12/29 2:27 下午
     * @description:
     * @return: Json：工保网数据格式
     */
    @Methods(methodsName = "新增或修改", methods = "addAndUpdate")
    @ApiOperation(value = "新增或修改", httpMethod = "POST", notes = "新增或修改", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "json数据", dataType = "String", required = true)
    })
    @PostMapping("/addAndUpdate")
    public Json<Object> addAndUpdate(@RequestBody String param) {
        YunV2ProxyContext<Json> yunV2ProxyContext = new YunV2ProxyContext(param);
        return yunV2ProxyContext.addAndUpdate(param);
    }

    /**
     * 同步云数据到网
     *
     * @return
     */
    @Methods(methodsName = "同步云数据到网", methods = "synchronousYunV2")
    @ApiOperation(value = "同步云数据到网", httpMethod = "GET", notes = "同步云数据到网", response = Json.class)
    @GetMapping("/synchronousYunV2")
    public Json<Object> synchronousYunV2() {
        yunV2Service.pullYunV2();
        return new Json<>();
    }
}
