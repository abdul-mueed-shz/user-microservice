package com.abdul.admin.adapter.out.persistence.repository;

import com.abdul.admin.adapter.out.persistence.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.username = :searchTerm OR user.email = :searchTerm")
    Optional<User> findUserByUsernameOrEmail(String searchTerm);

    Optional<User> findUserByLinkedinUser_State(String state);

    Optional<User> findUserByTwitterUser_State(String state);

    Optional<User> findUserByGoogleUser_AuthUserId(String authUserId);
}
