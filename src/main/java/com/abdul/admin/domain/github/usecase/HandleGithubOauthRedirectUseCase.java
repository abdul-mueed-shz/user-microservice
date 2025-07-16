package com.abdul.admin.domain.github.usecase;

import com.abdul.admin.adapter.in.web.mapper.UserDtoMapper;
import com.abdul.admin.adapter.out.web.GitHubClient;
import com.abdul.admin.domain.auth.port.in.AuthenticateUserUseCase;
import com.abdul.admin.domain.github.model.GithubUserResponse;
import com.abdul.admin.domain.user.mapper.UserInfoMapper;
import com.abdul.admin.domain.user.model.AccessToken;
import com.abdul.admin.domain.user.model.UserRegistrationRequestInfo;
import com.abdul.admin.domain.user.port.in.GetUserDetailUseCase;
import com.abdul.admin.domain.user.port.in.RegisterUserUseCase;
import com.abdul.admin.domain.user.port.in.UpdateUserUseCase;
import com.abdul.admin.domain.user.usecase.AbstractUserOauthUseCase;
import com.abdul.toolkit.utils.user.model.UserInfo;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service("githubRedirect")
public class HandleGithubOauthRedirectUseCase extends AbstractUserOauthUseCase {

    private final GitHubClient gitHubClient;
    private final GetUserDetailUseCase getUserDetailUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final UserInfoMapper userInfoMapper;
    private final UserDtoMapper userDtoMapper;

    public HandleGithubOauthRedirectUseCase(
            GitHubClient gitHubClient,
            GetUserDetailUseCase getUserDetailUseCase,
            UpdateUserUseCase updateUserUseCase,
            RegisterUserUseCase registerUserUseCase,
            UserInfoMapper userInfoMapper,
            UserDtoMapper userDtoMapper,
            AuthenticateUserUseCase authenticateUserUseCase) {
        super(authenticateUserUseCase);
        this.gitHubClient = gitHubClient;
        this.getUserDetailUseCase = getUserDetailUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.userInfoMapper = userInfoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    protected UserInfo getUserByState(String state) {
        // Because state is always null for GITHUB. Proceed with null response.
        return null;
    }

    /**
     * @param code
     * @param state
     * @param userInfo
     */
    @Override
    protected UserInfo executeTokenValidationFlow(String code, String state, UserInfo userInfo) {
        // Because state is always null for GITHUB. Proceed with auth code flow.
        return executeAuthCodeFlow(code, state);
    }

    /**
     * @param code
     * @param state
     */
    @Override
    protected UserInfo executeAuthCodeFlow(String code, String state) {
        AccessToken accessToken = gitHubClient.fetchAccessToken(code);
        GithubUserResponse githubUserResponse = gitHubClient.getUserProfile(accessToken);
        UserInfo userInfo = getUserDetailUseCase.get(githubUserResponse.getEmail());
        if (Objects.nonNull(userInfo)) {
            UserInfo updatedUserInfo =
                    userInfoMapper.map(
                            userInfo,
                            githubUserResponse,
                            accessToken
                    );
            return updateUserUseCase.execute(updatedUserInfo);
        }
        UserRegistrationRequestInfo userRegistrationRequestInfo =
                userDtoMapper.map(githubUserResponse, accessToken);
        return registerUserUseCase.execute(userRegistrationRequestInfo);
    }
}
