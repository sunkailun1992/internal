package com.gb.yunv2.utils.signature;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyifei
 */
public class BeanTool {

    public static Map<String, Object> toMap(Object source) {
        Map<String, Object> target = new HashMap<>(1);

        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null && !"class".equals(pd.getName())) {
                target.put(pd.getName(), srcValue);
            }
        }
        return target;
    }
}
