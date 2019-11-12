package com.jc17.select.serverCore;

import com.jc17.select.serverCore.resources.time.TimeResource;
import com.jc17.select.serverCore.resources.time.TimeZoneConfigure;
import com.jc17.select.serverCore.userAuth.SysUser;
import com.jc17.select.serverCore.userAuth.UserAuthenticator;
import com.jc17.select.serverCore.userAuth.UserAuthorizer;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;


public class MainApplication extends Application<TimeZoneConfigure> {
    public static void main(String[] args) throws Exception {
        new MainApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap timezoneConfigurationBootstrap) {
    }

    @Override
    public void run(TimeZoneConfigure timeZoneConfigure, io.dropwizard.setup.Environment environment) throws Exception {
        environment.jersey().register(new TimeResource(timeZoneConfigure.getDefaultTimezone()));
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<SysUser>()
                        .setAuthenticator(new UserAuthenticator())
                        .setAuthorizer(new UserAuthorizer())
                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(SysUser.class));


    }
}
