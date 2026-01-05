package com.gb.yunv2.entity.to;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.digest.DigestUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.common.entity.ProjectTextFieldValue;
import com.gb.rpc.enums.RpcTypeEnum;
import com.gb.yunv2.entity.constant.YunV2Constant;
import com.gb.yunv2.entity.enums.project.YunV2ProjectEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @className: com.gb.newYun.entity.Bo-> YunV2ProjectTo
 * @author: 王一飞
 * @createDate: 2021-12-28 11:23 上午
 * @description: 项目传输
 */
@Slf4j
@Data
public class YunV2ProjectTo {

    private YunV2ProjectTextTo text;
    private YunV2ProjectFileTo file;


    /**
     * @createAuthor: 王一飞
     * @title: 组织主体 数据结构
     * @createDate: 2021/12/24 3:25 下午
     */
    public static Object getAssemblyData(Boolean control, YunV2ProjectTextTo projectText, YunV2ProjectFileTo projectFile) {
        /*
          {
              "data":{
                  "dataDictionary":false,
                  "pageNum":1,
                  "pageSize":2,
                  "param":{
                      "project_name":"2019年惠河、粤赣高速公路沿线附属房建改造工程",
                      "project_masterdata":{
                          "bid_inviter":"广东省高速公路有限公司粤赣分公司"
                      }
                  }
              }
          }

          {
                    "modelName":"construction_project ",
                    "dataSource":"gongbaowang",
                    "apiVersion":"2.0",
                    "sign":"123*****456",
                    "data":[
                        {
                            "project_masterdata":{
                                "construction_period":"",
                                "bid_inviter":"张三三",
                                "construction_nature":"工程建设性",
                                "project_document_no":"立项文号",
                            },
                            "project_internal_number":"项目内部编号",
                            "project_number":"项目编号",
                            "project_name":"项目名称",
                            "bid_name":"标段名称",
                            "bid_number":"标段编号"
                        }
                    ]
                }
         */
        Map<String, Object> dataMap = new HashMap<>(16);
        Map<String, Object> paramMap = new HashMap<>(16);

        //  向param对象添加 字段text数据
        Map<String, Object> projectMasterDataMap = new HashMap<>(16);
        paramMap.put(YunV2Constant.PROJECT_MASTER_DATA, projectMasterDataMap);

        //  过滤字段value值为null的数据
        if (Objects.nonNull(projectText)) {
            Arrays.stream(projectText
                    .getClass()
                    .getDeclaredFields())
                    .filter(text -> Objects.nonNull(text.getAnnotation(JsonProperty.class)))
                    .peek(text -> {
                        String code = text.getAnnotation(JsonProperty.class).value();
                        //  没有分组，为外层数据
                        YunV2ProjectEnum projectEnum = YunV2ProjectEnum.getYunProjectEnumByYunCode(code);
                        if (StringUtils.isBlank(projectEnum.getYunBoGrouping())) {
                            String value = YunV2ProjectTo.getOrSetAssemblyData(Boolean.TRUE, projectEnum, projectText, null);
                            if (StringUtils.isNotBlank(value)) {
                                paramMap.put(code, value);
                            }
                        }
                        //  判断是否是 字段数据
                        if (YunV2Constant.PROJECT_MASTER_DATA.equals(projectEnum.getYunBoGrouping())) {
                            String value = YunV2ProjectTextTo.getOrSetAssemblyData(Boolean.TRUE, projectEnum, projectText, null);
                            if (StringUtils.isNotBlank(value)) {
                                projectMasterDataMap.put(code, value);
                            }
                        }
                    })
                    .collect(Collectors.toList());
        }

        //  组装工保云必填字段：
        String projectNameValue = Convert.toStr(paramMap.get(YunV2ProjectEnum.项目名称.getYunCode()));
        String projectProvinceValue = Convert.toStr(projectMasterDataMap.get(YunV2ProjectEnum.项目地址.getYunCode()));
        if (StringUtils.isNotBlank(projectNameValue) && StringUtils.isNotBlank(projectProvinceValue)) {
            //  标段名称：为 项目名称
            paramMap.put(YunV2ProjectEnum.标段名称.getYunCode(), projectNameValue);
            //  标段编号：为标段名称32位MD5加密
            String bidNumberValue = DigestUtil.md5Hex(projectNameValue);
            paramMap.put(YunV2ProjectEnum.标段编号.getYunCode(), bidNumberValue);
            //  工保云唯一标识：标段内部编号（由项目所在地区编码+各地区公共资源交易中心生成的标段编号组成）
            //  标段内部编号: 6位行政区划代码 + 标段编号
            paramMap.put(YunV2ProjectEnum.标段内部编号.getYunCode(), projectProvinceValue.substring(0, 6) + bidNumberValue);

            //  项目地址
            HashMap<String, String> rpcParamMap = new HashMap<String, String>(16) {{
                put("areaCode", projectProvinceValue);
            }};
            Map areaMap = YunV2ProjectTextTo.rpcComponentBean().rpcQuery(rpcParamMap, RpcTypeEnum.product_area_select, Map.class);
            projectMasterDataMap.put(YunV2ProjectEnum.项目地址.getYunCode(), Convert.toStr(areaMap.get("cityName")));
        }

        //  过滤附件值为null的数据
        if (Objects.nonNull(projectFile)) {

        }

        //  查询的参数结构
        if (control) {
            dataMap.put(YunV2Constant.REQUEST_DATA_PARAM, paramMap);
            return dataMap;
        } else {
            return paramMap;
        }
    }


    /**
     * @param isGetBoolean:          true-get，false-set
     * @param projectText:           原始对象
     * @param projectTextFieldValue: set转换数据来源
     * @createAuthor: 王一飞
     * @title: 组织主体 数据结构
     * @createDate: 2021/12/24 3:25 下午
     */
    public static String getOrSetAssemblyData(Boolean isGetBoolean, YunV2ProjectEnum projectEnum, YunV2ProjectTextTo projectText, ProjectTextFieldValue projectTextFieldValue) {
        String resultString = StringUtils.EMPTY;

        String value = Objects.nonNull(projectTextFieldValue) ? projectTextFieldValue.getValue() : StringUtils.EMPTY;
        switch (projectEnum) {
            case 项目编号:
                if (isGetBoolean) {
                    resultString = projectText.getProjectNumber();
                } else {
                    projectText.setProjectNumber(value);
                }
                break;
            case 项目名称:
                if (isGetBoolean) {
                    resultString = projectText.getProjectName();
                } else {
                    projectText.setProjectName(value);
                }
                break;
            default:
                break;
        }
        return resultString;
    }
}
