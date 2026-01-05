package com.gb.dun.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gb.bean.GongBaoConfig;
import com.gb.common.constant.CommonConstant;
import com.gb.dun.enmus.DunPushTypeEnum;
import com.gb.dun.entity.dto.QuerySubmitDTO;
import com.gb.dun.entity.dto.QuestionnaireDTO;
import com.gb.dun.entity.vo.*;
import com.gb.dun.log.DunLogHandle;
import com.gb.dun.service.QuestionnaireService;
import com.gb.dun.utils.DunBaseUtil;
import com.gb.rpc.component.RpcComponent;
import com.gb.rpc.enums.RpcTypeEnum;
import com.gb.utils.JsonUtil;
import com.gb.utils.RsaUtils;
import com.gb.utils.exception.BusinessException;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName QuestionnaireServiceImpl
 * @Author yyl
 * @Date 2022-09-05 13:48:44
 * @Description QuestionnaireServiceImpl
 * @Version 1.0
 */
@Setter(onMethod_ = {@Autowired})
@Service
@Slf4j
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private RpcComponent rpcComponent;

    private DunLogHandle dunLogHandle;

    @Override
    public QuestionnaireRespVO queryQuestionnaireContent(QuestionnaireDTO questionnaireDTO) {
        Map<String, String> productParam = Maps.newHashMap();
        //1、获取产品种类名称
        productParam.put("id", questionnaireDTO.getProductSpecName());
        String result = (String) rpcComponent.rpcQuery(productParam, RpcTypeEnum.product_plan, Map.class).get(CommonConstant.DUN_CODE);
        log.debug("产品方案类型名称获取的dunCode为：{}", result);
        questionnaireDTO.setProductSpecName(result);

        //2、获取产品属性名称
        productParam.clear();
        productParam.put("id", questionnaireDTO.getProductAttributeName());
        result = (String) rpcComponent.rpcQuery(productParam, RpcTypeEnum.product_plan_value, Map.class).get(CommonConstant.DUN_CODE);
        log.debug("产品方案类型值获取的dunCode为：{}", result);
        questionnaireDTO.setProductAttributeName(result);

        // 3、查询投保人属性
        productParam.clear();
        productParam.put("id", questionnaireDTO.getPolicyHolderTypeName());
        result = (String) rpcComponent.rpcQuery(productParam, RpcTypeEnum.product_policy_holder, Map.class).get(CommonConstant.DUN_CODE);
        log.debug("投保人类型名称获取的dunCode为：{}", result);
        questionnaireDTO.setPolicyHolderTypeName(result);

        // 4、查询投保人属性名称
        productParam.clear();
        productParam.put("id", questionnaireDTO.getPolicyHolderAttrName());
        result = (String) rpcComponent.rpcQuery(productParam, RpcTypeEnum.product_policy_holder_value, Map.class).get(CommonConstant.DUN_CODE);
        log.debug("投保人类型值获取的dunCode为：{}", result);
        questionnaireDTO.setPolicyHolderAttrName(result);

        //查询工保盾的dunCode
        Map<String, String> map = new HashMap<>(1);
        map.put("id", questionnaireDTO.getRiskCategoryId());
        String dunCode = (String) rpcComponent.rpcQuery(map, RpcTypeEnum.product_risk_category_id, Map.class).get(CommonConstant.DUN_CODE);
        //设置真正的dunCode
        questionnaireDTO.setRiskCategoryId(dunCode);
        QuestionnaireReqVO questionnaireReqVO = new QuestionnaireReqVO();
        questionnaireReqVO.setQuestionnaireBO(questionnaireDTO);
        //请求工保盾查询问卷信息
        String json = DunBaseUtil.sendDun(DunPushTypeEnum.DUN_QUESTIONNAIRE_NODE_QUERY_TYPE, JsonUtil.json(questionnaireReqVO));
        DunRespBaseVO<QuestionnaireRespVO> data = DunBaseUtil.jsonStringToObject(json, new TypeReference<DunRespBaseVO<QuestionnaireRespVO>>() {
        });
        if (data.getData() == null) {
            throw new BusinessException("工保盾：未找到对应问卷信息");
        }
        return data.getData();
    }

    @Override
    public boolean saveQuestionnaire(QuerySubmitDTO querySubmitDTO) {
        QuestionnaireSubmitReqVO questionnaireSubmitReqVO = new QuestionnaireSubmitReqVO();
        questionnaireSubmitReqVO.setQuestionnaireSubmitBO(querySubmitDTO);
        String sign = RsaUtils.generateSign(JsonUtil.json(querySubmitDTO), GongBaoConfig.privateKey);
        questionnaireSubmitReqVO.setSign(sign);
        //请求工保盾新增问卷信息
        String reqJson = JsonUtil.json(questionnaireSubmitReqVO);
        String respJson = DunBaseUtil.sendDun(DunPushTypeEnum.DUN_QUESTIONNAIRE_NODE_SAVE_TYPE, reqJson);
        dunLogHandle.logDunQuestionnaireCommitLog(querySubmitDTO, reqJson, respJson);
        DunRespBaseVO<QuestionnaireSubmitRespVO> data = DunBaseUtil.jsonStringToObject(respJson, new TypeReference<DunRespBaseVO<QuestionnaireSubmitRespVO>>() {
        });
        if (data.getData() == null) {
            throw new BusinessException("工保盾服务异常");
        }
        return data.getData().getQuestionnaireResult();
    }
}
