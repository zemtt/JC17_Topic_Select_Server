package com.jc17.select.serverCore;

import com.jc17.select.serverCore.resources.Studentsubjectscore.StudentsubjectscoreResource;
import com.jc17.select.serverCore.resources.SubjectList.SubjectListResource;
import com.jc17.select.serverCore.resources.UserAdd.UserAddResource;
import com.jc17.select.serverCore.resources.UserList.UserListResource;
import com.jc17.select.serverCore.resources.UserListDelete.UserListDeleteResource;
import com.jc17.select.serverCore.resources.UserListUpdate.UserListUpdateResource;
import com.jc17.select.serverCore.resources.userInfo.UserInfoResource;
import com.jc17.select.serverCore.resources.userLogin.UserLoginResource;
import com.jc17.select.serverCore.userAuth.SysUser;
import com.jc17.select.serverCore.userAuth.UserAuthenticator;
import com.jc17.select.serverCore.userAuth.UserAuthorizer;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;


public class MainApplication extends Application<AppConfigure> {
    public static void main(String[] args) throws Exception {
        new MainApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap timezoneConfigurationBootstrap) {
    }

    @Override
    public void run(AppConfigure configure, io.dropwizard.setup.Environment environment) throws Exception {
//         身份验证
//        environment.jersey().register(new AuthDynamicFeature(
//                new BasicCredentialAuthFilter.Builder<SysUser>()
//                        .setAuthenticator(new UserAuthenticator())
//                        .setAuthorizer(new UserAuthorizer())
//                        .setRealm("SUPER SECRET STUFF")
//                        .buildAuthFilter()));
//        environment.jersey().register(RolesAllowedDynamicFeature.class);
//        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(SysUser.class));


        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<SysUser>()
                        .setAuthenticator(new UserAuthenticator())
                        .setAuthorizer(new UserAuthorizer())
                        .setPrefix("Bearer")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(SysUser.class));


        // User资源
//        environment.jersey().register(new UserItemResource());
        environment.jersey().register(new UserListResource());
        environment.jersey().register(new UserInfoResource());
        environment.jersey().register(new UserLoginResource());
        environment.jersey().register(new UserListUpdateResource());
        environment.jersey().register(new UserListDeleteResource());
        environment.jersey().register(new UserAddResource());
        environment.jersey().register(new SubjectListResource());
        environment.jersey().register(new StudentsubjectscoreResource());
    }
}
