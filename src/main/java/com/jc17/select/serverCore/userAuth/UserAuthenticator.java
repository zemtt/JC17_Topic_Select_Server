package com.jc17.select.serverCore.userAuth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;

import java.util.Optional;

public class UserAuthenticator implements Authenticator<String, SysUser> {
    @Override
    public Optional<SysUser> authenticate(String credentials) {
        System.out.println(credentials.toString());
        SysUser user = new SysUser(credentials.toString());
        if (user.isChecked()){
            // 验证成功时跳转到此
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
