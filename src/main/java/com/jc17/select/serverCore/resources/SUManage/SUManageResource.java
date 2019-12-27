package com.jc17.select.serverCore.resources.SUManage;

import com.jc17.select.dao.*;
import com.jc17.select.instances.*;
import com.jc17.select.serverCore.resources.utils.ReturnObject;
import com.jc17.select.serverCore.userAuth.SysUser;
import io.dropwizard.auth.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/api/admin/sumanage")
@Produces(MediaType.APPLICATION_JSON)
public class SUManageResource {
    public SUManageResource() {
    }

    private List<User_table> findUser(String userid) {
        User_tableDao dao = new User_tableDao();
        List<User_table> result = dao.get_User_Table("USER_ID='" + userid + "'");
        return result;
    }

    @PUT
    //确认志愿
    public ReturnObject Confirm(@Auth SysUser usertoken) {
        ReturnObject returnObject = new ReturnObject();
        String userid = usertoken.getName();
        try {
            List<User_table> users = findUser(userid);
            if (users.get(0).getRights() == 0)//超级管理员权限
            {
                //获取所有确认志愿条目
                //对每个学生做循环，从权重1~3，如果1被确认，则其他全部置为未确认，循环直到结束位置
                StudentDao studentDao = new StudentDao();
                List<Student> studentList = studentDao.get_Student("");
                for(Student s:studentList){
                    SsublogDao ssublogDao = new SsublogDao();
                    //获取每个学生的志愿条目
                    List<Ssublog> ssublogList1 = ssublogDao.get_Ssublog("S_ID='"+s.getS_id()+"' AND PRIO=1");
                    List<Ssublog> ssublogList2 = ssublogDao.get_Ssublog("S_ID='"+s.getS_id()+"' AND PRIO=2");
                    List<Ssublog> ssublogList3 = ssublogDao.get_Ssublog("S_ID='"+s.getS_id()+"' AND PRIO=3");

                    for(Ssublog i:ssublogList2){
                        System.out.println("sid:"+i.getS_id()+"prio:"+i.getPrio()+"subid:"+i.getSub_id());
                    }

                    if(ssublogList1.size()!=0 && ssublogList1.get(0).getSchs()==1){//如果志愿1被选上
                        if(ssublogList2.size()!=0){
                            ssublogList2.get(0).setSchs(0);
                            ssublogDao.update_Ssublog(ssublogList2.get(0));
                        }
                        if(ssublogList3.size()!=0){
                            ssublogList3.get(0).setSchs(0);
                            ssublogDao.update_Ssublog(ssublogList3.get(0));
                        }
                    }
                    if(ssublogList2.size()!=0 && ssublogList2.get(0).getSchs()==1){//如果志愿2被选上
                        if(ssublogList3.size()!=0){
                            ssublogList3.get(0).setSchs(0);
                            ssublogDao.update_Ssublog(ssublogList3.get(0));
                        }
                    }
                }

                SsublogDao ssublogDao = new SsublogDao();
                List<Ssublog> ssublogList = ssublogDao.get_Ssublog("SCHS=1");//选出确认的条目



                for(Ssublog i:ssublogList){
                    SubjectDao subjectDao = new SubjectDao();
                    List<Subject> subjectList = subjectDao.get_Subject("SUB_ID='"+i.getSub_id()+"'");
                    int t = subjectList.get(0).getStusele();
                    subjectList.get(0).setStusele(t+1);
                    subjectDao.update_Subject(subjectList.get(0));//更新人数

                    Select select = new Select();
                    select.setS_id(i.getS_id());
                    select.setSub_id(i.getSub_id());
                    select.setSelected(1);
                    SelectDao selectDao = new SelectDao();
                    selectDao.insert_Select(select);
                }

                Map<String,Object> t = new HashMap<>();
                t.put("state","1");
                returnObject.setError_code(0);
                returnObject.setData(t);
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

