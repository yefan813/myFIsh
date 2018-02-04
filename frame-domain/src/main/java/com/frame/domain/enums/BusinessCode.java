package com.frame.domain.enums;

public enum BusinessCode {
	FAILED("999", 999, "调用接口失败", null),
	MUTIL_LOGIN("9999", 9999, "用户在其他手机上登录", null),
	SUCCESS("1000", 1000, "调用接口成功", null),
	SERVER_INTERNAL_ERROR("0001", 0001, "服务器内部错误", null),
	PARAMETERS_ERROR("0002", 0002, "传递参数错误", null),

	IS_CORRECT_YES("1001", 1001, "判断正确", null),
	IS_CORRECT_NO("1002", 1002, "判断失败", null),

	IS_EXIST_YES("1003", 1003, "记录存在", null),
	IS_EXIST_NO("1004", 1004, "记录不存在", null),

	IS_WRITE_YES("1005", 1005, "写入成功", null),
	IS_WRITE_NO("1006", 1006, "写入失败", null),

	IS_FRIEND("1007", 1007, "已经是好友", null),
	NO_FRIEND("1008", 1008, "还不是好友", null),

	NO_LOGIN("1009", 1009, "用户未登录", null),
	CRY_FAILE("1010", 1010, "cookie 解密失败", null),
	COOKIE_INVAID("1011", 1011, "用户cookie已过期", null),


	;


	private String code;
	private int key;
	private String value;
	private Object data;
	
	BusinessCode(String code, int key, String value, Object data){
		setCode(code);
		setKey(key);
		setValue(value);
		setData(data);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isSuccess(){
		if ("0000".equals(this.code)) {
			return true;
		}
		return false;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
