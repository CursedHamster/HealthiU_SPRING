package com.example.healthiu.entity;

public enum Gender {
    MALE("Чоловік"),
    FEMALE("Жінка");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
