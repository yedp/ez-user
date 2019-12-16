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
     * 添加用户
     *
     * @param userName
     * @param password
     * @param salt
     * @param email
     * @throws UserException
     */
    boolean addUser(String userName, String password, String salt, String email) throws UserException;


    /**
     * 获取信息
     *
     * @param userName
     * @return
     */
    User queryByUserName(String userName);
}
