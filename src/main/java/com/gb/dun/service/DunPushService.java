package com.gb.dun.service;


import com.gb.dun.entity.vo.DunResponseVO;
import com.gb.mq.dun.DunPushEvent;

/**
 * 推送盾服务类接口
 * @author yyl
 */
public interface DunPushService {

    /**
     * 业务处理
     * @param dunPushEvent 请求参数
     * @return DunResponseVO;
     */
    DunResponseVO dealBusiness(DunPushEvent dunPushEvent);
}
