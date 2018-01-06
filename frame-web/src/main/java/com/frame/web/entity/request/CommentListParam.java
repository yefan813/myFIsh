package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CommentListParam {
    @ApiModelProperty(value = "当前页",required = true)
    private Integer currrentPage;
    private Long topicId;
    private Integer type;

    public Integer getCurrrentPage() {
        return currrentPage;
    }

    public void setCurrrentPage(Integer currrentPage) {
        this.currrentPage = currrentPage;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
