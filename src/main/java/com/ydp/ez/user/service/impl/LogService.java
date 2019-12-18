package com.ydp.ez.user.service.impl;


import com.ydp.ez.user.entity.RequestLog;
import com.ydp.ez.user.mapper.RequestLogMapper;
import com.ydp.ez.user.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
public class LogService implements ILogService {
    /**
     * 请求日志表名前缀.
     */
    private static final String TABLE_NAME = "request_log_%s";
    /**
     * 时间格式化 -- 年月.
     */
    @Resource
    private RequestLogMapper requestLogMapper;

    /**
     * 异步添加日志
     *
     * @param requestLog
     */
    @Async
    public void addLog(RequestLog requestLog) {
        try {
            String tableName = String.format(TABLE_NAME, Calendar.getInstance().get(Calendar.YEAR));
            requestLog.setTableName(tableName);
            requestLogMapper.insertSelective(requestLog);
        }catch (Exception e){
            log.error("{}",e);
        }finally {
            log.info("{}",requestLog);
        }
    }
}