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
 * 保单承保详细信息
 *
 * @author ljh
 * @date 2022/3/19 10:55 上午
 */
@Data
public class PolicyResult implements Serializable {

    /**
     * 风控单号
     */
    @NotBlank(message = "风控单号不能为空")
    private String riskControlNumber;

    /**
     * 保司code
     */
    @NotBlank(message = "保司code不能为空")
    private String insuranceCode;

    /**
     * 承保结果(1,承保，2，拒保)
     */
    @NotBlank(message = "承保结果不能为空")
    private Integer underwritingOrRefusal;

    /**
     * 反馈时间
     */
    @Pattern(regexp = "^(\\d{4})(-|\\/)(\\d{2})\\2(\\d{2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$", message = "反馈时间格式不对")
    @NotNull(message = "反馈时间不能为空")
    private String feedbackDataTime;

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
