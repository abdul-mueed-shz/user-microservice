package com.abdul.admin.config;

import com.abdul.admin.domain.google.datastore.JPADataStoreFactory;
import com.abdul.admin.domain.google.port.out.repository.GoogleCredentialRepository;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.auth.TwitterOAuth20Service;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final OauthProperties oauthProperties;
    private final GoogleCredentialRepository googleCredentialRepository;

    @Bean
    AuthorizationCodeFlow getAuthorizationCodeFlow() throws IOException {
        DataStoreFactory dataStoreFactory = new JPADataStoreFactory(googleCredentialRepository);
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                oauthProperties.getRegistration().getGoogle().getClientId(),
                oauthProperties.getRegistration().getGoogle().getClientSecret(),
                oauthProperties.getRegistration().getGoogle().getScope())
                .setAccessType(oauthProperties.getRegistration().getGoogle().getAccessType())
                .setDataStoreFactory(dataStoreFactory)
                .build();
    }

    @Bean("githubBean")
    @RequestScope
    public OAuth20Service getGithubServiceInstance() {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String scopes = String.join(",", oauthProperties.getRegistration().getGithub().getScope());
        return new ServiceBuilder(oauthProperties.getRegistration().getGithub().getClientId())
                .apiSecret(oauthProperties.getRegistration().getGithub().getClientSecret())
                .callback(oauthProperties.getRegistration().getGithub()
                        .getRedirectUri().replace("{baseUrl}", baseUrl))
                .defaultScope(scopes)
                .build(GitHubApi.instance());
    }

    @Bean
    public TwitterOAuth20Service getXOAuthServiceInstance() {
        return getXOAuth20Service(
                getXCredentialsOAuth2(
                        oauthProperties.getRegistration().getX().getClientId(),
                        oauthProperties.getRegistration().getX().getClientSecret(),
                        oauthProperties.getRegistration().getX().getAccessToken()
                ),
                oauthProperties.getRegistration().getX().getRedirectUri(),
                oauthProperties.getRegistration().getX().getScope()
        );
    }

    private TwitterOAuth20Service getXOAuth20Service(
            TwitterCredentialsOAuth2 xCredentialsOAuth2,
            String redirectUri,
            String scopes) {
        return new TwitterOAuth20Service(
                xCredentialsOAuth2.getTwitterOauth2ClientId(),
                xCredentialsOAuth2.getTwitterOAuth2ClientSecret(),
                redirectUri,
                scopes.replace(",", " ")
        );
    }

    private TwitterCredentialsOAuth2 getXCredentialsOAuth2(String clientId, String clientSecret, String accessToken) {
        return new TwitterCredentialsOAuth2(clientId, clientSecret, accessToken, "");
    }
}
