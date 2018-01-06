package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CollectionListParam {
    @ApiModelProperty(value = "当前页",required = true)
    private Integer currrentPage;

    @ApiModelProperty(value="文章")
    /**文章 id**/
    private Long sourceId;

    @ApiModelProperty(value="用户 id")
    /****/
    private Long userId;

    @ApiModelProperty(value="远类型")
    /****/
    private Integer sourceType;


    public Integer getCurrrentPage() {
        return currrentPage;
    }

    public void setCurrrentPage(Integer currrentPage) {
        this.currrentPage = currrentPage;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
