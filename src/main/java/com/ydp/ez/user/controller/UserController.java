package com.ydp.ez.user.controller;


import com.ydp.ez.user.common.annotations.Authorize;
import com.ydp.ez.user.common.annotations.IgnoreAuthorize;
import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.common.util.IpUtils;
import com.ydp.ez.user.common.util.WebContext;
import com.ydp.ez.user.common.vo.Result;
import com.ydp.ez.user.service.IUserBusinessService;
import com.ydp.ez.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 用户接口
 */
@Controller
@Slf4j
public class UserController extends BaseController {
    @Resource
    HttpServletRequest request;

    @Autowired
    private IUserBusinessService userBusinessService;
    @Autowired
    private IUserService userService;


    @IgnoreAuthorize
    @RequestMapping("/user/send-valid-code")
    @ResponseBody
    public Result register(@RequestParam String email) {
        Result result = null;
        try {
            userBusinessService.sendValidCode(email);
            result = success();
        } catch (UserException e) {
            log.error("/user/send-valid-code error email:{},error {}", email, e);
            result = error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("/user/send-valid-code error email:{},error {}", email, e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }


    @IgnoreAuthorize
    @RequestMapping("/user/register")
    @ResponseBody
    public Result register(@RequestParam String userName, @RequestParam String password, @RequestParam String email, @RequestParam String validCode) {
        Result result = null;
        try {
            String requestIp = IpUtils.getRemoteRealIP(request);
            result = success(userBusinessService.register(userName, password, email, validCode, requestIp));
        } catch (UserException e) {
            log.error("/register error user:{},password:{},email:{},error {}", userName, password, email, e);
            result = error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("/register error user:{},password:{},email:{},error {}", userName, password, email, e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }

    /**
     * 登录
     *
     * @param userName  用户名
     * @param password  密码
     * @param validCode 验证码
     * @return
     */
    @IgnoreAuthorize
    @RequestMapping("/user/login")
    @ResponseBody
    public Result login(String userName, String password, String validCode) {
        Result result = new Result();
        try {
            String requestIp = IpUtils.getRemoteRealIP(request);
            result = success(userBusinessService.login(userName, password, requestIp));
        } catch (Exception e) {
            log.error("/login system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }


    /**
     * 获取本人session
     *
     * @return
     */
    @RequestMapping("/user/get-session")
    @ResponseBody
    public Result getSession() {
        Result result = new Result();
        try {
            result = success(WebContext.getContext());
        } catch (Exception e) {
            log.error("/login system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }

    /**
     * 通过用户名查询用户信息
     *
     * @param userName
     * @return
     */
    @RequestMapping("/user/queryByUserName")
    @ResponseBody
    @Authorize(permissionBit = 8)
    public Result queryByUserName(String userName) {
        Result result = new Result();
        try {
            result = success(userService.queryByUserName(userName));
        } catch (Exception e) {
            log.error("/login system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }
}
