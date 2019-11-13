package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TeacherDao {
    private static Connection conn=null;
    public TeacherDao(Connection conn)
    {
        this.conn=conn;
    }

    private static final String INSERT_TEACHER_SQL="INSERT INTO TEACHER VALUES(?,?,?,?,?,?)";
    public void insert_Teacher(Teacher teacher)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_TEACHER_SQL);
            pstm.setString(1,teacher.getT_id());
            pstm.setString(2,teacher.getTno());
            pstm.setString(3,teacher.getTn());
            pstm.setString(4,teacher.getSex());

            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
