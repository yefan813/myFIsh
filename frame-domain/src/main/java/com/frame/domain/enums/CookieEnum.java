package com.frame.domain.enums;

public enum CookieEnum {
    TICKET("TICKET", "登录TICKET"),

    ;

    private String key;
    private String desc;

    private CookieEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
