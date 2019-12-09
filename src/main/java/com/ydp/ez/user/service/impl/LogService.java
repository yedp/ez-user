package com.ydp.ez.user.service.impl;


import com.ydp.ez.user.entity.RequestLog;
import com.ydp.ez.user.mapper.RequestLogMapper;
import com.ydp.ez.user.service.ILogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogService implements ILogService {
    /**
     * 请求日志表名前缀.
     */
//    private static final String TABLE_NAME = "request_log_%s%s";
    /**
     * 时间格式化 -- 年月.
     */
//    private static  final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMM");
    @Resource
    private RequestLogMapper requestLogMapper;

    /**
     * 异步添加日志
     *
     * @param requestLog
     */
    @Async
    public void addLog(RequestLog requestLog) {
//        String tableName = String.format(TABLE_NAME, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)+1);
        requestLog.setTableName("request_log");
        requestLogMapper.insertSelective(requestLog);
    }
}