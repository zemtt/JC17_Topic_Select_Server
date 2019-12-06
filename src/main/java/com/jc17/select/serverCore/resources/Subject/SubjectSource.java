package com.jc17.select.serverCore.resources.Subject;


import com.jc17.select.dao.*;
import com.jc17.select.dao.SubjectDao;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.instances.*;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/api/teacher/subject")
@Produces(MediaType.APPLICATION_JSON)
public class SubjectSource {
    public SubjectSource(){}

    private List<User_table> findUser(String userid){
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='"+userid+"'");
        return result;
    }

    @POST
    public ReturnObject InsertSubject(@QueryParam("subjectname") Optional subjectname, @QueryParam("maxstudent") Optional maxstudent,
                                @QueryParam("majors") Optional majors, @QueryParam("sub_info") Optional sub_info,
                                @QueryParam("sub_requirements")Optional sub_requirements,
                                @QueryParam("sub_assessment")Optional sub_assessment,
                                @Auth SysUser usertoken)
    {
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try{
            List<User_table> users = findUser(userid);
            if(users.get(0).getRights()==1)//教师权限
            {
                TeacherDao teacherdao = new TeacherDao();
                List<Teacher> teacherList = teacherdao.get_Teacher("USER_ID='"+users.get(0).getUser_id()+"'");
                Subject subject = new Subject();
                subject.setSubno(subjectname.get().toString());//课程名
                subject.setStumax(Integer.parseInt(maxstudent.get().toString()));
                subject.setAssessment(sub_assessment.get().toString());
                //System.out.println(subject.getAssessment().getClass() + " , " + subject.getAssessment());
                subject.setStusele(0);
                subject.setT_id(teacherList.get(0).getT_id());
                subject.setSub_info(sub_info.get().toString());
                subject.setSub_requirements(sub_requirements.get().toString());

                //新建subject条目
                SubjectDao subjectdao = new SubjectDao();
                subjectdao.insert_Subject(subject);

                //获取sub id
                //SubjectDao subjectdao1 = new SubjectDao();
                List<Subject> subjectList = subjectdao.get_Subject("SUBN='"+subjectname.get().toString()+"'");
                String subId = subjectList.get(0).getSub_id();

                //插入major-subject
                //major分隔符   ,
                StringTokenizer st = new StringTokenizer(majors.get().toString(),",");
                while(st.hasMoreTokens()){
                    Submaj submaj = new Submaj();
                    //默认课题不重名，专业不重名
                    String majorname=st.nextToken();
                    MajorDao majorDao = new MajorDao();
                    List<Major> majorList=majorDao.get_Major("MAJOR_NAME='"+majorname+"'");
                    submaj.setMajor_id(majorList.get(0).getMajor_id());
                    submaj.setSub_id(subId);
                    SubmajDao submajDao = new SubmajDao();
                    submajDao.insert_Submaj(submaj);
                }
                List<Object> result = new ArrayList<>();
                Map<String,Object>t = new HashMap<>();
                t.put("state","1");
                result.add(t);
                returnObject.setError_code(0);
                returnObject.setData(result);
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

    @GET
    public ReturnObject getSubject(@QueryParam("subject_id") Optional subject_id, @Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try{
            List<User_table> users = findUser(userid);
            if(users.get(0).getRights()==1)//教师权限
            {
                SubjectDao subjectDao = new SubjectDao();
                List<Subject> subjectList = subjectDao.get_Subject("SUB_ID='"+subject_id.get()+"'");

                //获取majors   id
                SubmajDao submajDao = new SubmajDao();
                List<Submaj> submajList = submajDao.get_Submaj("SUB_ID='"+subject_id.get()+"'");
                StringBuffer majors = new StringBuffer();
                MajorDao majorDao = new MajorDao();
                for(Submaj i:submajList){
                    majors.append(majorDao.get_Major("MAJOR_ID='"+i.getMajor_id()+"'").get(0).getMajor_id() + " ");
                }
                //返回结果
                Map<String,Object> t = new HashMap<>();
                t.put("subname",subjectList.get(0).getSubno());
                t.put("majors",majors);
                t.put("stumax",subjectList.get(0).getStumax());
                t.put("stuselect",subjectList.get(0).getStusele());
                t.put("sub_info",subjectList.get(0).getSub_info());
                t.put("sub_requirements",subjectList.get(0).getSub_requirements());
                t.put("sub_assessment",subjectList.get(0).getAssessment());
                returnObject.setError_code(0);
                returnObject.setData(t);
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

    @PUT
    public ReturnObject updateSubject(@QueryParam("subject_id")Optional subject_id,
                                @QueryParam("subjectname") Optional subjectname, @QueryParam("maxstudent") Optional maxstudent,
                                @QueryParam("majors") Optional majors, @QueryParam("sub_info") Optional sub_info,
                                @QueryParam("sub_requirements")Optional sub_requirements,
                                @QueryParam("sub_assessment")Optional sub_assessment,
                                @Auth SysUser usertoken){
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try{
            List<User_table> users = findUser(userid);
            if(users.get(0).getRights()==1)//教师权限
            {
                TeacherDao teacherdao = new TeacherDao();
                List<Teacher> teacherList = teacherdao.get_Teacher("USER_ID='"+users.get(0).getUser_id()+"'");

                Subject subject = new Subject();
                subject.setSub_id(subject_id.get().toString());
                subject.setSubno(subjectname.get().toString());//课程名
                subject.setStumax(Integer.parseInt(maxstudent.get().toString()));
                subject.setAssessment(sub_assessment.get().toString());
                subject.setStusele(0);
                subject.setT_id(teacherList.get(0).getT_id());
                subject.setSub_info(sub_info.get().toString());
                subject.setSub_requirements(sub_requirements.get().toString());

                //更新subject条目
                SubjectDao subjectdao = new SubjectDao();
                subjectdao.update_Subject(subject);

                //sub id
                String subId = subject_id.get().toString();

                //插入major-subject
                //删除原来的，再新建
                //major分隔符   ,
                SubmajDao submajDao = new SubmajDao();
                List<Submaj> submajList = submajDao.get_Submaj("SUB_ID='" + subId + "'");
                for(Submaj i:submajList){
                    submajDao.delete_Submaj(i.getSubmaj_id());
                }
                StringTokenizer st = new StringTokenizer(majors.get().toString(),",");
                while(st.hasMoreTokens()){
                    Submaj submaj = new Submaj();
                    //默认课题不重名，专业不重名
                    String majorname=st.nextToken();
                    MajorDao majorDao = new MajorDao();
                    List<Major> majorList=majorDao.get_Major("MAJOR_NAME='"+majorname+"'");
                    submaj.setMajor_id(majorList.get(0).getMajor_id());
                    submaj.setSub_id(subId);
                    submajDao.insert_Submaj(submaj);
                }
                List<Object> result = new ArrayList<>();
                Map<String,Object>t = new HashMap<>();
                t.put("state","1");
                result.add(t);
                returnObject.setError_code(0);
                returnObject.setData(result);
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

    @DELETE
    public ReturnObject deleteSubject(@QueryParam("subject_id")Optional subject_id,@Auth  SysUser usertoken)
    {
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try{
            List<User_table> users = findUser(userid);
            if(users.get(0).getRights()==1)//教师权限
            {
                //删除submaj表
                SubmajDao submajDao = new SubmajDao();
                List<Submaj> submajList = submajDao.get_Submaj("SUB_ID='" + subject_id.get().toString() + "'");
                for(Submaj i:submajList){
                    submajDao.delete_Submaj(i.getSubmaj_id());
                }

                //删除subject表
                SubjectDao subjectDao = new SubjectDao();
                subjectDao.delete_Subject(subject_id.get().toString());

                List<Object> result = new ArrayList<>();
                Map<String,Object>t = new HashMap<>();
                t.put("state","1");
                result.add(t);
                returnObject.setError_code(0);
                returnObject.setData(result);
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
