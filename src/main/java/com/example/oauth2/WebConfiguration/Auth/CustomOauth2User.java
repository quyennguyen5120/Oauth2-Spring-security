package com.example.oauth2.WebConfiguration.Auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

public class CustomOauth2User implements OAuth2User {

    private OAuth2User oAuth2User;
    private String access_token;
    private String clientMethod;

    public CustomOauth2User(OAuth2User oAuth2User, OAuth2UserRequest userRequest){
        this.clientMethod = userRequest.getClientRegistration().getClientName();
        this.access_token = userRequest.getAccessToken().getTokenValue();
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }

    public String getAccess_token(){
        return access_token;
    }

    public String getClientMethod(){
        return clientMethod;
    }
}
