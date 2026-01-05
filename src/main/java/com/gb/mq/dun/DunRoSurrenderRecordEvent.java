package com.gb.mq.dun;

import com.gb.dun.enmus.DunPushTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退保信息Event
 * @author yyl
 */
@Data
@Valid
@EqualsAndHashCode(callSuper = false)
public class DunRoSurrenderRecordEvent extends DunPushEvent implements Serializable {

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
    public void setPushType() {
        this.pushType = DunPushTypeEnum.DUN_SURRENDER_TYPE;
    }
}