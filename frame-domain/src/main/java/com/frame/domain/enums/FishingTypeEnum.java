package com.frame.domain.enums;

public enum FishingTypeEnum {
	LU_YA(1, "路亚"),
	TAI_DIAO(2, "台钓"),
	HAI_DIAO(3, "海钓")
	;


	private Integer key;
	private String value;

	FishingTypeEnum(Integer key, String value){
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
