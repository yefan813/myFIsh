package com.frame.domain.enums;

public enum CollectionTypeEnum {
	ARTICLE_FISH(1, "文章"),
	FISH_SITE(2, "钓点"),
	FISH_SHOP(3, "渔具店"),
	;


	private Integer key;
	private String value;

	CollectionTypeEnum(Integer key, String value){
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

	public static CollectionTypeEnum getTypeByKey(int key){
		if(key == 0){
			return null;
		}
		for (CollectionTypeEnum type : CollectionTypeEnum.values()) {
			if (type.key == key) {
				return type;
			}
		}
		return null;
	}


}
