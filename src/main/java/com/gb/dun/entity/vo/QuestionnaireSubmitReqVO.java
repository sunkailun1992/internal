package com.gb.dun.entity.vo;

import com.gb.dun.entity.dto.QuerySubmitDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName QuestionnaireSubmitReqVO
 * @Author yyl
 * @Date 2022-09-07 15:01:04
 * @Description QuestionnaireSubmitReqVO
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class QuestionnaireSubmitReqVO extends DunBaseVo {

    private static final long serialVersionUID = -6499328229379938090L;

    private QuerySubmitDTO questionnaireSubmitBO;
}
