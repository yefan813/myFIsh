package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;

@ApiModel
public class SiteIdParam {
    private Long siteId;

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
}
