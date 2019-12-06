package com.jc17.select.serverCore.resources.StudentGetsubjectList;


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

@Path("/api/student/subjectList")
@Produces(MediaType.APPLICATION_JSON)

        //用于学生用户获取所有的课题
public class StudentGetsubjectList {

    public StudentGetsubjectList(){


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
    @GET
    public ReturnObject main(@Auth SysUser usertoken){
        ReturnObject returnObject=new ReturnObject();   //要返回的对象
        String userid = usertoken.getName();

        try{
            List<User_table> findResult = findkUser(userid);

            if(findResult.get(0).getRights()==2){ //该用户是学生 可以进行下一步操作
                //通过Report表 获取对应报告的所有信息
                SubjectDao subjectDao=new SubjectDao();
                List<Subject> subjectList=subjectDao.get_Subject("");

                returnObject.setError_code(0);

                List<Object> data=new ArrayList<>();

                for (Subject item:subjectList) {
                    Map<String,Object> datavalue =new HashMap<>();
                    datavalue.put("subject_id",item.getSub_id());
                    datavalue.put("subject_name",item.getSubno());
                    datavalue.put("stumax",item.getStumax());
                    datavalue.put("stusurplus",item.getStumax()-item.getStusele());
                    datavalue.put("stuselect",item.getStusele());//当前选择的人数
                    datavalue.put("teacher",item.getT_id());
                    datavalue.put("subject_info",item.getSub_info());
                    datavalue.put("subject_req",item.getSub_requirements());
                    datavalue.put("subject_ass",item.getAssessment());



                    data.add(datavalue);    //装入结果链表
                }


                returnObject.setData(data);//将一个链表jersy

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
