package com.abdul.admin.adapter.out.web;

import com.abdul.admin.config.OauthProperties;
import com.abdul.admin.domain.github.model.GithubUserResponse;
import com.abdul.admin.domain.user.model.AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class GitHubClient {

    private final OauthProperties oauthProperties;
    private final WebClient webClient;
    private final OAuth20Service githubOauth2Service;

    public GitHubClient(
            OauthProperties oauthProperties,
            WebClient.Builder webClientBuilder,
            @Qualifier("githubBean") OAuth20Service githubOauth2Service) {
        this.oauthProperties = oauthProperties;
        this.webClient = webClientBuilder
                .build();
        this.githubOauth2Service = githubOauth2Service;
    }

    public GithubUserResponse getUserProfile(AccessToken accessToken) throws WebClientResponseException {
        return webClient.get()
                .uri(oauthProperties.getProvider().getGithub().getUserInfoUri())
                .headers(headers -> headers.setBearerAuth(accessToken.getToken()))
                .retrieve()
                .bodyToMono(GithubUserResponse.class) // Map directly to the GithubUserResponse class
                .block(); // Blocking call; use reactive alternatives in production if possible
    }

    public AccessToken fetchAccessToken(String code) throws WebClientResponseException {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", oauthProperties.getRegistration().getGithub().getClientId());
        formData.add("client_secret", oauthProperties.getRegistration().getGithub().getClientSecret());
        formData.add("code", code);
        formData.add("redirect_uri", githubOauth2Service.getCallback());

        String responseBody = webClient.post()
                .uri(oauthProperties.getProvider().getGithub().getTokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class) // Fetch raw response as a String
                .block();
        assert responseBody != null;
        return parseFormEncodedResponse(responseBody);
    }

    private AccessToken parseFormEncodedResponse(String responseBody) {
        AccessToken accessToken = new AccessToken();
        String[] pairs = responseBody.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : null;

            switch (key) {
                case "access_token":
                    accessToken.setToken(value);
                    break;
                case "expires_in":
                    accessToken.setExpiresIn(value);
                    break;
                case "scope":
                    accessToken.setScope(value);
                    break;
                case "token_type":
                    accessToken.setTokenType(value);
                    break;
                case "id_token":
                    accessToken.setIdToken(value);
                    break;
                default:
                    // Ignore unexpected keys
            }
        }
        return accessToken;
    }
}