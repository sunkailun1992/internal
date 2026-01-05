package com.gb.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijinhao
 */
@Configuration
public class GongBaoConfig {

    /**
     * 服务名称
     */
    public static String serverName;
    /**
     * 工保云URL
     */
    public static String cloudUrl;
    /**
     * 工保云用户名
     */
    public static String cloudUsername;

    /**
     * 工保云密码
     */
    public static String cloudPassword;

    /**
     * 工保云平台编码
     */
    public static String cloudPlatCode;

    /**
     * Rsa私钥
     */
    public static String privateKey;

    /**
     * Rsa公钥
     */
    public static String publicKey;

    /**
     * 工保盾Rsa公钥
     */
    public static String publicKeyDun;

    /**
     * 工保盾Url
     */
    public static String gongbaoDunUrl;

    /**
     * 工保盾【针对工保网系统标志】APPID
     * 风控审核数据来源（工保盾给出）
     */
    public static Integer gongbaoDunAppId;

    /**
     * 工保盾【接口】版本号
     */
    public static String dunVersion;

    /**
     * 七陌云 账户访问域名  查询客户资料数据接口
     */
    public static String qimoHost;

    /**
     * 七陌云 帐号APISecret
     */
    public static String apiSecret;

    /**
     * 七陌云 账户编号(ACCOUNTID)
     */
    public static String accountId;

    /**
     * 七陌云版本号(可能会更改。目前使用的账号/密码  gbw/6253gsmh)
     */
    public static String version;

    /**
     * 工保云 模型数据查询/上报 url
     */
    public static String yunModelQueryUrl;
    /**
     * 工保云 公钥
     */
    public static String yunPublicKey;

    @Value("${gongbao.yun.yunModelQueryUrl}")
    public void setyunModelQueryUrl(String yunModelQueryUrl) {
        GongBaoConfig.yunModelQueryUrl = yunModelQueryUrl;
    }

    @Value("${gongbao.yun.yunPublicKey}")
    public void setYunPublicKey(String yunPublicKey) {
        GongBaoConfig.yunPublicKey = yunPublicKey;
    }


    @Value("${spring.application.name}")
    public void setServerName(String serverName) {
        GongBaoConfig.serverName = serverName;
    }

    @Value("${gongbao.cloudUrl}")
    public void setCloudUrl(String cloudUrl) {
        GongBaoConfig.cloudUrl = cloudUrl;
    }

    @Value("${gongbao.cloudUsername}")
    public void setCloudUsername(String cloudUsername) {
        GongBaoConfig.cloudUsername = cloudUsername;
    }

    @Value("${gongbao.cloudPassword}")
    public void setCloudPassword(String cloudPassword) {
        GongBaoConfig.cloudPassword = cloudPassword;
    }

    @Value("${gongbao.cloudPlatCode}")
    public void setCloudPlatCode(String cloudPlatCode) {
        GongBaoConfig.cloudPlatCode = cloudPlatCode;
    }

    @Value("${gongbao.privateKey}")
    public void setPrivateKey(String privateKey) {
        GongBaoConfig.privateKey = privateKey;
    }

    @Value("${gongbao.publicKey}")
    public void setPublicKey(String publicKey) {
        GongBaoConfig.publicKey = publicKey;
    }

    @Value("${gongbao.dun.publicKey}")
    public void setPublicKeyDun(String publicKeyDun) {
        GongBaoConfig.publicKeyDun = publicKeyDun;
    }

    @Value("${gongbao.dun.url}")
    public void setGongbaoDunUrl(String url) {
        GongBaoConfig.gongbaoDunUrl = url;
    }

    @Value("${gongbao.dun.appId}")
    public void setGongbaoDunAppId(Integer appId) {
        GongBaoConfig.gongbaoDunAppId = appId;
    }

    @Value("${gongbao.dun.version}")
    public void setDunVersion(String dunVersion) {
        GongBaoConfig.dunVersion = dunVersion;
    }

    @Value("${qimo.host}")
    public void setQimoHost(String host) {
        GongBaoConfig.qimoHost = host;
    }

    @Value("${qimo.apiSecret}")
    public void setApiSecret(String apiSecret) {
        GongBaoConfig.apiSecret = apiSecret;
    }

    @Value("${qimo.accountId}")
    public void setAccountId(String accountId) {
        GongBaoConfig.accountId = accountId;
    }

    @Value("${qimo.version}")
    public void setVersion(String version) {
        GongBaoConfig.version = version;
    }
}
