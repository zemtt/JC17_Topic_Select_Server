package com.jc17.select.dao;
import  java.sql.Connection;
import java.sql.DriverManager;

public class GetConn {
    private String username;
    private String password;
    private String database;
    private final String url="jdbc:sqlserver://60.205.220.17:1433";

    public GetConn(String username,String password,String database)
    {
        this.username=username;
        this.password=password;
        this.database=database;
    }

    public Connection GetConnection()
    {
        String url = this.url +";"+"databaseName="+this.database+";"+"user="+this.username+";"+"password="+this.password;
        Connection conn = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url);
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
