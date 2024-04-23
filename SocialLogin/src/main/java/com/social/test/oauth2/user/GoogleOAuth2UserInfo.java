package com.social.test.oauth2.user;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.GOOGLE;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

}
