package com.jc17.select.dao;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import  java.sql.Connection;
import java.sql.DriverManager;

public class GetConn {
    private final String url="jdbc:sqlserver://60.205.220.17:1433";
    private static Connection conn = null;

    public GetConn() {
    }

    public Connection GetConnection() {
        String url = this.url +";"+"databaseName=topic_select;user=jc17;password=jc172019";
        conn = null;
        if(conn==null){
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(url);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return conn;
    }
}
