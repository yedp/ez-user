package com.ydp.ez.user.service;

import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.entity.Module;
import com.ydp.ez.user.entity.Role;
import com.ydp.ez.user.entity.UserRoleRelation;

import java.util.List;

/**
 * @Author: yedp
 * @Date: 2019/12/16 13:48:00
 * @Description：角色权限相关服务
 */
public interface IRoleService {
    /**
     * 添加角色
     *
     * @param roleName
     * @param roleDesc
     * @return
     */
    Role addRole(String roleName, String roleDesc) throws UserException;

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    boolean deleteRole(Integer id) throws UserException;

    /**
     * 修改角色
     *
     * @param id
     * @param roleName
     * @param roleDesc
     * @return
     */
    boolean updateRole(Integer id, String roleName, String roleDesc) throws UserException;

    /**
     * 查询角色
     *
     * @param id
     * @param roleName
     * @return
     */
    List<Role> queryRoleList(Integer id, String roleName);

    /**
     * 通过用户编号查询角色列表
     *
     * @param userId
     * @return
     */
    List<Role> queryRoleByUserId(Long userId) throws UserException;
    
}
