package com.gb.mq.trade;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: lixinyao
 * @since: 2022-11-25 09:05:58
 * @description: TODO 退汇回调
 */
@Data
public class RefundNotifyEvent implements Serializable {


    private static final long serialVersionUID = -5521756783023809081L;
    /**
     * 客户编号
     */
    private String custNo;

    /**
     * 客户名称
     */
    private String custName;

    private String orderNo;
    /**
     * 交易流水号
     */
    private String tradeNo;

    /**
     * 交易类型PAY/TRANSFER
     **/
    private String tradeType;

    /**
     * 平台编号
     */
    private String platNo;

    /**
     * 交易状态（00待支付；01成功；02失败；03处理中；04关闭/取消）
     * WAIT 、SUCCESS、FAIL, PROCESSING, CLOSED
     */
    private String tradeState;

    /**
     * 银行（渠道）支付流水
     */
    private String channelSerialNo;

    /**
     * 平台支付流水
     */
    private String payNo;
    /**
     * 线下支付码
     */
    private String payCode;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private String channelCode;

    private String channelName;

    private BigDecimal tradeAmount;

    private String openBranch;

    private String payFinishTime;

    private String custShortName;

    private String tradeInfo;

    //todo 退汇不在此发布内容 暂缓tag
    /**
     * 关联订单号1、冲正业务为原线下充值订单号2、充值业务表示原充值交易但结果未知的订单号，T+1日与第三方清算后告知该笔交易成功的补帐业务(以本次通知的新订单对账，费用按原订单扣收)
     */
    private String assoSerial;

}
