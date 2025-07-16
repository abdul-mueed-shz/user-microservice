package com.abdul.admin.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserMessageCodeEnum {

    USER_REGISTERED_SUCCESSFULLY("U-001", "User Registered Successfully.");

    private final String messageCode;
    private final String messageDescription;
}
