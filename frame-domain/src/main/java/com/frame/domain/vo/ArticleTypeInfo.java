package com.frame.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ArticleTypeInfo {
    @ApiModelProperty(name = "文章类型")
    private Integer articleType;

    @ApiModelProperty(name = "文章类型名称")
    private String articleTypeName;


    @ApiModelProperty(name = "文章数量")
    private Long count;

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public String getArticleTypeName() {
        return articleTypeName;
    }

    public void setArticleTypeName(String articleTypeName) {
        this.articleTypeName = articleTypeName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
