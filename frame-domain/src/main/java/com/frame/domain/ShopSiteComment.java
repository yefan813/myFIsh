package com.frame.domain;

import com.frame.domain.base.BaseDomain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ShopSiteComment extends BaseDomain {

    private Byte type;

    private Long localId;

    private Long userId;

    private String content;

    private Byte isReview;

    private String feature;

    private Date created;

    private Date modified;

    private Integer yn;

    private static final long serialVersionUID = 1L;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getIsReview() {
        return isReview;
    }

    public void setIsReview(Byte isReview) {
        this.isReview = isReview;
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