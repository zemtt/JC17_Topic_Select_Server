package com.jc17.select.serverCore.resources.UseItem;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.jc17.select.serverCore.resources.test.Test;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import com.jc17.select.dao.

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserItemResource {
    public UserItemResource(){ }

    @GET
    public Test getTest(@QueryParam("timezone") Optional timezone , @Auth SysUser user) {

        return new Test(user);
    }
}