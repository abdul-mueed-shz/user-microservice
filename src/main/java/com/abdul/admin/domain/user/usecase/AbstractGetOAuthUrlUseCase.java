package com.abdul.admin.domain.user.usecase;

import com.abdul.admin.domain.user.model.OauthLoginRequest;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public abstract class AbstractGetOAuthUrlUseCase {

    public final UserRepository userRepository;
    private final ApplicationContext applicationContext;

    public String execute(OauthLoginRequest oauthLoginRequest) throws IOException {
        validateSearchTerm(oauthLoginRequest);
        return getOauthAuthorizationUrl(oauthLoginRequest);
    }

    public abstract String getOauthAuthorizationUrl(OauthLoginRequest oauthLoginRequest) throws IOException;

    private void validateSearchTerm(OauthLoginRequest oauthLoginRequest) {
        if (Objects.nonNull(userRepository.findByUsernameOrEmail(oauthLoginRequest.getSearchTerm()))) {
            // throw Error. user is already registered in the system. login with email/username
        }
    }

}
