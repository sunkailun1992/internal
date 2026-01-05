package com.gb.trade.entity.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @author:     	lixinyao
 * @since:   	    2022-11-25 09:05:58
 * @description:	TODO  交易平台响应
 */
@Data
public class BasePayPlatformVO implements Serializable {

    private static final long serialVersionUID = 8616254954439592380L;
    /**
     * 返回码
     */
    private String respCode;
    /**
     * 返回消息
     */
    private String respMsg;
    /**
     * 签名方式
     */
    private String signType;
    /**
     * 数据签名
     */
    private String sign;

    /**
     * 业务参数
     */
    private JSONObject body;

}
