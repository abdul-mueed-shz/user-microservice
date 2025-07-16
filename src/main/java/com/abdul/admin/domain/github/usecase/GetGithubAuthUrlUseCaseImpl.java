package com.abdul.admin.domain.github.usecase;

import com.abdul.admin.domain.user.model.OauthLoginRequest;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import com.abdul.admin.domain.user.usecase.AbstractGetOAuthUrlUseCase;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("github")
public class GetGithubAuthUrlUseCaseImpl extends AbstractGetOAuthUrlUseCase {

    private final OAuth20Service oAuth20Service;


    public GetGithubAuthUrlUseCaseImpl(
            UserRepository userRepository,
            ApplicationContext applicationContext,
            @Qualifier("githubBean") OAuth20Service oAuth20Service
    ) {
        super(userRepository, applicationContext);
        this.oAuth20Service = oAuth20Service;
    }

    @Override
    public String getOauthAuthorizationUrl(OauthLoginRequest oauthLoginRequest) {
        return oAuth20Service.getAuthorizationUrl();
    }
}
