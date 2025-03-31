package BUS;
import java.sql.*;
public class KetNoiCSDL {
    public KetNoiCSDL(){
        
    }
    public static void main(String[] args) {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://MIZUHITO:1433;DatabaseName=FastFoodSQL" ;
            String username="sa"; String password = "root";
            Connection con =DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Ketnoithanhcong");
            } catch(Exception e){
                System.out.println("cailonma");
                System.out.println(e); }
    }
}
