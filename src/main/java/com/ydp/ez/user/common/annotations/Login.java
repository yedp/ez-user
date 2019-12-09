package com.ydp.ez.user.common.annotations;

import java.lang.annotation.*;

/**
 * 登录验证
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Login {
}
