package com.abdul.admin.domain.user.port.out.repository;

import com.abdul.toolkit.utils.user.model.UserInfo;
import com.abdul.admin.domain.user.model.UserRegistrationRequestInfo;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserRepository {

    UserInfo save(UserRegistrationRequestInfo sampleInfo);

    UserInfo updateUser(UserInfo userInfo);

    List<UserInfo> findAll();

    UserInfo findByUsernameOrEmail(String searchTerm); // Username or Email for now

    UserInfo findByUserLinkedinState(String state);

    UserInfo findByUserTwitterState(String state);

    UserInfo findByGoogleAuthUser(String authUser);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
