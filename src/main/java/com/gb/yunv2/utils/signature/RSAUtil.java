package com.gb.yunv2.utils.signature;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author wangyifei
 */
@Slf4j
public class RSAUtil {
    /**
     * 加密
     * @param orgData
     * @param publicKey
     * @return
     */
    public static String encryptByPublicKey(String orgData, String publicKey) {
        RSA rsa = SecureUtil.rsa(null, publicKey);
        return new String(Base64Util.encode(rsa.encrypt(orgData, KeyType.PublicKey)));
    }

    /**
     * 解密
     * @param signData
     * @param privateKey
     * @return
     */
    public static String decryptByPrivateKey(String signData, String privateKey) {
        RSA rsa = SecureUtil.rsa(privateKey, null);
        return new String(rsa.decrypt(signData, KeyType.PrivateKey));
    }

    /**
     * 验签
     * @param orgData
     * @param signData
     * @param privateKey
     * @return
     */
    public static boolean check(String orgData, String signData, String privateKey) {
        try {
            RSA rsa = SecureUtil.rsa(privateKey, null);
            log.debug("参数内容为：" + orgData);
            String data = new String(rsa.decrypt(signData, KeyType.PrivateKey));
            log.debug("解密内容为：" + data);
            if (StringUtils.hasText(data) && orgData.equals(data)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            log.error("RSA签名验证失败: ", e.getMessage());
            return false;
        }

    }


}