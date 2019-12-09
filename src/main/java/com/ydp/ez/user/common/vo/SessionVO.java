package com.ydp.ez.user.common.vo;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ydp.ez.user.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class SessionVO implements Serializable {
    private static final long serialVersionUID = 222159356576070759L;

    public SessionVO(){}
    public SessionVO(User user){
        this.setUserId(user.getId());
        this.setUserName(user.getUserName());
        this.setEmail(user.getEmail());
        this.setPhone(user.getPhone());
        this.setPermitClientNum(user.getPermitClientNum());
        this.setNickName(user.getNickName());
        this.setRealName(user.getRealName());
    }

    private long userId;
    /**
     * 用户名
     */
    private String userName;


    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 允许客服端连接最大数量
     */
    private Integer permitClientNum;

    /**
     * token存储：token：ip
     */
    private String token;

    private String ip;



}