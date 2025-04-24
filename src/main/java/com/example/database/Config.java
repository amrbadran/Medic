package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Config {
    public Connection con;
    public static String user = "amr";
    public static String pass = "root";
    public static String DB = "medicinewarehouse";
    public Statement makeStatemnt() throws Exception{
        DriverManager.registerDriver(new org.postgresql.Driver());
        String ConnectionInfo = "jdbc:postgresql://localhost:5432/postgres";
        con = DriverManager.getConnection(ConnectionInfo,Config.user, Config.pass);

        Statement stm = con.createStatement();
        return stm;
    }
    public void closeConnection() throws Exception{

        con.close();
    }
}
