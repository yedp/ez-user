package com.ydp.ez.user.common.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ydp.ez.user.common.annotations.Authorize;
import com.ydp.ez.user.common.annotations.IgnoreAuthorize;
import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.util.RedisUtil;
import com.ydp.ez.user.common.util.UserConstants;
import com.ydp.ez.user.common.util.WebContext;
import com.ydp.ez.user.common.vo.Result;
import com.ydp.ez.user.common.vo.SessionVO;
import com.ydp.ez.user.entity.Role;
import com.ydp.ez.user.entity.RolePermission;
import com.ydp.ez.user.service.IRolePermissionService;
import com.ydp.ez.user.service.IRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    IRoleService roleService;
    @Autowired
    IRolePermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        /**
         * 0：服务端访问用签名
         */
        String signature = httpServletRequest.getHeader("signature");
        if (StringUtils.isNotEmpty(signature)) {
            Enumeration<String> paramEnum = httpServletRequest.getParameterNames();
            List<String> strList = new ArrayList<>();
            while (paramEnum.hasMoreElements()) {
                String param = httpServletRequest.getParameter(paramEnum.nextElement());
                if (StringUtils.isNotEmpty(param)) {
                    strList.add(param);
                }
            }
            if (CollectionUtils.isEmpty(strList)) {
                strList.add("signature");
            }
            Collections.sort(strList);
            StringBuilder builder = new StringBuilder();
            for (String tmp : strList) {
                builder.append(tmp).append(",");
            }
            String str = builder.toString();
            str = str.substring(0, str.length() - 1);
            if (signature.equals(str)) {
                return true;
            }
        }

        /**
         * 1、是否忽略验证授权
         */
        if (method.isAnnotationPresent(IgnoreAuthorize.class)) {
            return true;
        }
        //2、是否有权限注解，没有则返回true
        Authorize authorize = AnnotationUtils.findAnnotation(method, Authorize.class);
        if (Objects.isNull(authorize)) {
            return true;
        }
        //3、token判断
        String token = httpServletRequest.getParameter(UserConstants.HTTP_PARAMETER_REQUEST_TOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            error(httpServletResponse, UserErrorCode.AUTH_FAIL, "请重新登录");
            return false;
        }
        //4、session是否存在
        SessionVO sessionVO = this.getSession(token);
        if (sessionVO == null) {
            error(httpServletResponse, UserErrorCode.AUTH_FAIL, "请重新登录");
            return false;
        }
        //5、token是否有效
        if (!token.equals(sessionVO.getToken())) {
            error(httpServletResponse, UserErrorCode.LOGIN_BY_OTHER, sessionVO.getRequsetIp());
            return false;
        }

        //6、权限判断
        if (authorize.permissionBit() < 16) {
            sessionVO.setRoleMap(roleService.queryRoleByUserId(sessionVO.getUserId()));
            sessionVO.setRolePermissionMap(permissionService.queryByUserId(sessionVO.getUserId()));
            //超级管理员拥有所有权限
            Map<String, Role> roleMap = sessionVO.getRoleMap();
            if (roleMap != null) {
                if (roleMap.get("super_admin") == null) {
                    String interfaceName = httpServletRequest.getRequestURI();
                    Map<String, RolePermission> permissionMap = sessionVO.getRolePermissionMap();
                    if (permissionMap == null || permissionMap == null && permissionMap.get(interfaceName) == null
                            || (permissionMap.get(interfaceName).getPermission().intValue() & authorize.permissionBit()) == 0) {
                        error(httpServletResponse, UserErrorCode.PERMISSION_FAIL);
                        return false;
                    }
                }
            }
        }
        WebContext.registry(sessionVO);
        return true;
    }

    /**
     * 通过token获取session
     *
     * @param token
     * @return
     */
    private SessionVO getSession(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        List<String> audienceList = decodedJWT.getAudience();
        String userName = audienceList.get(0);
        String key = String.format(UserConstants.SESSION_KEY, userName);
        SessionVO sessionVO = redisUtil.get(key, SessionVO.class);
        return sessionVO;
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
