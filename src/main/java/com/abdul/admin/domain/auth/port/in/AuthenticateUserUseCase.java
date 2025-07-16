package com.abdul.admin.domain.auth.port.in;

import com.abdul.admin.domain.auth.model.AuthenticationInfo;
import com.abdul.admin.domain.auth.model.AuthenticationRequestInfo;
import com.abdul.toolkit.utils.user.model.UserInfo;

public interface AuthenticateUserUseCase {

    AuthenticationInfo authenticate(AuthenticationRequestInfo authenticationRequestInfo);

    AuthenticationInfo authenticate(UserInfo userInfo);
}
