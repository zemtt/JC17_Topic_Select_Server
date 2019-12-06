package com.jc17.select.serverCore.resources.report;


import com.jc17.select.dao.*;
import com.jc17.select.instances.Report;
import com.jc17.select.instances.Student;
import com.jc17.select.instances.Subject;
import com.jc17.select.instances.User_table;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/api/teacher/report")
@Produces(MediaType.APPLICATION_JSON)
public class reportResource {
    public reportResource(){}

    private List<User_table> findUser(String userid) {
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='" + userid + "'");
        return result;
    }

    @GET
    public ReturnObject getReport(@QueryParam("report_id")Optional report_id, @Auth SysUser usertoken) {
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 1)//教师权限
            {
                ReportDao reportDao = new ReportDao();
                StudentDao studentDao = new StudentDao();
                MajorDao majorDao  = new MajorDao();
                SubjectDao subjectDao = new SubjectDao();
                Report report = reportDao.get_Report_By_Id(report_id.get().toString());
                Map<String,Object> t = new HashMap<>();
                t.put("studentname", studentDao.get_Student_By_Id(report.getS_id()).getSn());
                t.put("studentnumber",report.getS_id());
                t.put("studentmajor",majorDao.get_Major_By_Id(studentDao.get_Student_By_Id(report.getS_id()).getMajor_id()).getMajor_name());
                t.put("subjectname",subjectDao.get_Subject_By_Id(report.getSub_id()).getSubno());
                t.put("report_type",report.getRepattribute());
                if(report.getMarked()==1)//已评阅
                    t.put("report_status","已评阅");
                else
                    t.put("report_status","未评阅");
                t.put("report_content",report.getContent());

                returnObject.setData(t);
                returnObject.setError_code(0);
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

    @PUT
    public ReturnObject updateScore(@QueryParam("report_id")Optional report_id,@QueryParam("score")Optional score, @Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 1)//教师权限
            {
                //get之前的report
                ReportDao reportDao = new ReportDao();
                Report report = reportDao.get_Report_By_Id(report_id.get().toString());
                report.setScore(Float.parseFloat(score.get().toString()));
                reportDao.update_Report(report);
                Map<String,Object>t = new HashMap<>();
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
