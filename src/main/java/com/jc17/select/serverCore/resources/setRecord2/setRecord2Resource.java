package com.jc17.select.serverCore.resources.setRecord2;


import com.jc17.select.dao.SsublogDao;
import com.jc17.select.dao.StudentDao;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.instances.Ssublog;
import com.jc17.select.instances.Student;
import com.jc17.select.instances.User_table;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/api/student/setRecord2")
@Produces(MediaType.APPLICATION_JSON)
public class setRecord2Resource {
    public setRecord2Resource(){}

    private List<User_table> findUser(String userid) {
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='" + userid + "'");
        return result;
    }

    @POST
    public ReturnObject setContent(@QueryParam("selectRecord_id")Optional selectRecord_id,@QueryParam("student_content")Optional student_content,
                                   @QueryParam("file_url")Optional file_url,@Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 2)//学生 权限
            {
                SsublogDao ssublogDao = new SsublogDao();
                List<Ssublog> ssublogList = ssublogDao.get_Ssublog("SSUBLOG_ID='"+selectRecord_id.get().toString()+"'");
                Ssublog ssublog = ssublogList.get(0);
                ssublog.setFile_url(file_url.get().toString());
                ssublog.setStu_content(student_content.get().toString());
                ssublogDao.update_Ssublog(ssublog);

                Map<String,Object> t = new HashMap<>();
                t.put("state","1");
                returnObject.setError_code(0);
                returnObject.setData(t);
            }
            else{
                returnObject.setError_code(1);
                returnObject.setData("非法请求");
                return returnObject;
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setError_code(5);
            returnObject.setData("报错");
        }
        return returnObject;
    }
}
