package com.ydp.ez.user.common.annotations;

import java.lang.annotation.*;

/**
 * 定义接口输入输出日志的切入点
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Log {

    /**
     * 打印日志类型，{@link LogTypeEnum}
     *
     * @return
     */
    LogTypeEnum value() default LogTypeEnum.ALL;

    /**
     * 日志输出前缀（建议配置接口名称）
     *
     * @return
     */
    String prefix() default "";
}
