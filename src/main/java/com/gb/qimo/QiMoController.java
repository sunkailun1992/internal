package com.gb.qimo;

import com.alibaba.fastjson.JSON;
import com.gb.common.constant.UrlConstant;
import com.gb.qimo.service.DbOperation;
import com.gb.qimo.service.QiMoService;
import com.gb.utils.Json;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.enumeration.ReturnCode;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author ljh
 * @date 2021/9/15 9:40 上午
 */

@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/qimo")
@Api(tags = "三方拉取控制器")
public class QiMoController {

    private QiMoService qiMoService;

    private DbOperation dbOperation;

    @Methods(methodsName = "手动拉取七陌云数据", methods = "pullQimoYun")
    @ApiOperation(value = "手动拉取七陌云数据", httpMethod = "GET", notes = "拉取七陌云数据", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginCreateTime", value = "开始时间", dataType = "String", required = true),
            @ApiImplicitParam(name = "endCreateTime", value = "结束时间", dataType = "String", required = true),
            @ApiImplicitParam(name = "isSave", value = "是否保存", dataType = "Boolean", required = true)
    })
    @GetMapping("/pullQimoYun")
    public Json pullQimoYun(String beginCreateTime, String endCreateTime, @RequestParam(required = false, defaultValue = "false") Boolean isSave) throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        params.put("beginCreateTime", beginCreateTime);
        params.put("page", "1");
        params.put("pageSize", "1000");
        params.put("endCreateTime", endCreateTime);
        //获取数据库版本号
        Map<String, Object> templateMap = qiMoService.sendQimoyun(Maps.newHashMap(), UrlConstant.QIMO_GET_TEMPLATE_URL);
        Map<String, Object> resultMap = JSON.parseObject(String.valueOf(templateMap.get("data")), Map.class);
        params.put("version", resultMap.get("version"));
        Map<String, Object> stringObjectMap = qiMoService.sendQimoyun(params, UrlConstant.QIMO_SELECT_URL);
        return new Json(ReturnCode.成功, isSave ? qiMoService.saveCustomerInfo(stringObjectMap) : stringObjectMap);
    }


    @Methods(methodsName = "手动拉取BI数据", methods = "pullBiData")
    @ApiOperation(value = "手动拉取BI数据", httpMethod = "GET", notes = "拉取BI数据", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginCreateTime", value = "开始时间", dataType = "String", required = true),
            @ApiImplicitParam(name = "endCreateTime", value = "结束时间", dataType = "String", required = true),
            @ApiImplicitParam(name = "isSave", value = "是否保存", dataType = "Boolean", required = true)
    })
    @GetMapping("/pullBiData")
    public Json pullBiData() {
        return new Json(ReturnCode.成功, dbOperation.conn());
    }
}
