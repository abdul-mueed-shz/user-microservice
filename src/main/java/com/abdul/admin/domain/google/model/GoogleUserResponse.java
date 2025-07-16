package com.abdul.admin.domain.google.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GoogleUserResponse {

    private String email;
    private String familyName;
    private String givenName;
    private String id;
    private String name;
    private String picture;
    private Boolean verifiedEmail;
}
