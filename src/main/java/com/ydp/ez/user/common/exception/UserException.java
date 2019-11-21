package com.ydp.ez.user.common.exception;

import com.alibaba.fastjson.JSON;

/**
 * @author yedp
 * 20190612
 */
public class UserException extends Exception {

    private static final long serialVersionUID = -5231314837647805050L;
    /**
     * 错误码.
     */
    private String code;
    /**
     * 错误信息.
     */
    private String message;
    /**
     * 结果对象.
     */
    private Object result;

    private Exception exception;

    public UserException(ErrorCode errCode) {
        this.code = errCode.getCode();
        this.message = errCode.getMessage();
    }

    public UserException(ErrorCode errCode, Exception exception) {
        this.code = errCode.getCode();
        this.message = errCode.getMessage();
        this.exception = exception;
    }
    public UserException(ErrorCode errCode, String extend) {
        this.code = errCode.getCode();
        this.message = String.format(errCode.getMessage(), extend);
    }

    public UserException(String errorCode, String errorMsg) {
        this.code = errorCode;
        this.message = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
