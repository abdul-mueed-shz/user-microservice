package com.abdul.admin.domain.user.port.in;

import com.abdul.toolkit.utils.user.model.UserInfo;

public interface UpdateUserUseCase {

    UserInfo execute(UserInfo userInfo);
}
