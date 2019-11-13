package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

public class StudentDao {
    private static Connection conn;
    public StudentDao(Connection conn)
    {
        this.conn=conn;
    }

    private final String INSERT_STUDENT_SQL = "INSERT INTO STUDENT VALUES (?,?,?,?,?,?,?);";
    public void insertStudent(Student student)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_STUDENT_SQL);
            pstm.setString(1,student.getS_id());
            pstm.setString(2,student.getSno());
            pstm.setString(3,student.getSn());
            pstm.setString( 4,student.getSex());
            pstm.setDate(5, student.getBirth());
            pstm.setString(6,student.getMajor_id());
            pstm.setString(7,student.getSelect_question());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_STUDENT_SQL="UPDATE STUDENT SET SNO=?,SN=?,SEX=?,BIRTH=?,MAJOR_ID=?,SELECTED_QUESTION=? WHERE S_ID=?";
    public void updateStudent(Student student) {
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_STUDENT_SQL);
            pstm.setString(1,student.getSno());
            pstm.setString(2,student.getSn());
            pstm.setString(3,student.getSex());
            pstm.setDate(4,student.getBirth());
            pstm.setString(5,student.getMajor_id());
            pstm.setString(6,student.getSelect_question());
            pstm.setString(7,student.getS_id());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_STUDENT_SQL="DELETE FROM STUDENT WHERE S_ID=?";
    public void deletetStudent(String id)
    {
        PreparedStatement pstm=null;
        try{
            pstm=conn.prepareStatement(DELETE_STUDENT_SQL);
            pstm.setString(1,id);
            pstm.executeUpdate();
            pstm.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
