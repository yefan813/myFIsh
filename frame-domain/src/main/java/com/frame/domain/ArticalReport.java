package com.frame.domain;

import com.frame.domain.base.BaseDomain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ArticalReport extends BaseDomain {

    private Long articalId;

    private Long userId;

    private Byte reportType;

    private String feature;

    private Date modified;

    private Date created;

    private Integer yn;

    private static final long serialVersionUID = 1L;


    public Long getArticalId() {
        return articalId;
    }

    public void setArticalId(Long articalId) {
        this.articalId = articalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getReportType() {
        return reportType;
    }

    public void setReportType(Byte reportType) {
        this.reportType = reportType;
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