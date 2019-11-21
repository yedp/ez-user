package com.ydp.ez.user.mapper;

import com.ydp.ez.user.entity.User;

public interface UserMapper {
    int insertSelective(User record);
    User selectByUserName(String userName);
}