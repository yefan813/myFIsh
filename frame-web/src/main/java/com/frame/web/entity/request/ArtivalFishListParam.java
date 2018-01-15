package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ArtivalFishListParam {
    @ApiModelProperty(value="当前页",required = true)
    private Integer currentPage;

    @ApiModelProperty(value="文章")
    /**文章 id**/
    private Long articalId;

    @ApiModelProperty(value="用户 id")
    /****/
    private Long userId;

    @ApiModelProperty(value="文章类型")
    private Integer articleType;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currrentPage) {
        this.currentPage = currrentPage;
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

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }
}
