package com.social.test.oauth2.user;

public interface OAuth2UserInfo {

    OAuth2Provider getProvider();
    String getEmail();
    String getName();
    String getNickname();
    String getProviderId();
}


