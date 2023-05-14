package com.webapp.firstwebapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    private static String url = "jdbc:mysql://localhost:3306/registration";
    private static String username = "root";
    private static String password = "root";
    private static Connection connection;

    private MyConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if(connection==null){
            new MyConnection();
        }
        return connection;
    }

    public static void stop(){
        if(connection!=null){
            try{
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
