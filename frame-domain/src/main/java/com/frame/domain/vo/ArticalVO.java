package com.frame.domain.vo;

import com.frame.domain.base.BaseDomain;

/**
 * Created by yefan on 2017/7/23.
 */
public class ArticalVO extends BaseDomain {

    private Long userId;
    private String title;

    private  Integer articleType;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

}
