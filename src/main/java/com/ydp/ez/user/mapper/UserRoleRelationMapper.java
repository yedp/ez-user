package com.ydp.ez.user.mapper;

import com.ydp.ez.user.entity.UserRoleRelation;

import java.util.List;

public interface UserRoleRelationMapper {
    int insert(Long userId, Integer roleId);

    List<UserRoleRelation> queryByUserId(Long userId);

    int logicalDelete(Long id);
}