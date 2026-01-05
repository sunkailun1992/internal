package com.gb.constants;

/**
 * 
 * 
 * 类职责：<br/>
 * @author wangyifei
 *     
 * <p>Title: DataConstant.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020 工保科技 </p>
 * <p>Company: 工保科技 </p>
 *  
 * <p>Author:Cmexico.Li</p>
 * <p>CreateTime:2020年11月3日下午8:00:30
 * <p>$LastChangedBy$</p>
 * <p>$LastChangedRevision$ </p>  
 * <p>$LastChangedDate$ </p>
 * <p>$Id$ </p>
 */
public class TransCode {

  /*********************************************************************************************************/
  /** * T0001:创建支付业务订单 */
  public static final String TRA_TRANSCODE_TRADE_CREATE_PAY = "T000001";

  /**T000002:新增会员*/
  public static final String TRA_TRANSCODE_CUST_CREATE = "T000002";

  /**T000003:转账-根据子账簿编号*/
  public static final String TRA_TRANSCODE_TRANSFER_BY_CHANNEL_ACCOUNT_CODE = "T000003";

  /**T000004:新增渠道子账簿*/
  public static final String TRA_TRANSCODE_CST_CHANNEL_ACCOUNT_CREATE = "T000004";

  /**T000005:新增渠道*/
  public static final String TRA_TRANSCODE_CST_CHANNEL_CREATE = "T000005";

  /**T000006:新增交易支付关联*/
  public static final String TRA_TRANSCODE_TRADE_PAY_RELATE_CREATE = "T000006";
  
  /**T000007:转账-根据子账簿帐号*/
  public static final String TRA_TRANSCODE_TRANSFER_BY_CHANNLE_ACCOUNT = "T000007";

  /**T000008:明细重新对账*/
  public static final String TRA_TRANSCODE_QUERY_RECONCILIATION_RECORD_CHECK_AGAIN = "T000008";



  /*********************************************************************************************************/

  /**Q0001:交易订单查询*/
  public static final String TRA_TRANSCODE_QUERY_TRADE = "Q000001";
  /**Q0002:查询交易订单查询详情*/
  public static final String TRA_TRANSCODE_QUERY_TRADE_VIEW = "Q000002";

  /**Q0003:查询商户所有渠道子账户列表*/
  public static final String TRA_TRANSCODE_QUERY_CHANNEL_ACCOUNT_LIST = "Q000003";

  /**Q0004:支付流水查询*/
  public static final String TRA_TRANSCODE_QUERY_PAYMENT = "Q000004";

  /**Q000005:会员信息列表*/
  public static final String TRA_TRANSCODE_QUERY_CST_INFO_LIST = "Q000005";

  /**Q000006:渠道信息查询列表*/
  public static final String TRA_TRANSCODE_QUERY_SYS_CHANNEL_LIST = "Q000006";
  
  /**Q000007:查询支付流水详情*/
  public static final String TRA_TRANSCODE_QUERY_PAY_VIEW = "Q000007";

  /**Q000008:对账记录列表查询*/
  public static final String TRA_TRANSCODE_QUERY_RECONCILIATION_RECORD_LIST = "Q000008";

  /**Q000009:对账记录详情查询*/
  public static final String TRA_TRANSCODE_QUERY_RECONCILIATION_RECORD_DETAIL = "Q000009";

  /**Q000010:查看会员公钥信息*/
  public static final String TRA_TRANSCODE_QUERY_CST_ACCESS_AUTH = "Q000010";

  /**Q000011:支付流水查询_仅入账*/
  public static final String TRA_TRANSCODE_QUERY_PAYMENT_RECHARGE = "Q000011";


  /**N000001:支付通知*/
  public static final String TRA_TRANSCODE_MERCHANT_NOTICE_PAYMENT = "N000001";
}
