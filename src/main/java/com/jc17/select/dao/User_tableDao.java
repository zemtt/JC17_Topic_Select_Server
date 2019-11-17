package com.jc17.select.dao;

import com.jc17.select.dao.utils.GetConn;
import com.jc17.select.instances.User_table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class User_tableDao {

    private static Connection conn = null;

    public User_tableDao() {
        this.conn = new GetConn().GetConnection();
    }

    private static final String INSERT_USERTABLE_SQL = "INSERT INTO USER_TABLE VALUES(replace(NEWID(),'-',''),?,?,?)";
    //第一个参数用SQL server自动生成ID直接写了

    public void insert_UserTable(User_table user_table) {
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(INSERT_USERTABLE_SQL);
            pstm.setString(1, user_table.getUser_account());
            pstm.setString(2, user_table.getPassword());
            pstm.setInt(3, user_table.getRights());
            pstm.executeUpdate();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String UPDATE_USERTABLE_SQL = "UPDATE USER_TABLE SET USER_ACCOUNT=?,PASSWORD=?,RIGHTS=? WHERE USER_ID=?";

    public void update_UserTable(User_table user_table) {
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(UPDATE_USERTABLE_SQL);
            pstm.setString(4, user_table.getUser_id());
            pstm.setString(1, user_table.getUser_account());
            pstm.setString(2, user_table.getPassword());
            pstm.setInt(3, user_table.getRights());
            pstm.executeUpdate();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String DELETE_USERTABLE_SQL = "DELETE FROM USER_TABLE WHERE USER_ID=?";

    public void delete_UserTable(String user_id) {
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(DELETE_USERTABLE_SQL);
            pstm.setString(1, user_id);
            pstm.executeUpdate();

            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String GET_BY_ID_USERTABLE_SQL = "SELECT USER_ID,USER_ACCOUNT,PASSWORD,RIGHTS FROM USER_TABLE WHERE USER_ID=?";

    public User_table get_User_Table_By_Id(String user_id) {
        User_table user_table = new User_table();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(GET_BY_ID_USERTABLE_SQL);
            pstm.setString(1, user_id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                user_table.setUser_id(rs.getString("USER_ID"));
                user_table.setUser_account(rs.getString("USER_ACCOUNT"));
                user_table.setPassword(rs.getString("PASSWORD"));
                user_table.setRights(rs.getInt("RIGHTS"));
            }
            rs.close();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_table;
    }

    private static final String GET_USERTABLE_SQL = "SELECT USER_ID,USER_ACCOUNT,PASSWORD,RIGHTS FROM USER_TABLE";

    public List<User_table> get_User_Table(String sql) {
        List<User_table> user_tables = new ArrayList<User_table>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
//            pstm = conn.prepareStatement(GET_USERTABLE_SQL);
//            rs = pstm.executeQuery(GET_USERTABLE_SQL);
            String finalsql = null;
            if (sql.equals("")) {
                finalsql = GET_USERTABLE_SQL;
            } else {
                finalsql = GET_USERTABLE_SQL + " WHERE " + sql;
            }
            pstm = conn.prepareStatement(finalsql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                User_table user_table = new User_table();
                user_table.setUser_id(rs.getString("USER_ID"));
                user_table.setUser_account(rs.getString("USER_ACCOUNT"));
                user_table.setPassword(rs.getString("PASSWORD"));
                user_table.setRights(rs.getInt("RIGHTS"));
                user_tables.add(user_table);
            }
            rs.close();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_tables;
    }

    public List<User_table> get_User_Table_Row(String num, String skip) {//用于输出用户指定范围的元组的SQl语句lzx写的
        List<User_table> user_tables = new ArrayList<User_table>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
//            pstm = conn.prepareStatement(GET_USERTABLE_SQL);
//            rs = pstm.executeQuery(GET_USERTABLE_SQL);
            String finalsql = null;
            if (num.equals("")) {
                finalsql = GET_USERTABLE_SQL;
            } else {
                finalsql = "SELECT TOP " + num + " USER_ID,USER_ACCOUNT,PASSWORD,RIGHTS FROM (SELECT TOP " +
                        (Integer.parseInt(num) + Integer.parseInt(skip))
                        + " * FROM USER_TABLE ORDER BY USER_ID) as tbl2 ORDER BY USER_ID DESC ";
            }//SELECT TOP 10 * FROM (SELECT TOP 20 * FROM tblORDER BY id) as tbl2 ORDER BY tbl2.id DESC
            pstm = conn.prepareStatement(finalsql);
            System.out.println(finalsql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                User_table user_table = new User_table();
                user_table.setUser_id(rs.getString("USER_ID"));
                user_table.setUser_account(rs.getString("USER_ACCOUNT"));
                user_table.setPassword(rs.getString("PASSWORD"));
                user_table.setRights(rs.getInt("RIGHTS"));
                user_tables.add(user_table);
            }
            rs.close();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_tables;
    }
}
