package com.ydp.ez.user.service;

import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.common.vo.UserRespVo;
import com.ydp.ez.user.entity.User;
import com.ydp.ez.user.entity.UserRoleRelation;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: yedp
 * @Date: 2019/11/20 19:57
 * @Description：${description}
 */
public interface IUserService {

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
     * 获取信息
     *
     * @param userName
     * @return
     */
    User queryByUserName(String userName);

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
