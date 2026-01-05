package com.gb.dun.enmus;

import com.gb.common.constant.UrlConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 推送盾类型枚举类
 *
 * @author: ranyang
 * @Date: 2021/03/30 09:05
 * @descript:
 */
@Getter
@AllArgsConstructor
@Slf4j
@SuppressWarnings("all")
public enum DunPushTypeEnum {
    DUN_SURRENDER_TYPE("surrenderInfo", "退保信息", UrlConstant.GBD_SURRENDER_URL),
    DUN_RISKVIEW_TYPE("riskView", "风控审核", UrlConstant.GBD_RISKRIEW_URL),
    DUN_UNDERWRITING_TYPE("underWriting", "承保结果信息", UrlConstant.GBD_UNDERWRITING_URL),
    DUN_ASSOCIATE_ORDER_TYPE("associateOrder", "关联订单信息", UrlConstant.GBD_ASSOCIATEORDER_URL),
    DUN_INSURANCE_INFO_TYPE("insuranceInfo", "保司保单文件信息", UrlConstant.GBD_INSURANCE_INFO_URL),
    DUN_UNDERWRITING_REFUSAL_RESULTS_TYPE("underwritingRefusalResults", "保司承保详细结果", UrlConstant.GBD_UNDERWRITING_REFUSAL_RESULTS_URL),
    DUN_QUESTIONNAIRE_NODE_QUERY_TYPE("questionnaireNodeQuery", "智能问卷内容节点信息查询", UrlConstant.GBD_QUESTIONNAIRE_NODE_QUERY_URL),
    DUN_QUESTIONNAIRE_NODE_SAVE_TYPE("questionnaireNodeSave", "智能问卷内容节点信息新增", UrlConstant.GBD_QUESTIONNAIRE_NODE_SAVE_URL),
    ;

    /**
     * 推送类型名称
     */
    private String name;

    /**
     * 推送类型码
     */
    private String code;

    /**
     * 推送给盾的url
     */
    private String dunUrl;

    /**
     * 根据推送类型代码获取枚举类型
     */
    public static DunPushTypeEnum getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            log.debug("根据【code：{}】获取对应的【推送盾的业务员类型】，code值为空", code);
            return null;
        }
        Optional<DunPushTypeEnum> codeEnum = Arrays.stream(DunPushTypeEnum.values()).filter(x -> code.equals(x.getCode())).findFirst();
        return codeEnum.orElse(null);
    }

}
