package com.frame.web.interceptor;

import java.util.Date;

public class LoginContext {
    private static final ThreadLocal<LoginContext> holder = new ThreadLocal();
    private Long longId;
    private String account;
    private String userName;
    private Date loginTime;

    public LoginContext() {
    }

    public static void setLoginContext(LoginContext erpLoginContext) {
        holder.set(erpLoginContext);
    }

    public static LoginContext getLoginContext() {
        return (LoginContext)holder.get();
    }

    public static void remove() {
        holder.remove();
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getLongId() {
        return this.longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
