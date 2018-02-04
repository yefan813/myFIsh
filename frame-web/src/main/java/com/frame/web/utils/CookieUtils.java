package com.frame.web.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
    public CookieUtils() {
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        if (!StringUtils.isBlank(cookieName) && request.getCookies() != null) {
            Cookie[] arr$ = request.getCookies();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Cookie cookie = arr$[i$];
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        return cookie == null ? null : cookie.getValue();
    }

    public static void removeCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, (String)null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        removeCookie(response, cookieName, (String)null);
    }

    public static void removeCookie(HttpServletResponse response, String cookieName, String domain) {
        Cookie cookie = new Cookie(cookieName, (String)null);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }

        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue) {
        setCookie(response, cookieName, cookieValue, "/");
    }

    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, String path) {
        setCookie(response, cookieName, cookieValue, path, "");
    }

    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, String domain) {
        if (!StringUtils.isBlank(cookieName) && cookieValue != null) {
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (StringUtils.isNotBlank(path)) {
                cookie.setPath(path);
            }

            if (StringUtils.isNotBlank(domain)) {
                cookie.setDomain(domain);
            }

            response.addCookie(cookie);
        }

    }

    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int expiry) {
        setCookie(response, cookieName, cookieValue, "/", expiry);
    }

    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, int expiry) {
        setCookie(response, cookieName, cookieValue, path, "", expiry);
    }

    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, String domain, int expiry) {
        if (!StringUtils.isBlank(cookieName) && cookieValue != null && expiry >= 0 && !StringUtils.isBlank(cookieName) && cookieValue != null) {
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (StringUtils.isNotBlank(path)) {
                cookie.setPath(path);
            }

            if (StringUtils.isNotBlank(domain)) {
                cookie.setDomain(domain);
            }

            cookie.setMaxAge(expiry);
            response.addCookie(cookie);
        }

    }
}