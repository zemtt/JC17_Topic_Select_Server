package com.jc17.select.serverCore.resources.UserList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.jc17.select.dao.User_table;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import io.dropwizard.auth.Auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Path("/api/admin/userList")
@Produces(MediaType.APPLICATION_JSON)
public class UserListResource {
    public UserListResource() {
    }

    @GET
    public ReturnObject getTest(@QueryParam("num") Optional num, @QueryParam("skip") Optional skip, @Auth SysUser user) {
        ReturnObject returnObj = new ReturnObject();
        if(!user.isAdmin()){
            returnObj.setError_code(1);
            returnObj.setData("非法请求");
            return returnObj;
        }
        try {
            List<Object> result = new ArrayList<>();
            List<User_table> users = new User_tableDao().get_User_Table_Row(num.get().toString(),skip.get().toString());
//            List<User_table> users = new User_tableDao().get_User_Table("");
            for(User_table a: users){
                Map<String, Object> t = new HashMap<>();
                t.put("userid",a.getUser_id());
                t.put("username", a.getUser_account());
                t.put("passworld", a.getPassword());
                t.put("usertype", a.getRights()==0?"admin":(a.getRights()==1?"teacher":"student"));
                result.add(t);
            }
            returnObj.setError_code(0);
            returnObj.setData(result);
        } catch (Exception e) {
            e.printStackTrace();
            returnObj.setError_code(5);
            returnObj.setData("参数不完整");
        }
        return returnObj;
    }
}
