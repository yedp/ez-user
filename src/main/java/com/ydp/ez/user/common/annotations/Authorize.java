package com.ydp.ez.user.common.annotations;

import org.apache.logging.log4j.util.Strings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 授权
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
    /**
     * 角色名称：默认不需要角色（只需要登录就可以）
     *
     * @return
     */
    String roleName() default Strings.EMPTY;

    /**
     * 权限：默认不需要权限（只需要登录就可以）
     *
     * @return
     */
    int permissionBit() default 16;
}