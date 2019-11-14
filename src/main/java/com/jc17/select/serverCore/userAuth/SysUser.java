package com.jc17.select.serverCore.userAuth;

import com.jc17.select.dao.User_table;
import com.jc17.select.dao.User_tableDao;
import com.jc17.select.serverCore.resources.utils.Token;

import java.security.Principal;

public class SysUser implements Principal {
    private String userId;
    private boolean checked = false;
    private int userType = -1;

    private boolean CheckUser(String userName, String passWord){
        return true;
    }

    public SysUser(String token) {
        String[] res = new Token().analyzeAToken(token);
        userId = res[1];
        User_tableDao dao = new User_tableDao();
        try {
            User_table user = dao.get_User_Table("USER_ID='"+userId+"'").get(0);
            userType = user.getRights();
            checked = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return userId;
    }

    public boolean isChecked(){
        return checked;
    }

    public boolean isAdmin(){
        return userType == 0;
    }

    public boolean isTeacher(){
        return userType == 1;
    }

    public boolean isStudent(){
        return userType == 2;
    }
}