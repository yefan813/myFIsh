package com.frame.domain.enums;

public enum FishSiteTypeEnum {
	YE_TANG(1, "野塘")
	;


	private Integer key;
	private String value;

	FishSiteTypeEnum(Integer key, String value){
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
