package com.frame.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @author yefan
 * @date 2017-11-29 16:41:12
 **/
@ApiModel
public class LikeVO {

    /****/
    @ApiModelProperty(value = "sourceId")
    private Long sourceId;

    /****/
    @ApiModelProperty(value = " 用户 ID")
    private Long userId;

    /****/

    /****/
    @ApiModelProperty(value = "1,文章 2 钓点 3渔具店")
    private Integer sourceType;

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
