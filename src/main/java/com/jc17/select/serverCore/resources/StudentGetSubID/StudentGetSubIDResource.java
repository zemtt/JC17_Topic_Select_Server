package com.jc17.select.serverCore.resources.StudentGetSubID;


import com.jc17.select.dao.*;
import com.jc17.select.instances.Report;
import com.jc17.select.instances.Student;
import com.jc17.select.instances.Subject;
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

@Path("/api/student/search")
@Produces(MediaType.APPLICATION_JSON)


//获取课程id 通过课程名称
public class StudentGetSubIDResource {
    public StudentGetSubIDResource(){}


    private List<User_table> findUser(String tokenid){

        User_tableDao user_tableDao=new User_tableDao();
        List<User_table> targetuser=new ArrayList<>();
        targetuser =user_tableDao.get_User_Table("USER_ID '"+tokenid+"'");
        return targetuser;
    }
    @GET
    public ReturnObject main(@Auth SysUser usertoken, @QueryParam("string_for_search")Optional subjectname){
        ReturnObject returnObject=new ReturnObject();
        String userid=usertoken.getName();

        try{
            List<User_table> findResult= findUser(userid);
            if(findResult.get(0).getRights()==2){//该用户是学生 可以进行下一步操作



                SubjectDao subjectDao=new SubjectDao();
                List<Subject>  subjectList=subjectDao.get_Subject("SUB_N='"+subjectname+"'");

                returnObject.setError_code(0);
                List<Object> data=new ArrayList<>();

                Map<String,Object> datavalue=new HashMap<>();
                datavalue.put("subject_id",subjectList.get(0).getSub_id());
                returnObject.setData(datavalue);




            }
            else{
                returnObject.setError_code(22);  //对应用户不是学生
                returnObject.setData("用户不是学生");
            }


        }
        catch (Exception e){
            e.printStackTrace();
            returnObject.setError_code(5);
            returnObject.setData("参数不完整");
        }
        return returnObject;

    }








}
