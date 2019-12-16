package com.ydp.ez.user.service.impl;

import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.entity.Role;
import com.ydp.ez.user.mapper.RoleMapper;
import com.ydp.ez.user.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class RoleService implements IRoleService {
    @Resource
    RoleMapper roleMapper;

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
}
