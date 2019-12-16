package com.ydp.ez.user.entity;

import lombok.Data;

import java.util.Date;
@Data
public class RolePermission {
    private Integer id;

    private Integer roleId;

    private String interfaceName;

    private Integer permission;

    private Long operatorId;

    private Date addTime;

    private Date modifyTime;

    private Integer isDelete;

}