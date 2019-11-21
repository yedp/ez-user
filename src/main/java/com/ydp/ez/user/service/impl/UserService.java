package com.ydp.ez.user.service.impl;

import com.ydp.ez.user.common.vo.UserRespVo;
import com.ydp.ez.user.entity.User;
import com.ydp.ez.user.mapper.UserMapper;
import com.ydp.ez.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: yedp
 * @Date: 2019/11/20 19:57
 * @Description：${description}
 */
@Service
@Slf4j
public class UserService implements IUserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public UserRespVo login(String userName, String password) {
        User user = this.selectByUserName(userName);
        if (user != null) {
            return new UserRespVo("test", user.getUserName(), user.getNickName());
        }
        return null;
    }

    @Override
    public User selectByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }
}
