package com.jc17.select.dao;

import java.sql.Date;

public class Student {
    private String s_id;
    private String sno;
    private String sn;
    private String sex;
    private Date birth;
    private String major_id;
    private String select_question;


    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getMajor_id() {
        return major_id;
    }

    public void setMajor_id(String major_id) {
        this.major_id = major_id;
    }

    public String getSelect_question() {
        return select_question;
    }

    public void setSelect_question(String select_question) {
        this.select_question = select_question;
    }
}
