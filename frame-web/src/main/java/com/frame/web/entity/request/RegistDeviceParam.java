package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;

@ApiModel
public class RegistDeviceParam {
    private Integer userId;
    private String deviceToken;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
