package com.mapa.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    private static ConnectionUtils instance;
    private static Connection conn;

    private ConnectionUtils(){ }

    public static ConnectionUtils getInstance() {
        if (instance == null)
            instance = new ConnectionUtils();
        return instance;
    }

    public Connection getConnection() {
        if (conn == null) {
            try {
              //  Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection
                        ("jdbc:mysql://localhost:3306/agenda?allowMultipleQueries=true", "root", "mapa1234");
            }
            catch (SQLException e) {
                System.out.println("Could not connect to database");
            }
           // catch (ClassNotFoundException e) {
             //   System.out.println("com.mysql.jdbc.Driver not found");
           // }
        }
        return conn;
    }
}
