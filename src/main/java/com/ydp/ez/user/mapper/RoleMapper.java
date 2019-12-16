package com.ydp.ez.user.mapper;

import com.ydp.ez.user.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int insertSelective(Role record);

    int logicalDelete(Integer id);

    List<Role> queryRoleList(@Param("id") Integer id, @Param("roleName") String roleName);

    int updateByPrimaryKeySelective(@Param("id") Integer id, @Param("roleName") String roleName, @Param("roleDesc") String roleDesc);
}