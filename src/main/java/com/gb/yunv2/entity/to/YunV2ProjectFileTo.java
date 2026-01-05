package com.gb.yunv2.entity.to;

import com.gb.common.entity.Project;
import lombok.Data;

/**
 * @className: com.gb.newYun.entity.Bo-> YunV2ProjectFileTo
 * @author: 王一飞
 * @createDate: 2021-12-28 1:54 下午
 * @description: 项目传输--附件
 */
@Data
public class YunV2ProjectFileTo {

    /**
     * @param project: 参数数据
     * @createAuthor: 王一飞
     * @title: 监听网数据, 并向云推送数据，进行格式转换
     * @createDate: 2022/1/7 2:14 下午
     * @description:
     * @return: YunV2EnterpriseTextTo.class
     */
    public static YunV2ProjectFileTo convert(Project project) {
        return new YunV2ProjectFileTo();
    }
}
