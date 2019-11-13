package com.jc17.select.dao;

import java.sql.Date;

public class Commu {
    private String commu_id;
    private Date comtime;
    private String sender_id;
    private String receiver_id;
    private String contient;


    public String getCommu_id() {
        return commu_id;
    }

    public void setCommu_id(String commu_id) {
        this.commu_id = commu_id;
    }

    public Date getComtime() {
        return comtime;
    }

    public void setComtime(Date comtime) {
        this.comtime = comtime;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getContient() {
        return contient;
    }

    public void setContient(String contient) {
        this.contient = contient;
    }
}
