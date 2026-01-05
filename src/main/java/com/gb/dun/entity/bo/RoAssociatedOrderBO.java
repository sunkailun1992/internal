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
 * 关联订单信息BO
 * @author yyl
 */
@Data
public class RoAssociatedOrderBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "风控单号不能为空")
    private String riskControlNumber;

    /**
     * 关联订单id
     * @NotBlank(message = "关联订单id不能为空")
     */
    private String associatedOrderNumber;

    @NotBlank(message = "保险机构id不能为空")
    private String insuranceInstitutionsId;

    @NotBlank(message = "保险机构名称不能为空")
    private String insuranceInstitutions;

    @NotBlank(message = "保单号不能为空")
    private String policyNumber;

    @Pattern(regexp = "^(\\d{4})(-|\\/)(\\d{2})\\2(\\d{2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$", message = "提交时间格式不对")
    @NotNull(message = "提交时间不能为空")
    private String submissionTime;

    /**
     * 状态：
     * 1：投保成功，2：拒保，3：失效
     */
    @NotNull(message = "状态不能为空")
    private Integer state;

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