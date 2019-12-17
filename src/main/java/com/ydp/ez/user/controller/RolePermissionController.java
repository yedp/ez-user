package com.ydp.ez.user.controller;



import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.common.util.WebContext;
import com.ydp.ez.user.common.vo.Result;
import com.ydp.ez.user.service.IRolePermissionService;
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
public class RolePermissionController extends BaseController {

    @Autowired
    private IRolePermissionService modulePermissionService;


    /**
     * 添加权限
     *
     * @param roleId
     * @return
     */
    @RequestMapping("/permission/add")
    @ResponseBody
    public Result addUserRoleRelation(Integer roleId, String interfaceName, Integer permission) {
        Result result = new Result();
        try {
            result = success(modulePermissionService.addPermission(roleId, interfaceName, permission, WebContext.getContext().getUserId()));
        } catch (UserException e) {
            log.warn("/permission/add business error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        } catch (Exception e) {
            log.error("/permission/add system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @RequestMapping("/permission/delete")
    @ResponseBody
    public Result deleteUserRoleRelation(Integer id) {
        Result result = new Result();
        try {
            result = success(modulePermissionService.logicalDelete(id, WebContext.getContext().getUserId()));
        } catch (Exception e) {
            log.error("/permission/add system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }

    /**
     * 更新权限
     *
     * @return
     */
    @RequestMapping("/permission/update")
    @ResponseBody
    public Result addUserRoleRelation(Integer id, Integer permission) {
        Result result = new Result();
        try {
            result = success(modulePermissionService.updatePermissionById(id, permission, WebContext.getContext().getUserId()));
        } catch (Exception e) {
            log.error("/permission/update system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }

    /**
     * 查询权限
     *
     * @return
     */
    @RequestMapping("/permission/query")
    @ResponseBody
    public Result queryPermission(Integer roleId) {
        Result result = new Result();
        try {
            result = success(modulePermissionService.queryByRoleId(roleId));
        } catch (Exception e) {
            log.error("/permission/query system error {}-{}", e.getMessage(), e);
            result = error(UserErrorCode.SYSTEM_ERROR_WITH_MSG, e.getMessage());
        }
        return result;
    }


}
