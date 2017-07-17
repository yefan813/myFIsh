package com.frame.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by garnett on 2015/11/18.
 */
@ApiModel(value = "UserAuthsParam")
public class UserAuthsParam implements Serializable{

	private static final long serialVersionUID = 6723005414893784943L;
	

	@ApiModelProperty(value = "登录类型1手机号2,email,3 qq,4 微信；5,用户名")
	private Integer identityType; // 登陆类型

	@ApiModelProperty(value = "第三放登录唯一标识,第三方登录需要传入此字段")
	private String identifier; // 第三放登录唯一标识

	@ApiModelProperty(value = "登录类型手机号登录时传入此字段")
	private String credential; // 密码

	@ApiModelProperty(value = "第三放登录时传入此字段")
	private String avartar;

	@ApiModelProperty(value = "第三放登录时传入此字段")
	private String nickName;

	@ApiModelProperty(value = "第三放登录时传入此字段")
	private Date birthday;

	@ApiModelProperty(value = "第三放登时传入此字段")
	private Integer sex;


	public Integer getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAvartar() {
		return avartar;
	}

	public void setAvartar(String avartar) {
		this.avartar = avartar;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
