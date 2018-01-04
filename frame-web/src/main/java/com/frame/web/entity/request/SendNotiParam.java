package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;

@ApiModel
public class SendNotiParam {
    private String msg;
    private String deviceToken;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
