package com.gb.yun.service;


import com.gb.common.entity.Enterprise;
import com.gb.common.entity.Project;
import com.gb.mq.yun.YunSyncDataEvent;
import com.gb.yun.enmus.YunObjectTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * 工保云推送服务接口
 * @author: sunx
 * @Date: 2021/03/22 23:30
 * @descript: 工保云推送服务接口
 */
public interface YunPushService {

    /**
     * 获取项目信息
     *
     * @param project: 项目
     * @return Map<String, Object>
     * @author sunx
     * @since 2021-03-18
     */
    Map<String, Object> queryProjectInfo(Project project);

    /**
     * 获取企业信息
     *
     * @param enterprisList: 企业列表
     * @return Map<String, Object>
     * @author sunx
     * @since 2021-03-18
     */
    Map<String,Object> queryEnterpriseInfo(List<Enterprise> enterprisList);

    /**
     * 推送到云
     *
     * @param linkId: 关联ID
     * @param linkName 关联Name
     * @param objectTypeEnum 业务类型
     * @param obj 请求参数
     * @return  Map<String, String> 响应结果
     * @author sunx
     * @since 2021-03-18
     * @throws Exception
     */
    Map<String, String> pushCloud(String linkId, String linkName, YunObjectTypeEnum objectTypeEnum, Object obj) throws Exception;

    /**
     * 请求参数校验
     *
     * @param event: 参数值校验
     * @author sunx
     * @since 2021-03-18
     * @throws Exception
     */
    void validateParams(YunSyncDataEvent event) throws Exception;
}
