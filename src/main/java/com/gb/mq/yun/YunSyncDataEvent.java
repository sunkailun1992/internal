package com.gb.mq.yun;

import com.gb.common.entity.Enterprise;
import com.gb.common.entity.Project;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author: ranyang
 * @Date: 2021/03/29 19:18
 * @descript: 同步工保云企业和项目数据事件1
 */
@Data
public class YunSyncDataEvent {

    /**
     * 投保内容id
     */
    @NotBlank
    private String contentId;

    /**
     * 当前登陆用户名
     */
    @NotBlank
    private String createName;

    /**
     * 项目相关信息
     */
    private Project project;

    /**
     * 企业相关信息
     */
    private List<Enterprise> enterprisList;

}
