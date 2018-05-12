package com.frame.domain.enums;

public enum ScanRecordTypeEnum {
	FISH_SITE(1, "钓点记录"),
	FISH_SHOP(2, "渔具店记录"),
	;


	private Integer key;
	private String value;

	ScanRecordTypeEnum(Integer key, String value){
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

	public static ScanRecordTypeEnum getTypeByKey(int key){
		if(key == 0){
			return null;
		}
		for (ScanRecordTypeEnum type : ScanRecordTypeEnum.values()) {
			if (type.key == key) {
				return type;
			}
		}
		return null;
	}


}
