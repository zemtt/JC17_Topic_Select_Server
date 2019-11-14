package com.jc17.select.serverCore.resources.userInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

@Path("/api/user/info")
@Produces(MediaType.APPLICATION_JSON)
public class UserInfoResource {
    public UserInfoResource(){ }

    @GET
    public ReturnObject getTest(@Auth SysUser user) {
        ReturnObject returnObj  =  new ReturnObject();
        returnObj.setData(user.getName());
        returnObj.setError_code(0);
        return returnObj;
    }
}