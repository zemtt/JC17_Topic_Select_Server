package com.jc17.select.dao;

import javax.ws.rs.DELETE;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MajorDao {
    private static Connection conn=null;
    public  MajorDao(Connection conn)
    {
        this.conn=conn;
    }

    private static final String INSERT_MAJOR_SQL="INSERT INTO MAJOR VALUES(?,?)";
    public void insert_Major(Major major)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_MAJOR_SQL);
            pstm.setString(1,major.getMajor_id());
            pstm.setString(2,major.getMajor_name());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_MAJOR_SQL="UPDATE MAJOR SET MAJOR_NAME=? WHERE MAJOR_ID=?";
    public void update_Major(Major major)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_MAJOR_SQL);
            pstm.setString(2,major.getMajor_id());
            pstm.setString(1,major.getMajor_name());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_MAJOR_SQL="DELETE FROM MAJOR WHERE MAJOR_ID=?";
    public void delete_Major(String majorId)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_MAJOR_SQL);
            pstm.setString(1,majorId);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
