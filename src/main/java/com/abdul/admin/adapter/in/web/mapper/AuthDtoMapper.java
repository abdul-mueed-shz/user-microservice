package com.abdul.admin.adapter.in.web.mapper;

import com.abdul.admin.domain.user.model.OauthLoginRequest;
import com.abdul.dto.AuthenticationInfo;
import com.abdul.dto.Oauth2LoginRequest;
import com.abdul.dto.Oauth2LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthDtoMapper {

    AuthenticationInfo map(com.abdul.admin.domain.auth.model.AuthenticationInfo authenticationInfo);

    Oauth2LoginResponse map(com.abdul.admin.domain.user.model.Oauth2LoginResponse oauth2LoginResponse);

    OauthLoginRequest map(Oauth2LoginRequest oauth2LoginRequest);
}
