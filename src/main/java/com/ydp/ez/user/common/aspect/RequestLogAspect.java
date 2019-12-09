package com.ydp.ez.user.common.aspect;

import com.alibaba.fastjson.JSON;
import com.ydp.ez.user.common.annotations.RequestLoggerDefinition;
import com.ydp.ez.user.common.util.WebContext;

import com.ydp.ez.user.common.vo.SessionVO;
import com.ydp.ez.user.entity.RequestLog;
import com.ydp.ez.user.service.impl.LogService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 请求日志记录 AOP 组件
 */
@Aspect
@Component
public class RequestLogAspect {
    /**
     * 日志对象.
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String TRACE_NO = "trackId";
    @Autowired
    private LogService logService;

    /**
     * 定义切入点，切入点为com.example.aop下的所有函数
     */
    @Pointcut("execution(* com.ydp.ez.user.controller..*Controller.*(..))")
    public void controller(){}

    /**
     * 将要执行切面方法时.
     *
     * @param pjp join point 对象.
     * @return 返回对象.
     * @throws Throwable throw throwable.
     */
    @Around(value = "controller()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        final boolean isNeedLog = checkNeedLog(pjp);
        logger.debug("Is need log result is {}", isNeedLog);
        final long timeBefore = System.currentTimeMillis();
        Object result = pjp.proceed();
        if (isNeedLog) {
            RequestLog requestLog = this.logTheRequestInfo(pjp, timeBefore, result);
            logService.addLog(requestLog);
        }
        return result;

    }


    /**
     * 记录请求信息.
     *
     * @param pjp               切入点对象.
     * @param timeBefore        方法执行前花费时间.
     * @param result            结果.
     */
    private RequestLog logTheRequestInfo(ProceedingJoinPoint pjp, long timeBefore, Object result) {
        RequestLog requestLog = new RequestLog();
//        requestLog.setTrackId();
        requestLog.setRequestUrl(getRequestPath());
        fillCallArguments(pjp, requestLog);
        requestLog.setRequestTime(new Date(timeBefore));
        final long timeAfter = System.currentTimeMillis();
        requestLog.setReturnTime(new Date(timeAfter));
        final long timeSpend = timeAfter - timeBefore;
        requestLog.setRequestResult(JSON.toJSONString(result));
        requestLog.setTimeSpend(timeSpend);
        requestLog.setServerType("ez-user");
        SessionVO sessionVO = WebContext.getContext();
        if (sessionVO != null) {
            requestLog.setRequestUser(sessionVO.getUserName());
            requestLog.setRequestId(sessionVO.getTokenCache().asMap().values().iterator().next().toString());
        }
        return requestLog;
    }

    /**
     * 判断当前请求是否需要记录日志.
     *
     * @param pjp 切入点对象.
     * @return true/false.
     */
    private boolean checkNeedLog(ProceedingJoinPoint pjp) {
        final String requestPath = getRequestPath();
        logger.debug("The request path is {}", requestPath);
        if (StringUtils.isBlank(requestPath)) {
            logger.warn("The request path is empty when call the join point {} - so no need log.", pjp);
            return false;
        }
        RequestLoggerDefinition requestLoggerDefinition = getRequestLoggerDefinition(pjp);
        if (requestLoggerDefinition == null) {
            return true;
        }
        if (requestLoggerDefinition.ignore()) {
            logger.debug("The request log definition - is ignore is true, not log the request {}", requestPath);
            return false;
        }
        if (ArrayUtils.isNotEmpty(requestLoggerDefinition.ignorePaths())) {
            for (String ignorePath : requestLoggerDefinition.ignorePaths()) {
                if (StringUtils.lastIndexOf(requestPath, ignorePath) > -1) {
                    logger.debug("The ignorePath {} is match the request path {}", ignorePath, requestPath);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 得到请求日志定义注解对象.
     *
     * @param pjp 切入点.
     * @return 请求日志定义对象.
     */
    private RequestLoggerDefinition getRequestLoggerDefinition(ProceedingJoinPoint pjp) {
        RequestLoggerDefinition result    = null;
        final Signature         signature = pjp.getSignature();
        if (signature instanceof MethodSignature) {
            Method method = ((MethodSignature) signature).getMethod();
            result = AnnotationUtils.getAnnotation(method, RequestLoggerDefinition.class);
        }
        return result;
    }

    /**
     * 放入请求参数.
     *
     * @param pjp               连接点对象.
     * @param requestLog 请求日志对象.
     */
    private void fillCallArguments(ProceedingJoinPoint pjp, RequestLog requestLog) {
        final Object[] arguments = pjp.getArgs();
        if (ArrayUtils.isNotEmpty(arguments)) {
            List<Object> argLi     = Arrays.asList(arguments);
            List<Object> argNeedLi = new ArrayList<>();
            for (Object arg : argLi) {
                if (arg instanceof ServletRequest || arg instanceof ServletResponse) {
                    continue;
                }
                argNeedLi.add(arg);
            }
            requestLog.setRequestParams(JSON.toJSONString(argNeedLi));
        }
    }

    /**
     * 得到接口请求的路径.
     * 先根据 request 拿到路径， 如果没有 request 根据注解拿到路径.
     *
     * @return 接口请求路径.
     */
    private String getRequestPath() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            return request.getRequestURL().toString();
        }
        return StringUtils.EMPTY;
    }
}