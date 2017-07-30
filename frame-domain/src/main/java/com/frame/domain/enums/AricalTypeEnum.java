package com.frame.domain.enums;

public enum AricalTypeEnum {
	FISH_ARCHIVE_SHOW(0, "钓获展示"),
	QIU_YING(2, "蚯蚓"),
	SKILL_ANOSWER(1, "技巧问答"),
	FISH_TOOLS_DIY(1, "钓具DIY"),
	FISH_TOOL_TEST(1, "钓具测评"),
	ER_LIAO_RESOURCE(1, "饵料配方"),
	LU_YA(1, "路亚"),
	FISH_FOOD(1, "钓友美食"),
	WHATEREVER(1, "随便侃侃")
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
