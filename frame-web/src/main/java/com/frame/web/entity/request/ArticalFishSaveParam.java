package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class ArticalFishSaveParam {

    @ApiModelProperty(required = true,name = "标题")
    private String title;
    @ApiModelProperty(name = "时间")
    private Date time;
    @ApiModelProperty(name = "水源类型")
    private  String waterType;
    @ApiModelProperty(name = "时间")
    private String bait;
    @ApiModelProperty(name = "鱼类型")
    private  String fishType;
    @ApiModelProperty(name = "钓鱼方式")
    private String fishingFunc;
    @ApiModelProperty(name = "鱼线类型")
    private  String fishLines;
    private String fishPoleLength;
    private String fishPoleBrand;
    @ApiModelProperty(name = "精度")
    private  String lat;
    @ApiModelProperty(name = "维度")
    private String lng;
    @ApiModelProperty(name = "坐标地址详细")
    private  String locationAddress;
    @ApiModelProperty(required = true,name = "文章类型")
    private  Integer articleType;
    @ApiModelProperty(required = true,name = "文章内容")
    private String content;
    @ApiModelProperty(required = true,name = "图片 url")
    private String recommends;
    private Date fishTime;
    @ApiModelProperty(required = true,name = "城市")
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecommends() {
        return recommends;
    }

    public void setRecommends(String recommends) {
        this.recommends = recommends;
    }

    public Date getFishTime() {
        return fishTime;
    }

    public void setFishTime(Date fishTime) {
        this.fishTime = fishTime;
    }
}
