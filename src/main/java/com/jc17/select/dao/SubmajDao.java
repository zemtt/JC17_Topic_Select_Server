package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class SubmajDao {
    private static Connection conn=null;
    public SubmajDao()
    {
        this.conn = new GetConn().GetConnection();
    }

    private static final String INSERT_SUBMAJ_SQL="INSERT INTO SUBMAJ VALUES(replace(NEWID(),'-',''),?,?)";
    public void insert_Submaj(Submaj submaj)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_SUBMAJ_SQL);
            //pstm.setString(1,submaj.getSubmaj_id());
            pstm.setString(1,submaj.getSub_id());
            pstm.setString(2,submaj.getMajor_id());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_SUBMAJ_SQL="UPDATE SUBMAJ SET SUB_ID=?, MAJOR_ID=? WHERE SUBMAJ_ID=?";
    public void update_Submaj(Submaj submaj)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_SUBMAJ_SQL);
            pstm.setString(3,submaj.getSubmaj_id());
            pstm.setString(1,submaj.getSub_id());
            pstm.setString(2,submaj.getMajor_id());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_SUBMAJ_SQL="DELETE FROM SUBMAJ WHERE SUBMAJ_ID=?";
    public void delete_Submaj(String submajId)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_SUBMAJ_SQL);
            pstm.setString(1,submajId);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String GET_BY_ID_SUBMAJ_SQL="SELECT SUBMAJ_ID,SUB_ID,MAJOR_ID FROM SUBMAJ WHERE SUBMAJ_ID=?";
    public Submaj get_Submaj_By_Id(String submaj_id)
    {
        Submaj submaj=new Submaj();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_BY_ID_SUBMAJ_SQL);
            pstm.setString(1,submaj_id);
            rs = pstm.executeQuery();
            if(rs.next()) {
                submaj.setSubmaj_id(rs.getString(1));
                submaj.setSub_id(rs.getString(2));
                submaj.setMajor_id(rs.getString(3));
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return submaj;
    }

    private static final String GET_SUBMAJ_SQL="SELECT SUBMAJ_ID,SUB_ID,MAJOR_ID FROM SUBMAJ";
    public List<Submaj> get_Submaj(String sql)
    {
        List<Submaj> Submajs = new ArrayList<Submaj>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            String finalsql = null;
            if(sql.equals("")){
                finalsql = GET_SUBMAJ_SQL;
            }
            else{
                finalsql = GET_SUBMAJ_SQL + " WHERE " + sql;
            }
            pstm = conn.prepareStatement(finalsql);
            rs = pstm.executeQuery();
            while(rs.next()) {
                Submaj submaj= new Submaj();
                submaj.setSubmaj_id(rs.getString(1));
                submaj.setSub_id(rs.getString(2));
                submaj.setMajor_id(rs.getString(3));
                Submajs.add(submaj);
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return Submajs;
    }
}
