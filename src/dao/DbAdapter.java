package dao;

import util.StartGame;

import java.sql.*;

public class DbAdapter {

    String jdbcUrl = "jdbc:postgresql://localhost:5432/Bilard";
    String username = "postgres";
    String password = "postgre";

    Connection conn = null;
    Statement stnt = null;
    ResultSet rs = null;


    public DbAdapter() {

    }

    public void connect () {
        try {

            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Polaczono z baza danych");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect () {
        try {

            if (stnt != null) {
                stnt.close();
            }
            if (rs !=null){
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
