package com.gb.mq.dun;

import com.gb.dun.enmus.DunPushTypeEnum;
import lombok.Data;

/**
 * 推送盾接收事件Event
 * @author: ranyang
 * @Date: 2021/03/29 19:18
 * @descript: 推送到工保盾风控审核事件
 */
@Data
@SuppressWarnings(value = "all")
public abstract class DunPushEvent{

    public DunPushTypeEnum pushType;

    public DunPushEvent(){
        setPushType();
    }
    /**
     * 设置推送类型
     */
    public abstract void setPushType();

}
