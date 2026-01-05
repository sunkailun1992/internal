package com.gb.yun.log.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * 云数据同步日志实体类
 * @author sunx
 * @DateTime 2018/7/16  上午10:10
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@Data
@ApiModel(value = "云数据同步日志实体类")
@SuppressWarnings(value = "all")
public class YunDataSyncLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ApiModelProperty(name = "_id", value = "序列")
    private ObjectId _id;

    @ApiModelProperty(name = "ip", value = "本地IP")
    private String ip;

    @ApiModelProperty(name = "mqReqJson", value = "内部请求JSON报文")
    private String mqReqJson;

    @ApiModelProperty(name = "contentId", value = "投保内容ID")
    private String contentId;

    @ApiModelProperty(name = "projectTextJson", value = "项目字段JSON报文")
    private String projectTextJson;

    @ApiModelProperty(name = "respProjectTextJson", value = "项目附件JSON报文")
    private String projectFileJson;

    @ApiModelProperty(name = "enterpriseTxtJson", value = "企业字段JSON报文")
    private String enterpriseTxtJson;

    @ApiModelProperty(name = "enterpriseFileJson", value = "企业附件JSON报文")
    private String enterpriseFileJson;

    @ApiModelProperty(value = "失败消息")
    private String errorMsg;
    
    @ApiModelProperty(name = "createName", value = "创建人")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyDateTime;

    @ApiModelProperty(name = "modifyName", value = "修改人")
    private String modifyName;

    @ApiModelProperty(name = "operationContent", value = "备注")
    private String description;

}
