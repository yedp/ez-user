package com.ydp.ez.user.common.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ydp.ez.user.common.annotations.Authentication;
import com.ydp.ez.user.common.annotations.Authorization;
import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.util.RedisUtil;
import com.ydp.ez.user.common.util.UserConstants;
import com.ydp.ez.user.common.util.WebContext;
import com.ydp.ez.user.common.vo.Result;
import com.ydp.ez.user.common.vo.SessionVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(Authentication.class) || method.isAnnotationPresent(Authorization.class)) {
            Map<String, String> paramMap = new HashMap<>();
            String token = httpServletRequest.getParameter(UserConstants.HTTP_PARAMETER_REQUEST_TOKEN_KEY);
            if (StringUtils.isEmpty(token)) {
                error(httpServletResponse, UserErrorCode.AUTH_FAIL, "非法访问");
                return false;
            }
            DecodedJWT decodedJWT = JWT.decode(token);
            List<String> audienceList = decodedJWT.getAudience();
            String userName = audienceList.get(0);
            String key = String.format(UserConstants.SESSION_KEY, userName);
            SessionVO sessionVO = redisUtil.get(key, SessionVO.class);
            if (sessionVO != null && token.equals(sessionVO.getToken())) {
                WebContext.registry(sessionVO);
                return true;
            }
            error(httpServletResponse, UserErrorCode.AUTH_FAIL, "登录失效，请重新登录");
            return false;
        }
        return true;
    }


    private void error(HttpServletResponse httpServletResponse, String errorCode, String errorMsg) throws IOException {
        Result result = new Result();
        result.setStatus(Result.Status.ERROR.code());
        result.setCode(errorCode);
        result.setMessage(errorMsg);
        httpServletResponse.getWriter().write(JSONObject.toJSONString(result));
    }

    private void error(HttpServletResponse httpServletResponse, UserErrorCode errorCode) throws IOException {
        error(httpServletResponse, errorCode.getCode(), errorCode.getMessage());
    }

    private void error(HttpServletResponse httpServletResponse, UserErrorCode errorCode, String errorMsg) throws IOException {
        String msg = errorCode.getMessage();
        errorMsg = String.format(msg, errorMsg);
        error(httpServletResponse, errorCode.getCode(), errorMsg);
    }

}
