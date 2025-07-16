package com.abdul.admin.domain.user.usecase;

import com.abdul.admin.domain.auth.model.AuthenticationInfo;
import com.abdul.admin.domain.auth.port.in.AuthenticateUserUseCase;
import com.abdul.admin.domain.user.model.Oauth2LoginResponse;
import com.abdul.toolkit.utils.user.model.UserInfo;
import com.twitter.clientlib.ApiException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractUserOauthUseCase {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    @Transactional
    public Oauth2LoginResponse execute(String code, String state)
            throws IOException, ExecutionException, InterruptedException, ApiException {
        UserInfo userInfo = getUserByState(state);
        if (Objects.nonNull(userInfo)) {
            userInfo = executeTokenValidationFlow(code, state, userInfo);
        } else {
            userInfo = executeAuthCodeFlow(code, state);
        }
        AuthenticationInfo authenticationInfo = authenticateUserUseCase.authenticate(userInfo);
        return Oauth2LoginResponse.builder()
                .accessToken(authenticationInfo.getAccessToken())
                .refreshToken(authenticationInfo.getRefreshToken())
                .build();
    }

    protected abstract UserInfo getUserByState(String state);

    protected abstract UserInfo executeTokenValidationFlow(String code, String state, UserInfo userInfo)
            throws ApiException, IOException, ExecutionException, InterruptedException;

    protected abstract UserInfo executeAuthCodeFlow(String code, String state)
            throws ApiException, IOException, ExecutionException, InterruptedException;


    protected boolean isAccessTokenValid(LocalDateTime createdAt, String expiresInSeconds) {
        LocalDateTime tokenExpiryDateTime = createdAt.plusSeconds(Long.parseLong(expiresInSeconds));
        return !LocalDateTime.now().isAfter(tokenExpiryDateTime);
    }

}
