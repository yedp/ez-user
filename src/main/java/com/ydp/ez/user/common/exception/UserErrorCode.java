package com.ydp.ez.user.common.exception;

/**
 * @author yedp 20190612
 */
public enum UserErrorCode implements ErrorCode {

    SYSTEM_ERROR("2001", "系统异常"),
    SYSTEM_ERROR_WITH_MSG("2002", "系统异常:%s"),
    SYSTEM_REMIND("2003", "系统提醒:%s"),
    PARAM_NULL("2004", "参数:'%s'不能为空"),
    ALREADY_EXIST("2005", "'%s'已存在,请换个或者找回"),
    USER_CENTER_RETURN_NULL("2006", "用户中心反馈数据为空"),
    AUTH_FAIL("2007", "认证失败：%s"),
    PARAM_ERROR("2008", "参数错误:%s"),
    LOGIN_BY_OTHER("2009", "已被其他登录挤下线"),;


    private String code;

    private String message;

    UserErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserErrorCode getByCode(String code) {
        for (UserErrorCode errorCode : UserErrorCode.values()) {
            if (code.equals(errorCode.getCode())) {
                return errorCode;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getSystemName() {
        return "stock";
    }
}