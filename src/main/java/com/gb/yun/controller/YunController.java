package com.gb.yun.controller;

import com.gb.utils.Json;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.yun.entity.vo.ApiRequestVO;
import com.gb.yun.entity.vo.FieldEnterpriseRequestVO;
import com.gb.yun.entity.vo.FieldProjectRequestVO;
import com.gb.yun.service.YunCommonService;
import com.gb.yun.service.YunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static com.gb.yun.enmus.YunFunctionEnum.OBJECT_QUERY;
import static com.gb.yun.enmus.YunFunctionEnum.XZQH_TREE;
import static com.gb.yun.enmus.YunSyncDataTypeEnum.*;

/**
 * <p>
 * 第三方控制器 前端控制器（主要转发第三方的返回结果）
 * </p>
 *
 * @author sunx
 * @since 2021-03-17
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/yun")
@Api(tags = "云控制器")
public class YunController {

    private YunService yunService;

    private YunCommonService yunCommonService;

    /**
     * 业务对象查询
     *
     * @param requestDTO:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     */
    @Methods(methodsName = "业务对象查询", methods = "findAll")
    @ApiOperation(value = "业务对象查询", httpMethod = "GET", notes = "业务对象查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectCode", value = "业务对象代码", dataType = "String", required = true)
    })
    @GetMapping("/findAll")
    public Json<Object> findAll(ApiRequestVO requestDTO) throws Exception {
        return new Json(ReturnCode.成功, yunCommonService.functionSend(OBJECT_QUERY, requestDTO));
    }

    /**
     * 企业字段新增
     *
     * @param requestDTO:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     */
    @Methods(methodsName = "企业字段新增", methods = "fieldEnterpriseInsert")
    @ApiOperation(value = "企业字段新增", httpMethod = "POST", notes = "企业字段新增", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkId", value = "关联数据ID", dataType = "String", required = true),
            @ApiImplicitParam(name = "linkName", value = "关联数据名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "objectCode", value = "业务对象代码", dataType = "String", required = true),
    })
    @PostMapping("/fieldEnterpriseInsert")
    public Json<Object> fieldEnterpriseInsert(@RequestBody @Valid FieldEnterpriseRequestVO requestDTO) throws Exception {
        //调用第三方字段新增接口
        Object obj =  yunCommonService.sendCloud(false, EN_TXT_ADD, requestDTO);
        return new Json(ReturnCode.成功, obj);
    }

    /**
     * 企业字段更新
     *
     * @param requestDTO:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     */
    @Methods(methodsName = "企业字段更新", methods = "fieldEnterpriseUpdate")
    @ApiOperation(value = "企业字段更新", httpMethod = "PUT", notes = "企业字段更新", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectCode", value = "业务对象代码", dataType = "String", required = true),
            @ApiImplicitParam(name = "id", value = "序列", dataType = "String", required = true)
    })
    @PutMapping("/fieldEnterpriseUpdate")
    public Json<Object> fieldEnterpriseUpdate(FieldEnterpriseRequestVO requestDTO) throws Exception {
        //调用第三方字段新增接口
        Object obj =  yunCommonService.sendCloud(false, EN_TXT_UPATE, requestDTO);
        return new Json(ReturnCode.成功, obj);
    }

    /**
     * 项目字段新增
     *
     * @param requestDTO:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     */
    @Methods(methodsName = "项目字段新增", methods = "fieldProjectInsert")
    @ApiOperation(value = "项目字段新增", httpMethod = "POST", notes = "项目字段新增", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkId", value = "关联数据ID", dataType = "String", required = true),
            @ApiImplicitParam(name = "linkName", value = "关联数据名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "objectCode", value = "业务对象代码", dataType = "String", required = true),
    })
    @PostMapping("/fieldProjectInsert")
    public Json<Object> fieldProjectInsert(@Valid FieldProjectRequestVO requestDTO) throws Exception {
        //调用第三方字段新增接口
        Object obj =  yunCommonService.sendCloud(false, PJ_TXT_ADD, requestDTO);
        return new Json(ReturnCode.成功, obj);
    }

    /**
     * 项目字段更新
     *
     * @param requestDTO:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     */
    @Methods(methodsName = "项目字段更新", methods = "fieldProjectUpdate")
    @ApiOperation(value = "项目字段更新", httpMethod = "PUT", notes = "项目字段更新", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectCode", value = "业务对象代码", dataType = "String", required = true),
            @ApiImplicitParam(name = "id", value = "序列", dataType = "String", required = true)
    })
    @PutMapping("/fieldProjectUpdate")
    public Json<Object> fieldProjectUpdate(FieldProjectRequestVO requestDTO) throws Exception {
        //调用第三方字段更新接口
        Object obj =  yunCommonService.sendCloud(false, PJ_TXT_UPDATE, requestDTO);
        return new Json(ReturnCode.成功, obj);
    }

    /**
     * 区划代码树
     *
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-17
     */
    @Methods(methodsName = "区划代码树", methods = "treeData")
    @ApiOperation(value = "区划代码树", httpMethod = "GET", notes = "区划代码树", response = Json.class)
    @GetMapping("/treeData")
    public Json<Object> treeData() throws Exception {
        return new Json(ReturnCode.成功, yunCommonService.functionSend(XZQH_TREE, null));
    }

    /**
     * 第三方登录
     *
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-17
     */
    @Methods(methodsName = "第三方登录", methods = "loginThirdparty")
    @ApiOperation(value = "第三方登录", httpMethod = "GET", notes = "第三方登录", response = Json.class)
    @GetMapping("/loginThirdparty")
    public Json<String> loginThirdparty() throws Exception {
        return new Json(ReturnCode.成功, Optional.of(yunService.loginThirdparty()));
    }
}