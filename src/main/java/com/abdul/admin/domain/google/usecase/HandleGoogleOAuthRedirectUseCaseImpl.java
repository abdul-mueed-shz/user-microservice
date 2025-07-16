package com.abdul.admin.domain.google.usecase;

import com.abdul.admin.adapter.in.web.mapper.UserDtoMapper;
import com.abdul.admin.domain.google.model.GoogleOauthRedirectInfo;
import com.abdul.admin.domain.google.model.GoogleUserResponse;
import com.abdul.admin.domain.google.port.in.GetGoogleOAuthRedirectUriUseCase;
import com.abdul.admin.domain.google.port.in.GetUserProfileUseCase;
import com.abdul.admin.domain.google.port.in.HandleGoogleOAuthRedirectUseCase;
import com.abdul.admin.domain.user.mapper.UserInfoMapper;
import com.abdul.admin.domain.user.model.Oauth2LoginResponse;
import com.abdul.toolkit.utils.user.model.UserInfo;
import com.abdul.admin.domain.user.port.in.RegisterUserUseCase;
import com.abdul.admin.domain.user.port.in.UpdateUserUseCase;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandleGoogleOAuthRedirectUseCaseImpl implements HandleGoogleOAuthRedirectUseCase {

    private final AuthorizationCodeFlow authorizationCodeFlow;
    private final GetGoogleOAuthRedirectUriUseCase getGoogleOAuthRedirectUriUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;
    private final UserDtoMapper userDtoMapper;
    private final RegisterUserUseCase registerUserUseCase;
    private final UserRepository userRepository;
    private final UpdateUserUseCase updateUserUseCase;
    private final UserInfoMapper userInfoMapper;

    @Value("${spring.application.client-app-home-url}")
    private String clientAppHomeUrl;

    @Override
    public Oauth2LoginResponse execute(GoogleOauthRedirectInfo googleOauthRedirectInfo) throws IOException {
        Credential credential = authorizationCodeFlow.loadCredential(googleOauthRedirectInfo.getAuthuser());
        UserInfo userInfo = getUserByState(googleOauthRedirectInfo.getAuthuser());
        if (Objects.nonNull(credential) && Objects.nonNull(userInfo)) {
            executeTokenValidationFlow(
                    userInfo,
                    credential,
                    googleOauthRedirectInfo.getCode(),
                    googleOauthRedirectInfo.getAuthuser());
        } else {
            executeAuthCodeFlow(googleOauthRedirectInfo.getCode(), googleOauthRedirectInfo.getAuthuser());
        }
        return Oauth2LoginResponse.builder()
                .accessToken("System Generated")
                .refreshToken("System Generated")
                .build();
    }

    protected UserInfo getUserByState(String authUser) {
        // In GOOGLE's case it's auth user
        return userRepository.findByGoogleAuthUser(authUser);
    }

    private void executeTokenValidationFlow(UserInfo userInfo, Credential credential, String code, String authUser)
            throws IOException {
        if (isAccessTokenValid(userInfo.getGoogleUser().getCreatedAt(),
                String.valueOf(credential.getExpiresInSeconds()))
                || credential.refreshToken()) {
            return;
        }
        executeAuthCodeFlow(code, authUser);
    }

    private void executeAuthCodeFlow(String code, String authUser) throws IOException {
        AuthorizationCodeTokenRequest tokenRequest = authorizationCodeFlow.newTokenRequest(code)
                .setRedirectUri(getGoogleOAuthRedirectUriUseCase.execute());
        TokenResponse tokenResponse = tokenRequest.execute();
        authorizationCodeFlow.createAndStoreCredential(tokenResponse, authUser);
        GoogleUserResponse googleUserResponse = getUserProfileUseCase.execute(authUser);
        UserInfo userInfo = userRepository.findByUsernameOrEmail(googleUserResponse.getEmail());
        if (Objects.nonNull(userInfo)) {
            updateUserUseCase.execute(userInfoMapper.map(userInfo, googleUserResponse, authUser));
            return;
        }
        registerUserUseCase.execute(userDtoMapper.map(googleUserResponse, authUser));
    }

    protected boolean isAccessTokenValid(LocalDateTime createdAt, String expiresInSeconds) {
        LocalDateTime tokenExpiryDateTime = createdAt.plusSeconds(Long.parseLong(expiresInSeconds));
        return !LocalDateTime.now().isAfter(tokenExpiryDateTime);
    }
}
