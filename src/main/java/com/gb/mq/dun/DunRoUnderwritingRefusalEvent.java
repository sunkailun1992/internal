package com.gb.mq.dun;

import com.gb.dun.enmus.DunPushTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 承保结果信息Event
 * @author yyl
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DunRoUnderwritingRefusalEvent extends DunPushEvent implements Serializable {

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

    /**
     * 操作人
     */
    private String createName;

    @Override
    public void setPushType() {
        this.pushType = DunPushTypeEnum.DUN_UNDERWRITING_TYPE;
    }
}
