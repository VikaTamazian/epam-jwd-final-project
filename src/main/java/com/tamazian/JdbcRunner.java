package com.tamazian;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) {
//        String className = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/start";
//        String dbUrl = "jdbc:mysql://127.0.0.1:3306/start?serverTimezone=Europe/Minsk";
        String user = "root";
        String password = "root";

        try {
//            Class.forName(className);
            Connection cn = null;
            Statement st = null;
            ResultSet rs = null;
            try {
                cn = DriverManager.getConnection(dbUrl, user, password);
                System.out.println(DriverManager.getDriver(dbUrl).getClass().getName());
                System.out.println(cn.getClass().getName());
                st = cn.createStatement();
                rs = st.executeQuery("SELECT account, mark FROM results");
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " => " + rs.getInt(2));
                }
            } finally {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (cn != null) {
                    cn.close();
                }
            }
        }catch (/*ClassNotFoundException |*/SQLException e) {
            e.printStackTrace();
        }
    }
}

