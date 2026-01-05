package com.gb.dun.controller;

import com.gb.dun.entity.dto.QuerySubmitDTO;
import com.gb.dun.entity.dto.QuestionnaireDTO;
import com.gb.dun.entity.vo.QuestionnaireRespVO;
import com.gb.dun.service.QuestionnaireService;
import com.gb.utils.Json;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.enumeration.ReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName QuestionnaireController
 * @Author yyl
 * @Date 2022-09-05 11:07:06
 * @Description 智能问卷请求接口
 * @Version 1.0
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/questionnaire")
@Api(tags = "智能问卷控制器")
public class QuestionnaireController {

    private QuestionnaireService questionnaireService;


    /**
     * 获取工保盾问卷数据
     * @param questionnaireDTO
     * @return
     */
    @Methods(methodsName = "获取工保盾问卷数据", methods = "queryQuestionnaireContent")
    @ApiOperation(value = "获取工保盾问卷数据", httpMethod = "POST", notes = "获取工保盾问卷数据", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireDTO", value = "请求数据", dataType = "QuestionnaireDTO", required = true)
    })
    @PostMapping("/query")
    public Json<QuestionnaireRespVO> queryQuestionnaireContent(@RequestBody QuestionnaireDTO questionnaireDTO) {
        QuestionnaireRespVO questionnaireRespVO = questionnaireService.queryQuestionnaireContent(questionnaireDTO);
        return new Json(ReturnCode.成功, questionnaireRespVO);
    }

    /**
     * 新增工保盾问卷数据
     * @param querySubmitDTO
     * @return
     */
    @Methods(methodsName = "新增工保盾问卷数据", methods = "saveQuestionnaire")
    @ApiOperation(value = "新增工保盾问卷数据", httpMethod = "POST", notes = "新增工保盾问卷数据", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "querySubmitDTO", value = "请求数据", dataType = "QuerySubmitDTO", required = true)
    })
    @PostMapping("/save")
    public Json<Boolean> saveQuestionnaire(@RequestBody QuerySubmitDTO querySubmitDTO) {
        boolean saveQuestionnaire = questionnaireService.saveQuestionnaire(querySubmitDTO);
        return new Json(ReturnCode.成功, saveQuestionnaire);
    }

}
