package com.jc17.select.serverCore.userAuth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

public class UserAuthenticator implements Authenticator<BasicCredentials, SysUser> {
    @Override
    public Optional<SysUser> authenticate(BasicCredentials credentials) throws AuthenticationException {
        SysUser user = new SysUser(credentials.getUsername(), credentials.getPassword());
        if (user.isChecked()){
            // 验证成功时跳转到此
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
