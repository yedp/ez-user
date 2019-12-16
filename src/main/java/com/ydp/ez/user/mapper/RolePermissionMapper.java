package com.ydp.ez.user.mapper;

import com.ydp.ez.user.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper {
    int insert(@Param("roleId") Integer roleId, @Param("interfaceName") String interfaceName, @Param("permission") Integer permission, @Param("userId") Long userId);

    List<RolePermission> queryByRoleId(@Param("roleId") Integer roleId);

    int updatePermissionById(@Param("id") Integer id, @Param("permission") Integer permission, @Param("operatorId") Long operatorId);

    int logicalDelete(@Param("id") Integer id, @Param("operatorId") Long operatorId);
}