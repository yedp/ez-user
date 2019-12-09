package com.ydp.ez.user.mapper;

import com.ydp.ez.user.entity.RequestLog;

public interface RequestLogMapper {
    int insertSelective(RequestLog record);
}