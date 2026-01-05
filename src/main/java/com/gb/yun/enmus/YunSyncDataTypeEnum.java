package com.gb.yun.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 推送云数据同步类型枚举类
 * @author: ranyang
 * @Date: 2021/03/30 09:05
 * @descript:
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
@Slf4j
public enum YunSyncDataTypeEnum {
    EN_TXT_ADD("en_txt_add","企业字段新增"),
    EN_TXT_UPATE("en_txt_update","企业字段更新"),
    EN_FILE_ADD("en_file_add","企业附件新增"),
    EN_FILE_UPDATE("en_file_update","企业附件更新"),
    PJ_TXT_ADD("pj_txt_add","项目字段新增"),
    PJ_TXT_UPDATE("pj_txt_update","项目字段更新"),
    PJ_FILE_ADD("pj_file_add","项目附件新增"),
    PJ_FILE_UPDATE("pj_file_update","项目附件更新");

    /**
     * 推送类型码
     */
    private String code;

    /**
     * 推送类型名称
     */
    private String name;


    /**
     * 根据推送类型代码获取枚举类型
     */
    public static YunSyncDataTypeEnum getByCode(String code) {
        if(StringUtils.isBlank(code)){
            log.debug("【工保云】业务处理类型码值为空！");
            return null;
        }
        Optional<YunSyncDataTypeEnum> codeEnum = Arrays.stream(YunSyncDataTypeEnum.values()).filter(x -> code.equals(x.getCode())).findFirst();
        return codeEnum.orElse(null);
    }

}
