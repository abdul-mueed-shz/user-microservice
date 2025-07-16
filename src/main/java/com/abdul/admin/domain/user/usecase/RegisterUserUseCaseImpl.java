package com.abdul.admin.domain.user.usecase;

import com.abdul.toolkit.utils.user.model.UserInfo;
import com.abdul.admin.domain.user.model.UserRegistrationRequestInfo;
import com.abdul.admin.domain.user.port.in.RegisterUserUseCase;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepository userRepository;

    @Override
    public UserInfo execute(UserRegistrationRequestInfo userRegistrationRequestInfo) {
        if ((userRegistrationRequestInfo.getEmail() == null || userRegistrationRequestInfo.getPassword() == null)
                && userRegistrationRequestInfo.getTwitterUser() == null
                && userRegistrationRequestInfo.getGoogleUser() == null
                && userRegistrationRequestInfo.getGithubUser() == null
                && userRegistrationRequestInfo.getLinkedinUser() == null
        ) {
            // Throw new Application Exception.
            return null;
        }
        return userRepository.save(userRegistrationRequestInfo);
    }
}
