package com.gb.rpc.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 潜在客户来源表
 * </p>
 *
 * @author lijh
 * @since 2021-06-03
 */
@Data
public class PotentialCustomerSource {

    private String id;
    private String name;

    private String description;

    private LocalDateTime createDateTime;

    private String createName;

    private LocalDateTime modifyDateTime;

    private String modifyName;

    private Boolean isDelete;

    private Integer type;

    private Integer state;

    private String label;

    private Integer sorting;

}
