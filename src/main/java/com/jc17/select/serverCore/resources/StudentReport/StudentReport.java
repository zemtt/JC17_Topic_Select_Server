package com.jc17.select.serverCore.resources.StudentReport;

import com.jc17.select.dao.*;
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


@Path("/api//student/processRecord")
@Produces(MediaType.APPLICATION_JSON)

//获取过程报告
//针对这个业务请求   我们请求参数record_id 在Report表中 得到record type\status\content
public class StudentReport {

    public StudentReport(){}
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
    public ReturnObject main(@Auth SysUser usertoken,@QueryParam("reportid") Optional reportid){
        ReturnObject returnObject=new ReturnObject();   //要返回的对象
        String userid = usertoken.getName();

        try{
            List<User_table> findResult = findkUser(userid);

            if(findResult.get(0).getRights()==2){ //该用户是学生 可以进行下一步操作
                //通过Report表 获取对应报告的所有信息

                ReportDao reportDao=new ReportDao();
                List<Report> reportList=reportDao.get_Report("REPORT_ID='"+reportid+"'");  //传入一个where 的条件语句“REPORT_ID=reportid”

                returnObject.setError_code(0);
                Map<String, Object> dataValue = new HashMap<>();
                dataValue.put("report_type",reportList.get(0).getRepattribute());
                dataValue.put("report_status",reportList.get(0).getMarked());
                dataValue.put("report_content",reportList.get(0).getContent());

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

