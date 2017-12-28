package com.frame.domain.enums;

public enum AricalTypeEnum {
	FISH_ARCHIVE_SHOW(0, "钓获展示"),
	SKILL_ANOSWER(1, "技巧问答"),
	FISH_TOOLS_DIY(2, "钓具DIY"),
	FISH_TOOL_TEST(3, "钓具测评"),
	ER_LIAO_RESOURCE(4, "饵料配方"),
	LU_YA(5, "路亚"),
	FISH_FOOD(6, "钓友美食"),
	WHATEREVER(7, "随便侃侃"),
	;


	private Integer key;
	private String value;

	AricalTypeEnum(Integer key, String value){
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
