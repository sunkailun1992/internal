package com.gb.yunv2.entity.vo;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gb.common.entity.ProjectTextFieldValue;
import com.gb.yunv2.entity.constant.YunV2Constant;
import com.gb.yunv2.entity.enums.project.YunV2ProjectEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: com.gb.newYun-> YunV2ProjectEntityVo
 * @author: 王一飞
 * @createDate: 2021-12-23 2:20 下午
 * @description: 项目基本信息
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class YunV2ProjectEntityVo {
    /**
     * 标段内部编号
     */
    private String projectInternalNumber;

    /**
     * 项⽬编号
     */
    @JsonProperty("project_number")
    private String projectNumber;

    /**
     * 项目名称
     */
    @JsonProperty("project_name")
    private String projectName;

    /**
     * 标段编号
     */
    private String bidNumber;

    /**
     * 标段名称
     */
    private String bidName;

    /**
     * 标段编号是否云自定义  1自定义 0否
     */
    @JsonProperty("whether_bid_number_is_customized")
    private String whetherBidNumberIsCustomized;

    /**
     * 项目传输主数据模型
     */
    private YunV2ProjectMasterDataEntityVo projectMasterData;


    /**
     * @createAuthor: 王一飞
     * @title: 组织 返回 主数据
     * @createDate: 2021/12/27 10:33 下午
     */
    public static List getResultJsonData(String send) {
        JSONObject resultObject = JSONObject.parseObject(send);

        if (!resultObject.get(YunV2Constant.RESULT_STATUS).equals(YunV2Constant.RESULT_STATUS_SUCCESS)) {
            return ListUtil.empty();
        }

        //  获取data外层数据
        String resultDataJson = Convert.toStr(JSONObject.parseObject(Convert.toStr(resultObject.get(YunV2Constant.RESULT_DATA)))
                .get(YunV2Constant.RESULT_CONTENT));
        return JSONArray.parseArray(resultDataJson)
                .stream()
                .map(projectEntityJson -> {
                    //  组装外层对象
                    YunV2ProjectEntityVo yunProjectEntityVo = JSONObject.parseObject(
                            JSON.toJSONString(projectEntityJson), YunV2ProjectEntityVo.class);

                    JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(projectEntityJson));

                    //  组装里层对象
                    yunProjectEntityVo.setProjectMasterData(
                            JSONObject.parseObject(
                                    JSON.toJSONString(jsonObject.get(YunV2Constant.PROJECT_MASTER_DATA)),
                                    YunV2ProjectMasterDataEntityVo.class));
                    return yunProjectEntityVo;
                })
                .collect(Collectors.toList());
    }


    /**
     * @param yunCode            :yun字段标识
     * @param yunProjectEntityVo :数据来源
     * @createAuthor: 王一飞
     * @title: 类型转换，云数据结构 转化为 网数据结构（用于 下拉云数据并向网同步数据）
     * @createDate: 2022/1/10 2:58 下午
     * @description:
     * @return:
     */
    public static ProjectTextFieldValue convertToTextFieldValue(String yunCode, YunV2ProjectEntityVo yunProjectEntityVo) {
        ProjectTextFieldValue projectTextFieldValue = new ProjectTextFieldValue();

        YunV2ProjectEnum yunProjectEnum = YunV2ProjectEnum.getYunProjectEnumByYunCode(yunCode);
        projectTextFieldValue.setCode(yunProjectEnum.getWangCode());
        switch (yunProjectEnum) {
            case 项目编号:
                projectTextFieldValue.setValue(yunProjectEntityVo.getProjectNumber());
                break;
            case 项目名称:
                projectTextFieldValue.setValue(yunProjectEntityVo.getProjectName());
                break;
            default:
                break;
        }
        return StringUtils.isNotBlank(projectTextFieldValue.getValue()) ? projectTextFieldValue : null;
    }
}