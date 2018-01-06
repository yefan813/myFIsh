package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CollectionListParam {
    @ApiModelProperty(value = "当前页",required = true)
    private Integer currentPage;


    @ApiModelProperty(value="用户 id")
    /****/
    private Long userId;

    @ApiModelProperty(value="远类型")
    /****/
    private Integer sourceType;


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
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
