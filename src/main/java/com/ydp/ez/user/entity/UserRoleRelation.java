package com.ydp.ez.user.entity;

import lombok.Data;

import java.util.Date;
@Data
public class UserRoleRelation {
    private Long id;

    private Long userId;

    private Integer roleId;

    private Date addTime;

    private Date modifyTime;

    private Integer isDelete;

}