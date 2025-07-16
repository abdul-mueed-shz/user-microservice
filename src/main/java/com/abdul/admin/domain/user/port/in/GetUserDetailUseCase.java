package com.abdul.admin.domain.user.port.in;

import com.abdul.toolkit.utils.user.model.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface GetUserDetailUseCase extends UserDetailsService {

    UserInfo get(String searchTerm);
}
