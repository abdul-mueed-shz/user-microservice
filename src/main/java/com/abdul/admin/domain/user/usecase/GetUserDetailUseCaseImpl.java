package com.abdul.admin.domain.user.usecase;

import com.abdul.admin.domain.user.port.in.GetUserDetailUseCase;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import com.abdul.toolkit.utils.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserDetailUseCaseImpl implements GetUserDetailUseCase {

    private final UserRepository userRepository;

    @Override
    public UserInfo get(String searchTerm) {
        return userRepository.findByUsernameOrEmail(searchTerm);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.loadUserByUsername(username);
    }
}
