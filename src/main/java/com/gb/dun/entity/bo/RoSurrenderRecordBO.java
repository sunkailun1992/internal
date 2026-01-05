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
 * 退保信息BO
 * @author yyl
 */
@Data
public class RoSurrenderRecordBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "投保人id不能为空")
    private String policyHolderId;

    @NotBlank(message = "风控单号不能为空")
    private String riskControlNumber;

    /**
     * 关联订单id
     * @NotBlank(message = "关联订单id不能为空")
     */
    private String associatedOrderNumber;

    @NotBlank(message = "保单号不能为空")
    private String policyNumber;

    @NotNull(message = "退保金额不能为空")
    private BigDecimal surrenderAmount;

    @NotBlank(message = "创建人不能为空")
    private String createName;

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