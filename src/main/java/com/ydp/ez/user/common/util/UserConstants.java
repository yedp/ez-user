package com.ydp.ez.user.common.util;

public class UserConstants {
    public static final String HTTP_PARAMETER_REQUEST_TOKEN_KEY = "token";
    public static final String SESSION_KEY = "session_key:%s";
    // token过期时间 31天
    private static final long TOKEN_EXPIRE_TIME = 31 * 24 * 60 * 60 * 1000;
}
