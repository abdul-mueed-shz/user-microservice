package com.abdul.admin.domain.user.port.in;

import com.abdul.toolkit.utils.user.model.UserInfo;
import com.abdul.admin.domain.user.model.UserRegistrationRequestInfo;

public interface RegisterUserUseCase {

    UserInfo execute(UserRegistrationRequestInfo userRegistrationRequestInfo);
}
