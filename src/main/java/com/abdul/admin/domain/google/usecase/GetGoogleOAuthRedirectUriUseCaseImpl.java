package com.abdul.admin.domain.google.usecase;

import com.abdul.admin.config.OauthProperties;
import com.abdul.admin.domain.google.port.in.GetGoogleOAuthRedirectUriUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GetGoogleOAuthRedirectUriUseCaseImpl implements GetGoogleOAuthRedirectUriUseCase {

    private final OauthProperties oauthProperties;

    public String execute() {
        return oauthProperties.getRegistration().getGoogle().getRedirectUri()
                .replace("{baseUrl}",
                        ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());
    }
}
