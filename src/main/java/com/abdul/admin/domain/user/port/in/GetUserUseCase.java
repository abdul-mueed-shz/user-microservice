package com.abdul.admin.domain.user.port.in;

import com.abdul.toolkit.utils.user.model.UserInfo;
import java.util.List;

public interface GetUserUseCase {

    List<UserInfo> findAll();
}
