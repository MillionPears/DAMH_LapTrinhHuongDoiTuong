/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BanHang_HoaDon;

import helper.DataBaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.TaiKhoan;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author ASUS
 */
public class TaiKhoanDao {
       public static boolean insert (TaiKhoan tk) throws Exception
    {
        
        
        String sql="insert into TAI_KHOAN_DN(MANV,TENDN,MATKHAU,VAITRO,TRANGTHAI) values(?,?,?,?,?)";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1,tk.getMaNV());
            psmt.setString(2, tk.getTenDangNhap());
            psmt.setString(3, tk.getMatKhau());
            psmt.setString(4, tk.getVaiTro());
            psmt.setInt(5, tk.getTrangThai());
           
         
            
            return psmt.executeUpdate()>0;
        }
    }
        public static boolean update (TaiKhoan tk) throws Exception
        {

            String sql="update TAI_KHOAN_DN set TENDN=?, MATKHAU=?, VAITRO=?, TRANGTHAI=? where MANV=?";

            try(Connection conn=DataBaseHelper.openConnection();
                    PreparedStatement psmt=conn.prepareStatement(sql);
                    ){
                psmt.setString(5,tk.getMaNV());
                psmt.setString(1, tk.getTenDangNhap());
                psmt.setString(2, tk.getMatKhau());
                psmt.setString(3, tk.getVaiTro());
                psmt.setInt(4, tk.getTrangThai());
               


                return psmt.executeUpdate()>0;
            }
        }
         public static boolean updateTrangThai0_1 (TaiKhoan tk) throws Exception
    {

        String sql="update TAI_KHOAN_DN set TRANGTHAI=? where MANV=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(2, tk.getMaNV());
            psmt.setInt(1, tk.getTrangThai());
           
           
            return psmt.executeUpdate()>0;
        }
    }
        public static boolean delete (String manv) throws Exception
    {
        String sql="delete from TAI_KHOAN_DN  where MANV=? AND TRANGTHAI=0 OR TRANGTHAI=10";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, manv);
           
            return psmt.executeUpdate()>0;
        }
    }
        public static TaiKhoan findById (String maNV) throws Exception
    {
        //? tìm thấy ..hỏi có muốn khôi phục kh
        String sql="select * from TAI_KHOAN_DN  where MANV=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, maNV);
            ResultSet rs=psmt.executeQuery();
            if(rs.next())
            {
                TaiKhoan nv=createTaiKhoan(rs);
                return nv;
            }
            
        }
       
        return null;
    }

    private static TaiKhoan createTaiKhoan(ResultSet rs) throws SQLException {

        TaiKhoan tk=new TaiKhoan();
        tk.setMaNV(rs.getString("MANV"));
        tk.setTenDangNhap(rs.getString("TENDN"));
        tk.setMatKhau(rs.getString("MATKHAU"));
        tk.setVaiTro(rs.getString("VAITRO"));
        tk.setTrangThai(rs.getInt("TRANGTHAI"));
        return tk;
    }
     public static TaiKhoan checkLogin(String tenDangNhap,String MatKhau) throws Exception
    {
        String sql="select TENDN, MATKHAU, VAITRO, MANV from TAI_KHOAN_DN where TENDN=? and MATKHAU=? and TRANGTHAI=1";
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
               
                ){
            psmt.setString(1, tenDangNhap);
            psmt.setString(2, MatKhau);
            ResultSet rs=psmt.executeQuery();
                    if(rs.next())
                        {
                            TaiKhoan tk=new TaiKhoan();
                            tk.setTenDangNhap(tenDangNhap);
                            tk.setVaiTro(rs.getString("vaitro"));
                            tk.setMaNV(rs.getString("MANV"));
                          return tk;
                            
                        }
                    }
            
        
        return null;
    }
}
