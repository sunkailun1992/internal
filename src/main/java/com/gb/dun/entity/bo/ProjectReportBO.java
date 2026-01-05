package com.gb.dun.entity.bo;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;

/**
 * @author yyl
 */
@Data
public class ProjectReportBO implements Serializable {

    /**
     * 项目资料名称
     */
    private CloudBO dataFileName;

    /**
     * 项目资料路径
     */
    private CloudBO creditReportFilepaths;

    @Override
    public String toString() {
        return ObjectUtils.toString(dataFileName, String::new)
                + ObjectUtils.toString(creditReportFilepaths, String::new);
    }
}
