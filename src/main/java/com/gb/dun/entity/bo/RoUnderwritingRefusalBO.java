package com.gb.dun.entity.bo;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.TreeMap;

/**
 * 承保结果信息BO
 * @author yyl
 */
@Data
public class RoUnderwritingRefusalBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "投保人id不能为空")
    private String policyHolderId;

    @NotBlank(message = "风控单号不能为空")
    private String riskControlNumber;

    @NotBlank(message = "保单号不能为空")
    private String policyNumber;

    /**
     * 承保金额（如果承保结果为
     * true，不能为空
     */
    private BigDecimal underwritingAmount;

    /**
     * 承保结果(true 为承保)
     */
    @NotNull(message = "承保结果不能为空")
    private Boolean underwritingOrRefusal;

    /**
     * 承反馈信息
     */
    private String feedbackInfo;

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
                treeMap.put(field.getName(), StringUtils.trim(ObjectUtils.toString(value, String::new)));
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
