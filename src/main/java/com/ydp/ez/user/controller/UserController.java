package com.ydp.ez.user.controller;


import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.vo.Result;
import com.ydp.ez.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户接口
 */
@Controller
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 登录
     *
     * @param userName  用户名
     * @param password  密码
     * @param validCode 验证码
     * @return
     */
    @RequestMapping("/user/loginx")
    @ResponseBody
    public Result login(String userName, String password, String validCode) {
        Result result = new Result();
        try {
            result = success(userService.login(userName,password));
        } catch (Exception e) {
            log.error("/login system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        } finally {
            log.info("/login userId:{} password:{} result:{}", userName, password, result);
        }
        return result;
    }

    /**
     * 通过用户名查询用户信息
     * @param userName
     * @return
     */
    @RequestMapping("/user/queryByUserName")
    @ResponseBody
    public Result queryByUserName(String userName) {
        Result result = new Result();
        try {
            result = success(userService.queryByUserName(userName));
        } catch (Exception e) {
            log.error("/login system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        } finally {
            log.info("/login userId:{} result:{}", userName, result);
        }
        return result;
    }
}
