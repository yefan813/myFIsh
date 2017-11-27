package com.frame.domain;

import com.frame.domain.base.BaseDomain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class CommentLike extends BaseDomain {

    private Long commentId;

    private Long userId;

    private Byte like;

    private Byte unliked;

    private String feature;

    private Date created;

    private Date modified;

    private Integer yn;

    private static final long serialVersionUID = 1L;


    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getLike() {
        return like;
    }

    public void setLike(Byte like) {
        this.like = like;
    }

    public Byte getUnliked() {
        return unliked;
    }

    public void setUnliked(Byte unliked) {
        this.unliked = unliked;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}