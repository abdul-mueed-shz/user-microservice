package com.abdul.admin.domain.google.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleOauthRedirectInfo {

    String code;

    String scope;

    String authuser;

    String prompt;
}
