package com.ydp.ez.user.service;

import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.entity.RolePermission;

import java.util.List;

public interface IRolePermissionService {
    boolean addPermission(Integer roleId, String interfaceName, Integer permission, Long operatorId) throws UserException;

    List<RolePermission> queryByRoleId(Integer roleId) throws UserException;

    boolean updatePermissionById(Integer id, Integer permission, Long operatorId) throws UserException;

    boolean logicalDelete(Integer id, Long operatorId) throws UserException;

    List<RolePermission> queryByUserId(Long userId) throws UserException;
}
