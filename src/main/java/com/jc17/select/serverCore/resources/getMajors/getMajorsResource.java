package com.jc17.select.serverCore.resources.getMajors;

import com.jc17.select.dao.MajorDao;
import com.jc17.select.dao.SubjectDao;
import com.jc17.select.dao.SubmajDao;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.instances.Major;
import com.jc17.select.instances.Submaj;
import com.jc17.select.instances.User_table;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/api/teacher/getMajors")
@Produces(MediaType.APPLICATION_JSON)
public class getMajorsResource {

    public getMajorsResource(){}

    private List<User_table> findUser(String userid){
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='"+userid+"'");
        return result;
    }

    @GET
    public ReturnObject getMajors(@QueryParam("subject_id") Optional subject_id, @Auth SysUser usertoken){

        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try{
            List<User_table> users = findUser(userid);
            if(users.get(0).getRights()==1)//教师权限
            {
                SubmajDao submajDao = new SubmajDao();
                List<Submaj> submajList = submajDao.get_Submaj("SUB_ID='"+subject_id.get().toString()+"'");
                List<Object> result = new ArrayList<>();
                MajorDao majorDao = new MajorDao();
                for(Submaj i:submajList){
                    Major major = majorDao.get_Major_By_Id(i.getMajor_id());

                    Map<String,Object> t = new HashMap<>();
                    t.put("major_id", major.getMajor_id());
                    t.put("major_name",major.getMajor_name());
                    result.add(t);
                }
                returnObject.setError_code(0);
                returnObject.setData(result);
            }
            else
            {
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
