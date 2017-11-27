package com.frame.domain;

import com.frame.domain.base.BaseDomain;
import io.swagger.models.auth.In;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SiteCollection extends BaseDomain {

    private Long shopSiteId;

    private Long userId;

    private Byte type;

    private String feature;

    private Date modified;

    private Date created;

    private Integer yn;

    private static final long serialVersionUID = 1L;

    public Long getShopSiteId() {
        return shopSiteId;
    }

    public void setShopSiteId(Long shopSiteId) {
        this.shopSiteId = shopSiteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}