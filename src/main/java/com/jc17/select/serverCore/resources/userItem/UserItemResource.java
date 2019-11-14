package com.jc17.select.serverCore.resources.userItem;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import com.jc17.select.dao.User_table;
import com.jc17.select.dao.User_tableDao;

import java.util.ArrayList;
import java.util.List;

@Path("/api/admin/userList")
@Produces(MediaType.APPLICATION_JSON)
public class UserItemResource {
    public UserItemResource(){ }

    @GET
    public ReturnObject getTest(@Auth SysUser user) {
        List<User_table> users = new ArrayList<User_table>();
        User_tableDao dao = new User_tableDao();
        users = dao.get_User_Table("user_account='kkk'");
        ReturnObject returnObj  =  new ReturnObject();
        returnObj.setData(users);
        returnObj.setError_code(0);
        return returnObj;
    }
}