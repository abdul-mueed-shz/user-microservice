package com.abdul.admin.domain.user.usecase;

import com.abdul.toolkit.utils.user.model.UserInfo;
import com.abdul.admin.domain.user.port.in.GetUserUseCase;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {

    private final UserRepository userRepository;

    @Override
    public List<UserInfo> findAll() {
        return userRepository.findAll();
    }
}
