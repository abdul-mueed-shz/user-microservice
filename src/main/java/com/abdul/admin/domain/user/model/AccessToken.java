package com.abdul.admin.domain.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * POJO to encapsulate access token from 2/3-legged LinkedIn OAuth 2.0  flow.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public final class AccessToken {

    @JsonProperty(value = "access_token")
    private String token;
    @JsonProperty(value = "expires_in")
    private String expiresIn;
    @JsonProperty(value = "scope")
    private String scope;
    @JsonProperty(value = "token_type")
    private String tokenType;
    @JsonProperty("id_token")
    private String idToken;

}
