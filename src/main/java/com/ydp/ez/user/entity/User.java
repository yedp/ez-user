package com.ydp.ez.user.entity;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;
@Data
public class User {
    public User() {
    }

    public User(String username, String password, String salt, String email) {
        this.setUserName(username);
        this.setPassword(password);
        this.setSalt(salt);
        this.setEmail(email);
    }


    private Long id;

    private String userName;

    private String password;

    private String salt;

    private String nickName;

    private String realName;

    private String phone;

    private String email;

    private Date addTime;

    private Date lastLoginTime;

    private Date modifyTime;
    private Integer permitClientNum;
    private Integer isDelete;

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}