package com.ydp.ez.user.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Module {
    private Integer id;

    private Integer parentId;

    private String moduleName;

    private String moduleDesc;

    private String subSystem;

    private String interfaceName;

    private Date addTime;

    private Date modifyTime;

    private Integer isDelete;
}