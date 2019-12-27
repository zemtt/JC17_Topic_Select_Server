package com.jc17.select.serverCore.resources.Studentsubjectlist;
import com.jc17.select.dao.*;
import com.jc17.select.instances.*;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.*;


//获取学生得所有志愿
public class Studentsubjectlist {
    public Studentsubjectlist(){

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
                StudentDao studentDao=new StudentDao();
                List<Student> studentList=studentDao.get_Student("USER_ID='"+userid+"'");

                SsublogDao ssublogDao=new SsublogDao();
                List<Ssublog> ssublogList=ssublogDao.get_Ssublog("S_ID='"+studentList.get(0).getS_id()+"'");




                returnObject.setError_code(0);

                List<Object> data=new ArrayList<>();

                for (Ssublog item:ssublogList) {
                    Map<String,Object> datavalue =new HashMap<>();
                    datavalue.put("subject_id",item.getSub_id());
                    datavalue.put("priority",item.getPrio());
                    //datavalue.put("student_content",);  无学生简介
                    datavalue.put("status",item.getSchs());//是否被选中


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
