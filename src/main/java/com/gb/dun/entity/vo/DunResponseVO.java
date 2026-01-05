package com.gb.dun.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 盾基础VO
 * @author yyl
 */
@Data
@Accessors(chain = true)
public class DunResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String respJson;

    private Map<String, Object> reqObject;

    private Object mqObject;

    private String errorMsg;

    /**
     * 操作类型：风控审核的时候会用
     */
    private String optionType;

    /**
     * 风控单号：风控审核的时候会用
     */
    private String riskControlNumber;

    /**
     * 投保单号
     */
    private String castId;

}
