package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Operation_logDao {
    private static Connection conn=null;
    public Operation_logDao(Connection conn)
    {
        this.conn=conn;
    }

    private static final String INSERT_OPERATIONLOG_SQL="INSERT INTO OPERATION_LOG VALUES(?,?.?,?,?)";
    public void insert_OperationLog(Operation_log operation_log)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_OPERATIONLOG_SQL);
            pstm.setString(1,operation_log.getOplog_id());
            pstm.setString(2,operation_log.getUser_id());
            pstm.setDate(3,operation_log.getOptime());
            pstm.setString(4,operation_log.getOptype());
            pstm.setString(5,operation_log.getOpstate());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_OPERATIONLOG_SQL="UPDATE OPERATION_LOG SET USER_ID=?,OPTIME=?,OPTYPE=?,OPTYPE=?,OPSTATE=? WHERE OPLOG_ID=?";
    public void update_OperationLog(Operation_log operation_log)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_OPERATIONLOG_SQL);
            pstm.setString(5,operation_log.getOplog_id());
            pstm.setString(1,operation_log.getUser_id());
            pstm.setDate(2,operation_log.getOptime());
            pstm.setString(3,operation_log.getOptype());
            pstm.setString(4,operation_log.getOpstate());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_OPERATIONLOG_SQL="DELETE FROM OPERATION_LOG WHERE OPLOG_ID=?";
    public void delete_OperationLog(String oplogId)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_OPERATIONLOG_SQL);
            pstm.setString(1,oplogId);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
