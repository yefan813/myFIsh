package com.frame.domain.enums;

public enum ReplyTypeEnum {
	COMMENT(1, "回复平罗"),
	REPLY(2, "回复回复")
	;


	private Integer key;
	private String value;

	ReplyTypeEnum(Integer key, String value){
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

}
