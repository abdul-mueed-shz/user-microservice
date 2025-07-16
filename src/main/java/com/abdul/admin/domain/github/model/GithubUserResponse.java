package com.abdul.admin.domain.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GithubUserResponse {

    private String name;
    private String login;
    private String email;
    private String location;
    private boolean hireable;
    private String bio;
    private String company;
    private String blog;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String accessToken;

}
