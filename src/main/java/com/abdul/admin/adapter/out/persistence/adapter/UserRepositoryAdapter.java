package com.abdul.admin.adapter.out.persistence.adapter;

import com.abdul.admin.adapter.out.persistence.entity.User;
import com.abdul.admin.adapter.out.persistence.mapper.UserMapper;
import com.abdul.admin.adapter.out.persistence.repository.RoleJpaRepository;
import com.abdul.admin.adapter.out.persistence.repository.UserJpaRepository;
import com.abdul.admin.domain.user.model.UserRegistrationRequestInfo;
import com.abdul.admin.domain.user.port.out.repository.UserRepository;
import com.abdul.toolkit.utils.user.model.UserInfo;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;
    private final RoleJpaRepository roleJpaRepository;

    @Override
    public UserInfo save(UserRegistrationRequestInfo userRegistrationRequestInfo) {
        roleJpaRepository.findAll();
        User user = userJpaRepository.save(userMapper.mapRegistrationInfoToUser(userRegistrationRequestInfo));
        return userMapper.map(user);
    }


    @Override
    public UserInfo updateUser(UserInfo userInfo) {
        User user = userJpaRepository.save(userMapper.mapUserInfoToUser(userInfo));
        return userMapper.map(user);
    }

    @Override
    public List<UserInfo> findAll() {
        return userMapper.map(userJpaRepository.findAll());
    }

    @Override
    public UserInfo findByUsernameOrEmail(String searchTerm) {
        Optional<User> userOptional = userJpaRepository.findUserByUsernameOrEmail(searchTerm);
        return userOptional.map(userMapper::map).orElse(null);
    }

    @Override
    public UserInfo findByUserLinkedinState(String state) {
        Optional<User> userOptional = userJpaRepository.findUserByLinkedinUser_State(state);
        return userOptional.map(userMapper::map).orElse(null);
    }

    @Override
    public UserInfo findByUserTwitterState(String state) {
        Optional<User> userOptional = userJpaRepository.findUserByTwitterUser_State(state);
        return userOptional.map(userMapper::map).orElse(null);
    }

    @Override
    public UserInfo findByGoogleAuthUser(String googleAuthUser) {
        Optional<User> userOptional = userJpaRepository.findUserByGoogleUser_AuthUserId(googleAuthUser);
        return userOptional.map(userMapper::map).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String searchTerm) throws UsernameNotFoundException {
        // !BUG: Ensure only users with unique usernames/emails exist in the system
        return userJpaRepository.findUserByUsernameOrEmail(searchTerm)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + searchTerm));
    }
}
