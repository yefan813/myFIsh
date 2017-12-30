package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserLoginParam {

    @ApiModelProperty(value = "tel", required = true)
    String tel;
    @ApiModelProperty(value = "password", required = true)
    String password;


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
