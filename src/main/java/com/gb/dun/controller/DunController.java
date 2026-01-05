package com.gb.dun.controller;

import com.alibaba.fastjson.JSON;
import com.gb.dun.service.DunPushService;
import com.gb.mq.dun.*;
import com.gb.utils.Json;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.enumeration.ReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 第三方控制器 前端控制器
 * </p>
 *
 * @author sunx
 * @since 2021-03-17
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/dun")
@Api(tags = "盾控制器")
public class DunController {

    private DunPushService dunPushService;

    /**
     * 手动重推工保盾审核
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     */
    @Methods(methodsName = "手动重推工保盾审核", methods = "findAll")
    @ApiOperation(value = "手动重推工保盾审核", httpMethod = "POST", notes = "手动重推工保盾审核", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "event", value = "请求json", dataType = "String", required = true),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "String", required = true)
    })
    @PostMapping("/riskHandPush")
    public Json<String> riskHandPush(@RequestBody String event, Integer type) {
        switch (type) {
            //风控审核
            case 1:
                dunPushService.dealBusiness(JSON.parseObject(event, DunRiskSysReviewEvent.class));
                break;
            //承保结果信息
            case 2:
                dunPushService.dealBusiness(JSON.parseObject(event, DunRoUnderwritingRefusalEvent.class));
                break;
            //退保
            case 3:
                dunPushService.dealBusiness(JSON.parseObject(event, DunRoSurrenderRecordEvent.class));
                break;
            //关联订单信息
            case 4:
                dunPushService.dealBusiness(JSON.parseObject(event, DunRoAssociateOrderEvent.class));
                break;
            //保司保单文件
            case 5:
                dunPushService.dealBusiness(JSON.parseObject(event, DunInsuranceInfoEvent.class));
                break;
            //保司承保详细结果
            case 6:
                dunPushService.dealBusiness(JSON.parseObject(event, DunRoUnderwritingRefusalResultEvent.class));
                break;
            default:
                break;
        }
        return new Json(ReturnCode.成功);
    }

}