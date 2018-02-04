package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;

@ApiModel
public class RegistDeviceParam {
    private String deviceToken;


    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
