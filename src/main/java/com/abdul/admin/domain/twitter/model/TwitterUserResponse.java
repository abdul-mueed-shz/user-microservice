package com.abdul.admin.domain.twitter.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwitterUserResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;

    @JsonProperty("username")
    private String username;

    @JsonProperty("verified")
    private Boolean verified;
}
