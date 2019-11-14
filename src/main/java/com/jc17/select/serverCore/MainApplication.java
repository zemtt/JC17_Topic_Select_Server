//package com.jc17.select.serverCore;
//
//import com.jc17.select.serverCore.resources.userItem.UserItemResource;
//import com.jc17.select.serverCore.resources.test.TestResource;
//import com.jc17.select.serverCore.resources.userLogin.UserLoginResource;
//import com.jc17.select.serverCore.userAuth.SysUser;
//import com.jc17.select.serverCore.userAuth.UserAuthenticator;
//import com.jc17.select.serverCore.userAuth.UserAuthorizer;
//import io.dropwizard.Application;
//import io.dropwizard.auth.AuthDynamicFeature;
//import io.dropwizard.auth.AuthValueFactoryProvider;
//import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
//import io.dropwizard.setup.Bootstrap;
//import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
//
//
//public class MainApplication extends Application<AppConfigure> {
//    public static void main(String[] args) throws Exception {
//        new MainApplication().run(args);
//    }
//
//    @Override
//    public void initialize(Bootstrap timezoneConfigurationBootstrap) {
//    }
//
//    @Override
//    public void run(AppConfigure configure, io.dropwizard.setup.Environment environment) throws Exception {
//        // 身份验证
//        environment.jersey().register(new AuthDynamicFeature(
//                new BasicCredentialAuthFilter.Builder<SysUser>()
//                        .setAuthenticator(new UserAuthenticator())
//                        .setAuthorizer(new UserAuthorizer())
//                        .setRealm("SUPER SECRET STUFF")
//                        .buildAuthFilter()));
//        environment.jersey().register(RolesAllowedDynamicFeature.class);
//        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(SysUser.class));
//
//        // 时间资源
//        environment.jersey().register(new TestResource());
//
//        // User资源
//        environment.jersey().register(new UserItemResource());
//
//        environment.jersey().register(new UserLoginResource());
//
//    }
//}
