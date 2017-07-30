package com.frame.domain.enums;

public enum FishTypeEnum {
	JI_FISH(1, "鲫鱼"),
	LI_FISH(2, "鲤鱼")
	;


	private Integer key;
	private String value;

	FishTypeEnum(Integer key, String value){
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
