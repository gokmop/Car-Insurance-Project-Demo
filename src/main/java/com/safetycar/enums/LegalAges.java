package com.safetycar.enums;

public enum LegalAges {
    MIN(18),
    MAX(120);
    private final int age;

    LegalAges(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
