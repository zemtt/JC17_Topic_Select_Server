package com.jc17.select.serverCore.resources.Studentsubjectscore;


import com.jc17.select.dao.*;
import com.jc17.select.instances.Report;
import com.jc17.select.instances.Ssublog;
import com.jc17.select.instances.Student;
import com.jc17.select.instances.User_table;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/student/getScore")
@Produces(MediaType.APPLICATION_JSON)
public class Studentsubjectscore {
    public Studentsubjectscore(){}

    //获取账号
    private List<User_table> findkUser(String tokenid){

        User_tableDao dao = new User_tableDao();
        List<User_table> result = new ArrayList<>();
        result = dao.get_User_Table("USER_ID='"+tokenid+"'");
        return result;   //找到目标用户
    }

    @GET
    public ReturnObject main(@Auth SysUser usertoken){
        ReturnObject returnObject=new ReturnObject();   //要返回的对象
        String userid = usertoken.getName();


        try{
            List<User_table> findResult = findkUser(userid);

            if(findResult.get(0).getRights()==2){ //该用户是学生 可以进行下一步操作
                //现在要通过userid去找xstudentid 利用Student表
                StudentDao studentDao=new StudentDao();
                List<Student> studentList= studentDao.get_Student(userid);


                //通过studentid 找课程id 和得分  分别用到了 ssublog表  和 Report表

                SsublogDao ssublogDao=new SsublogDao();
                List<Ssublog> ssublogList=ssublogDao.get_Ssublog(userid);
                String subjectid=ssublogList.get(0).getSub_id();

                ReportDao reportDao=new ReportDao();
                List<Report> reportList=reportDao.get_Report(userid);  //这个时候得到的是四个报告
                //四个报告取平均分
                float score=(reportList.get(0).getScore()+reportList.get(1).getScore()+reportList.get(2).getScore()+reportList.get(3).getScore())/4;


                returnObject.setError_code(0);
                Map<String, Object> dataValue = new HashMap<>();
                dataValue.put("subject_id",subjectid);
                dataValue.put("score",score);

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
