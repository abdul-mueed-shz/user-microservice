package com.abdul.admin.domain.user.mapper;

import com.abdul.admin.domain.github.model.GithubUserResponse;
import com.abdul.admin.domain.google.model.GoogleUserResponse;
import com.abdul.admin.domain.twitter.model.TwitterAccessTokenResponse;
import com.abdul.admin.domain.twitter.model.TwitterUserResponse;
import com.abdul.admin.domain.user.model.AccessToken;
import com.abdul.toolkit.utils.linkedin.model.LinkedinUserResponse;
import com.abdul.toolkit.utils.user.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "githubUserResponse.login", target = "username")
    @Mapping(source = "githubUserResponse.name", target = "firstName")
    @Mapping(source = "githubUserResponse.avatarUrl", target = "githubUser.avatarUrl")
    @Mapping(source = "githubUserResponse.htmlUrl", target = "githubUser.htmlUrl")
    @Mapping(source = "githubUserResponse.hireable", target = "githubUser.hireable")
    @Mapping(source = "githubUserResponse.bio", target = "githubUser.bio")
    @Mapping(source = "githubUserResponse.company", target = "githubUser.company")
    @Mapping(source = "githubUserResponse.blog", target = "githubUser.blog")
    @Mapping(source = "accessToken.token", target = "githubUser.accessToken")
    UserInfo map(@MappingTarget UserInfo userInfo, GithubUserResponse githubUserResponse, AccessToken accessToken);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "googleUserResponse.name", target = "userInfo.username")
    @Mapping(source = "googleUserResponse.familyName", target = "userInfo.lastName")
    @Mapping(source = "googleUserResponse.givenName", target = "userInfo.firstName")
    @Mapping(source = "googleUserResponse.verifiedEmail", target = "userInfo.emailVerified")
    @Mapping(source = "googleUserResponse.id", target = "userInfo.googleUser.googleId")
    @Mapping(source = "googleUserResponse.picture", target = "userInfo.googleUser.picture")
    @Mapping(source = "authUserId", target = "userInfo.googleUser.authUserId")
//    @Mapping(target = "userInfo.googleUser.user", ignore = true)
    UserInfo map(@MappingTarget UserInfo userInfo, GoogleUserResponse googleUserResponse, String authUserId);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "state", target = "userInfo.twitterUser.state")
    @Mapping(source = "authCode", target = "userInfo.twitterUser.usedAuthCode")
    @Mapping(source = "twitterAccessTokenResponse.accessToken", target = "userInfo.twitterUser.accessToken")
    @Mapping(source = "twitterAccessTokenResponse.expiresIn", target = "userInfo.twitterUser.expiresIn")
    @Mapping(source = "twitterAccessTokenResponse.scope", target = "userInfo.twitterUser.tokenScope")
    @Mapping(source = "twitterAccessTokenResponse.tokenType", target = "userInfo.twitterUser.tokenType")
    @Mapping(source = "twitterAccessTokenResponse.refreshToken", target = "userInfo.twitterUser.refreshToken")
    @Mapping(source = "twitterUserResponse.profileImageUrl", target = "userInfo.twitterUser.picture")
    @Mapping(source = "twitterUserResponse.name", target = "firstName")
    @Mapping(source = "twitterUserResponse.username", target = "username")
    UserInfo map(@MappingTarget UserInfo userInfo, TwitterAccessTokenResponse twitterAccessTokenResponse,
            TwitterUserResponse twitterUserResponse, String authCode, String state);

    @Mapping(source = "state", target = "userInfo.linkedinUser.state")
    @Mapping(source = "authCode", target = "userInfo.linkedinUser.usedAuthCode")
    @Mapping(source = "accessToken.token", target = "userInfo.linkedinUser.accessToken")
    @Mapping(source = "accessToken.idToken", target = "userInfo.linkedinUser.idToken")
    @Mapping(source = "accessToken.expiresIn", target = "userInfo.linkedinUser.expiresIn")
    @Mapping(source = "accessToken.scope", target = "userInfo.linkedinUser.tokenScope")
    @Mapping(source = "accessToken.tokenType", target = "userInfo.linkedinUser.tokenType")
    @Mapping(source = "linkedinUserResponse.picture", target = "userInfo.linkedinUser.picture")
    @Mapping(source = "linkedinUserResponse.name", target = "userInfo.username")
    @Mapping(source = "linkedinUserResponse.familyName", target = "userInfo.lastName")
    @Mapping(source = "linkedinUserResponse.givenName", target = "userInfo.firstName")
    @Mapping(target = "id", ignore = true)
    UserInfo map(@MappingTarget UserInfo userInfo, LinkedinUserResponse linkedinUserResponse,
            com.abdul.toolkit.utils.model.AccessToken accessToken,
            String state, String authCode);
}
