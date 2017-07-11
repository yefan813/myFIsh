package com.frame.domain;

import com.frame.domain.base.BaseDomain;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by garnett on 2015/11/18.
 */
public class User extends BaseDomain {

	private static final long serialVersionUID = 6723005414893784943L;

	private String name; // 姓名

	private Integer sex; //性别
	

	private Integer role; //角色
	
	private String address; //地址
	
	private String password; // 密码

	private String point; // 积分

	private String email; // email

	private String tel; // 用户电话

	private Integer level; // 用户等级

	private String nickName; // 用户昵称

	private String avatarUrl; // 用户头像URL
	



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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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







	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
