package com.ydp.ez.user.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Role {
    public Role() {
    }

    public Role(String roleName, String roleDesc) {
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    private Integer id;

    private String roleName;

    private String roleDesc;

    private Date addTime;

    private Date modifyTime;

    private Integer isDelete;

}