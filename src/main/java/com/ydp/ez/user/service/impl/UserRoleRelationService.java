package com.ydp.ez.user.service.impl;

import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.entity.UserRoleRelation;
import com.ydp.ez.user.mapper.UserRoleRelationMapper;
import com.ydp.ez.user.service.IUserRoleRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserRoleRelationService implements IUserRoleRelationService {
    @Resource
    UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public boolean addUserRoleRelation(Long userId, Integer roleId) throws UserException {
        if (userId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "用户编号");
        }
        if (userId == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "角色编号");
        }
        return userRoleRelationMapper.insert(userId, roleId) > 0 ? true : false;
    }

    @Override
    public boolean logicalDeleteUserRoleRelation(Long id) throws UserException {
        if (id == null) {
            throw new UserException(UserErrorCode.PARAM_NULL, "编号");
        }
        return userRoleRelationMapper.logicalDelete(id) > 0 ? true : false;
    }

    @Override
    public List<UserRoleRelation> queryUserRoleRelationByUserId(Long userId) {
        return userRoleRelationMapper.queryByUserId(userId);
    }
}
