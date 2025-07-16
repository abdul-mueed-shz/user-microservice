package com.abdul.admin.adapter.out.persistence.mapper;


import com.abdul.admin.adapter.out.persistence.entity.User;
import com.abdul.toolkit.utils.user.model.UserInfo;
import com.abdul.admin.domain.user.model.UserRegistrationRequestInfo;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/*
 * note -> Maps model info objects to entity objects and vice versa
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    List<UserInfo> map(List<User> users);

    User mapRegistrationInfoToUser(UserRegistrationRequestInfo userRegistrationRequestInfo);

    User mapUserInfoToUser(UserInfo userInfo);

    //    @Mapping(target = "googleUser.user", ignore = true)
    UserInfo map(User user);

    @AfterMapping
    default void setDefaultFields(@MappingTarget final User.UserBuilder user) {
        user.isActive(Boolean.TRUE);
        user.isSystemLock(Boolean.FALSE);
        user.areCredentialsValid(Boolean.TRUE);
    }
}
