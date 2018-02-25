package com.frame.web.entity.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserFriendParam {


    @ApiModelProperty(value = "用户id")
    private Long toUserId; // 用户id



    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }


}
