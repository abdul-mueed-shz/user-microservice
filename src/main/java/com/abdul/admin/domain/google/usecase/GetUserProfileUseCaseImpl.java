package com.abdul.admin.domain.google.usecase;

import com.abdul.admin.config.OauthProperties;
import com.abdul.admin.domain.google.mapper.GoogleUserInfoMapper;
import com.abdul.admin.domain.google.model.GoogleUserResponse;
import com.abdul.admin.domain.google.port.in.GetUserProfileUseCase;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserProfileUseCaseImpl implements GetUserProfileUseCase {

    private final AuthorizationCodeFlow authorizationCodeFlow;
    private final OauthProperties oauthProperties;
    private final GoogleUserInfoMapper googleUserInfoMapper;

    public GoogleUserResponse execute(String userId) throws IOException {
        Credential credential = authorizationCodeFlow.loadCredential(userId);
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleCredentials credentials = GoogleCredentials
                .create(getAccessToken(
                        credential.getAccessToken(),
                        System.currentTimeMillis(),
                        credential.getExpiresInSeconds())
                );

        credentials = credentials.createScoped(
                oauthProperties.getRegistration().getGoogle().getScope()
        );
        credentials.refreshIfExpired();
        Oauth2 oauth2 = new Oauth2.Builder(transport, jsonFactory, new HttpCredentialsAdapter(credentials))
                .setApplicationName("user-admin")
                .build();
        Userinfo userinfo = oauth2.userinfo().get().execute();
        return googleUserInfoMapper.mapToGoogleUserInfo(userinfo);
    }

    private AccessToken getAccessToken(String accessToken, Long tokenCreationTime, Long tokenExpirationTimeInSeconds) {
        long expirationTimeInMS = (tokenCreationTime + tokenExpirationTimeInSeconds) * 1000;
        return new AccessToken(accessToken, new Date(expirationTimeInMS));
    }
}
