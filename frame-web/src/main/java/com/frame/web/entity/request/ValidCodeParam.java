package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;

@ApiModel
public class ValidCodeParam {
    private String tel;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
