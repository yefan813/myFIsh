package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestParam;

@ApiModel
public class UserThirdPartLoginParam {

    @ApiModelProperty(value = "avartar", required = false) String avartar;
    @ApiModelProperty(value = "birthday", required = false) Long birthday;
    @ApiModelProperty(value = "credential", required = false) String credential;
    @ApiModelProperty(value = "identifier", required = true) String identifier;
    @ApiModelProperty(value = "identityType", required = true) Integer identityType;
    @ApiModelProperty(value = "nickName", required = false) String nickName;
    @ApiModelProperty(value = "sex", required = false) Integer sex;


    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Integer identityType) {
        this.identityType = identityType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
