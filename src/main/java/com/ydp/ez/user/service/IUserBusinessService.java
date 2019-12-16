package com.ydp.ez.user.service;

import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.common.vo.UserRespVo;
import com.ydp.ez.user.entity.User;

public interface IUserBusinessService {

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @param email
     * @param validCode
     * @return
     * @throws UserException
     */
    UserRespVo register(String username, String password, String email, String validCode) throws UserException;

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    UserRespVo login(String userName, String password) throws UserException;

    /**
     * 发送验证码
     */
    void sendValidCode(String email) throws UserException;

    /**
     * 验证验证码
     *
     * @param email
     * @param validCode
     * @return
     */
    boolean verifyValidCode(String email, String validCode);
}
