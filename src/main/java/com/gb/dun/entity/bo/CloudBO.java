package com.gb.dun.entity.bo;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;

/**
 * @author yyl
 */
@Data
public class CloudBO implements Comparable<CloudBO>, Serializable {

    /**
     * 业务封装类的属性值
     */
    private String value;

    /**
     * 主数据ID
     */
    private String masterDataId;

    /**
     * 主数据分类ID
     */
    private String masterDataCategoryId;

    /**
     * 因子编码
     */
    private String factorCode;

    /**
     * 重写业务类的toString方法，方便后面验签
     * @return
     */
    @Override
    public String toString() {
        return ObjectUtils.toString(masterDataId, String::new)
                + ObjectUtils.toString(value, String::new)
                + ObjectUtils.toString(masterDataCategoryId, String::new)
                + ObjectUtils.toString(factorCode, String::new);
    }

    @Override
    public int compareTo(CloudBO o) {

        if (o.toString().length() > this.toString().length()){
            return -1;
        }

        if (o.toString().length() < this.toString().length()){
            return 1;
        }

        return 0;
    }
}
