package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SelectDao {
    private static Connection conn=null;
    public SelectDao(Connection conn)
    {
        this.conn=conn;
    }

    private static final String INSERT_SELECT_SQL="INSERT INTO SELECT VALUES(?,?,?,?)";
    public void insert_Select(Select select)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_SELECT_SQL);
            pstm.setString(1,select.getSelect_id());
            pstm.setString(2,select.getS_id());
            pstm.setString(3,select.getSub_id());
            pstm.setInt(4,select.getSelected());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_SELECT_SQL="UPDATE SELECT SET S_ID=?, SUB_ID=?,SELECTED=? WHERE SELECT_ID=?";
    public void update_Select(Select select)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_SELECT_SQL);
            pstm.setString(4,select.getSelect_id());
            pstm.setString(1,select.getS_id());
            pstm.setString(2,select.getSub_id());
            pstm.setInt(3,select.getSelected());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_SELECT_SQL="DELETE FROM SELECT WHERE SELECT_ID=?";
    public void delete_Select(String select_id)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_SELECT_SQL);
            pstm.setString(1,select_id);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String GET_BY_ID_SELECT_SQL="SELECT SELECT_ID,S_ID,SUB_ID,SELECTED FROM SELECT WHERE SELECT_ID=?";
    public Select get_Select_By_Id(String select_id)
    {
        Select select = new Select();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_BY_ID_SELECT_SQL);
            pstm.setString(1,select_id);
            rs = pstm.executeQuery(GET_BY_ID_SELECT_SQL);
            if(rs.next()) {
                select.setSelect_id(rs.getString("SELECT_ID"));
                select.setS_id(rs.getString("S_ID"));
                select.setSub_id(rs.getString("SUB_ID"));
                select.setSelected(rs.getInt("SELECTED"));
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return select;
    }

    private static final String GET_SELECT_SQL="SELECT SELECT_ID,S_ID,SUB_ID,SELECTED FROM SELECT";
    public List<Select> get_Select(String sql)
    {
        List<Select> selects = new ArrayList<Select>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_SELECT_SQL);
            rs = pstm.executeQuery(GET_SELECT_SQL);
            String finalsql = null;
            if(sql.equals("")){
                finalsql = GET_SELECT_SQL;
            }
            else{
                finalsql= GET_SELECT_SQL + "WHERE" + sql;
            }
            rs = pstm.executeQuery(finalsql);
            while(rs.next()) {
                Select select = new Select();
                select.setSelect_id(rs.getString("SELECT_ID"));
                select.setS_id(rs.getString("S_ID"));
                select.setSub_id(rs.getString("SUB_ID"));
                select.setSelected(rs.getInt("SELECTED"));
                selects.add(select);
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return selects;
    }
}
