package com.jc17.select.dao;


import java.sql.Date;

public class Test {
    public static void main(String args[])
    {
        GetConn conn=new GetConn("jc17","jc172019","topic_select");

//        Student student=new Student();
//        student.setSex("男");
//        student.setSno("171002602");
//        student.setSn("我");
//        student.setBirth(new Date(1999,7,9));
//        student.setMajor_id("1111");
//        student.setS_id("03");
//        student.setSelect_question("0");

        User_table user_table=new User_table();
        user_table.setUser_id("01");
        user_table.setUser_account("hhh");
        user_table.setPassword("998");
        user_table.setRights(1);
        User_tableDao user_tableDao=new User_tableDao(conn.GetConnection());
        user_tableDao.insert_UserTable(user_table);

        user_table.setUser_account("kkk");
        user_tableDao.update_UserTable(user_table);
    }
}
