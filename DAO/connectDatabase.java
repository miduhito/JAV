package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class connectDatabase {
    protected Connection conn = null;

    static final String url = "jdbc:mysql://localhost:3306/fastfood";
    static final String nameUser = "root";
    static final String pass = "Tuan171204@";

    public boolean openConnectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, nameUser, pass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnectDB() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        connectDatabase newCon = new connectDatabase();
        newCon.openConnectDB();
        newCon.closeConnectDB();
    }
}