package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SitCollectionListParam {
    @ApiModelProperty(value = "当前页",required = true)
    private Integer currrentPage;
    /****/
    @ApiModelProperty(value = "站点，钓点 id")
    private Long shopSiteId;

    /****/
    @ApiModelProperty(value = "user id")
    private Long userId;

    /****/
    @ApiModelProperty(value = "1钓点， 2渔具店")
    private Integer type;

    public Integer getCurrrentPage() {
        return currrentPage;
    }

    public void setCurrrentPage(Integer currrentPage) {
        this.currrentPage = currrentPage;
    }

    public Long getShopSiteId() {
        return shopSiteId;
    }

    public void setShopSiteId(Long shopSiteId) {
        this.shopSiteId = shopSiteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
