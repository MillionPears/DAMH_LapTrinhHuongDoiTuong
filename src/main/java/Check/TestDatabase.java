/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Check;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ACER
 */
public class TestDatabase {
    
    public static Connection getDBConnect(){
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
            System.out.println("Chưa có Driver"+e.toString());
        }
        try {
            String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS01:1433;database=Mobile_Store;trustServerCertificate=true;";
            String username = "sa";
            String password = "12345678";
            con = DriverManager.getConnection(connectionUrl, username, password);
            return con;
        } catch (Exception e) {
            //Lỗi sai tên database
            //Lỗi tên đăng nhập và mật khẩu
            System.out.println("Lỗi connect"+e.toString());
        }
        return null;
    }
}
