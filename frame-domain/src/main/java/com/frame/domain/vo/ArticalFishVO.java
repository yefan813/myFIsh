package com.frame.domain.vo;

import com.frame.domain.base.BaseDomain;

/**
 * Created by yefan on 2017/7/23.
 */
public class ArticalFishVO{

    private Long userId;
    private String title;
    private  String waterType;
    private String bait;
    private  String fishType;
    private String fishingFunc;
    private  String fishLines;
    private String fishPoleLength;
    private String fishPoleBrand;
    private  String lat;
    private String lng;
    private  String locationAddress;
    private  Integer articleType;

    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getWaterType() {
        return waterType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }

    public String getBait() {
        return bait;
    }

    public void setBait(String bait) {
        this.bait = bait;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public String getFishingFunc() {
        return fishingFunc;
    }

    public void setFishingFunc(String fishingFunc) {
        this.fishingFunc = fishingFunc;
    }

    public String getFishLines() {
        return fishLines;
    }

    public void setFishLines(String fishLines) {
        this.fishLines = fishLines;
    }

    public String getFishPoleLength() {
        return fishPoleLength;
    }

    public void setFishPoleLength(String fishPoleLength) {
        this.fishPoleLength = fishPoleLength;
    }

    public String getFishPoleBrand() {
        return fishPoleBrand;
    }

    public void setFishPoleBrand(String fishPoleBrand) {
        this.fishPoleBrand = fishPoleBrand;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }
}
