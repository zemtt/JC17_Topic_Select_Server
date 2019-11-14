package com.jc17.select.serverCore.resources.userLogin;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.jc17.select.dao.User_table;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.resources.utils.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/user/login")
@Produces(MediaType.APPLICATION_JSON)
public class UserLoginResource {
    public UserLoginResource(){ }

    @POST
    public ReturnObject getTest(@QueryParam("username") Optional username
            ,@QueryParam("password") Optional password,@QueryParam("usertype") Optional usertype) {
        ReturnObject returnObj = new ReturnObject();
        try {
            List<User_table> findResult = findkUser(
                    username.get().toString(), password.get().toString(), usertype.get().toString());
            if (!findResult.isEmpty()){
                returnObj.setError_code(0);
                Map<String, Object> dataValue = new HashMap<>();
                dataValue.put("state", 1);
                dataValue.put("token", Token.makeAToken(findResult.get(0).getUser_id()));
                returnObj.setData(dataValue);
            } else {
                returnObj.setError_code(0);
                Map<String, Integer> dataValue = new HashMap<>();
                dataValue.put("state", 0);
                returnObj.setData(dataValue);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            returnObj.setError_code(5);
            returnObj.setData("参数不完整");
        }
        return returnObj;
    }

    private List<User_table> findkUser(String usn, String psw, String tp){
        User_tableDao dao = new User_tableDao();
        List<User_table> result = new ArrayList<>();
        result = dao.get_User_Table("USER_ACCOUNT='"+usn+"' AND "+"PASSWORD= '"+psw+"' AND "+"RIGHTS="+
                (tp.compareTo("admin")==0?"0":(tp.compareTo("teacher")==0?"1":"2")));
        return result;
    }
}
