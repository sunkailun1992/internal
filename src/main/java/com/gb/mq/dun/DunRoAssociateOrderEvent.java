package com.gb.mq.dun;

import com.gb.dun.enmus.DunPushTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 关联订单信息Event
 * @author yyl
 */
@Data
@Valid
@EqualsAndHashCode(callSuper = false)
public class DunRoAssociateOrderEvent extends DunPushEvent implements Serializable {

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
    public void setPushType() {
        this.pushType = DunPushTypeEnum.DUN_ASSOCIATE_ORDER_TYPE;
    }
}