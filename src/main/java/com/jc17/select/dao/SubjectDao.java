package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SubjectDao {
    private static Connection conn=null;
    public SubjectDao(Connection conn)
    {
        this.conn=conn;
    }

    private static final String INSERT_SUBJECT_SQL="INSERT INTO SUBJECT VALUES(?,?.?,?,?,?,?,?)";
    public void insert_Subject(Subject subject)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_SUBJECT_SQL);
            pstm.setString(1,subject.getSub_id());
            pstm.setString(2,subject.getSubno());
            pstm.setString(3,subject.getT_id());
            pstm.setString(4,subject.getSub_info());
            pstm.setString(5,subject.getSub_requirements());
            pstm.setString(6,subject.getAssessment());
            pstm.setInt(7,subject.getStumax());
            pstm.setInt(8,subject.getStusele());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_SUBJECT_SQL="UPDATE SUBJECT SET SUBNO=?, T_ID=?, SUB_INFO=?, SUB_REQUIREMENTS=?, ASSESSMENT=?, STUMAX=?, STUSELE=? WHERE  SUB_ID=?";
    public void update_Subject(Subject subject)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_SUBJECT_SQL);
            pstm.setString(8,subject.getSub_id());
            pstm.setString(1,subject.getSubno());
            pstm.setString(2,subject.getT_id());
            pstm.setString(3,subject.getSub_info());
            pstm.setString(4,subject.getSub_requirements());
            pstm.setString(5,subject.getAssessment());
            pstm.setInt(6,subject.getStumax());
            pstm.setInt(7,subject.getStusele());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_SUBJECT_SQL="DELETE FROM SUBJECT WHERE SUB_ID=?";
    public void delete_Subject(String subjectId)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_SUBJECT_SQL);
            pstm.setString(1,subjectId);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
