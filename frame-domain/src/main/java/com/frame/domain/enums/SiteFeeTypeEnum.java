package com.frame.domain.enums;

public enum SiteFeeTypeEnum {
	TEN_PER_JIN(1, "10元/斤"),
	THREETY_PER_HOUR(2, "30元/4小时"),
	FREE(3, "野塘免费"),

	;


	private Integer key;
	private String value;

	SiteFeeTypeEnum(Integer key, String value){
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
