package com.ydp.ez.user.service.impl;

import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.entity.RolePermission;
import com.ydp.ez.user.mapper.RolePermissionMapper;
import com.ydp.ez.user.service.IRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class RolePermissionService implements IRolePermissionService {
    @Resource
    RolePermissionMapper permissionMapper;

    @Override
    public boolean addPermission(Integer roleId, String interfaceName, Integer permission, Long operatorId) throws UserException {
        if (roleId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "角色编号");
        }
        if (StringUtils.isEmpty(interfaceName)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "接口名称");
        }
        if (permission == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "权限");
        }
        if (operatorId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "操作人员编号");
        }
        return permissionMapper.insert(roleId, interfaceName, permission, operatorId) > 0 ? true : false;
    }

    @Override
    public List<RolePermission> queryByRoleId(Integer roleId) throws UserException {
        if (roleId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "角色编号");
        }
        return permissionMapper.queryByRoleId(roleId);
    }

    @Override
    public boolean updatePermissionById(Integer id, Integer permission, Long operatorId) throws UserException {
        if (permission == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "编号");
        }
        if (permission == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "权限");
        }
        if (operatorId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "操作人员编号");
        }
        return permissionMapper.updatePermissionById(id, permission, operatorId) > 0 ? true : false;
    }

    @Override
    public boolean logicalDelete(Integer id, Long operatorId) throws UserException {
        if (id == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "编号");
        }
        if (operatorId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "操作人员编号");
        }
        return permissionMapper.logicalDelete(id, operatorId) > 0 ? true : false;
    }

    @Override
    public List<RolePermission> queryByUserId(Long userId) throws UserException {
        if (userId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "用户编号");
        }
        return permissionMapper.queryByUserId(userId);
    }
}
