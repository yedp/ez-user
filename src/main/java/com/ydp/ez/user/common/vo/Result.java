package com.ydp.ez.user.common.vo;



import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author yedp 20190612
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object content;
    private String status;
    private String code;
    private String message;

    public Result() {
    }

    public Result(Object content) {
        this.content = content;
    }

    public Result(Object content, Status status) {
        this.content = content;
        this.status = status.code;
    }

    public Result(String code, String message, Status status) {
        this.code = code;
        this.message = message;
        this.status = status.code;
    }


    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public enum Status {
        /**
         * 成功或失败结果
         */
        SUCCESS("OK"),
        ERROR("ERROR");

        private String code;

        private Status(String code) {
            this.code = code;
        }

        public String code() {
            return this.code;
        }
    }
}
