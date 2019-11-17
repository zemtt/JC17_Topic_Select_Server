package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao {
    private static Connection conn=null;
    public SubjectDao()
    {
        this.conn = new GetConn().GetConnection();
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

    private static final String GET_BY_ID_SUBJECT_SQL="SELECT SUB_ID,T_ID,SUB_INFO,SUB_REQUIREMENTS, ASSESSMENT,STUMAX,STUSELE FROM SUBJECT WHERE SUB_ID=?";
    public Subject get_Subject_By_Id(String sub_id)
    {
        Subject subject= new Subject();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_BY_ID_SUBJECT_SQL);
            pstm.setString(1,sub_id);
            rs = pstm.executeQuery();
            if(rs.next()) {
                subject.setSub_id(rs.getString(1));
                subject.setT_id(rs.getString(2));
                subject.setSub_info(rs.getString(3));
                subject.setSub_requirements(rs.getString(4));
                subject.setAssessment(rs.getString(5));
                subject.setStumax(rs.getInt(6));
                subject.setStusele(rs.getInt(7));
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return subject;
    }

    private static final String GET_SUBJECT_SQL="SELECT SUB_ID,T_ID,SUB_INFO,SUB_REQUIREMENTS, ASSESSMENT,STUMAX,STUSELE,SUBN FROM SUBJECT";
    public List<Subject> get_Subject(String sql)
    {
        List<Subject> subjects = new ArrayList<Subject>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            String finalsql = null;
            if(sql.equals("")){
                finalsql = GET_SUBJECT_SQL;
            }
            else{
                finalsql = GET_SUBJECT_SQL + " WHERE " + sql;
            }
            pstm = conn.prepareStatement(finalsql);
            rs = pstm.executeQuery();
            while(rs.next()) {
                Subject subject = new Subject();
                subject.setSub_id(rs.getString(1));
                subject.setT_id(rs.getString(2));
                subject.setSub_info(rs.getString(3));
                subject.setSub_requirements(rs.getString(4));
                subject.setAssessment(rs.getString(5));
                subject.setStumax(rs.getInt(6));
                subject.setStusele(rs.getInt(7));
                subject.setSubno(rs.getString(8));
                subjects.add(subject);
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return subjects;
    }

}
