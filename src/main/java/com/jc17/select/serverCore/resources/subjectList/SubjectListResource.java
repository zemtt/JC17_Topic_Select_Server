package com.jc17.select.serverCore.resources.subjectList;

import com.jc17.select.dao.*;
import com.jc17.select.instances.*;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/api/teacher/subjectList")
@Produces(MediaType.APPLICATION_JSON)

public class SubjectListResource {
    public SubjectListResource(){}

    private List<User_table> findUser(String userid){
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='"+userid+"'");
        return result;
    }

    @GET
    public ReturnObject getTest(@Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try{
            List<User_table> findResult = findUser(userid);
            if(findResult.get(0).getRights()!=1)
            {
                System.out.println("userid:"+userid);
                List<Object> result = new ArrayList<>();
                //subid subname majors stumax stuselect
                TeacherDao teacherDao = new TeacherDao();
                List<Teacher> teacherList = teacherDao.get_Teacher("USER_ID='"+userid+"'");
                SubjectDao subjectDao = new SubjectDao();
                List<Subject> subjectList = subjectDao.get_Subject("T_ID='"+teacherList.get(0).getT_id()+"'");
                for(Subject i:subjectList){
                    StringBuffer majors = new StringBuffer();
                    SubmajDao submajDao = new SubmajDao();
                    List<Submaj> submajList = submajDao.get_Submaj("SUB_ID='"+i.getSub_id()+"'");
                    for(Submaj j:submajList){
                        MajorDao majorDao = new MajorDao();
                        System.out.println("majorid:"+j.getMajor_id());
                        List<Major> majorList = majorDao.get_Major("MAJOR_ID='"+j.getMajor_id()+"'");//1条
                        System.out.println("majorname:"+majorList.get(0).getMajor_name());
                        majors.append(majorList.get(0).getMajor_name()+" ");
                    }
                    Map<String,Object> t = new HashMap<>();
                    t.put("sub_id",i.getSub_id());
                    t.put("sub_name",i.getSubno());//课题名称
                    t.put("majors",majors);
                    t.put("stumax",i.getStumax());
                    t.put("stuselect",i.getStusele());//需不需要当前选择人数？
                    result.add(t);
                }
                returnObject.setError_code(0);
                returnObject.setData(result);
            }
            else {
                returnObject.setError_code(1);
                returnObject.setData("非法请求");
                return returnObject;
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setError_code(5);
            returnObject.setData("参数不完整");
        }
        return returnObject;
    }
}
