package com.gb.dun.service;

import com.gb.dun.entity.dto.QuerySubmitDTO;
import com.gb.dun.entity.dto.QuestionnaireDTO;
import com.gb.dun.entity.vo.QuestionnaireRespVO;

/**
 * @ClassName QuestionnaireService
 * @Author yyl
 * @Date 2022-09-05 11:14:40
 * @Description 智能问卷服务接口
 * @Version 1.0
 */
public interface QuestionnaireService {


    /**
     * 获取智能问卷内容
     * @param questionnaireDTO
     * @return
     */
    QuestionnaireRespVO queryQuestionnaireContent(QuestionnaireDTO questionnaireDTO);

    /**
     * 新增智能问卷信息
     * @param querySubmitDTO
     * @return
     */
    boolean saveQuestionnaire(QuerySubmitDTO querySubmitDTO);

}
