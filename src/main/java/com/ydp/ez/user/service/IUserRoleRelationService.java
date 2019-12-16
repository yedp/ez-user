package com.ydp.ez.user.service;

import com.ydp.ez.user.common.exception.UserException;
import com.ydp.ez.user.entity.UserRoleRelation;

import java.util.List;

public interface IUserRoleRelationService {
    /**
     * @param userId
     * @param roleId
     * @return
     */
    boolean addUserRoleRelation(Long userId, Integer roleId) throws UserException;


    /**
     * @param id
     * @return
     */
    boolean logicalDeleteUserRoleRelation(Long id) throws UserException;

    /**
     * 查询用户角色关系列表
     *
     * @param userId
     * @return
     */
    List<UserRoleRelation> queryUserRoleRelationByUserId(Long userId);

}
