package com.gb.dun.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 盾日志注解
 * </p>
 *
 * @author sunx
 * @since 2021-12-26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DunLog {
}
