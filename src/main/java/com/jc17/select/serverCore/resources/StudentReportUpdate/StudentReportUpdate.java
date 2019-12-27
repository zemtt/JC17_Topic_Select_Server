package com.jc17.select.serverCore.resources.StudentReportUpdate;


import com.jc17.select.dao.*;
import com.jc17.select.instances.Report;
import com.jc17.select.instances.Student;
import com.jc17.select.instances.User_table;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;



@Path("/api/student/processRecord")
@Produces(MediaType.APPLICATION_JSON)


public class StudentReportUpdate {

    public StudentReportUpdate(){

    }

    //获取账号

    private List<User_table> findkUser(String tokenid){

        User_tableDao dao = new User_tableDao();
        List<User_table> result = new ArrayList<>();
        result = dao.get_User_Table("USER_ID='"+tokenid+"'");
        return result;   //找到目标用户
    }

    //获取报告
    private List<Report> findReport(String reportid)
    {
        ReportDao reportDao=new ReportDao();
        List<Report> result=new ArrayList<>();
        Report result1 = reportDao.get_Report_By_Id(reportid);
        result.add(result1);
        return result;
    }

    @PUT
    public ReturnObject main(@Auth SysUser usertoken,@QueryParam("REPORT_ID") Optional reportid,@QueryParam("CONTENT") Optional reportcontent){
        ReturnObject returnObject=new ReturnObject();   //要返回的对象
        String userid = usertoken.getName();


        try{
            List<User_table> findResult = findkUser(userid);

            if(findResult.get(0).getRights()==2){ //该用户是学生 可以进行下一步操作
                //现在要通过userid去找studentid 利用Student表
                StudentDao studentDao=new StudentDao();
                List<Student> studentList= studentDao.get_Student("USER_ID='"+userid+"'");

                String studnetid=studentList.get(0).getS_id();   //得到学号


                //通过studentid 找课程id  Report表


                ReportDao reportDao=new ReportDao();
                List<Report> reportList=reportDao.get_Report("S_ID='"+studnetid+"'");  //利用SQL语言查找
                //其实可以直接用获取的参数

                reportList.get(0).setContent(reportcontent.toString());

                returnObject.setError_code(0);
                Map<String, Object> dataValue = new HashMap<>();
                dataValue.put("state",1);
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
