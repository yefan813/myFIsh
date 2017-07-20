package com.frame.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by garnett on 2015/11/18.
 */
@ApiModel(value = "UserVO")
public class UserVO implements Serializable{

	private static final long serialVersionUID = 6723005414893784943L;

	@ApiModelProperty(value = "用户名id")
	private Integer id;

	@ApiModelProperty(hidden = true)
	private String name; // 姓名

	@ApiModelProperty(value = "性别")
	private Integer sex; //性别1 男 2 女

	@ApiModelProperty(hidden = true)
	private Integer role; //角色

	@ApiModelProperty(value = "地址")
	private String address; //地址

	@ApiModelProperty(value = "生日")
	private Long birthday;

	@ApiModelProperty(hidden = true)
	private String point; // 积分

	@ApiModelProperty(hidden = true)
	private String email; // email

	@ApiModelProperty(hidden = true)
	private String tel; // 用户电话

	@ApiModelProperty(hidden = true)
	private Integer level; // 用户等级

	@ApiModelProperty(value = "用户昵称")
	private String nickName; // 用户昵称

	@ApiModelProperty(value = "用户头像URL")
	private String avatarUrl; // 用户头像URL

	@ApiModelProperty(hidden = true)
	private Long fans;  //粉丝

	@ApiModelProperty(hidden = true)
	private Long focuses; //关注数
	



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

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getFocuses() {
		return focuses;
	}

	public void setFocuses(Long focuses) {
		this.focuses = focuses;
	}

	public Long getFans() {
		return fans;
	}

	public void setFans(Long fans) {
		this.fans = fans;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
