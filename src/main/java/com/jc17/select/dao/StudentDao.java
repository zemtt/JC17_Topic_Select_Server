package com.jc17.select.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private static Connection conn;
    public StudentDao()
    {
        this.conn = new GetConn().GetConnection();
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
            pstm.setString(7,student.getUser_id());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_STUDENT_SQL="UPDATE STUDENT SET SNO=?,SN=?,SEX=?,BIRTH=?,MAJOR_ID=?,USER_ID=? WHERE S_ID=?";
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
            pstm.setString(6,student.getUser_id());
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

    private static final String GET_BY_ID_STUDENT_SQL="SELECT S_ID,SNO,SN,SEX,BIRTH,MAJOR_ID,USER_ID FROM STUDENT WHERE S_ID=?";
    public Student get_Student_By_Id(String s_id)
    {
        Student student = new Student();
        PreparedStatement pstm=null;
        ResultSet rs=null;
        try{
            pstm=conn.prepareStatement(GET_BY_ID_STUDENT_SQL);
            pstm.setString(1,s_id);
            rs=pstm.executeQuery();
            if(rs.next()) {
                student.setS_id(rs.getString(1));
                student.setSno(rs.getString(2));
                student.setSn(rs.getString(3));
                student.setSex(rs.getString(5));
                student.setBirth(rs.getDate(6));
                student.setMajor_id(rs.getString(7));
                student.setUser_id(rs.getString(8));
                rs.close();
                pstm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    private static final String GET_STUDENT_SQL="SELECT S_ID,SNO,SN,SEX,BIRTH,MAJOR_ID,USER_ID FROM STUDENT";
    public List<Student> get_Student(String sql)
    {
        List<Student> Students = new ArrayList<Student>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            String finalsql = null;
            if(sql.equals("")){
                finalsql = GET_STUDENT_SQL;
            }
            else{
                finalsql = GET_STUDENT_SQL + " WHERE " + sql;
            }
            pstm = conn.prepareStatement(finalsql);
            rs = pstm.executeQuery();
            while(rs.next()) {
                Student student=new Student();
                student.setS_id(rs.getString(1));
                student.setSno(rs.getString(2));
                student.setSn(rs.getString(3));
                student.setSex(rs.getString(4));
                student.setBirth(rs.getDate(5));
                student.setMajor_id(rs.getString(6));
                student.setUser_id(rs.getString(7));
                Students.add(student);
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return Students;
    }
}
