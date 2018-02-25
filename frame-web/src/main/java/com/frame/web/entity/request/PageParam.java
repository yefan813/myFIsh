package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PageParam {
    @ApiModelProperty(value = "当前页",required = true)
    private Integer currrentPage;

    public Integer getCurrrentPage() {
        return currrentPage;
    }

    public void setCurrrentPage(Integer currrentPage) {
        this.currrentPage = currrentPage;
    }

}
