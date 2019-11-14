package com.jc17.select.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {
    private static Connection conn=null;
    public ReportDao(Connection conn)
    {
        this.conn=conn;
    }

    private static final String INSERT_REPORT_SQL="INSERT INTO REPORT VALUES(?,?,?,?,?,?,?)";
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
            pstm.setString(7,report.getContent());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String UPDATE_REPORT_SQL="UPDATE REPORT SET S_ID=?,SUB_ID=?,REPATTRIBUTE=?,SCORE=?,MARKED=?,CONTENT=? WHERE REPORT_ID=?";
    public void update_Report(Report report)
    {
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(UPDATE_REPORT_SQL);
            pstm.setString(7,report.getReport_id());
            pstm.setString(1,report.getS_id());
            pstm.setString(2,report.getSub_id());
            pstm.setString(3,report.getRepattribute());
            pstm.setFloat(4, report.getScore());
            pstm.setInt(5,report.getMarked());
            pstm.setString(6,report.getContent());
            pstm.executeUpdate();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final String DELETE_REPORT_SQL="DELETE FROM REPORT WHERE REPORT_ID=?";
    public void delete_Report(String reportId)
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

    private static final String GET_BY_ID_REPORT_SQL="SELECT REPORT_ID,S_ID,SUB_ID,REPATTRIBUTE,SCORE,MARKED,CONTENT FROM REPORT WHERE REPORT_ID=?";
    public Report get_Report_By_Id(String reportId)
    {
        Report report = new Report();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_BY_ID_REPORT_SQL);
            pstm.setString(1,reportId);
            rs = pstm.executeQuery(GET_BY_ID_REPORT_SQL);
            if(rs.next()) {
                report.setReport_id(rs.getString("REPORT_ID"));
                report.setS_id(rs.getString("S_ID"));
                report.setSub_id(rs.getString("SUB_ID"));
                report.setRepattribute(rs.getString("REPATTRIBUTE"));
                report.setScore(rs.getFloat("SCORE"));
                report.setMarked(rs.getInt("MARKED"));
                report.setContent(rs.getString("CONTENT"));
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return report;
    }

    private static final String GET_REPORT_SQL="SELECT REPORT_ID,S_ID,SUB_ID,REPATTRIBUTE,SCORE,MARKED,CONTENT FROM REPORT";
    public List<Report> get_Report(String sql)
    {
        List<Report> reports= new ArrayList<Report>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            pstm = conn.prepareStatement(GET_REPORT_SQL);
            rs = pstm.executeQuery(GET_REPORT_SQL);
            String finalsql = null;
            if(sql.equals("")){
                finalsql = GET_REPORT_SQL;
            }
            else{
                finalsql= GET_REPORT_SQL+ "WHERE" + sql;
            }
            rs = pstm.executeQuery(finalsql);
            while(rs.next()) {
                Report report = new Report();
                report.setReport_id(rs.getString("REPORT_ID"));
                report.setS_id(rs.getString("S_ID"));
                report.setSub_id(rs.getString("SUB_ID"));
                report.setRepattribute(rs.getString("REPATTRIBUTE"));
                report.setScore(rs.getFloat("SCORE"));
                report.setMarked(rs.getInt("MARKED"));
                report.setContent(rs.getString("CONTENT"));
                reports.add(report);
            }
            rs.close();
            pstm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return reports ;
    }
}
