package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;

@ApiModel
public class CollecttionId {
    private Long collectionId;

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }
}
