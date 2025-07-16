package com.abdul.admin.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.client")
public class OauthProperties {

    private ProviderProperties provider = new ProviderProperties();
    private RegistrationProperties registration = new RegistrationProperties();

    @Data
    public static class ProviderProperties {

        private GoogleProviderProperties google = new GoogleProviderProperties();
        private LinkedinProviderProperties linkedin = new LinkedinProviderProperties();
        private GithubProviderProperties github = new GithubProviderProperties();

        @Data
        public static class GithubProviderProperties {

            private String authorizationUri;
            private String tokenUri;
            private String userInfoUri;
            private String emailsUri;
        }

        @Data
        public static class GoogleProviderProperties {

            private String authorizationUri;
            private String tokenUri;
            private String userInfoUri;
        }

        @Data
        public static class LinkedinProviderProperties {

            private String authorizationUri;
            private String tokenUri;
            private String userInfoUri;
        }
    }

    @Data
    public static class RegistrationProperties {

        private GoogleRegistrationProperties google = new GoogleRegistrationProperties();
        private LinkedinRegistrationProperties linkedin = new LinkedinRegistrationProperties();
        private XRegistrationProperties x = new XRegistrationProperties();
        private GithubRegistrationProperties github = new GithubRegistrationProperties();

        @Data
        public static class GithubRegistrationProperties {

            private String clientId;
            private String clientSecret;
            private List<String> scope;
            private String redirectUri;
            private String authorizationGrantType;
            private String tokenGrantTypeParam;
        }

        @Data
        public static class XRegistrationProperties {

            private String clientId;
            private String clientSecret;
            private String scope;
            private String redirectUri;
            private String authorizationGrantType;
            private String tokenGrantTypeParam;
            private String accessToken;
            private String bearerToken;
            private String accessTokenSecret;
            private String pkceCodeVerifier;
            private String pkceCodeChallenge;
        }

        @Data
        public static class GoogleRegistrationProperties {

            private String clientId;
            private String clientSecret;
            private List<String> scope;
            private String redirectUri;
            private String accessType;
            private String authorizationGrantType;
        }

        @Data
        public static class LinkedinRegistrationProperties {

            private String clientId;
            private String clientSecret;
            private List<String> scope;
            private String redirectUri;
            private String authorizationGrantType;
            private String tokenGrantTypeParam;
        }
    }
}