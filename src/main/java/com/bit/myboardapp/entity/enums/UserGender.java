package com.bit.myboardapp.entity.enums;

import lombok.Getter;

@Getter
public enum UserGender {

    // 남자
    Male("Male"),
    // 여자
    Female("Female"),
    // 그 외
    Other("Other");

    private final String value;

    // 입력받은 enum값을 value에 저장
    UserGender(String value) {
        this.value = value;
    }

    public static UserGender fromValue(String value) {
        for (UserGender gender : UserGender.values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + value + "]");
    }
}