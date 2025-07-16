package com.abdul.admin.adapter.out.persistence.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientTypeEnum {

    LINKEDIN("linkedin"),
    GOOGLE("google");

    private final String name;

}
