package com.gb.mq.dun;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * 盾交互同步回调响应结果
 * @author sunx
 * @DateTime 2018/7/16  上午10:10
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@Data
public class DunSyncResponseEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  风控订单ID
     */
    private String riskOrderId;

    /**
     *  保单ID
     */
    private String castInsuranceId;


    /**
     * 风控默认同步回调
     */
    public boolean response = true;


}
