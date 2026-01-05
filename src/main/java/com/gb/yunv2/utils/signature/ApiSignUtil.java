package com.gb.yunv2.utils.signature;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;


@Slf4j
public class ApiSignUtil {
    public static String encode(Object req, String publicKey) {
        try {
            Map<String, Object> map;
            if(req instanceof String) {
                map = JacksonUtils.jsonString2Obj((String)req, HashMap.class);
            } else if(req instanceof Map) {
                map = (Map) req;
            } else {
                map = BeanTool.toMap(req);
            }
            String signStr = mapCovertString(map, null, "&", "=");
            log.info("加密前明文 {}", signStr);
            String signature = RSAUtil.encryptByPublicKey(signStr, publicKey);
            return signature;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static Boolean check(Object req, String sign, String privateKey){
        try {
            Map<String, Object> map;
            if(req instanceof String) {
                map = JacksonUtils.jsonString2Obj((String)req, HashMap.class);
            } else if(req instanceof Map) {
                map = (Map) req;
            } else {
                map = BeanTool.toMap(req);
            }
            map.remove("sign");
            String signStr = mapCovertString(map, null, "&", "=");
            log.info("根据报文生成的加密前明文 signStr {}", signStr);
            String plain = RSAUtil.decryptByPrivateKey(sign, privateKey);
            log.info("sign解密出来的加密前明文 plain {}", plain);
            if(StringUtils.hasText(plain) && plain.equals(signStr)){
                return true;
            }
            log.error("签名验证失败");
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 按照Json数据中首字母大小写正序排列
     *
     * @param map             Map实体类
     * @param removeKeyList   不需要拼接的key值
     * @param connectSymlinks 连接符号，连接两个key/value字段之间的符号，例如‘&’、‘,’号
     * @param assignment      赋值符号,例如‘=’号
     * @return
     */
    public static String mapCovertString(Map map, List<String> removeKeyList, String connectSymlinks, String assignment) {
        StringBuffer content = new StringBuffer();
        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        boolean flag = false;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            flag = false;
            if (removeKeyList != null && removeKeyList.size() > 0) {
                for (String removeKey : removeKeyList) {
                    if (removeKey.equals(key)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    continue;
                }
            }
            if(ObjectUtils.isEmpty(map.get(key))) {
                continue;
            }
            String value = map.get(key) instanceof Map || map.get(key) instanceof List ? JacksonUtils.obj2JsonString(map.get(key)) : String.valueOf(map.get(key));
            // 空串不参与签名
            if (!StringUtils.hasText(value)) {
                continue;
            }
            content.append((i == 0 ? "" : connectSymlinks) + key + assignment + value);
        }
        String signSrc = content.toString();
        if (signSrc.startsWith(connectSymlinks)) {
            signSrc = signSrc.replaceFirst(connectSymlinks, "");
        }
        return signSrc;
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "        \"resultAttributtes\": [\n" +
                "            \"project_internal_number\",\n" +
                "            \"project_number\",\n" +
                "            \"project_name\",\n" +
                "            \"bid_name\",\n" +
                "            \"bid_number\",\n" +
                "            \"project_masterdata\"\n" +
                "        ],\n" +
                "        \"dataDictionary\": false,\n" +
                "        \"returnScore\": false,\n" +
                "        \"param\": {\n" +
                "            \"project_name\": \"2019年惠河、粤赣高速公路沿线附属房建改造工程\",\n" +
                "            \"project_masterdata\": {\n" +
                "                \"bid_inviter\": \"广东省高速公路有限公司粤赣分公司\"\n" +
                "            }\n" +
                "        }\n" +
                "    }";

        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANmSk+0ZJGS7RzivSla9UhMUhg6A6VuspWG4ghhbUJVZO/tWVvO1lvXivRbHUL753sdmCcc2isaMoTuZO1NSqmO1Xko1okX+e7x2DmJKHIp4rsqYiK2wFPlQglvnH3+6P/yrzx1SUl7C5s8TUck1/r/RHbK4B04o6SyUaffuT8z7AgMBAAECgYEAoISKzG8zMXoV9pUktE/i4J7QtJyZDgCW1zzIBm5ASp9WKH0vk4gSgwAwX0DXqr4whU4bwrTTt96DCbRoV3XyrFb1DlYm3yEm2cgYukxNMtxhlL3Tpu7bL3YdGYaAIYYtgbwzvvMvT7omKI85iztweKRWQ0VjR8sdT8oE9gAsSykCQQDzrVvH6MEayi2rsesmg2Fd0bJYCp3zUuv6kbeXy+mANYbeePjPTbu/kmir/HZn4fnJfoGxCW/o0LxW56ubb0v1AkEA5JNE9RoEbRAA5SDJpRi/1u/KFH35BU88lVGixXDNaS8YFWjZAswIUViyC7ms2cs8bHgKNt4zOhSJbM1DtsePLwJAWntBvE5Z/eea48kx5uAb9GlcDsMKeYKN60HWaUAnWRsHFG7Y/KkBkRX9VfdtxA8t4DrgT2uQqWNwu9hUaWf/TQJBAIwFl2GafYmeGx5BtqUXgzWVETL6dJkHEDLcnpza3EqKGfPLldz7xkCm1/MM3FFTCgHci01PUwxKVmE7YTbQCusCQHwyerbP/DaI/eUkyE06yzbrvAJXmFR3ck+AZaQi3eiS5WGXeOMKBlVTOShsLdn82v6vKzfRggkEWeVkB+mcDaU=";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZkpPtGSRku0c4r0pWvVITFIYOgOlbrKVhuIIYW1CVWTv7VlbztZb14r0Wx1C++d7HZgnHNorGjKE7mTtTUqpjtV5KNaJF/nu8dg5iShyKeK7KmIitsBT5UIJb5x9/uj/8q88dUlJewubPE1HJNf6/0R2yuAdOKOkslGn37k/M+wIDAQAB";
        String sign = encode(json, publicKey);
        System.out.println(sign);
        //System.out.println(check(json, sign, privateKey));
    }
}
