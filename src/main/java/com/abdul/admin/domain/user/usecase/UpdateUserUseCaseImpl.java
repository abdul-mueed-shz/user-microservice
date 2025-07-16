package com.abdul.admin.domain.user.usecase;

import com.abdul.admin.domain.user.port.in.UpdateUserUseCase;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import com.abdul.toolkit.utils.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepository userRepository;

    @Override
    public UserInfo execute(UserInfo userInfo) {
        return userRepository.updateUser(userInfo);
    }
}
