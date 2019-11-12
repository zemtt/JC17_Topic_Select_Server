package com.jc17.select.serverCore.userAuth;

import io.dropwizard.auth.Authorizer;

public class UserAuthorizer implements Authorizer<SysUser> {
    @Override
    public boolean authorize(SysUser user, String role) {
        return user.getName().equals("good-guy") && role.equals("ADMIN");
    }
}