package com.abdul.admin.domain.linkedin.usecase;

import com.abdul.admin.config.OauthProperties;
import com.abdul.admin.domain.twitter.utils.Oauth2Helper;
import com.abdul.admin.domain.user.model.OauthLoginRequest;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import com.abdul.admin.domain.user.usecase.AbstractGetOAuthUrlUseCase;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Getter
@Service("linkedin")
public class LinkedInOAuthUseCaseImpl extends AbstractGetOAuthUrlUseCase {

    private final OauthProperties oauthProperties;
    private final Oauth2Helper oauth2Helper;

    public LinkedInOAuthUseCaseImpl(UserRepository userRepository, ApplicationContext applicationContext,
            OauthProperties oauthProperties, Oauth2Helper oauth2Helper) {
        super(userRepository, applicationContext);
        this.oauth2Helper = oauth2Helper;
        this.oauthProperties = oauthProperties;
    }

    @Transactional
    @Override
    public String getOauthAuthorizationUrl(OauthLoginRequest oauthLoginRequest) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String scopes = String.join("+", oauthProperties.getRegistration().getLinkedin().getScope());

        return oauth2Helper.getUrl(baseUrl, oauthProperties.getProvider().getLinkedin().getAuthorizationUri(),
                oauthProperties.getRegistration().getLinkedin().getAuthorizationGrantType(),
                oauthProperties.getRegistration().getLinkedin().getClientId(),
                oauthProperties.getRegistration().getLinkedin().getRedirectUri(),
                scopes,
                oauthLoginRequest.getSearchTerm());
    }
}