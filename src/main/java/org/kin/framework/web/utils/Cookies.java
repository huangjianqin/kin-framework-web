package org.kin.framework.web.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * cookies工具类
 *
 * @author huangjianqin
 * @date 2020-03-07
 */
public class Cookies {
    /**
     * 设置cookies缓存
     * @param response 返回
     * @param key cookie key
     * @param value cookie缓存值
     * @param domain 域
     * @param path cookie path
     * @param maxAge cookie最大存活时间
     * @param isHttpOnly 是否仅仅http生效
     */
    public static void set(HttpServletResponse response, String key, String value, String domain, String path, int maxAge, boolean isHttpOnly) {
        Cookie cookie = new Cookie(key, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie缓存数据
     * @param request 请求
     * @param key cookie key
     */
    public static String getValue(HttpServletRequest request, String key) {
        Cookie cookie = get(request, key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 获取cookie缓存
     * @param request 请求
     * @param key cookie key
     */
    private static Cookie get(HttpServletRequest request, String key) {
        Cookie[] arr_cookie = request.getCookies();
        if (arr_cookie != null && arr_cookie.length > 0) {
            for (Cookie cookie : arr_cookie) {
                if (cookie.getName().equals(key)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 移除cookies缓存
     * @param request 请求
     * @param response  返回
     * @param key   cookies key
     * @param path  cookies path
     */
    public static void remove(HttpServletRequest request, HttpServletResponse response, String key, String path) {
        Cookie cookie = get(request, key);
        if (cookie != null) {
            set(response, key, "", null, path, 0, true);
        }
    }

}