package com.jc17.select.dao;

import javax.ws.rs.DELETE;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MajorDao {
    private static Connection conn=null;
    public  MajorDao()
    {
        this.conn = new GetConn().GetConnection();
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

    private static final String GET_BY_ID_Major_SQL="SELECT MAJOR_ID,MAJOR_NAME FROM MAJOR WHERE MAJOR_ID=?";
    public Major get_Major_By_Id(String major_id)
    {
        Major major = new Major();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_BY_ID_Major_SQL);
            pstm.setString(1,major_id);
            rs = pstm.executeQuery();
            if(rs.next()) {
                major.setMajor_id(rs.getString("MAJOR_ID"));
                major.setMajor_name(rs.getString("MAJOR_NAME"));
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return major;
    }

    private static final String GET_Major_SQL="SELECT MAJOR_ID,MAJOR_NAME FROM MAJOR";
    public List<Major> get_Major(String sql)
    {
        List<Major> majors = new ArrayList<Major>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            String finalsql = null;
            if(sql.equals("")){
                finalsql = GET_Major_SQL;
            }
            else{
                finalsql=GET_Major_SQL + " WHERE " + sql;
            }
            rs = pstm.executeQuery(finalsql);
            while(rs.next()) {
                Major major = new Major();
                major.setMajor_id(rs.getString("MAJOR_ID"));
                major.setMajor_name(rs.getString("MAJOR_NAME"));
                majors.add(major);
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return majors;
    }
}
