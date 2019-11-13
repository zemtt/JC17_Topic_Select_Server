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

import com.jc17.select.dao.User_table;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.dao.GetConn;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserItemResource {
    public UserItemResource(){ }

    @GET
    public String getTest(@Auth SysUser user) {
        User_table user_ = new User_table();
        user_.setUser_id("hdsahudsba");
        user_.setRights(1);
        user_.setPassword("ssss");
        user_.setUser_account(user.getName());
        GetConn conn=new GetConn("jc17","jc172019","topic_select");
        User_tableDao dao = new User_tableDao(conn.GetConnection());
        return "success!";
    }
}