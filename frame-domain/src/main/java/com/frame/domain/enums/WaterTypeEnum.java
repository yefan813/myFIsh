package com.frame.domain.enums;

public enum WaterTypeEnum {
	LARK(1, "湖泊"),
	JIANGHE(2, "江河"),
	REIVER(3, "小溪"),
	;


	private Integer key;
	private String value;

	WaterTypeEnum(Integer key, String value){
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
