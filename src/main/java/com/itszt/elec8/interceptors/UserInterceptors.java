package com.itszt.elec8.interceptors;

import com.itszt.elec8.ConstantsKeys;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by zyy on 2019/8/14.
 */
public class UserInterceptors implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute(ConstantsKeys.SESSION__USER_NOW);
        Object roles = session.getAttribute(ConstantsKeys.SESSION_ROLES_NOW);
        Object popes = session.getAttribute(ConstantsKeys.SESSION_POPES_NOW);

        boolean b = user != null && roles != null && popes != null && ((List) roles).size() > 0 && ((TreeSet) popes).size() > 0;
        if (!b) {
            response.sendRedirect("/index.jsp");
        }

        return b;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
