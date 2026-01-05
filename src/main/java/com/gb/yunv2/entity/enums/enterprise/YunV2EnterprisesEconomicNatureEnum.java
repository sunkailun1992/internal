package com.gb.yunv2.entity.enums.enterprise;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @className: com.gb.yun_v2.entity.enums.enterprise-> YunV2EnterprisesEconomicNatureEnum
 * @author: 王一飞
 * @createDate: 2022-03-16 4:12 下午
 * @description: 企业经济性质(工保网<-->工保云  双方code对应)
 */
@Getter
@AllArgsConstructor
public enum YunV2EnterprisesEconomicNatureEnum {
    /**
     * 企业经济性质
     */
    国有("国有全资", "110", "国有", "1"),
    集体("集体全资", "120", "集体", "2"),
    民营("个体经营", "175", "民营", "3"),
    私营("私有", "170", "私营", "4"),
    外资("国外投资", "300", "外资", "5"),
    合资("中外合资", "310", "合资", "6"),
    其他("其他", "900", "其他", "7"),
    无法识别("", "", "", "");

    //  云名称
    private String yunName;
    //  云code
    private String yunCode;
    //  网名称
    private String wangName;
    //  网code
    private String wangCode;


    /**
     * 根据网code 获取对应的类型
     */
    public static YunV2EnterprisesEconomicNatureEnum getYunEnterpriseEnumByWaningCode(String wangCode) {
        Optional<YunV2EnterprisesEconomicNatureEnum> value = Arrays.stream(YunV2EnterprisesEconomicNatureEnum.values())
                .filter(x -> x.getWangCode().equals(wangCode))
                .findFirst();
        return value.orElse(无法识别);
    }

    /**
     * 根据云code 获取对应的类型
     */
    public static YunV2EnterprisesEconomicNatureEnum getYunEnterpriseEnumByYunCode(String yunCode) {
        Optional<YunV2EnterprisesEconomicNatureEnum> value = Arrays.stream(YunV2EnterprisesEconomicNatureEnum.values())
                .filter(x -> x.getYunCode().equals(yunCode))
                .findFirst();
        return value.orElse(无法识别);
    }
}
