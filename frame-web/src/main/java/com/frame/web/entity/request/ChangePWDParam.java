package com.frame.web.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ChangePWDParam {

    @ApiModelProperty(value = "id", required = true)
    Integer id;
    @ApiModelProperty(value = "oldPwd", required = true)
    String oldPwd;
    @ApiModelProperty(value = "newPwd", required = true)
    String newPwd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
