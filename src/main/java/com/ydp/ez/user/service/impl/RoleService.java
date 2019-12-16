package com.ydp.ez.user.service.impl;

import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.entity.Role;
import com.ydp.ez.user.entity.UserRoleRelation;
import com.ydp.ez.user.mapper.RoleMapper;
import com.ydp.ez.user.mapper.UserRoleRelationMapper;
import com.ydp.ez.user.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class RoleService implements IRoleService {
    @Resource
    RoleMapper roleMapper;
    @Resource
    UserRoleRelationMapper userRroleRelationMapper;

    @Override
    public Role addRole(String roleName, String roleDesc) throws UserException {
        if (StringUtils.isEmpty(roleName)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "角色名称");
        }
        Role role = new Role(roleName, roleDesc);
        return this.roleMapper.insertSelective(role) > 0 ? role : null;
    }

    @Override
    public boolean deleteRole(Integer id) throws UserException {
        if (id == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "角色编号");
        }
        return this.roleMapper.logicalDelete(id) > 0 ? true : false;
    }

    @Override
    public boolean updateRole(Integer id, String roleName, String roleDesc) throws UserException {
        if (StringUtils.isEmpty(roleName) || StringUtils.isEmpty(roleDesc)) {
            throw new UserException(UserErrorCode.PARAM_NULL, "角色名和角色描述");
        }
        if (id == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "角色编号");
        }
        return this.roleMapper.updateByPrimaryKeySelective(id, roleName, roleDesc) > 0 ? true : false;
    }

    @Override
    public List<Role> queryRoleList(Integer id, String roleName) {
        return this.roleMapper.queryRoleList(id, roleName);
    }

    @Override
    public boolean addUserRoleRelation(Long userId, Integer roleId) throws UserException {
        if (userId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "用户编号");
        }
        if (userId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "角色编号");
        }
        return userRroleRelationMapper.insert(userId, roleId) > 0 ? true : false;
    }

    @Override
    public boolean logicalDeleteUserRoleRelation(Long id) throws UserException {
        if (id == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "编号");
        }
        return userRroleRelationMapper.logicalDelete(id) > 0 ? true : false;
    }

    @Override
    public List<UserRoleRelation> queryUserRoleRelationByUserId(Long userId) {
        return userRroleRelationMapper.queryByUserId(userId);
    }

    @Override
    public List<Role> queryRoleByUserId(Long userId) throws UserException {
        if (userId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "用户编号");
        }
        return roleMapper.queryRoleByUserId(userId);
    }
}
