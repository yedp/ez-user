package com.ydp.ez.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.util.concurrent.RateLimiter;
import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.common.util.CodecUtil;
import com.ydp.ez.user.common.util.RedisUtil;
import com.ydp.ez.user.common.util.UserConstants;
import com.ydp.ez.user.common.vo.SessionVO;
import com.ydp.ez.user.common.vo.UserRespVo;
import com.ydp.ez.user.entity.User;
import com.ydp.ez.user.mapper.UserMapper;
import com.ydp.ez.user.service.IEmailService;
import com.ydp.ez.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    @Resource
    IEmailService emailService;

    @Autowired
    RedisUtil redisUtil;
    //用户信息redis_key
    private static final String USER_INFO_KEY = "USER_INFO:%s";


    @Override
    public boolean addUser(String userName, String password, String salt, String email) throws UserException {
        if (StringUtils.isEmpty(userName)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "用户名");
        }
        if (StringUtils.isEmpty(password)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "密码");
        }
        if (StringUtils.isEmpty(email)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "邮箱");
        }
        User user = new User(userName, password, salt, email);
        boolean isAddSuccess = userMapper.insertSelective(user) > 0 ? true : false;
        return true;
    }

    @Override
    public User queryByUserName(String userName) {
        if (userName == null) {
            return null;
        }
        String key = String.format(USER_INFO_KEY, userName);
        User user = redisUtil.get(key, User.class);
        if (user == null) {
            user = userMapper.selectByUserName(userName);
            if (user != null) {
                redisUtil.set(key, JSON.toJSONString(user), 3600);
            }
        }
        return user;
    }
}

