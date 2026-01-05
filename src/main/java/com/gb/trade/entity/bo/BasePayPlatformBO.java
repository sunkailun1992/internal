package com.gb.trade.entity.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @author:     	lixinyao
 * @since:   	    2022-11-25 09:05:58
 * @description:	TODO  交易平台入参
 */
@Data
public class BasePayPlatformBO implements Serializable {

    private static final long serialVersionUID = -6089464229752067243L;
    /**
     * 签名方式
     */

    private String signType;
    /**
     * 数据签名
     */
    @NotEmpty()
    private String sign;
    /**
     * 平台编号
     */
    private String platNo;
    /**
     * 商户编号
     */
    private String custNo;
    /**
     * 版本号
     */
    private String version;
    /**
     * 业务参数
     */
    @NotEmpty(message = "业务参数不能为空")
    private JSONObject body;

}
