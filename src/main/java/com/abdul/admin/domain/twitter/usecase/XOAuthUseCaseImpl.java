package com.abdul.admin.domain.twitter.usecase;

import com.abdul.admin.config.OauthProperties;
import com.abdul.admin.domain.twitter.utils.Oauth2Helper;
import com.abdul.admin.domain.user.model.OauthLoginRequest;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import com.abdul.admin.domain.user.usecase.AbstractGetOAuthUrlUseCase;
import com.twitter.clientlib.auth.TwitterOAuth20Service;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("twitter")
public class XOAuthUseCaseImpl extends AbstractGetOAuthUrlUseCase {

    private final OauthProperties oauthProperties;
    private final Oauth2Helper oauth2Helper;
    private final TwitterOAuth20Service twitterOAuth20Service;

    public XOAuthUseCaseImpl(
            UserRepository userRepository,
            ApplicationContext applicationContext,
            TwitterOAuth20Service twitterOAuth20Service,
            OauthProperties oauthProperties,
            Oauth2Helper oauth2Helper
    ) {
        super(userRepository, applicationContext);
        this.oauthProperties = oauthProperties;
        this.oauth2Helper = oauth2Helper;
        this.twitterOAuth20Service = twitterOAuth20Service;
    }

    @Override
    public String getOauthAuthorizationUrl(OauthLoginRequest oauthLoginRequest) throws IOException {
        return twitterOAuth20Service.getAuthorizationUrl(
                oauth2Helper.getProofKeyForCodeExchange(
                        oauthProperties.getRegistration().getX().getPkceCodeChallenge(),
                        oauthProperties.getRegistration().getX().getPkceCodeVerifier()),
                oauth2Helper.getSecretState(oauthLoginRequest.getSearchTerm())
        );
    }
}
