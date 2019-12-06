package com.jc17.select.dao;

import com.jc17.select.dao.utils.GetConn;
import com.jc17.select.instances.Commu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommuDao {
    private static Connection conn=null;
    public CommuDao() { this.conn = new GetConn().GetConnection();}
    private static final String INSERT_COMMU_SQL="INSERT INTO COMMU VALUES(replace(NEWID(),'-',''),?,?,?,?,?,?)";
    public void insert_Commu(Commu commu)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_COMMU_SQL);
            //pstm.setString(1,commu.getCommu_id());
            pstm.setDate(1,commu.getComtime());
            pstm.setString(2,commu.getSender_id());
            pstm.setString(3,commu.getReceiver_id());
            pstm.setString(4,commu.getContent());
            pstm.setInt(5,commu.getReaded());
            pstm.setString(6,commu.getCommu_title());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_COMMU_SQL="UPDATE COMMU SET COTIME=?,SENDER_ID=?,RECIEVER_ID=?,CONTENT=?,READED=?,COMMU_TITLE=? WHERE COMMU_ID=?";
    public void update_Commu(Commu commu)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_COMMU_SQL);
            pstm.setString(7,commu.getCommu_id());
            pstm.setDate(1,commu.getComtime());
            pstm.setString(2,commu.getSender_id());
            pstm.setString(3,commu.getReceiver_id());
            pstm.setString(4,commu.getContent());
            pstm.setInt(5,commu.getReaded());
            pstm.setString(6,commu.getCommu_title());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_COMMU_SQL="DELETE FROM COMMU WHERE COMMU_ID=?";
    public void delete_Commu(String commu_id)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_COMMU_SQL);
            pstm.setString(1,commu_id);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String GET_BY_ID_COMMU_SQL="SELECT COMMU_ID,COTIME,SENDER_ID,RECIEVER_ID,CONTENT,READED,COMMU_TITLE FROM COMMU WHERE COMMU_ID=?";
    public Commu get_Commu_By_Id(String commu_id)
    {
        Commu commu = new Commu();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_BY_ID_COMMU_SQL);
            pstm.setString(1,commu_id);
            rs = pstm.executeQuery();
            if(rs.next()) {
                commu.setCommu_id(rs.getString("COMMU_ID"));
                commu.setComtime(rs.getDate("COTIME"));
                commu.setSender_id(rs.getString("SENDER_ID"));
                commu.setReceiver_id(rs.getString("RECIEVER_ID"));
                commu.setContent(rs.getString("CONTENT"));
                commu.setReaded(rs.getInt("READED"));
                commu.setCommu_title(rs.getString("COMMU_TITLE"));
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return commu;
    }

    private static final String GET_COMMU_SQL="SELECT COMMU_ID,COTIME,SENDER_ID,RECIEVER_ID,CONTENT,READED,COMMU_TITLE FROM COMMU";
    public List<Commu> get_Commu(String sql)
    {
        List<Commu> commus = new ArrayList<Commu>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            String finalsql = null;
            if(sql.equals("")){
                finalsql = GET_COMMU_SQL;
            }
            else{
                finalsql=GET_COMMU_SQL + " WHERE " + sql;
            }
            //rs = pstm.executeQuery(finalsql);
            pstm = conn.prepareStatement(finalsql);
            rs = pstm.executeQuery();
            while(rs.next()) {
                Commu commu = new Commu();
                commu.setCommu_id(rs.getString("COMMU_ID"));
                commu.setComtime(rs.getDate("COTIME"));
                commu.setSender_id(rs.getString("SENDER_ID"));
                commu.setReceiver_id(rs.getString("RECIEVER_ID"));
                commu.setContent(rs.getString("CONTENT"));
                commu.setReaded(rs.getInt("READED"));
                commu.setCommu_title(rs.getString("COMMU_TITLE"));
                commus.add(commu);
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return commus;
    }


}
