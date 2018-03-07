package com.frame.web.entity.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class FishSiteListParam {
    @ApiModelProperty(value = "当前页",required = true)
    private Integer currentPage;
    private String title;
    private String address;
    private String siteType;
    private String siteFeeType;
    private String siteFishType;
    private String sitePhone;
    private Boolean canPark;
    private Boolean canNight;
    private Boolean canEat;
    private Boolean canHotel;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
