package com.frame.domain.enums;

public enum SendSMSTypeEnum {
	REGIST_USER(1, "注册用户验证码"),
	FORGET_PWD(2, "找回密码验证码"),
	BIND_TEL(3, "绑定手机号验证码"),

	;


	private Integer key;
	private String value;

	SendSMSTypeEnum(Integer key, String value){
		setKey(key);
		setValue(value);
	}
	

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static SendSMSTypeEnum getTypeByKey(int key){
		if(key == 0){
			return null;
		}
		for (SendSMSTypeEnum type : SendSMSTypeEnum.values()) {
			if (type.key == key) {
				return type;
			}
		}
		return null;
	}


}
