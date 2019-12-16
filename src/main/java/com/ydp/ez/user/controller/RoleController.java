package com.ydp.ez.user.controller;


import com.ydp.ez.user.common.annotations.Authentication;
import com.ydp.ez.user.common.annotations.Log;
import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.common.util.WebContext;
import com.ydp.ez.user.common.vo.Result;
import com.ydp.ez.user.service.IRoleService;
import com.ydp.ez.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 角色接口
 */
@Controller
@Slf4j
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;


    @RequestMapping("/role/add")
    @ResponseBody
    @Log(prefix = "添加角色")
    public Result register(String roleName, String roleDesc) {
        Result result = null;
        try {
            result = success(roleService.addRole(roleName, roleDesc));
        } catch (UserException e) {
            log.warn("/role/add error ", e);
            result = error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("/role/add error ", e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }


    @RequestMapping("/role/query")
    @ResponseBody
    @Log(prefix = "角色查询")
    public Result login(Integer id, String roleName) {
        Result result = new Result();
        try {
            result = success(roleService.queryRoleList(id, roleName));
        } catch (Exception e) {
            log.error("/login system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }


    @RequestMapping("/role/update")
    @ResponseBody
    @Log(prefix = "角色更新")
    @Authentication
    public Result roleUpdate(Integer id, String roleName, String roleDesc) {
        Result result = new Result();
        try {
            result = success(roleService.updateRole(id, roleName, roleDesc));
        } catch (UserException e) {
            log.warn("/role/update error ", e);
            result = error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("/role/update system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }

    @RequestMapping("/role/delete")
    @ResponseBody
    @Log(prefix = "角色更新")
    @Authentication
    public Result roleUpdate(Integer id) {
        Result result = new Result();
        try {
            result = success(roleService.deleteRole(id));
        } catch (UserException e) {
            log.warn("/role/delete error ", e);
            result = error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("/role/delete system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }
}
