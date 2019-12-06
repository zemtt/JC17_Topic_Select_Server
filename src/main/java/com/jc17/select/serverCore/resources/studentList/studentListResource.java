package com.jc17.select.serverCore.resources.studentList;


import com.jc17.select.dao.MajorDao;
import com.jc17.select.dao.SelectDao;
import com.jc17.select.dao.StudentDao;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.instances.Select;
import com.jc17.select.instances.Student;
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

@Path("/api/teacher/studentList")
@Produces(MediaType.APPLICATION_JSON)
public class studentListResource {
    public studentListResource(){}

    private List<User_table> findUser(String userid){
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='"+userid+"'");
        return result;
    }
    @GET
    public ReturnObject getStudentList(@QueryParam("subject_id")Optional subject_id, @Auth SysUser usertoken) {
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try{
            List<User_table> users = findUser(userid);
            if(users.get(0).getRights()==1)//教师权限
            {
                SelectDao selectDao  = new SelectDao();
                List<Select> selectList = selectDao.get_Select("SUB_ID='" + subject_id.get().toString() + "'");
                List<Object> result = new ArrayList<>();

                for(Select i:selectList){
                    StudentDao studentDao = new StudentDao();
                    Student student = studentDao.get_Student_By_Id(i.getS_id());
                    Map<String,Object> t=new HashMap<>();
                    MajorDao majorDao = new MajorDao();
                    String major = majorDao.get_Major_By_Id(student.getMajor_id()).getMajor_name();
                    t.put("studentname",student.getSn());
                    t.put("student_id",student.getS_id());
                    t.put("studentmajor",major);
                    t.put("studentnumber",student.getSno());
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
