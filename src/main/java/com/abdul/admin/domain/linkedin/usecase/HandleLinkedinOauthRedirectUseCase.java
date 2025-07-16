package com.abdul.admin.domain.linkedin.usecase;

import com.abdul.admin.adapter.in.web.mapper.UserDtoMapper;
import com.abdul.admin.config.OauthProperties;
import com.abdul.admin.domain.auth.port.in.AuthenticateUserUseCase;
import com.abdul.admin.domain.user.mapper.UserInfoMapper;
import com.abdul.admin.domain.user.model.UserRegistrationRequestInfo;
import com.abdul.admin.domain.user.port.in.GetUserDetailUseCase;
import com.abdul.admin.domain.user.port.in.RegisterUserUseCase;
import com.abdul.admin.domain.user.port.in.UpdateUserUseCase;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import com.abdul.admin.domain.user.usecase.AbstractUserOauthUseCase;
import com.abdul.toolkit.utils.linkedin.model.LinkedinUserResponse;
import com.abdul.toolkit.utils.linkedin.port.in.LinkedinApiUseCase;
import com.abdul.toolkit.utils.model.AccessToken;
import com.abdul.toolkit.utils.user.model.UserInfo;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component("linkedinRedirect")
public class HandleLinkedinOauthRedirectUseCase extends AbstractUserOauthUseCase {

    private final LinkedinApiUseCase linkedinApiUseCase;
    private final OauthProperties oauthProperties;
    private final UserDtoMapper userDtoMapper;
    private final UserInfoMapper userInfoMapper;
    private final UserRepository userRepository;
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserDetailUseCase getUserDetailUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public HandleLinkedinOauthRedirectUseCase(
            LinkedinApiUseCase linkedinApiUseCase,
            OauthProperties oauthProperties,
            UserDtoMapper userDtoMapper,
            UserInfoMapper userInfoMapper,
            UserRepository userRepository,
            RegisterUserUseCase registerUserUseCase,
            GetUserDetailUseCase getUserDetailUseCase,
            UpdateUserUseCase updateUserUseCase,
            AuthenticateUserUseCase authenticateUserUseCase) {
        super(authenticateUserUseCase);
        this.linkedinApiUseCase = linkedinApiUseCase;
        this.oauthProperties = oauthProperties;
        this.userDtoMapper = userDtoMapper;
        this.userInfoMapper = userInfoMapper;
        this.userRepository = userRepository;
        this.registerUserUseCase = registerUserUseCase;
        this.getUserDetailUseCase = getUserDetailUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    @Override
    protected UserInfo executeTokenValidationFlow(String code, String state, UserInfo userInfo) {
        if (isAccessTokenValid(userInfo.getLinkedinUser().getCreatedAt(), userInfo.getLinkedinUser().getExpiresIn())) {
            String accessTokenValue = userInfo.getLinkedinUser().getAccessToken();
            LinkedinUserResponse linkedinUserResponse = linkedinApiUseCase.getUserResponse(accessTokenValue);
            UserInfo updatedUserInfo = userInfoMapper.map(
                    userInfo,
                    linkedinUserResponse,
                    null,
                    state,
                    code);
            return updateUserUseCase.execute(updatedUserInfo);
        }
        return executeAuthCodeFlow(code, state);

    }

    @Override
    protected UserInfo executeAuthCodeFlow(String code, String state) {
        AccessToken accessToken = getAccessTokenByAuthCode(code);
        LinkedinUserResponse linkedinUserResponse = linkedinApiUseCase.getUserResponse(accessToken.getToken());
        UserInfo userInfo = getUserDetailUseCase.get(linkedinUserResponse.getEmail());
        if (Objects.nonNull(userInfo)) {
            return updateUserUseCase
                    .execute(userInfoMapper.map(userInfo, linkedinUserResponse, accessToken, state, code));
        }
        UserRegistrationRequestInfo userRegistrationRequestInfo = userDtoMapper.map(linkedinUserResponse, accessToken,
                state, code);
        return registerUserUseCase.execute(userRegistrationRequestInfo);
    }

    @Override
    protected UserInfo getUserByState(String state) {
        return userRepository.findByUserLinkedinState(state);
    }


    protected AccessToken getAccessTokenByAuthCode(String code) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return linkedinApiUseCase.getAccessToken(
                oauthProperties.getRegistration().getLinkedin().getTokenGrantTypeParam(), code,
                oauthProperties.getRegistration().getLinkedin().getRedirectUri().replace("{baseUrl}", baseUrl),
                oauthProperties.getRegistration().getLinkedin().getClientId(),
                oauthProperties.getRegistration().getLinkedin().getClientSecret());
    }
}
