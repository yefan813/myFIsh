package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
public class UserLoginDetailParam {


    private String address;
    private Double latitude;
    private Double longitude;
    private Date loginTime; // 用户登录ip

    private String location; // 用户定位坐标

    private Double myDistance;

    private String deviceToken;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getMyDistance() {
        return myDistance;
    }

    public void setMyDistance(Double myDistance) {
        this.myDistance = myDistance;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
