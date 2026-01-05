package com.gb.yun.controller;

import com.gb.utils.Json;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.yun.entity.bo.YunDicDataInsertBO;
import com.gb.yun.entity.bo.YunDicDataQueryBO;
import com.gb.yun.service.YunCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gb.yun.enmus.YunFunctionEnum.DICDATA_INSERT;
import static com.gb.yun.enmus.YunFunctionEnum.DICDATA_QUERY;

/**
 * <p>
 * 云数据字典控制器
 * </p>
 *
 * @author sunx
 * @since 2021-03-17
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/yunDicData")
@Api(tags = "云数据字典控制器")
public class YunDicDataController {

    private YunCommonService yunCommonService;

    /**
     * 数据字典列表查询
     *
     * @param bo:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     */
    @Methods(methodsName = "数据字典列表查询", methods = "findList")
    @ApiOperation(value = "数据字典列表查询", httpMethod = "GET", notes = "数据字典列表查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataCode", value = "业务对象代码", dataType = "String", required = true)
    })
    @GetMapping("/findList")
    public Json<Object> findList(YunDicDataQueryBO bo) throws Exception {
        return new Json(ReturnCode.成功, yunCommonService.functionSend(DICDATA_QUERY, bo));
    }



    /**
     * 字典数据值录入
     *
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-17
     */
    @Methods(methodsName = "字典数据值录入", methods = "字典数据值录入")
    @ApiOperation(value = "字典数据值录入", httpMethod = "POST", notes = "字典数据值录入", response = Json.class)
    @PostMapping("/dicDataInsert")
    public Json<Object> dicDataInsert(YunDicDataInsertBO bo) throws Exception {
        return new Json(ReturnCode.成功, yunCommonService.functionSend(DICDATA_INSERT, bo));
    }
}