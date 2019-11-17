package com.jc17.select.serverCore.resources.userStateUpdate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.jc17.select.instances.User_table;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/sys/config")
@Produces(MediaType.APPLICATION_JSON)
public class UserStateUpdateResource {
    public UserStateUpdateResource() {
    }

    @PUT
    public ReturnObject getTest(@QueryParam("userid") Optional userid,@QueryParam("account") Optional account,
                                @QueryParam("passworld") Optional passworld,
                                @QueryParam("usertype") Optional usertype, @Auth SysUser user) {
//                    @QueryParam("usertype") Optional usertype) {
        ReturnObject returnObj = new ReturnObject();
        if (!user.isAdmin()) {
            returnObj.setError_code(1);
            returnObj.setData("非法请求");
            return returnObj;
        }
        try {
            List<Object> result = new ArrayList<>();
            User_table users = new User_tableDao().get_User_Table_By_Id(userid.get().toString());
//            List<User_table> users = new User_tableDao().get_User_Table("");
            users.setUser_account(account.get().toString());
            users.setPassword(passworld.get().toString());
            users.setRights(Integer.parseInt(usertype.get().toString()));
            new User_tableDao().update_UserTable(users);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("state", 1);
            result.add(map);
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
