package com.abdul.admin.domain.twitter.mapper;

import com.abdul.admin.domain.twitter.model.TwitterAccessTokenResponse;
import com.abdul.admin.domain.twitter.model.TwitterUserResponse;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.twitter.clientlib.model.Get2UsersMeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TwitterInfoMapper {

    @Mapping(source = "data.id", target = "id")
    @Mapping(source = "data.name", target = "name")
    @Mapping(source = "data.username", target = "username")
    @Mapping(source = "data.profileImageUrl", target = "profileImageUrl")
    @Mapping(source = "data.verified", target = "verified")
    TwitterUserResponse map(Get2UsersMeResponse get2UsersMeResponse);

    TwitterAccessTokenResponse map(OAuth2AccessToken oAuth2AccessToken);
}
