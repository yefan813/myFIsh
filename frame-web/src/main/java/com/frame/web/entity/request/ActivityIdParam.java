package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ActivityIdParam {
    @ApiModelProperty(value = "文章 id")
    private Long articalId;

    public Long getArticalId() {
        return articalId;
    }

    public void setArticalId(Long articalId) {
        this.articalId = articalId;
    }
}
