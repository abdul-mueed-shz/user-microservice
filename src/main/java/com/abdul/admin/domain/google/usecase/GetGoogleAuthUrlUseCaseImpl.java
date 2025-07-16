package com.abdul.admin.domain.google.usecase;

import com.abdul.admin.domain.google.port.in.GetGoogleOAuthRedirectUriUseCase;
import com.abdul.admin.domain.user.model.OauthLoginRequest;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import com.abdul.admin.domain.user.usecase.AbstractGetOAuthUrlUseCase;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("google")
public class GetGoogleAuthUrlUseCaseImpl extends AbstractGetOAuthUrlUseCase {

    private final GetGoogleOAuthRedirectUriUseCase getRedirectUriUseCaseImpl;
    private final AuthorizationCodeFlow authorizationCodeFlow;

    public GetGoogleAuthUrlUseCaseImpl(
            UserRepository userRepository,
            ApplicationContext applicationContext,
            GetGoogleOAuthRedirectUriUseCase getRedirectUriUseCaseImpl,
            AuthorizationCodeFlow authorizationCodeFlow) {
        super(userRepository, applicationContext);
        this.getRedirectUriUseCaseImpl = getRedirectUriUseCaseImpl;
        this.authorizationCodeFlow = authorizationCodeFlow;
    }

    @Override
    public String getOauthAuthorizationUrl(OauthLoginRequest oauthLoginRequest) {
        return authorizationCodeFlow.newAuthorizationUrl()
                .setRedirectUri(getRedirectUriUseCaseImpl.execute())
                .build();
    }

}
