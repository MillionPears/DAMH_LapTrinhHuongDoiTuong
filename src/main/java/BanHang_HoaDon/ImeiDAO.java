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
import model.Imei;
import model.NhaCungCap;


/**
 *
 * @author ASUS
 */
public class ImeiDAO {
    public static boolean insert (Imei im) throws Exception
    {
        
        
        String sql="insert into IMEI(SOSERI,MASP,TRANGTHAI) values(?,?,?)";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1,im.getSoSeri());
             psmt.setString(2,im.getMaSp());
              psmt.setInt(3,im.getTrangThai());
            
            return psmt.executeUpdate()>0;
        }
    }
     public static Imei findById (String seri) throws Exception
    {
        //? tìm thấy ..hỏi có muốn khôi phục kh
        String sql="select TRANGTHAI from IMEI  where SOSERI=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, seri);
            ResultSet rs=psmt.executeQuery();
            if(rs.next())
            {
                Imei im=createImei(rs);
                return im;
            }
        }
        return null;
    }
     public static Imei findByMaSp (String masp) throws Exception
    {
        //? tìm thấy ..hỏi có muốn khôi phục kh
        String sql="select TRANGTHAI from IMEI  where MASP=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, masp);
            ResultSet rs=psmt.executeQuery();
            if(rs.next())
            {
                Imei im=createImei(rs);
                return im;
            }
        }
        return null;
    }

    private static Imei createImei(ResultSet rs) throws SQLException {
  
        Imei im=new Imei();
       // im.setSoSeri(rs.getString("SOSERI"));
        im.setTrangThai(rs.getInt("TRANGTHAI"));
        return im;
    }
    public static boolean updateTrangThai0_1 (Imei im) throws Exception
    {

        String sql="update IMEI set TRANGTHAI=? where MASP=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(2, im.getMaSp());
            psmt.setInt(1, im.getTrangThai());
           
           
            return psmt.executeUpdate()>0;
        }
    }
     public static boolean delete (String masp) throws Exception
    {
        String sql="delete from IMEI  where MASP=? AND TRANGTHAI=0 OR TRANGTHAI=10";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, masp);
           
            return psmt.executeUpdate()>0;
        }
    }
}
