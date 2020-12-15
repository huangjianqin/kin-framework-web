package org.kin.framework.web.interceptor;

import org.kin.framework.web.domain.Permission;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 *
 * @author huangjianqin
 * @date 2019/7/26
 */
public abstract class PermissionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public final boolean preHandle(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull Object handler) throws Exception {
        //判断是否是方法处理
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }

        //获取权限限制
        boolean needLogin = true;
        boolean needAdmin = false;
        HandlerMethod method = (HandlerMethod) handler;
        Permission permission = method.getMethodAnnotation(Permission.class);
        if (permission != null) {
            needLogin = permission.login();
            needAdmin = permission.admin();
        }

        if (needLogin) {
            //检查权限
            return customCheckLogin(request, response, needAdmin);
        }

        return super.preHandle(request, response, handler);
    }

    public abstract boolean customCheckLogin(HttpServletRequest request, HttpServletResponse response, boolean needAdmin);
}
