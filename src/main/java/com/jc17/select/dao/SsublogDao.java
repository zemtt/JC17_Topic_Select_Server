package com.jc17.select.dao;

import com.jc17.select.dao.utils.GetConn;
import com.jc17.select.instances.Ssublog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SsublogDao {

    private static Connection conn=null;
    public SsublogDao() {
        this.conn = new GetConn().GetConnection();
    }

    private static final String INSERT_SSUBLOG_SQL="INSERT INTO SSUBLOG VALUES(replace(NEWID(),'-',''),?,?,?,?,?)";
    public void insert_Ssublog(Ssublog ssublog) {
        PreparedStatement pstm = null;
        try{
            pstm=conn.prepareStatement(INSERT_SSUBLOG_SQL);
            //pstm.setString(1,ssublog.getSsublog_id());
            pstm.setString(1,ssublog.getSub_id());
            pstm.setString(2,ssublog.getS_id());
            pstm.setInt(3,ssublog.getPrio());
            pstm.setInt(4,ssublog.getSchs());
            pstm.setInt(5,ssublog.getRchs());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_SSUBLOG_SQL="UPDATE SSUBLOG SET SUB_ID=?,S_ID=?,PRIO=?,SCHS=?,RCHS=? WHERE SSUBLOG_ID=?";
    public void update_Ssublog(Ssublog ssublog)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_SSUBLOG_SQL);
            pstm.setString(6,ssublog.getSsublog_id());
            pstm.setString(1,ssublog.getSub_id());
            pstm.setString(2,ssublog.getS_id());
            pstm.setInt(3,ssublog.getPrio());
            pstm.setInt(4,ssublog.getSchs());
            pstm.setInt(5,ssublog.getRchs());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_SSUBLOG_SQL="DELETE FROM SSUBLOG WHERE SSUBLOG_ID=?";
    public void delete_Ssublog(String ssublog_id)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_SSUBLOG_SQL);
            pstm.setString(1,ssublog_id);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String GET_BY_ID_SSUBLOG_SQL="SELECT SSUBLOG_ID,SUB_ID,S_ID,PRIO,SCHS,RCHS FROM SSUBLOG WHERE SSUBLOG_ID=?";
    public Ssublog get_Ssublog_By_Id(String ssublog_id)
    {
        Ssublog ssublog=new Ssublog();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_BY_ID_SSUBLOG_SQL);
            pstm.setString(1,ssublog_id);
            rs = pstm.executeQuery();
            if(rs.next()) {
                ssublog.setSsublog_id(rs.getString(1));
                ssublog.setSub_id(rs.getString(2));
                ssublog.setS_id(rs.getString(3));
                ssublog.setPrio(rs.getInt(4));
                ssublog.setSchs(rs.getInt(5));
                ssublog.setRchs(rs.getInt(6));
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ssublog;
    }

    private static final String GET_SSUBLOG_SQL="SELECT SSUBLOG_ID,SUB_ID,S_ID,PRIO,SCHS,RCHS FROM SSUBLOG";
    public List<Ssublog> get_Ssublog(String sql)
    {
        List<Ssublog> ssublogs = new ArrayList<Ssublog>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            String finalsql = null;
            if(sql.equals("")){
                finalsql = GET_SSUBLOG_SQL;
            }
            else{
                finalsql = GET_SSUBLOG_SQL + " WHERE " + sql;
            }
            pstm = conn.prepareStatement(finalsql);
            rs = pstm.executeQuery();
            while(rs.next()) {
                Ssublog ssublog=new Ssublog();
                ssublog.setSsublog_id(rs.getString(1));
                ssublog.setSub_id(rs.getString(2));
                ssublog.setS_id(rs.getString(3));
                ssublog.setPrio(rs.getInt(4));
                ssublog.setSchs(rs.getInt(5));
                ssublog.setRchs(rs.getInt(6));
                ssublogs.add(ssublog);
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ssublogs;
    }
}
