package com.ydp.ez.user.common.exception;

public interface ErrorCode {
    /**
     * 得到错误码.
     *
     * @return 错误码.
     */
    String getCode();

    /**
     * 得到错误消息.
     *
     * @return 错误消息.
     */
    String getMessage();

    /**
     * 得到系统名称.
     *
     * @return 系统名称.
     */
    String getSystemName();
}