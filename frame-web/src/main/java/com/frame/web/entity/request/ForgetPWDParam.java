package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ForgetPWDParam {
    @ApiModelProperty(value = "tel", required = true)
    String tel;
    @ApiModelProperty(value = "password", required = true)
    String password;
    @ApiModelProperty(value = "validCode", required = true)
    String validCode;

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

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }
}
