package com.ydp.ez.user.controller;


import com.ydp.ez.user.common.exception.ErrorCode;
import com.ydp.ez.user.common.vo.Result;

/**
 * @author yedp 20190612
 */
public class BaseController {

    /**
     * 成功-无返回结果
     *
     * @return
     */
    protected Result success() {
        Result result = new Result();
        result.setStatus(Result.Status.SUCCESS.code());
        return result;
    }

    /**
     * 成功-有返回结果
     *
     * @param content
     * @return
     */
    protected Result success(Object content) {
        Result result = new Result();
        result.setContent(content);
        result.setStatus(Result.Status.SUCCESS.code());
        return result;
    }

    protected Result error(ErrorCode errCode) {
        return new Result(errCode.getCode(), errCode.getMessage(), Result.Status.ERROR);
    }

    protected Result error(ErrorCode errCode, String message) {
        return new Result(errCode.getCode(), String.format(errCode.getMessage(), message), Result.Status.ERROR);
    }

    protected Result error(String code, String message) {
        return new Result(code, message, Result.Status.ERROR);
    }

}