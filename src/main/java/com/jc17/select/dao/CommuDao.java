package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CommuDao {
    private static Connection conn=null;
    public CommuDao(Connection conn)
    {
        this.conn=conn;
    }
    private static final String INSERT_COMMU_SQL="INSERT INTO COMMU VALUES(?,?,?,?,?)";
    public void insert_Commu(Commu commu)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_COMMU_SQL);
            pstm.setString(1,commu.getCommu_id());
            pstm.setDate(2,commu.getComtime());
            pstm.setString(3,commu.getSender_id());
            pstm.setString(4,commu.getReceiver_id());
            pstm.setString(5,commu.getContient());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_COMMU_SQL="UPDATE COMMU SET COTIME=?,SENDER_ID=?,RECIEVER_ID=?,CONTENT=? WHERE COMMU_ID=?";
    public void update_Commu(Commu commu)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_COMMU_SQL);
            pstm.setString(5,commu.getCommu_id());
            pstm.setDate(1,commu.getComtime());
            pstm.setString(2,commu.getSender_id());
            pstm.setString(3,commu.getReceiver_id());
            pstm.setString(4,commu.getContient());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_COMMU_SQL="DELETE FROM COMMU WHERE COMMU_ID=?";
    public void delete_Commu(String commu_id)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_COMMU_SQL);
            pstm.setString(1,commu_id);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
