package com.jc17.select.serverCore.resources.ssublog;


import com.jc17.select.dao.MajorDao;
import com.jc17.select.dao.SsublogDao;
import com.jc17.select.dao.StudentDao;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.instances.Major;
import com.jc17.select.instances.Ssublog;
import com.jc17.select.instances.Subject;
import com.jc17.select.instances.User_table;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.swing.text.html.Option;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/api/teacher/ssublog")
@Produces(MediaType.APPLICATION_JSON)
public class ssublogResource {
    public ssublogResource(){}

    private List<User_table> findUser(String userid) {
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='" + userid + "'");
        return result;
    }

    @GET
    public ReturnObject getSsublog(@QueryParam("subject_id") Optional subject_id, @Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 1)//教师权限
            {
                SsublogDao ssublogDao = new SsublogDao();
                List<Ssublog> ssublogList = ssublogDao.get_Ssublog("SUB_ID='"+subject_id.get().toString()+"'");
                List<Object> result = new ArrayList<>();
                for(Ssublog i:ssublogList){
                    Map<String,Object> t = new HashMap<>();
                    StudentDao studentDao=new StudentDao();
                    MajorDao majorDao = new MajorDao();
                    t.put("studentname",studentDao.get_Student_By_Id(i.getS_id()).getSn());
                    t.put("studentnumber",i.getS_id());
                    t.put("studentmajor",majorDao.get_Major_By_Id(studentDao.get_Student_By_Id(i.getS_id()).getMajor_id()).getMajor_name());
                    t.put("priority","志愿"+i.getPrio());
                    if(i.getRchs()==1)
                        t.put("status","已接收");
                    else
                        t.put("status","未接收");
                    t.put("studentinfo",i.getStu_content());
                    t.put("file_url",i.getFile_url());

                    result.add(t);
                }
                returnObject.setError_code(0);
                returnObject.setData(result);
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
    public ReturnObject updateSsublog(@QueryParam("ssublog_id")Optional ssublog_id,@QueryParam("chs")Optional chs, @Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 1)//教师权限
            {
                SsublogDao ssublogDao = new SsublogDao();
                Ssublog ssublog = ssublogDao.get_Ssublog_By_Id(ssublog_id.get().toString());
                ssublog.setRchs(Integer.parseInt(chs.get().toString()));
                ssublogDao.update_Ssublog(ssublog);

                Map<String,Object> t = new HashMap<>();
                t.put("state","1");
                returnObject.setError_code(0);
                returnObject.setData(t);
            }else{
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
