package com.bit.myboardapp.entity.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

    // 활동
    Active("Active"),
    // 비활동
    InActive("InActive"),
    // 차단
    Banned("Banned"),
    // 탈퇴
    Withdraw("Withdraw");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public static UserStatus fromValue(String value) {
        for (UserStatus status : UserStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + value + "]");
    }
}