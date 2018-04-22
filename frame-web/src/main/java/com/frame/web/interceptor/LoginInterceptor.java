package com.frame.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.tools.DateUtils;
import com.frame.domain.common.AesKey;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.enums.CookieEnum;
import com.frame.service.utils.AESUtils;
import com.frame.web.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
    private AesKey aesKey;

    @Value("${DEV_MODE}")
    private String DEV_MODE;

    public static final List<String> whiteList = new ArrayList<>(Arrays.asList("/articalFish/articalFishList", "/articalFish/articalFishDetail",
            "/fishShop/fishShopList", "/fishShop/fishShopDetail",
            "/fishSite/fishSiteList", "/fishSite/fishSiteDetail"));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        LOGGER.debug("look up:" + url);
//        if(StringUtils.isNotBlank(DEV_MODE) && "debug".equalsIgnoreCase(DEV_MODE)){
//            return true;
//        }

        String loginValue = CookieUtils.getCookieValue(request, CookieEnum.TICKET.getKey());
        if (StringUtils.isBlank(loginValue) && !whiteList.contains(uri)) {
            LOGGER.error("用户未登录");
            JSONObject result = new JSONObject();
            result.put("code", BusinessCode.NO_LOGIN.getCode());
            result.put("msg", BusinessCode.NO_LOGIN.getValue());
            this.write(result, response);
            return false;
        }

        if(StringUtils.isBlank(loginValue) &&  whiteList.contains(uri)){
            return true;
        }

        String cookieValue = "";
        try {
            cookieValue = AESUtils.decrypt(loginValue, this.aesKey.getKey());
        } catch (RuntimeException var17) {
            LOGGER.error("cookie 解密失败", var17);
            JSONObject result = new JSONObject();
            result.put("code", BusinessCode.CRY_FAILE.getCode());
            result.put("msg", BusinessCode.CRY_FAILE.getValue());
            this.write(result, response);
            return false;
        }

        String[] cookieValues = cookieValue.split("##");
        long loginTime = NumberUtils.toLong(cookieValues[3]);
        long now = DateUtils.getServerTime();
        long ms = 259200000L;
        if (now - loginTime > ms  && !whiteList.contains(uri)) {
            LOGGER.error("用户cookie已过期");
            JSONObject result = new JSONObject();
            result.put("code", BusinessCode.COOKIE_INVAID.getCode());
            result.put("msg", BusinessCode.COOKIE_INVAID.getValue());
            this.write(result, response);
            return false;
        } else {
            LoginContext lc = new LoginContext();
            lc.setLongId(NumberUtils.toLong(cookieValues[0]));
            lc.setAccount(cookieValues[1]);
            lc.setUserName(URLDecoder.decode(cookieValues[2], "UTF-8"));
            lc.setLoginTime(new Date(loginTime));
            LoginContext.setLoginContext(lc);
            request.setAttribute("login_context", lc);
            return true;
        }
    }

    private void write(JSONObject result, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.println(JSONObject.toJSONString(result));
        out.flush();
        out.close();
    }

    public AesKey getAesKey() {
        return aesKey;
    }

    public void setAesKey(AesKey aesKey) {
        this.aesKey = aesKey;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        LoginContext.remove();
    }
}
