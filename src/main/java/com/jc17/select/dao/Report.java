package com.jc17.select.dao;

public class Report {
    private String report_id;
    private String s_id;
    private String sub_id;
    private String repattribute;
    private float score;
    private int marked;
    private String content;

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getRepattribute() {
        return repattribute;
    }

    public void setRepattribute(String repattribute) {
        this.repattribute = repattribute;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getMarked() {
        return marked;
    }

    public void setMarked(int marked) {
        this.marked = marked;
    }


    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
