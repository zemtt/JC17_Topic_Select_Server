package com.jc17.select.serverCore.resources.msg;


import com.jc17.select.dao.CommuDao;
import com.jc17.select.dao.StudentDao;
import com.jc17.select.dao.TeacherDao;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.instances.*;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.*;

@Path("/api/msg/msg")
@Produces(MediaType.APPLICATION_JSON)
public class msgResource {
    public msgResource(){}

    private List<User_table> findUser(String userid) {
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='" + userid + "'");
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
    public ReturnObject getMsg(@QueryParam("msgid")Optional msgid,@Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 1 || users.get(0).getRights() == 2)//教师 学生权限
            {
                CommuDao commuDao = new CommuDao();
                List<Commu> commuList;
                commuList = commuDao.get_Commu("COMMU_ID='"+msgid.get().toString()+"'");
                Map<String,Object> t = new HashMap<>();
                t.put("sender",findName(commuList.get(0).getSender_id()));
                t.put("receiver",findName(commuList.get(0).getReceiver_id()));
                t.put("sendtime",commuList.get(0).getComtime());
                t.put("title",commuList.get(0).getCommu_title());
                t.put("readed",commuList.get(0).getReaded());
                t.put("content",commuList.get(0).getContent());

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


    @POST
    public ReturnObject newMsg(@QueryParam("receiver")Optional receiver, @QueryParam("title")Optional title,
                               @QueryParam("content")Optional content,@Auth SysUser usertoken) {
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 1 || users.get(0).getRights() == 2)//教师 学生权限
            {
                String sendername = findName(userid);
                Commu commu = new Commu();
                commu.setCommu_title(title.get().toString());
                //java 获取时间需要精确到秒吗？从小时开始都是0
                Date date = new Date(Calendar.getInstance().getTime().getTime());
                commu.setComtime(date);
                commu.setContent(content.get().toString());
                commu.setReaded(0);
                //收信人的下拉选择，要寻找学生的idList  已完成，仍需要改进
                commu.setReceiver_id(receiver.get().toString());
                commu.setSender_id(userid);

                CommuDao commuDao = new CommuDao();
                commuDao.insert_Commu(commu);

                returnObject.setError_code(0);
                Map<String,Object> t = new HashMap<>();
                t.put("state",1);
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


    @DELETE
    public ReturnObject delMsg(@QueryParam("msgid")Optional msgid,@Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 1 || users.get(0).getRights() == 2)//教师 学生权限
            {
                CommuDao commuDao = new CommuDao();


                returnObject.setError_code(0);
                Map<String,Object> t = new HashMap<>();
                try{
                    commuDao.delete_Commu(msgid.get().toString());
                    t.put("state",1);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    t.put("state",0);
                }
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
