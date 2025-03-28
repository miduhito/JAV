package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class connectDatabase {
    private Connection con;

    public Connection getCon() {
        return this.con;
    }

    public void connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/";
            String nameDatabase = "fastfood";
            String user = "root";
            String password = "Tuan171204@";  // pass user 'root' của ai thì tự sửa lại nha
            this.con = DriverManager.getConnection(url + nameDatabase, user, password);
            System.out.println("Kết nối thành công!");
            Statement s = con.createStatement();
        } catch (Exception e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Đóng kết nối thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        connectDatabase newCon = new connectDatabase();
        newCon.connect();
        newCon.close();
    }
}