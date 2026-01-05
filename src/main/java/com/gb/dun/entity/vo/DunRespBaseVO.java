package com.gb.dun.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName DunRespBaseVO
 * @Author yyl
 * @Date 2022-09-05 17:21:55
 * @Description DunRespBaseVO
 * @Version 1.0
 */
@Data
public class DunRespBaseVO<T> implements Serializable {

    private static final long serialVersionUID = -3859376658982827251L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 详细错误码
     */
    private String detailedErrorCode;

    /**
     * 状态
     */
    private boolean status;

    /**
     * 返回数据
     */
    private T data;
}
