package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Operation_logDao {
    private static Connection conn=null;
    public Operation_logDao()
    {
        this.conn = new GetConn().GetConnection();
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

    private static final String UPDATE_OPERATIONLOG_SQL="UPDATE OPERATION_LOG SET USER_ID=?,OPTIME=?,OPTYPE=?,OPSTATE=? WHERE OPLOG_ID=?";
    public void update_OperationLog(Operation_log operation_log)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_OPERATIONLOG_SQL);
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

    private static final String GET_BY_ID_OPERATIONLOG_SQL="SELECT OPLOG_ID,USER_ID,OPTIME,OPTYPE,OPSTATE FROM OPERATION_LOG WHERE OPLOG_ID=?";
    public Operation_log get_OperationLog_By_Id(String operationLog_id)
    {
        Operation_log operation_log = new Operation_log();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_BY_ID_OPERATIONLOG_SQL);
            pstm.setString(1,operationLog_id);
            rs = pstm.executeQuery();
            if(rs.next()) {
                operation_log.setOplog_id(rs.getString("OPLOG_ID"));
                operation_log.setUser_id(rs.getString("USER_ID"));
                operation_log.setOptime(rs.getDate("OPTIME"));
                operation_log.setOptype(rs.getString("OPTYPE"));
                operation_log.setOpstate(rs.getString("OPSTATE"));
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return operation_log;
    }

    private static final String GET_OPERATIONLOG_SQL="SELECT OPLOG_ID,USER_ID,OPTIME,OPTYPE,OPSTATE FROM OPERATION_LOG";
    public List<Operation_log> get_(String sql)
    {
        List<Operation_log> operation_logs = new ArrayList<Operation_log>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            String finalsql = null;
            if(sql.equals("")){
                finalsql = GET_OPERATIONLOG_SQL;
            }
            else{
                finalsql= GET_OPERATIONLOG_SQL+ " WHERE " + sql;
            }
            rs = pstm.executeQuery(finalsql);
            while(rs.next()) {
                Operation_log operation_log = new Operation_log();
                operation_log.setOplog_id(rs.getString("OPLOG_ID"));
                operation_log.setUser_id(rs.getString("USER_ID"));
                operation_log.setOptime(rs.getDate("OPTIME"));
                operation_log.setOptype(rs.getString("OPTYPE"));
                operation_log.setOpstate(rs.getString("OPSTATE"));
                operation_logs.add(operation_log);
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return operation_logs;
    }
}
