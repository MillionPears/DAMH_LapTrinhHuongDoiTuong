/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.sql.*;
public class DataBaseHelper {
    public static Connection openConnection() throws ClassNotFoundException, SQLException {
      
             String hostName ="localhost";
        String dataBaseName ="Mobile_Store";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectURL = "jdbc:sqlserver://" +hostName + ":1433;DatabaseName=" + dataBaseName + ";encrypt=true;trustServerCertificate=true;";
        String username = "sa";
        String password = "19001005";
        Connection conn = (Connection)DriverManager.getConnection(connectURL, username, password);
       
        
        return conn;
       
    }
   
    
}
