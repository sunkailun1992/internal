package com.gb.mq.dun;

import com.gb.dun.enmus.DunPushTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 保司承保详细结果Event
 *
 * @author lijh
 */
@Data
@Valid
@EqualsAndHashCode(callSuper = false)
public class DunRoUnderwritingRefusalResultEvent extends DunPushEvent {

    private static final long serialVersionUID = 1L;

    /**
     * 风控单号
     */
    @NotBlank(message = "风控单号不能为空")
    private String riskControlNumber;

    /**
     * 投保单id
     */
    private String castInsuranceId;

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
        this.pushType = DunPushTypeEnum.DUN_UNDERWRITING_REFUSAL_RESULTS_TYPE;
    }
}
