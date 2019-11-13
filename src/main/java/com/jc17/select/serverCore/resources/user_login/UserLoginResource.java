package com.jc17.select.serverCore.resources.user_login;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.jc17.select.serverCore.resources.Utils.ReturnObject.ReturnObject;

import java.util.HashMap;
import java.util.Map;

@Path("/user/login")
@Produces(MediaType.APPLICATION_JSON)
public class UserLoginResource {
    public UserLoginResource(){ }

    @GET
    public ReturnObject getTest(@QueryParam("username") Optional username
            ,@QueryParam("password") Optional password,@QueryParam("usertype") Optional usertype) {
        ReturnObject returnObj = new ReturnObject();
        try {
            if (cheackUser(username.get().toString(),password.get().toString(),usertype.get().toString())){
                returnObj.setError_code(0);
                Map<String, Integer> dataValue = new HashMap<String, Integer>();
                dataValue.put("state", 1);
                returnObj.setData(dataValue);
            } else {
                returnObj.setError_code(0);
                Map<String, Integer> dataValue = new HashMap<String, Integer>();
                dataValue.put("state", 0);
                returnObj.setData(dataValue);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            returnObj.setError_code(5);
            Map<String, Integer> dataValue = new HashMap<String, Integer>();
            returnObj.setData("参数不完整");
        }
        return returnObj;
    }

    private boolean cheackUser(String usn, String psw, String tp){
        return psw.compareTo("jc172019")==0;
    }
}
