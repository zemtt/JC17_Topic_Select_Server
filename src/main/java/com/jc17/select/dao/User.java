package com.jc17.select.dao;

public class User {
    private String id;
    private String username;
    private String password;
    private int userType;

    private boolean fUsername = false;
    private boolean fPassword = false;
    private boolean fUserType = false;

    public void setId(String id)
    {
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        fUsername = true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        fPassword = true;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
        fUserType = true;
    }

    public boolean isfUsername() {
        return fUsername;
    }

    public boolean isfPassword() {
        return fPassword;
    }

    public boolean isfUserType() {
        return fUserType;
    }

    public void setfUsername(boolean fUsername)
    {
        this.fUsername=fUsername;
    }

    public void setfPassword(boolean fPassword)
    {
        this.fPassword=fPassword;
    }

    public void setfUserType(boolean fUserType)
    {
        this.fUserType=fUserType;
    }
}

