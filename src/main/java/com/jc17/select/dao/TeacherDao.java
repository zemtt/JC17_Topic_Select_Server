package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao {
    private static Connection conn = null;

    public TeacherDao() {
        this.conn = new GetConn().GetConnection();
    }

    private static final String INSERT_TEACHER_SQL = "INSERT INTO TEACHER VALUES(replace(NEWID(),'-',''),?,?,?,?,?,?)";

    public void insert_Teacher(Teacher teacher) {
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(INSERT_TEACHER_SQL);
            //pstm.setString(1, teacher.getT_id());
            pstm.setString(1, teacher.getTno());
            pstm.setString(2, teacher.getTn());
            pstm.setDate(3, teacher.getBirth());
            pstm.setString(4, teacher.getMajor_id());
            pstm.setString(5, teacher.getUser_id());
            pstm.setString(6, teacher.getSex());
            pstm.executeUpdate();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String UPDATE_TEACHER_SQL = "UPDATE TEACHER SET TNO=?,TN=?,TN=?,BIRTH=?,MAJOR_ID=?,USER_ID=?,TSEX=? WHERE T_ID=?";
    public void update_Teacher(Teacher teacher) {
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(UPDATE_TEACHER_SQL);

            pstm.setString(7, teacher.getT_id());
            pstm.setString(1, teacher.getTno());
            pstm.setString(2, teacher.getTn());
            pstm.setDate(3, teacher.getBirth());
            pstm.setString(4, teacher.getMajor_id());
            pstm.setString(5, teacher.getUser_id());
            pstm.setString(6, teacher.getSex());
            pstm.executeUpdate();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String DELETE_TEACHER_SQL="DELETE FROM TEACHER WHERE T_ID=?";
    public void delete_Teacher(String t_id)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_TEACHER_SQL);
            pstm.setString(1,t_id);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String GET_BY_ID_TEACHER_SQL="SELECT T_ID,TNO,TN,BIRTH,MAJOR_ID,USER_ID,TSEX FROM TEACHER WHERE T_ID=?";
    public Teacher get_Teacher_By_Id(String t_id)
    {
        Teacher teacher=new Teacher();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_BY_ID_TEACHER_SQL);
            pstm.setString(1,t_id);
            rs = pstm.executeQuery();
            if(rs.next()) {
                teacher.setT_id(rs.getString(1));
                teacher.setTno(rs.getString(2));
                teacher.setTn(rs.getString(3));
                teacher.setBirth(rs.getDate(4));
                teacher.setMajor_id(rs.getString(5));
                teacher.setUser_id(rs.getString(6));
                teacher.setSex(rs.getString(7));
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return teacher;
    }

    private static final String GET_TEACHER_SQL="SELECT T_ID,TNO,TN,BIRTH,MAJOR_ID,USER_ID,TSEX FROM TEACHER";
    public List<Teacher> get_Teacher(String sql)
    {
        List<Teacher> teachers = new ArrayList<Teacher>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            String finalsql = null;
            if(sql.equals("")){
                finalsql = GET_TEACHER_SQL;
            }
            else{
                finalsql = GET_TEACHER_SQL + " WHERE " + sql;
            }
            pstm = conn.prepareStatement(finalsql);
            rs = pstm.executeQuery();
            while(rs.next()) {
                Teacher teacher=new Teacher();
                teacher.setT_id(rs.getString(1));
                teacher.setTno(rs.getString(2));
                teacher.setTn(rs.getString(3));
                teacher.setBirth(rs.getDate(4));
                teacher.setMajor_id(rs.getString(5));
                teacher.setUser_id(rs.getString(6));
                teacher.setSex(rs.getString(7));
                teachers.add(teacher);
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return teachers;
    }

}
