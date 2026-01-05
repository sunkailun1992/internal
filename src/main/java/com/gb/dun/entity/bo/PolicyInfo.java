package com.gb.dun.entity.bo;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.TreeMap;

/**
 * 保司保单文件
 *
 * @author lijh
 */
@Data
public class PolicyInfo implements Serializable {

    /**
     * 风控单号
     */
    @NotBlank(message = "风控单号不能为空")
    private String riskControlNumber;

    /**
     * 保单号
     */
    @NotBlank(message = "保单号不能为空")
    private String policyNumber;

    /**
     * 保费
     */
    @NotBlank(message = "保费不能为空")
    private String premium;

    /**
     * 保险开始时间
     */
    @Pattern(regexp = "^(\\d{4})(-|\\/)(\\d{2})\\2(\\d{2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$", message = "保险开始时间格式不对")
    @NotNull(message = "保险开始时间不能为空")
    private String startTime;

    /**
     * 保险结束时间
     */
    @Pattern(regexp = "^(\\d{4})(-|\\/)(\\d{2})\\2(\\d{2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$", message = "保险结束时间格式不对")
    @NotNull(message = "保险结束时间不能为空")
    private String endTime;

    /**
     * 保单文件
     */
    @NotBlank(message = "保单文件不能为空")
    private String path;

    /**
     * 承保保险公司名称
     */
    @NotBlank(message = "承保保险公司不能为空")
    private String insuranceCompany;


    @Override
    public String toString() {
        TreeMap<String, String> treeMap = new TreeMap<>();
        try {
            Class<?> forName = this.getClass();

            for (Field field : forName.getDeclaredFields()) {

                Object value = field.get(this);
                if (value == null) {
                    continue;
                }
                treeMap.put(field.getName(), StringUtils.trim(ObjectUtils.toString(value, String::new)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        for (String value : treeMap.values()) {
            sb.append(value);
        }

        return sb.toString();
    }
}
