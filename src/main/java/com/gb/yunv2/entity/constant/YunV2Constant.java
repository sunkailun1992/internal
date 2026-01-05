package com.gb.yunv2.entity.constant;

import com.gb.bean.GongBaoConfig;

/**
 * @className: com.gb.newYun.entity.constant-> YunV2Constant
 * @author: 王一飞
 * @createDate: 2021-12-24 2:33 下午
 * @description: 云 常量
 */
public class YunV2Constant {

    private YunV2Constant() {

    }

    /**
     * 公钥
     */
    public static String PUBLIC_KEY = GongBaoConfig.yunPublicKey;

    /*
     -----------------------------------返回参数数据结构----------------------------------------------
     */
    /**
     * 工保云结果错误消息
     */
    public static final String RESULT_MSG_YUN = "errorMsg";
    /**
     * 工保云结果错误代码
     */
    public static final String RESULT_ERROR_CODE = "errorCode";
    /**
     * 工保云结果状态
     */
    public static final String RESULT_STATUS = "status";
    /**
     * 工保云结果状态成功返回码
     */
    public static final String RESULT_STATUS_SUCCESS = "SUCCESS";
    /**
     * 工保云结果data
     */
    public static final String RESULT_DATA = "data";
    /**
     * 工保云结果数据集合
     */
    public static final String RESULT_CONTENT = "content";


    /*
     -----------------------------------请求参数数据结构----------------------------------------------
     */
    /**
     * 接口版本号
     */
    public static final String API_VERSION = "apiVersion";
    /**
     * 接口版本号
     */
    public static String API_VERSION_VALUE = "2.0";
    /**
     * 模型名称
     */
    public static final String MODEL_NAME = "modelName";
    /**
     * 数据来源
     */
    public static final String DATA_SOURCE = "dataSource";
    /**
     * 数据来源
     */
    public static String DATA_SOURCE_VALUE = "gongbaowang";
    /**
     * 签名
     */
    public static final String SIGN = "sign";
    /**
     * 查询主体
     */
    public static final String REQUEST_DATA = "data";
    /**
     * 查询主体
     */
    public static final String REQUEST_DATA_PARAM = "param";
    /**
     * 是否为数据字典查询
     */
    public static final String REQUEST_DATA_DICTIONARY = "dataDictionary";
    /**
     * 查询-请求的页数
     */
    public static final String REQUEST_DATA_PAGE_SIZE = "pageSize";
    /**
     * 查询-请求第几页
     */
    public static final String REQUEST_DATA_PAGE_CURRENT = "pageNum";

    /*
     -----------------------------------模型名称----------------------------------------------
     */
    /**
     * 工保云--项目查询or上传 分组名称
     */
    public static final String PROJECT_MASTER_DATA = "project_masterdata";
    /**
     * 工保云--基础项目模型
     */
    public static final String PROJECT_DATA_MODEL = "construction_project";
    /**
     * 工保云--基础企业模型
     */
    public static final String ENTERPRISE_DATA_MODEL = "enterprise";


    /*
     -----------------------------------企业数字模型名称----------------------------------------------
     */
    /**
     * 工保云--基础企业模型--企业的建筑业企业资质、勘查、监理、设计资质
     */
    public static final String ENTERPRISE_QUALIFICATION_INFORMATION_MODEL = "enterprise_qualification_information";
    /**
     * 工保云--基础企业模型--企业基本账户信息
     */
    public static final String ENTERPRISE_BASIC_ENTERPRISE_ACCOUNT_INFORMATION_MODEL = "basic_enterprise_account_information";
    /**
     * 工保云--基础企业模型--企业影像资料
     */
    public static final String ENTERPRISE_IMAGE_DATA_MODEL = "enterprise_image_data";
    /**
     * 工保云--基础企业模型--企业基本信息
     */
    public static final String ENTERPRISE_MASTERDATA_MODEL = "enterprise_masterdata";


    /*
     -----------------------------------工保云UR 和 接口地址----------------------------------------------
     */

    /**
     * 工保云 查询url
     */
    public static String GBY_SELECT_URl = "v2/findAll";
    /**
     * 工保云 上报数据url
     */
    public static String GBY_ADD_AND_UPDATE_URL = "addAndUpdate";

    /**
     * 获取云路径
     */
    public static String getYunUrl(String url) {
        return GongBaoConfig.yunModelQueryUrl + url;
    }
}