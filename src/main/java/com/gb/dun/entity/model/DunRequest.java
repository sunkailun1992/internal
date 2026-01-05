package com.gb.dun.entity.model;

import com.gb.bean.GongBaoConfig;
import com.gb.dun.enmus.DunRiskViewEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author sunx
 * @since 2021-12-26
 */
@Data
public class DunRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer appId = GongBaoConfig.gongbaoDunAppId;

    private String sign;

    private Object object;
}
