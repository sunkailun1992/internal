package com.gb.dun.entity.vo;

import com.gb.dun.entity.bo.RiskOrderBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 风控审核VO
 * @author yyl
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RiskOrderVO extends DunBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    private RiskOrderBO riskOrderBO;


}
