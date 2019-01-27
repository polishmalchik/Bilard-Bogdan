package dao;

import util.StartGame;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BilardDbImpl extends DbAdapter {

    public void createTables() {
        try {
            stnt = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS Players (" + "palyer_id SERIAL PRIMARY KEY NOT NULL, " + "name VARCHAR(100) NOT NULL)";
            stnt.executeUpdate(sql);
            stnt.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void setup (String name) {
        insertPlayer(name);
        System.out.println("Wrzucam gracza " + name);
    }

    public void insertPlayer(String name) {

        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Players (" + "name) " + "VALUES(?)");
            st.setString(1, name);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
