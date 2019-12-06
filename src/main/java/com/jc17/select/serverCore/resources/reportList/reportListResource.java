package com.jc17.select.serverCore.resources.reportList;

import com.jc17.select.dao.*;
import com.jc17.select.instances.Major;
import com.jc17.select.instances.Report;
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

@Path("/api/teacher/reportList")
@Produces(MediaType.APPLICATION_JSON)
public class reportListResource {
    public reportListResource() {
    }

    private List<User_table> findUser(String userid) {
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='" + userid + "'");
        return result;
    }

    @GET
    public ReturnObject getReportList(@QueryParam("subject_id") Optional subject_id, @Auth SysUser usertoken) {
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 1)//教师权限
            {
                ReportDao reportDao = new ReportDao();
                List<Report> reportList = reportDao.get_Report("SUB_ID='"+subject_id.get().toString()+"'");
                List<Object> result = new ArrayList<>();
                for (Report i:reportList){
                    StudentDao studentDao = new StudentDao();
                    MajorDao majorDao = new MajorDao();
                    SubjectDao subjectDao = new SubjectDao();
                    Map<String,Object> t = new HashMap<>();
                    t.put("report_id",i.getReport_id());
                    t.put("studentname",studentDao.get_Student_By_Id(i.getS_id()).getSn());
                    t.put("studentnumber",i.getS_id());
                    t.put("studentmajor",majorDao.get_Major_By_Id(studentDao.get_Student_By_Id(i.getS_id()).getMajor_id()).getMajor_name());
                    t.put("subjectname",subjectDao.get_Subject_By_Id(i.getSub_id()).getSubno());
                    t.put("report_type",i.getRepattribute());
                    if(i.getMarked()==1)//已评阅
                        t.put("report_status","已评阅");
                    else
                        t.put("report_status","未评阅");

                    result.add(t);
                }
                returnObject.setError_code(0);
                returnObject.setData(result);
            }
            else            {
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
