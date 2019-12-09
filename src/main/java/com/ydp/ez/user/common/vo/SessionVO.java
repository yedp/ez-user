package com.ydp.ez.user.common.vo;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class SessionVO implements Serializable {
    private static final long serialVersionUID = 222159356576070759L;
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
     *允许客服端连接最大数量
     */
    private Integer permitClientNum;

    /**
     * token存储：token：ip
     */
    private Cache<String,Token> tokenCache;

    public void setPermitClientNum(Integer permitClientNum){
        if(permitClientNum == null){
            permitClientNum = 1;
        }
        this.permitClientNum = permitClientNum;
        tokenCache = CacheBuilder.newBuilder().maximumSize(permitClientNum).recordStats().build();
    }

    @Data
    public static class Token{
        String token;
        String ip;

        @Override
        public String toString(){
            return JSON.toJSONString(this);
        }

    }



}