package com.frame.domain.enums;

public enum AricalTypeEnum {
	FISH_ARCHIVE_SHOW(1, "钓获展示"),
	ARTICAL(2,"普通文章"),
	SKILL_ANOSWER(3, "技巧问答"),
	FISH_TOOLS_DIY(4, "钓具DIY"),
	FISH_TOOL_TEST(5, "钓具测评"),
	ER_LIAO_RESOURCE(6, "饵料配方"),
	LU_YA(7, "路亚"),
	FISH_FOOD(8, "钓友美食"),
	WHATEREVER(9, "随便侃侃"),
	QIU_YING(10, "蚯蚓"),
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
