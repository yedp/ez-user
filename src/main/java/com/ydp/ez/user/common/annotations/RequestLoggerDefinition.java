package com.ydp.ez.user.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求日志定义注解 - 定义日志记录规则.
 *
 * @author fengjiantao.
 * @since 2016/6/23
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLoggerDefinition {

    /**
     * 是否记录请求日志开关.
     *
     * @return true/false.
     */
    boolean ignore() default false;

    /**
     * 请求日志忽略列表 -- 当 ignore 为 false 时生效.
     *
     * @return 忽略列表.
     */
    String[] ignorePaths() default {};
}