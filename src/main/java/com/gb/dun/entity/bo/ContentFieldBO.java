package com.gb.dun.entity.bo;

import lombok.Data;

import java.io.Serializable;


/**
 * <p>
 * 内容字段BO
 * </p>
 *
 * @author sunx
 * @since 2021-03-17
 */
@Data
public class ContentFieldBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 值
     */
    private String value;

    /**
     * 标签
     */
    private String label;


}