package com.gb.qimo.entity.bo;

import lombok.Data;

import java.util.List;

/**
 * 七陌云信息BO
 * @author lijh
 * @date 2021/6/3
 */
@SuppressWarnings(value = "all")
@Data
public class QiMoInfoBo {

    private String name;

    private String address;

    private String 了解险种;

    private String createTime;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    private List<Phone> phone;
    /**
     * 数据来源
     * <p>
     * "工保科技官网",
     * "微信公众号",
     * "400电话",
     * "龚保儿微信",
     * "保费算一算",
     * "电子保函小程序",
     * "工保网官网",
     * "工保网业务官网",
     * "报价方案H5",
     * "经纪人招募H5",
     * "业务官网在线咨询"
     */
    private String source;
    /**
     * 客户状态
     * <p>
     * "status6": "投诉客户",
     * "status10": "黑龙江改工保网",
     * "status7": "其他",
     * "status8": "基本户问题",
     * "status12": "合同履约保险",
     * "status9": "费率问题",
     * "status11": "其他",
     * "status2": "经纪咨询",
     * "status14": "报价方案预约",
     * "status3": "风控咨询",
     * "status13": "农民工工资支付保险",
     * "status4": "无效咨询",
     * "status5": "投诉客户",
     * "status15": "总部业务合作",
     * "status0": "电子保函客户",
     * "status1": "管家预约"
     */
    private String status;

    @Data
    public class Phone {
        private String area;
        private String memo;
        private String tel;
    }

}
