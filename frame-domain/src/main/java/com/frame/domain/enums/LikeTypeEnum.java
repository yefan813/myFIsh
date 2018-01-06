package com.frame.domain.enums;

public enum LikeTypeEnum {
    ARTICAL(1, "文章"),
    SITE(2,"钓点"),
    SHOP(2,"渔具店"),
    ;

    private int key;
    private String value;

    LikeTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
