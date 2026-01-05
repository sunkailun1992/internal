package com.gb.dun.entity.vo;

import com.gb.dun.entity.bo.RoSurrenderRecordBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 退保信息VO
 * @author yyl
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoSurrenderRecordVO extends DunBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Valid
    private RoSurrenderRecordBO roSurrenderRecordBO;


}
