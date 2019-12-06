package com.jc17.select.serverCore.resources.msgList;


import com.jc17.select.dao.CommuDao;
import com.jc17.select.dao.StudentDao;
import com.jc17.select.dao.TeacherDao;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.instances.Commu;
import com.jc17.select.instances.Student;
import com.jc17.select.instances.Teacher;
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

@Path("/api/msg/msgList")
@Produces(MediaType.APPLICATION_JSON)
public class msgListResource {
    public msgListResource(){}

    private List<User_table> findUser(String userid){
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='"+userid+"'");
        return result;
    }

    private String findName(String userid){
        String name;
        User_tableDao user_tableDao = new User_tableDao();
        List<User_table> user_tableList = user_tableDao.get_User_Table("USER_ID='" + userid + "'");
        if(user_tableList.get(0).getRights()==1)//教师
        {
            TeacherDao teacherDao = new TeacherDao();
            List<Teacher> teacherList = teacherDao.get_Teacher("USER_ID='"+userid+"'");
            System.out.println(userid);
            return teacherList.get(0).getTn();
        }
        else if(user_tableList.get(0).getRights()==2)//学生
        {
            StudentDao studentDao = new StudentDao();
            List<Student> studentList = studentDao.get_Student("USER_ID='" + userid + "'");
            return studentList.get(0).getSn();
        }
        else{//管理员可以发站内信吗？
            //返回管理员账户名
            return user_tableList.get(0).getUser_account();
        }
    }

    @GET
    public ReturnObject getMsgList(@QueryParam("type") Optional type, @Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try{
            List<User_table> users = findUser(userid);
            if(users.get(0).getRights()==1||users.get(0).getRights()==2)//教师 学生 权限
            {
                //获取用户姓名？？？两个表，判断权限？？
                CommuDao commuDao = new CommuDao();
                List<Commu> commuList;
                if(Integer.parseInt(type.get().toString())==1)//如果是收件箱
                    commuList = commuDao.get_Commu("RECIEVER_ID='"+userid+"'");
                else
                    commuList = commuDao.get_Commu("SENDER_ID='"+userid+"'");

                List<Object> result = new ArrayList<>();
                for(Commu i:commuList){
                    Map<String,Object> t = new HashMap<>();
                    t.put("msgid",i.getCommu_id());
                    t.put("sender",findName(i.getSender_id()));
                    t.put("receiver",findName(i.getReceiver_id()));
                    t.put("sendtime",i.getComtime());
                    t.put("title",i.getCommu_title());
                    t.put("readed",i.getReaded());
                    result.add(t);
                }
                returnObject.setData(result);
                returnObject.setError_code(0);
            }
            else
            {
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
