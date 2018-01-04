package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ArtivalFishListParam {
    @ApiModelProperty(value="当前页",required = true)
    private Integer currrentPage;

    @ApiModelProperty(value="文章")
    /**文章 id**/
    private Long articalId;

    @ApiModelProperty(value="用户 id")
    /****/
    private Long userId;

    public Integer getCurrrentPage() {
        return currrentPage;
    }

    public void setCurrrentPage(Integer currrentPage) {
        this.currrentPage = currrentPage;
    }

    public Long getArticalId() {
        return articalId;
    }

    public void setArticalId(Long articalId) {
        this.articalId = articalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
