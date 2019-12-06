package com.jc17.select.serverCore.resources.msgReceiverList;


import com.jc17.select.dao.*;
import com.jc17.select.instances.*;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.swing.text.html.Option;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/api/msg/msgReceiverList")
@Produces(MediaType.APPLICATION_JSON)
public class msgRecieverListResource {
    public msgRecieverListResource(){}

    private List<User_table> findUser(String userid) {
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='" + userid + "'");
        return result;
    }

    @GET
    //管理员要加到待选择用户列表中吗？
    public ReturnObject getReceiverList(@Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 1)//教师 权限
            {
                TeacherDao teacherDao = new TeacherDao();
                List<Teacher> teacherList = teacherDao.get_Teacher("USER_ID='"+userid+"'");

                //获取教师课题组的学生
                List<Object> result = new ArrayList<>();
                //获取课题list
                SubjectDao subjectDao = new SubjectDao();
                List<Subject> subjectList = subjectDao.get_Subject("T_ID='"+teacherList.get(0).getT_id()+"'");

                for(Subject i:subjectList){
                    //获取学生List
                    SsublogDao ssublogDao = new SsublogDao();
                    List<Ssublog> ssublogList = ssublogDao.get_Ssublog("SUB_ID='"+i.getSub_id()+"' AND RCHS=1");
                    for(Ssublog j:ssublogList){
                        StudentDao studentDao = new StudentDao();
                        List<Student> studentList = studentDao.get_Student("S_ID='"+j.getS_id()+"'");
                        Map<String,Object> t = new HashMap<>();
                        t.put("name",studentList.get(0).getSn());
                        t.put("id",j.getS_id());
                        result.add(t);
                    }
                }
                returnObject.setError_code(0);
                returnObject.setData(result);
            }
            else if(users.get(0).getRights() == 2)//学生权限
            {
                StudentDao studentDao = new StudentDao();
                List<Student> studentList = studentDao.get_Student("USER_ID='"+userid+"'");

                SsublogDao ssublogDao = new SsublogDao();
                List<Ssublog> ssublogList = ssublogDao.get_Ssublog("S_ID='"+studentList.get(0).getS_id()+"' AND RCHS=1");

                SubjectDao subjectDao = new SubjectDao();
                List<Subject> subjectList = subjectDao.get_Subject("SUB_ID='"+ssublogList.get(0).getSub_id()+"'");


                //获取教师的姓名
                //subjectList.get(0).getT_id();
                List<Object> result = new ArrayList<>();
                TeacherDao teacherDao = new TeacherDao();

                Map<String,Object> t= new HashMap<>();
                t.put("id",subjectList.get(0).getT_id());
                t.put("name",teacherDao.get_Teacher("T_ID='"+subjectList.get(0).getT_id()+"'").get(0).getTn());
                result.add(t);

                //获取同组学生的姓名
                //ssublogList.get(0).getSub_id()   subid
                List<Ssublog> ssublogList1 = ssublogDao.get_Ssublog("SUB_ID='"+ssublogList.get(0).getSub_id()+"' AND RCHS=1");
                for(Ssublog i:ssublogList1){
                    if(!i.getS_id().equals(studentList.get(0).getS_id()))
                    {
                        Map<String,Object> tt = new HashMap<>();
                        tt.put("name",studentDao.get_Student("S_ID='"+i.getS_id()+"'").get(0).getSn());
                        tt.put("id",i.getS_id());
                        result.add(tt);
                    }
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



}
