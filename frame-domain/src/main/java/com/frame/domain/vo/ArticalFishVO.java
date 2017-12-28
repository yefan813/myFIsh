package com.frame.domain.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yefan on 2017/7/23.
 */
@ApiModel
public class ArticalFishVO{

    private Long id;
    private Long userId;
    private String title;
    private  String waterType;
    @ApiModelProperty(hidden = true)
    private String bait;
    private  String fishType;
    @ApiModelProperty(hidden = true)
    private String fishingFunc;
    @ApiModelProperty(hidden = true)
    private  String fishLines;
    @ApiModelProperty(hidden = true)
    private String fishPoleLength;
    @ApiModelProperty(hidden = true)
    private String fishPoleBrand;
    @ApiModelProperty(hidden = true)
    private  String lat;
    @ApiModelProperty(hidden = true)
    private String lng;
    @ApiModelProperty(hidden = true)
    private  String locationAddress;
    @ApiModelProperty(value = "文章内容")
    private  Integer articleType;


    @ApiModelProperty(hidden = true)
    private Long liked;  //点赞数量
    @ApiModelProperty(hidden = true)
    private Long unliked;
    @ApiModelProperty(hidden = true)
    private Boolean isMineLiked; //是否已经点赞
    @ApiModelProperty(hidden = true)
    private String recommends;

    public String getRecommends() {
        return recommends;
    }

    public void setRecommends(String recommends) {
        this.recommends = recommends;
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

    public Long getLiked() {
        return liked;
    }

    public void setLiked(Long liked) {
        this.liked = liked;
    }

    public Long getUnliked() {
        return unliked;
    }

    public void setUnliked(Long unliked) {
        this.unliked = unliked;
    }

    public Boolean getMineLiked() {
        return isMineLiked;
    }

    public void setMineLiked(Boolean mineLiked) {
        isMineLiked = mineLiked;
    }
}
