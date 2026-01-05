package com.gb.dun.log.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName DunQuestionnaireCommitLog
 * @Author yyl
 * @Date 2022-09-15 09:32:20
 * @Description DunQuestionnaireCommitLog
 * @Version 1.0
 */
@Data
@ApiModel(value = "智能问卷提交日志实体类")
@SuppressWarnings(value = "all")
public class DunQuestionnaireCommitLog implements Serializable {

    @Id
    @ApiModelProperty(name = "_id", value = "序列")
    private ObjectId _id;

    @ApiModelProperty(name = "ip", value = "访问IP")
    private String ip;

    @ApiModelProperty(name = "reqJson", value = "请求JSON报文")
    private String reqJson;

    @ApiModelProperty(name = "respJson", value = "响应JSON报文")
    private String respJson;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

    @ApiModelProperty(name = "operationContent", value = "备注")
    private String description;

}
