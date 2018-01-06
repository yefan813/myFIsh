package com.frame.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by garnett on 2015/11/18.
 */
@ApiModel
public class UserBaseVO implements Serializable {

    private static final long serialVersionUID = 6723005414893784943L;

    @ApiModelProperty(value = "用户名id")
    private Integer id;

    @ApiModelProperty(hidden = true)
    private String name; // 姓名

    @ApiModelProperty(value = "性别")
    private Integer sex; //性别1 男 2 女


    @ApiModelProperty(value = "用户昵称")
    private String nickName; // 用户昵称

    @ApiModelProperty(value = "用户头像URL")
    private String avatarUrl; // 用户头像URL

    @ApiModelProperty(value = "用户address")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
