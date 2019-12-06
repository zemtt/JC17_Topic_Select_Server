package com.jc17.select.instances;

import java.sql.Date;

public class Operation_log {
    private String oplog_id;
    private String user_id;
    private Date optime;
    private String optype;
    private String opstate;

    public String getOplog_id() {
        return oplog_id;
    }

    public void setOplog_id(String oplog_id) {
        this.oplog_id = oplog_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype;
    }

    public String getOpstate() {
        return opstate;
    }

    public void setOpstate(String opstate) {
        this.opstate = opstate;
    }
}
