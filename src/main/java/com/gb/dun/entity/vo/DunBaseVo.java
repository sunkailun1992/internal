package com.gb.dun.entity.vo;

import com.gb.bean.GongBaoConfig;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 盾基础VO
 * @author yyl
 */
@Data
public class DunBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "appId不能为空")
    private Integer appId = GongBaoConfig.gongbaoDunAppId;

    @NotBlank(message = "签名信息不能为空")
    private String sign;

}
