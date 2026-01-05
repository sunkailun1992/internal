package com.gb.common.constant;

import com.gb.bean.GongBaoConfig;

/**
 * <p>
 * 路径常量类
 * </p>
 *
 * @author sunx
 * @since 2021-03-17
 */
public class UrlConstant {

    private UrlConstant() {

    }

    //-----------------------------------------------------------------工保云---------------------------------------------------------------------------

    /**
     * 工保云登录URL
     * old：http://114.55.146.226/base/admin/login
     */
    public static final String GBY_LOGIN_URL = "admin/login";

    /**
     * 工保云业务对象查询URL
     */
    public static final String GBY_OBJECT_CXURL = "api/findAll";

    /**
     * 工保云主数据查询URL
     */
    public static final String GBY_MASTER_CXURL = "elasticSearch/findAll";

    /**
     * 工保云字段插入URL
     */
    public static final String GBY_APIZD_XZURL = "api/insert";

    /**
     * 工保云字段更新URL
     */
    public static final String GBY_APIZD_GXURL = "api/update";

    /**
     * 工保云区划代码树
     */
    public static final String GBY_XZQH_TREE = "city/treeData";

    /**
     * 工保云附件上传URL
     */
    public static final String GBY_APIFJ_UPURL = "api/upload/";

    /**
     * 工保云数据字典查询列表
     */
    public static final String GBY_DICDATA_LIST_CXURL = "field/findList";

    /**
     * 工保云数据字典新增列表
     */
    public static final String GBY_DICDATA_LIST_INSERTURL = "elasticSearch/insert";

    //-----------------------------------------------------------------工保盾---------------------------------------------------------------------------

    /**
     * 工保盾风控审核URL
     * http://172.16.200.220:8080/risk-integration/risk-order-api
     */
    public static final String GBD_RISKRIEW_URL = "/risk-order-api";

    /**
     * 工保盾关联订单URL
     * http://172.16.200.220:8080/risk-integration/ro-risk-order-custom-value
     */
    public static final String GBD_ASSOCIATEORDER_URL = "/ro-risk-order-custom-value";

    /**
     * 工保盾退保记录外部订单数据接收URL
     * http://172.16.200.220:8080/risk-integration/ro-surrender-record
     */
    public static final String GBD_SURRENDER_URL = "/ro-surrender-record";

    /**
     * 工保盾承保结果外部数据接收URL
     * http://172.16.200.220:8080/risk-integration/ro-underwriting-refusal
     */
    public static final String GBD_UNDERWRITING_URL = "/ro-underwriting-refusal";

    /**
     * 工保盾保司保单文件信息工保盾外部数据接收URL
     * http://172.16.200.220:8080/risk-integration/policy
     */
    public static final String GBD_INSURANCE_INFO_URL = "/ro-underwriting-refusal/policy";

    /**
     * 工保盾保司承保详细结果工保盾外部数据接收URL
     * http://172.16.200.220:8080/risk-integration/ro-underwriting-refusal/detail
     */
    public static final String GBD_UNDERWRITING_REFUSAL_RESULTS_URL = "/ro-underwriting-refusal/detail";

    /**
     * 工保盾智能问卷内容各个节点获取URL
     * http://172.16.200.220:8080/risk-integration/questionnaire/getQuestionnaireList
     */
    public static final String GBD_QUESTIONNAIRE_NODE_QUERY_URL = "/questionnaire/getQuestionnaireList";

    /**
     * 工保盾智能问卷内容各个节点新增URL
     * http://172.16.200.220:8080/risk-integration/questionnaire/submitQuestionnaire
     */
    public static final String GBD_QUESTIONNAIRE_NODE_SAVE_URL = "/questionnaire/submitQuestionnaire";

    //-----------------------------------------------------------------七陌云---------------------------------------------------------------------------

    /**
     * 七陌云 查询客户资料数据接口
     * https://apis.7moor.com/v20170418/customer/select/N00000032024
     */
    public static final String QIMO_SELECT_URL = "/v20170418/customer/select/";

    /**
     * 七陌云 获取客户数据库版本号、字段结构接口
     */
    public static final String QIMO_GET_TEMPLATE_URL = "/v20170418/customer/getTemplate/";

    /**
     * 获取盾路径
     */
    public static String getDunUrl(String url) {
        return GongBaoConfig.gongbaoDunUrl + url;
    }

    /**
     * 获取云路径
     */
    public static String getYunUrl(String url) {
        return GongBaoConfig.cloudUrl + url;
    }

    /**
     * 获取七陌云路径
     *
     * @param url
     * @return
     */
    public static String getQimoUrl(String url) {
        return GongBaoConfig.qimoHost + url + GongBaoConfig.accountId;
    }
}
