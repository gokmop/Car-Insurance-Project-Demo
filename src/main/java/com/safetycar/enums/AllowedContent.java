package com.safetycar.enums;

public enum AllowedContent {

    PNG("image/png"),
    JPEG("image/jpeg"),
    GIF("image/gif");

    private final String type;

    AllowedContent(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
