package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SubmajDao {
    private static Connection conn=null;
    public SubmajDao(Connection conn)
    {
        this.conn=conn;
    }

    private static final String INSERT_SUBMAJ_SQL="INSERT INTO SUBMAJ VALUES(?,?,?)";
    public void insert_Submaj(Submaj submaj)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_SUBMAJ_SQL);
            pstm.setString(1,submaj.getSubmaj_id());
            pstm.setString(2,submaj.getSub_id());
            pstm.setString(3,submaj.getMajor_id());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_SUBMAJ_SQL="UPDATE SUBMAJ SET SUB_ID=?, MAJOR_ID=? WHERE SUBMAJ_ID=?";
    public void update_Submaj(Submaj submaj)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_SUBMAJ_SQL);
            pstm.setString(3,submaj.getSubmaj_id());
            pstm.setString(1,submaj.getSub_id());
            pstm.setString(2,submaj.getMajor_id());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_SUBMAJ_SQL="DELETE FROM SUBMAJ WHERE SUBMAJ_ID=?";
    public void delete_Submaj(String submajId)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_SUBMAJ_SQL);
            pstm.setString(1,submajId);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
