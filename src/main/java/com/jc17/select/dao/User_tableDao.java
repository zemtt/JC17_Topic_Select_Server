package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class User_tableDao {

    private static Connection conn=null;
    public User_tableDao(Connection conn)
    {
        this.conn=conn;
    }

    private static final String INSERT_USERTABLE_SQL="INSERT INTO USER_TABLE VALUES(?,?,?,?)";
    public void insert_UserTable(User_table user_table)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_USERTABLE_SQL);
            pstm.setString(1,user_table.getUser_id());
            pstm.setString(2,user_table.getUser_account());
            pstm.setString(3,user_table.getPassword());
            pstm.setInt(4,user_table.getRights());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_USERTABLE_SQL="UPDATE USER_TABLE SET USER_ACCOUNT=?,PASSWORD=?,RIGHTS=? WHERE USER_ID=?";
    public void update_UserTable(User_table user_table)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_USERTABLE_SQL);
            pstm.setString(4,user_table.getUser_id());
            pstm.setString(1,user_table.getUser_account());
            pstm.setString(2,user_table.getPassword());
            pstm.setInt(3,user_table.getRights());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_USERTABLE_SQL="DELETE FROM USER_TABLE WHERE USER_ID=?";
    public void delete_UserTable(String user_id)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_USERTABLE_SQL);
            pstm.setString(1,user_id);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
