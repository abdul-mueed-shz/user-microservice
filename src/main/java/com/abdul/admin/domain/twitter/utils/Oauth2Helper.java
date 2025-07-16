package com.abdul.admin.domain.twitter.utils;

import com.github.scribejava.core.pkce.PKCE;
import com.github.scribejava.core.pkce.PKCECodeChallengeMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class Oauth2Helper {

    public String getSecretState(String statePostFix) {
        return "secret_" + statePostFix;
    }

    public String getUrl(
            String appBaseUrl,
            String authUrl,
            String grantType,
            String clientId,
            String redirectUri,
            String scopes,
            String searchTerm) {
        return UriComponentsBuilder.fromUriString(authUrl)
                .queryParam("response_type", grantType)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri.replace("{baseUrl}", appBaseUrl))
                .queryParam("state", getSecretState(searchTerm))
                .queryParam("scope", scopes).encode()
                .toUriString();
    }

    public PKCE getProofKeyForCodeExchange(String codeChallenge, String codeVerifier) {
        PKCE pkce = new PKCE();
        pkce.setCodeChallenge(codeChallenge);
        pkce.setCodeChallengeMethod(PKCECodeChallengeMethod.PLAIN);
        pkce.setCodeVerifier(codeVerifier);
        return pkce;
    }
}
