package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestParam;

@ApiModel
public class UserInfoParam {
    @ApiModelProperty(value = "id", required = true) Integer id;
    @ApiModelProperty(value = "nikeName", required = false) String nikeName;
    @ApiModelProperty(value = "sex", required = false) Integer sex;
    @ApiModelProperty(value = "avatarUrl", required = false) String avatarUrl;
    @ApiModelProperty(value = "address", required = false) String address;
    @ApiModelProperty(value = "birthday", required = false) Long birthday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }
}
