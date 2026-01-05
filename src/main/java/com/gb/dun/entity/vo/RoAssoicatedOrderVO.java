package com.gb.dun.entity.vo;

import com.gb.dun.entity.bo.RoAssociatedOrderBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 关联订单信息VO
 * @author yyl
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoAssoicatedOrderVO extends DunBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Valid
    private RoAssociatedOrderBO roAssociatedOrderBO;


}
