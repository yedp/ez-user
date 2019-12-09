package com.ydp.ez.user.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限验证
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
    String systemCode() default "stock";

    String moduleCode() default "grid";

    int permissionBit() default 8;
}