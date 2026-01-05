package com.gb.dun.entity.vo;

import com.gb.dun.entity.dto.QuestionnaireDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName QuestionnaireReqVO
 * @Author yyl
 * @Date 2022-09-06 16:04:28
 * @Description QuestionnaireReqVO
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class QuestionnaireReqVO extends DunBaseVo {

    private static final long serialVersionUID = -3188603430266884923L;

    private QuestionnaireDTO questionnaireBO;

}
