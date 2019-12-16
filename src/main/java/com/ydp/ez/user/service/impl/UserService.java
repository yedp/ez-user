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
    //验证码redis_key
    private static final String VALID_CODE = "VALID_CODE:%s";
    // token过期时间 31天
    private static final long TOKEN_EXPIRE_TIME = 31 * 24 * 60 * 60 * 1000;
    //限流
    private static final RateLimiter rateLimiter = RateLimiter.create(5, 20, TimeUnit.SECONDS);


    @Override
    public UserRespVo register(String username, String password, String email, String validCode) throws UserException {
        if (!rateLimiter.tryAcquire()) {
            throw new UserException(UserErrorCode.SYSTEM_REMIND, "系统忙，请稍后再试");
        }

        if (!this.verifyValidCode(email, validCode)) {
            throw new UserException(UserErrorCode.SYSTEM_REMIND, "验证码不正确");
        }
        String salt = CodecUtil.generateRandomStr(6);
        String encodePassword = CodecUtil.encodeSha256Hash(password, salt);
        User user = new User(username, encodePassword, salt, email);
        boolean isAddSuccess = userMapper.insertSelective(user) > 0 ? true : false;
        if (isAddSuccess) {
            return login(user.getUserName(), password);
        }
        throw new UserException(UserErrorCode.SYSTEM_REMIND, "注册失败:用户名或者邮箱注册");
    }

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public UserRespVo login(String userName, String password) throws UserException {
        if (!rateLimiter.tryAcquire()) {
            throw new UserException(UserErrorCode.SYSTEM_REMIND, "系统忙，请稍后再试");
        }
        UserRespVo userRespVo = null;
        User user = this.queryByUserName(userName);
        if (user != null && CodecUtil.verifySha256Hash(password, user.getSalt(), user.getPassword())) {
            Date expireTime = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
            String token = JWT.create().withExpiresAt(expireTime).withAudience(user.getUserName()).sign(Algorithm.HMAC256(password));
            userRespVo = new UserRespVo(token, user.getUserName(), user.getNickName());
            String sessionKey = String.format(UserConstants.SESSION_KEY, user.getUserName());
            SessionVO sessionVO = redisUtil.get(sessionKey, SessionVO.class);
            if (sessionVO == null) {
                sessionVO = new SessionVO(user);
            }
            sessionVO.setToken(token);
            redisUtil.set(sessionKey, JSON.toJSONString(sessionVO));
        } else {
            throw new UserException(UserErrorCode.SYSTEM_REMIND, "用户名或者密码错误");
        }


        return userRespVo;
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

    private static final String EMAIL_SUBJECT = "ezmmm注册验证码";
    private static final String EMAIL_CONTENT = "ezmmm注册验证码:'%s'，5分钟内有效";

    @Override
    public void sendValidCode(String email) throws UserException {
        if (StringUtils.isEmpty(email)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "邮箱");
        }
        if (!rateLimiter.tryAcquire()) {
            throw new UserException(UserErrorCode.SYSTEM_REMIND, "系统忙，请稍后再试");
        }
        String key = String.format(VALID_CODE, email);
        String validCode = redisUtil.getStr(key);
        if (validCode == null) {
            validCode = CodecUtil.generateRandomStr(6);
            redisUtil.set(key, validCode, 300);
        }
        emailService.sendEmail(email, EMAIL_SUBJECT, String.format(EMAIL_CONTENT, validCode));
    }

    @Override
    public boolean verifyValidCode(String email, String validCode) {
        if (email == null || validCode == null) {
            return false;
        }
        String key = String.format(VALID_CODE, email);
        String cacheValidCode = String.valueOf(redisUtil.get(key));
        if (cacheValidCode == null) {
            return false;
        }
        boolean result = validCode.equalsIgnoreCase(cacheValidCode);
        if (result) {
            redisUtil.del(key);
        }
        return result;
    }
}

