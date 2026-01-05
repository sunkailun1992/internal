package com.gb.mq.dun;

import com.gb.dun.enmus.DunPushTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 保司保单文件信息Event
 *
 * @author lijh
 */
@Data
@Valid
@EqualsAndHashCode(callSuper = false)
public class DunInsuranceInfoEvent extends DunPushEvent {

    private static final long serialVersionUID = 1L;

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

    /**
     * 创建人
     */
    private String createName;

    /**
     * 投保单id
     */
    private String castInsuranceId;

    @Override
    public void setPushType() {
        this.pushType = DunPushTypeEnum.DUN_INSURANCE_INFO_TYPE;
    }
}
