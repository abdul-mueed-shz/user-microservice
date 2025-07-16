package com.abdul.admin.domain.google.port.in;

import com.abdul.admin.domain.google.model.GoogleOauthRedirectInfo;
import com.abdul.admin.domain.user.model.Oauth2LoginResponse;
import java.io.IOException;

public interface HandleGoogleOAuthRedirectUseCase {

    Oauth2LoginResponse execute(GoogleOauthRedirectInfo googleOauthRedirectInfo) throws IOException;
}
