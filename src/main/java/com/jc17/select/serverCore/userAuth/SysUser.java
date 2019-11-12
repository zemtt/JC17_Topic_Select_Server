package com.jc17.select.serverCore.userAuth;

import java.security.Principal;

public class SysUser implements Principal {
    private String userName;
    private boolean checked = false;
    private int userType = -1;

    private boolean CheckUser(String userName, String passWord){
        return true;
    }

    public SysUser(String userName, String passWord) {
        this.userName = userName;
        this.checked = CheckUser(userName, passWord);
    }

    @Override
    public String getName() {
        return userName;
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