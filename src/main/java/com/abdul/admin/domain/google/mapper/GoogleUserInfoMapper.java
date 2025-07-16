package com.abdul.admin.domain.google.mapper;

import com.abdul.admin.domain.google.model.GoogleUserResponse;
import com.google.api.services.oauth2.model.Userinfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoogleUserInfoMapper {

    GoogleUserResponse mapToGoogleUserInfo(Userinfo userinfo);
}
