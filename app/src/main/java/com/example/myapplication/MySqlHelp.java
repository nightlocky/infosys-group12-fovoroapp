package com.example.myapplication;

import android.nfc.Tag;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySqlHelp {
    public static int getUserSize(){
        final String cls = "com.mysql.cj.jdbc.Driver";
        final String url = "jdbc:mysql://localhost:3306/sutd";
        final String username = "root";
        final String password = "qhc5211314";

        int count = 0;
        try{
            Class.forName(cls);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select count(1) as s1 from as_userList";
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()){
                count = resultSet.getInt("s1");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return count;
    }
}
