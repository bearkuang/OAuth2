package com.social.test.oauth2.user;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.KAKAO;
    }

    @Override
    public String getEmail() {
        // kakao_account라는 Map에서 추출
        return (String) ((Map) attributes.get("kakao_account")).get("email");
    }

    @Override
    public String getName() {
        // kakao_account라는 Map에서 추출
        return (String) ((Map) attributes.get("properties")).get("nickname");
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

}
