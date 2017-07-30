package com.frame.domain;

import com.frame.domain.base.BaseDomain;

import java.util.Date;

/**
 * Created by yefan on 2017/7/23.
 */
public class ArticalFish extends BaseDomain {

    private Long userId;
    private String title;
    private Date time;
    private  Integer waterType;
    private Integer bait;
    private  Integer fishType;
    private Integer fishingFunc;
    private  Double fishLines;
    private Double fishPoleLength;
    private String fishPoleBrand;
    private  String lat;
    private String lng;
    private  String locationAddress;
    private  Integer articleType;
    private Integer isPublish;
    private String content;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getWaterType() {
        return waterType;
    }

    public void setWaterType(Integer waterType) {
        this.waterType = waterType;
    }

    public Integer getBait() {
        return bait;
    }

    public void setBait(Integer bait) {
        this.bait = bait;
    }

    public Integer getFishType() {
        return fishType;
    }

    public void setFishType(Integer fishType) {
        this.fishType = fishType;
    }

    public Integer getFishingFunc() {
        return fishingFunc;
    }

    public void setFishingFunc(Integer fishingFunc) {
        this.fishingFunc = fishingFunc;
    }

    public Double getFishLines() {
        return fishLines;
    }

    public void setFishLines(Double fishLines) {
        this.fishLines = fishLines;
    }

    public Double getFishPoleLength() {
        return fishPoleLength;
    }

    public void setFishPoleLength(Double fishPoleLength) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }
}
