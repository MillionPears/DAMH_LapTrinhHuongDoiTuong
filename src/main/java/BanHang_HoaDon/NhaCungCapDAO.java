/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BanHang_HoaDon;
import helper.DataBaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.NhaCungCap;


/**
 *
 * @author ASUS
 */
public class NhaCungCapDAO {
     public static boolean insert (NhaCungCap ncc) throws Exception
    {
        
        
        String sql="insert into NHA_CUNG_CAP(TENNCC) values(?)";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1,ncc.getTenNhaCungCap());
            
         
            
            return psmt.executeUpdate()>0;
        }
    }
     public static ArrayList<NhaCungCap> findAll () throws Exception
    {
        String sql="select * from NHA_CUNG_CAP";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            ResultSet rs=psmt.executeQuery();
            ArrayList<NhaCungCap> list=new ArrayList<>();
            while(rs.next())
            {
                NhaCungCap ncc=createNhaCungCap(rs);
                list.add(ncc);
            }
            return list;
        }
      
    }
     public static int findByName (String ten) throws Exception
    {
        
        String sql="select * from NHA_CUNG_CAP  where TENNCC=N'"+ten+"'";
        
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
          
            ResultSet rs=psmt.executeQuery();
           
            if(rs.next())
            {
                
                return rs.getInt("MANCC");
            }
        }
        return 0;
    }

    private static NhaCungCap createNhaCungCap(ResultSet rs) throws SQLException {
        NhaCungCap ncc=new NhaCungCap();
        
        ncc.setTenNhaCungCap(rs.getString("TENNCC"));
      
        return ncc;
    }
     public static NhaCungCap findByTenNCC (String ten) throws Exception
    {
        
        String sql="select * from NHA_CUNG_CAP  where TENNCC=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, ten);
            ResultSet rs=psmt.executeQuery();
            if(rs.next())
            {
                NhaCungCap ncc=createNhaCungCap(rs);
                return ncc;
            }
            
        }
       
        return null;
    }
      public static NhaCungCap findById (int id) throws Exception
    {
        //? tìm thấy ..hỏi có muốn khôi phục kh
        String sql="select TENNCC from NHA_CUNG_CAP  where MANCC=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setInt(1, id);
            ResultSet rs=psmt.executeQuery();
            if(rs.next())
            {
                NhaCungCap ncc=createNhaCungCap(rs);
                return ncc;
            }
        }
        return null;
    }
}
