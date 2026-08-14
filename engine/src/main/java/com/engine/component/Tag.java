package com.engine.component;

public class Tag implements Component {
    private String value;

    public Tag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
