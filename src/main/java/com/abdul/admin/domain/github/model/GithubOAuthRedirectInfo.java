package com.abdul.admin.domain.github.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GithubOAuthRedirectInfo {

    private String code;
    private String state;
}
