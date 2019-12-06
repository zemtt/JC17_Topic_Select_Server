package com.jc17.select.serverCore.resources.StudentGetselectrecord;



import com.jc17.select.dao.*;
import com.jc17.select.instances.*;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
@Path("/api/student/selectRecord")
@Produces(MediaType.APPLICATION_JSON)


public class StudentGetselectrecord{
    public StudentGetselectrecord(){

    }


    private List<User_table> findUser(String tokenid){

        User_tableDao user_tableDao=new User_tableDao();
        List<User_table> targetuser=new ArrayList<>();
        targetuser =user_tableDao.get_User_Table("USER_ID '"+tokenid+"'");
        return targetuser;
    }

    @GET
    public ReturnObject main(@Auth SysUser usertoken,@QueryParam("selectRecord_id")Optional selectRecord_id){
        ReturnObject returnObject=new ReturnObject();
        String userid=usertoken.getName();

        try{
            List<User_table> findResult= findUser(userid);
            if(findResult.get(0).getRights()==2){//该用户是学生 可以进行下一步操作
                StudentDao studentDao=new StudentDao();
                List<Student> studentList=studentDao.get_Student("USER_ID='"+userid+"'");

                SsublogDao ssublogDao=new SsublogDao();
                List<Ssublog>  ssublogList=ssublogDao.get_Ssublog("SSUBLOG_ID='"+selectRecord_id+"'");
                returnObject.setError_code(0);

                Map<String, Object> dataValue = new HashMap<>();

                dataValue.put("subject_id",ssublogList.get(0).getSub_id());
                dataValue.put("priority",ssublogList.get(0).getPrio());
                //datavalue.put("student_content",);  无学生简介
                dataValue.put("status",ssublogList.get(0).getSchs());//是否被选中

                returnObject.setData(dataValue);




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
