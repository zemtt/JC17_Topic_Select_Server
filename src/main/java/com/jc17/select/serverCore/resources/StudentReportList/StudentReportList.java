package com.jc17.select.serverCore.resources.StudentReportList;

import com.jc17.select.dao.*;
import com.jc17.select.instances.Report;
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

@Path("/api/student/processRecordList")
@Produces(MediaType.APPLICATION_JSON)
//获取过程报告list


//针对这个业务请求   已知userid  通过STUDNET表找到studentid   然后通过REPORT表找到学生id所对应得四个报告的元组
//小问题  这个业务返回四个报告是不是需要再加上课程名？  就是这个报告是这个学生得哪一门课得报告 虽然说是一对一得关系
public class StudentReportList {
    public StudentReportList(){}

    private List<User_table> findUser(String tokenid){

        User_tableDao user_tableDao=new User_tableDao();
        List<User_table> targetuser=new ArrayList<>();
        targetuser =user_tableDao.get_User_Table("USER_ID '"+tokenid+"'");
        return targetuser;
    }

    @GET
    public ReturnObject main(@Auth SysUser usertoken){
        ReturnObject returnObject=new ReturnObject();
        String userid=usertoken.getName();

        try{
            List<User_table> findResult= findUser(userid);
            if(findResult.get(0).getRights()==2){//该用户是学生 可以进行下一步操作
                StudentDao studentDao=new StudentDao();
                List<Student> studentList=studentDao.get_Student("USER_ID='"+userid+"'");

                ReportDao reportDao=new ReportDao();
                List<Report> reportList=reportDao.get_Report("S_ID='"+studentList.get(0).getS_id()+"'");//四个report元组

                returnObject.setError_code(0);
                List<Object> data=new ArrayList<>();

                for (Report item:reportList) {
                    Map<String,Object> datavalue =new HashMap<>();
                    datavalue.put("record_id",item.getReport_id());
                    datavalue.put("record_type",item.getRepattribute());
                    //dao中没有status的函数
                    //datavalue.put("record_status",item.get);
                    datavalue.put("record_score",item.getScore());
                    data.add(datavalue);    //装入结果链表

                }


                returnObject.setData(data);//装入一个链表

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
