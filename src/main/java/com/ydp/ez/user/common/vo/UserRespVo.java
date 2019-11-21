package com.ydp.ez.user.common.vo;

import lombok.Data;

/**
 * @author yedp
 * @date 2019/11/10 11:51
 * description : 用户登录信息
 */
@Data
public class UserRespVo {
    public UserRespVo(String token, String userName, String nickName) {
        this.token = token;
        this.userName = userName;
        this.nickName = nickName;
    }

    /**
     * 登录验证token
     */
    private String token;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

}
