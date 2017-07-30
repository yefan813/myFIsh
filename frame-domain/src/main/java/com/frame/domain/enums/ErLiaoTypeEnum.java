package com.frame.domain.enums;

public enum ErLiaoTypeEnum {
	LONG_WANG_HENG(1, "龙王恨"),
	QIU_YING(2, "蚯蚓")
	;


	private Integer key;
	private String value;

	ErLiaoTypeEnum(Integer key, String value){
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
