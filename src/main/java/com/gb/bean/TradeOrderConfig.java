package com.gb.bean;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 交易平台固定字段
 * @author lxy
 * @since 2022-11-28
 */
@Configuration
@RefreshScope
@Data
public class TradeOrderConfig {


	@Value("${trade.publicKey}")
	private String publicKey;

	@Value("${trade.createPayOrderUrl:创建支付订单url未配置}")
	private String createPayOrderUrl;

	@Value("${trade.transferUrl:转账url未配置}")
	private String transferUrl;

	@Value("${trade.tradeBinding:绑定url未配置}")
	private String tradeBinding;

	/**
	 * 渠道编号
	 */
	@Value("${trade.channelCode:channelCode未配置}")
	private String channelCode;
	/**
	 * 子账簿编号
	 */
	@Value("${trade.channelAccountCode:channelAccountCode未配置}")
	private String channelAccountCode;

	/**
	 * 商户编号
	 */
	@Value("${trade.custNo:未配置}")
	private String custNo;

	/**
	 * 平台编号
	 */
	@Value("${trade.platNo:未配置}")
	private String platNo;


	/**
	 * 工保网配置
	 */
	@Value("${trade.defaultNoticeUrl:默认url未配置}")
	private String defaultNoticeUrl;
	/**
	 * 工保网配置
	 */
	@Value("${trade.transferNoticeUrl:转账通知url未配置}")
	private String transferNoticeUrl;





}