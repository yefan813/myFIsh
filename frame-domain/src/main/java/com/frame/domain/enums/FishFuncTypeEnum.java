package com.frame.domain.enums;

public enum FishFuncTypeEnum {
	ZHU_LINE(1, "1.2主线"),
	ZI_LINE(2, "0.4子线")
	;


	private Integer key;
	private String value;

	FishFuncTypeEnum(Integer key, String value){
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
