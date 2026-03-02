package com.banking.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    private static HikariDataSource dataSource;

    static {
        try {
        	System.out.println("DBConnection class loaded");
        	  
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl("jdbc:mysql://localhost:3306/bankdb");
            config.setUsername("root");
            config.setPassword("root123");
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            config.setMaximumPoolSize(10);  // max connections
            config.setMinimumIdle(2);       // minimum idle
            config.setIdleTimeout(30000);   // 30 sec
            config.setConnectionTimeout(30000);

            dataSource = new HikariDataSource(config);
            System.out.println("HikariCP Connection Pool Created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}