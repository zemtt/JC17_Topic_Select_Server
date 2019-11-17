package com.jc17.select.dao;

public class Subject {
    private String sub_id;
    private String subno;//课程名
    private String t_id;
    private String sub_info;
    private String sub_requirements;
    private String assessment;
    private int stumax;
    private int stusele;

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getSubno() {
        return subno;
    }

    public void setSubno(String subno) {
        this.subno = subno;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getSub_info() {
        return sub_info;
    }

    public void setSub_info(String sub_info) {
        this.sub_info = sub_info;
    }

    public String getSub_requirements() {
        return sub_requirements;
    }

    public void setSub_requirements(String sub_requirements) {
        this.sub_requirements = sub_requirements;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public int getStumax() {
        return stumax;
    }

    public void setStumax(int stumax) {
        this.stumax = stumax;
    }

    public int getStusele() {
        return stusele;
    }

    public void setStusele(int stusele) {
        this.stusele = stusele;
    }
}
