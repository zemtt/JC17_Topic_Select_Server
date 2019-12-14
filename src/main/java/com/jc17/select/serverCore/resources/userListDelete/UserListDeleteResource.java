package com.jc17.select.serverCore.resources.userListDelete;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/api/admin/userList")
@Produces(MediaType.APPLICATION_JSON)
public class UserListDeleteResource {
    public UserListDeleteResource() {
    }

    //
    @DELETE
    public ReturnObject getTest(@QueryParam("userid") Optional userid, @Auth SysUser user) {
        ReturnObject returnObj = new ReturnObject();
        if (!user.isAdmin()) {
            returnObj.setError_code(1);
            returnObj.setData("非法请求");
            return returnObj;
        }
        try {
            List<Object> result = new ArrayList<>();
            new User_tableDao().delete_UserTable(userid.get().toString());
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
