package com.ydp.ez.user.service;

import com.ydp.ez.user.common.vo.UserRespVo;
import com.ydp.ez.user.entity.User;

/**
 * @Author: yedp
 * @Date: 2019/11/20 19:57
 * @Description：${description}
 */
public interface IUserService {

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    UserRespVo login(String userName, String password);

    User getByUserName(String userName);
}
