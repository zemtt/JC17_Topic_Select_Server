package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReportDao {
    private static Connection conn=null;
    public ReportDao(Connection conn)
    {
        this.conn=conn;
    }

    private static final String INSERT_REPORT_SQL="INSERT INTO REPORT VALUES(?,?.?,?,?,?)";
    public void insert_Report(Report report)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(INSERT_REPORT_SQL);
            pstm.setString(1,report.getReport_id());
            pstm.setString(2,report.getS_id());
            pstm.setString(3,report.getSub_id());
            pstm.setString(4,report.getRepattribute());
            pstm.setFloat(5, report.getScore());
            pstm.setInt(6,report.getMarked());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_REPORT_SQL="UPDATE REPORT SET S_ID=?,SUB_ID=?,REPATTRIBUTE=?,SCORE=?,MARKED=? WHERE REPORT_ID=?";
    public void update_Report(Report report)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_REPORT_SQL);
            pstm.setString(6,report.getReport_id());
            pstm.setString(1,report.getS_id());
            pstm.setString(2,report.getSub_id());
            pstm.setString(3,report.getRepattribute());
            pstm.setFloat(4, report.getScore());
            pstm.setInt(5,report.getMarked());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_REPORT_SQL="DELETE FROM REPORT WHERE REPORT_ID=?";
    public void delete_REPORT(String reportId)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(DELETE_REPORT_SQL);
            pstm.setString(1,reportId);
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
