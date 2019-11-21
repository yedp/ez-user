package com.ydp.ez.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.ydp.ez.user.common.util.RedisUtil;
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
    @Autowired
    RedisUtil redisUtil;
    /**
     * 用户信息redis_key
     */
    private static final String USER_INFO_KEY = "USER_INFO:%s";

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public UserRespVo login(String userName, String password) {

        User user = this.getByUserName(userName);
        if (user != null) {
            return new UserRespVo("test", user.getUserName(), user.getNickName());
        }
        return null;
    }


    @Override
    public User getByUserName(String userName) {
        if (userName == null) {
            return null;
        }
        String key = this.getKey(userName);
        User user = redisUtil.get(key, User.class);
        if (user == null) {
            user = userMapper.selectByUserName(userName);
            if (user != null) {
                redisUtil.set(key, JSON.toJSONString(user), 3600);
            }
        }
        return user;
    }

    private String getKey(String userName) {
        return String.format(USER_INFO_KEY, userName);
    }
}
