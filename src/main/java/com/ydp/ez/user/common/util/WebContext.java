package com.ydp.ez.user.common.util;

import com.ydp.ez.user.common.vo.SessionVO;

/**
 * web的session存放
 *
 * @author yedp
 */
public class WebContext {
    private static final ThreadLocal<SessionVO> WEB_CONTEXT_LOCAL = new ThreadLocal();

    private WebContext() {
    }

    public static SessionVO getContext() {
        SessionVO sessionVO = (SessionVO) WEB_CONTEXT_LOCAL.get();
        return sessionVO;
    }

    public static void registry(SessionVO SessionVO) {
        WEB_CONTEXT_LOCAL.set(SessionVO);
    }

    public static void release() {
        WEB_CONTEXT_LOCAL.remove();
    }
}
