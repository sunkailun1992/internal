package com.gb.dun.entity.bo;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.TreeMap;

/**
 * @author: sunx
 * @Date: 2021/12/20 15:28
 * @descript:
 */
@Data
public class InsuranceBO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 保险公司code
     */
    private String insuranceCode;
    /**
     * 保险公司name
     */
    private String insuranceName;
    /**
     * 子订单号
     */
    private String childPolicyNumber;

    /**
     * 风控报告地址
     */
    private String pdfUrl;

    /**
     * 重写业务类的toString方法，方便后面验签
     * @return
     */
    @Override
    public String toString() {
        TreeMap<String, String> treeMap = new TreeMap<>();
        try {
            Class<?> forName = this.getClass();
            for (Field field : forName.getDeclaredFields()){

                Object value = field.get(this);
                if (value == null){
                    continue;
                }
                treeMap.put(field.getName(), ObjectUtils.toString(value, String::new));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        for (String value : treeMap.values()){
            sb.append(value);
        }

        return sb.toString();
    }
}
