package com.ydp.ez.user.controller;


import com.ydp.ez.user.common.annotations.Log;
import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.vo.Result;
import com.ydp.ez.user.service.IUserRoleRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 角色接口
 */
@Controller
@Slf4j
public class UserRoleRelationController extends BaseController {

    @Autowired
    private IUserRoleRelationService userRoleRelationService;


    /**
     * 添加用户角色关系
     *
     * @param userId
     * @param roleId
     * @return
     */
    @RequestMapping("/relation/addUserRoleRelation")
    @ResponseBody
    @Log(prefix = "添加用户角色关系")
    public Result addUserRoleRelation(Long userId, Integer roleId) {
        Result result = new Result();
        try {
            result = success(userRoleRelationService.addUserRoleRelation(userId, roleId));
        } catch (Exception e) {
            log.error("/relation/addUserRoleRelation system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }

    /**
     * 添加用户角色关系
     *
     * @param id
     * @return
     */
    @RequestMapping("/relation/deleteUserRoleRelation")
    @ResponseBody
    @Log(prefix = "添加用户角色关系")
    public Result deleteUserRoleRelation(Long id) {
        Result result = new Result();
        try {
            result = success(userRoleRelationService.logicalDeleteUserRoleRelation(id));
        } catch (Exception e) {
            log.error("/relation/deleteUserRoleRelation system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }

    /**
     * 添加用户角色关系
     *
     * @param userId
     * @return
     */
    @RequestMapping("/relation/queryUserRoleRelation")
    @ResponseBody
    @Log(prefix = "查询用户角色关系")
    public Result queryUserRoleRelation(Long userId) {
        Result result = new Result();
        try {
            result = success(userRoleRelationService.queryUserRoleRelationByUserId(userId));
        } catch (Exception e) {
            log.error("/relation/queryUserRoleRelation system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }


}
