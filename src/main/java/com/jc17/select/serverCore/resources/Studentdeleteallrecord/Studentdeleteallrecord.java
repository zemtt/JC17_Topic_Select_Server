package com.jc17.select.serverCore.resources.Studentdeleteallrecord;


import com.jc17.select.dao.SsublogDao;
import com.jc17.select.dao.StudentDao;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.instances.Ssublog;
import com.jc17.select.instances.Student;
import com.jc17.select.instances.User_table;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import java.util.*;

public class Studentdeleteallrecord {


    public  Studentdeleteallrecord(){

    }


    private List<User_table> findUser(String tokenid){

        User_tableDao user_tableDao=new User_tableDao();
        List<User_table> targetuser=new ArrayList<>();
        targetuser =user_tableDao.get_User_Table("USER_ID '"+tokenid+"'");
        return targetuser;
    }

    @DELETE
    public ReturnObject main(@Auth SysUser usertoken){
        ReturnObject returnObject=new ReturnObject();
        String userid=usertoken.getName();

        try{
            List<User_table> findResult= findUser(userid);
            if(findResult.get(0).getRights()==2){//该用户是学生 可以进行下一步操作
                StudentDao studentDao=new StudentDao();
                List<Student> studentList=studentDao.get_Student("USER_ID='"+userid+"'");


                SsublogDao ssublogDao=new SsublogDao();
                List<Ssublog> ssublogList=ssublogDao.get_Ssublog("S_ID='"+studentList.get(0).getS_id()+"'"); //获取该学生的所有选课记录

                for (Ssublog item:ssublogList) {

                    ssublogDao.delete_Ssublog(item.getSsublog_id());

                }


                returnObject.setError_code(0);
                Map<String,Object> dataValue =new HashMap<>();
                dataValue.put("status",1);//默认成功

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
