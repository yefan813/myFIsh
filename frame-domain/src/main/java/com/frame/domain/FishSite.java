package com.frame.domain;

import com.frame.domain.base.BaseDomain;

/**
 * Created by yefan on 2017/7/23.
 */
public class FishSite extends BaseDomain {
    private Long userId;
    private String title;
    private  String introduce;
    private Integer isPublish;
    private String content;
    private String pic0;
    private String pic1;
    private String pic2;
    private String pic3;
    private String lng;
    private String lat;
    private String address;
    private Integer publishType;
    private String siteType;
    private String siteFeeType;
    private String siteFishType;
    private String sitePhone;
    private Boolean canPark;
    private Boolean canNight;
    private Boolean canEat;
    private Boolean canHotel;

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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic0() {
        return pic0;
    }

    public void setPic0(String pic0) {
        this.pic0 = pic0;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPublishType() {
        return publishType;
    }

    public void setPublishType(Integer publishType) {
        this.publishType = publishType;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getSiteFeeType() {
        return siteFeeType;
    }

    public void setSiteFeeType(String siteFeeType) {
        this.siteFeeType = siteFeeType;
    }

    public String getSiteFishType() {
        return siteFishType;
    }

    public void setSiteFishType(String siteFishType) {
        this.siteFishType = siteFishType;
    }

    public String getSitePhone() {
        return sitePhone;
    }

    public void setSitePhone(String sitePhone) {
        this.sitePhone = sitePhone;
    }

    public Boolean getCanPark() {
        return canPark;
    }

    public void setCanPark(Boolean canPark) {
        this.canPark = canPark;
    }

    public Boolean getCanNight() {
        return canNight;
    }

    public void setCanNight(Boolean canNight) {
        this.canNight = canNight;
    }

    public Boolean getCanEat() {
        return canEat;
    }

    public void setCanEat(Boolean canEat) {
        this.canEat = canEat;
    }

    public Boolean getCanHotel() {
        return canHotel;
    }

    public void setCanHotel(Boolean canHotel) {
        this.canHotel = canHotel;
    }
}
