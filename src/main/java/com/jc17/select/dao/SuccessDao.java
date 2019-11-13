package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SuccessDao {
    private static Connection conn=null;
    public SuccessDao(Connection conn)
    {
        this.conn=conn;
    }

    private static final String INSERT_SUCCESS_SQL="INSERT INTO SUCESS VALUES(?,?,?)";
    public void insert_Success(Success success)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_SUCCESS_SQL);
            pstm.setString(1,success.getSucess_id());
            pstm.setString(2,success.getS_id());
            pstm.setString(3,success.getSub_id());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_SUCCESS_SQL="UPDATE SUCCESS SET S_ID=?, SUB_ID=? WHERE SUCCESS_ID=?";
    public void update_Success(Success success)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_SUCCESS_SQL);
            pstm.setString(3,success.getSucess_id());
            pstm.setString(1,success.getS_id());
            pstm.setString(2,success.getSub_id());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_SUCCESS_SQL="DELETE FROM SUCCESS WHERE SUCCESS_ID=?";
    public void delete_Success(String success_id)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_SUCCESS_SQL);
            pstm.setString(1,success_id);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
