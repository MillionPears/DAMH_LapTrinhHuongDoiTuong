/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BanHang_HoaDon;

import helper.DataBaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ThongKeDao {
     public static int getTienTheoThang (int thang) throws Exception
        {
            String sql="select tien=sum(tongTien) from HOA_DON where MONTH(NGAYLAP)="+thang;

            try(Connection conn=DataBaseHelper.openConnection();
                    PreparedStatement psmt=conn.prepareStatement(sql);
                    ){

                ResultSet rs=psmt.executeQuery();

                int sl=0;
               
                while(rs.next())
                {
                    
                         sl=(rs.getInt("tien"));
                         return sl;
                    

                }
                return sl;
            }}
     
     
     public static int getLoiNhuanTheoHoaDon (String soDH) throws Exception
        {
            
            String sql="SELECT LOI=SUM(LOINHUAN) FROM(SELECT LOINHUAN=TIEN-GIANHAP,SOHD " +
                        "FROM(select MASP,SOHD, TIEN=DONGIA*SOLUONG-(DONGIA*SOLUONG*20/100) " +
                        "from CT_HOA_DON where SOHD="+soDH+") TMP INNER JOIN (SELECT MASP,GIANHAP FROM PHIEU_NHAP ) " +
                        "TMP2  ON TMP.MASP=TMP2.MASP ) TMP3 WHERE SOHD="+soDH;

            try(Connection conn=DataBaseHelper.openConnection();
                    PreparedStatement psmt=conn.prepareStatement(sql);
                    ){

                ResultSet rs=psmt.executeQuery();

                int sl=0;
               
                while(rs.next())
                {
                    
                         sl=(rs.getInt("LOI"));
                         return sl;
                    

                }
                return sl;
            }}
     
     public static ArrayList<String> getSoHDchoThang (int thang) throws Exception
        {
            String sql="select SOHD from HOA_DON where MONTH(NGAYLAP)="+thang;
            ArrayList<String>list=new ArrayList<>();
            try(Connection conn=DataBaseHelper.openConnection();
                    PreparedStatement psmt=conn.prepareStatement(sql);
                    ){

                ResultSet rs=psmt.executeQuery();

                
               
                while(rs.next())
                {
                    
                        
                         list.add((rs.getString("SOHD")));
                         System.out.print(thang+"-:"+rs.getString("SOHD")+"\t");
                         System.out.println("\n");
                         
                        
                    

                }
                return list;
            }}
     
}
