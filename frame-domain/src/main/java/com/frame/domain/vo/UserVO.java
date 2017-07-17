package com.frame.domain.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by garnett on 2015/11/18.
 */
public class UserVO implements Serializable{

	private static final long serialVersionUID = 6723005414893784943L;

	private Integer id;

	private String name; // 姓名

	private Integer sex; //性别
	
	private Integer role; //角色 1 男 2 女
	
	private String address; //地址
	
	private Long birthday;

	private String point; // 积分

	private String email; // email

	private String tel; // 用户电话

	private Integer level; // 用户等级

	private String nickName; // 用户昵称

	private String avatarUrl; // 用户头像URL

	private Long fans;  //粉丝

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
