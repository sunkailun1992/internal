package com.gb.dun.entity.vo;

import com.gb.dun.entity.bo.RoUnderwritingRefusalBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 承保结果信息VO
 * @author yyl
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoUnderwritingRefusalVO extends DunBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Valid
    private RoUnderwritingRefusalBO roUnderwritingRefusalBO;


}
