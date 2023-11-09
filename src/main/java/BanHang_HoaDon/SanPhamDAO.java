/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BanHang_HoaDon;

import helper.DataBaseHelper;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.NhanVien;
import model.SanPham;

/**
 *
 * @author ASUS
 */
public class SanPhamDAO {
     public static boolean insert (SanPham sp) throws Exception
    {
        
        String sql="insert into SAN_PHAM(MASP,TENSP,GIA,HANG,ROM,RAM,MANHINH,KICHTHUOC,CAMERA,MAUSAC,HDH,CPU,PIN,LOAI,MOTASP,XUATXU,TRANGTHAI,HINHANH) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1,sp.getMaSp());
            psmt.setString(2, sp.getTenSp());
            psmt.setInt(3, sp.getGia());
            psmt.setString(4, sp.getHang());
            psmt.setString(5, sp.getRom());
            psmt.setString(6, sp.getRam());
            psmt.setString(7, sp.getManHinh());
            psmt.setString(8, sp.getKichThuoc());
            psmt.setString(9, sp.getCamera());
            psmt.setString(10, sp.getMausac());
            psmt.setString(11, sp.getHDH());
            psmt.setString(12, sp.getCPU());
            psmt.setString(13, sp.getPin());
            psmt.setString(14, sp.getLoai());
            psmt.setString(15, sp.getSacNhanh());
            psmt.setString(16, sp.getXuatxu());
            psmt.setInt(17, sp.getTrangthai());
            psmt.setString(18, sp.getHinhAnh());
            
           
            
            return psmt.executeUpdate()>0;
        }
    }
      public static boolean update (SanPham sp) throws Exception
    {

        String sql="update SAN_PHAM set TENSP=?, GIA=?, XUATXU=?, HANG=?, ROM=? ,RAM=?,MANHINH=?,KICHTHUOC=?,CAMERA=?,MAUSAC=?,HDH=?,CPU=?,PIN=?,LOAI=?,MOTASP=?,HINHANH=? where MASP=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(17, sp.getMaSp());
            psmt.setString(1, sp.getTenSp());
            psmt.setInt(2, sp.getGia());
            psmt.setString(3, sp.getXuatxu());
            psmt.setString(4, sp.getHang());
            psmt.setString(5, sp.getRom());
            psmt.setString(6, sp.getRam());
            psmt.setString(7, sp.getManHinh());
            psmt.setString(8, sp.getKichThuoc());
            psmt.setString(9, sp.getCamera());
            psmt.setString(10, sp.getMausac());
            psmt.setString(11, sp.getHDH());
            psmt.setString(12, sp.getCPU());
            psmt.setString(13, sp.getPin());
            psmt.setString(14, sp.getLoai());
            psmt.setString(15, sp.getSacNhanh());
            psmt.setString(16,sp.getHinhAnh());
            
            
           
          
           
            return psmt.executeUpdate()>0;
        }
    }
      public static boolean updateGia (SanPham sp) throws Exception
    {

        String sql="update SAN_PHAM set GIA=? where MASP=? and TRANGTHAI=1";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(2, sp.getMaSp());
            psmt.setInt(1, sp.getGia());
           
           
            return psmt.executeUpdate()>0;
        }
    }
      
      public static boolean updateTrangThai0_1 (SanPham sp) throws Exception
    {

        String sql="update SAN_PHAM set TRANGTHAI=? where MASP=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(2, sp.getMaSp());
            psmt.setInt(1, sp.getTrangthai());
           
           
            return psmt.executeUpdate()>0;
        }
    }
      
        public static boolean delete (String masp) throws Exception
    {
        String sql="delete from SAN_PHAM  where MASP=? AND TRANGTHAI=0";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, masp);
            return psmt.executeUpdate()>0;
        }
    }
     public static ArrayList<SanPham> findAll () throws Exception
    {
        String sql="select * from SAN_PHAM WHERE TRANGTHAI=1  ";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            ResultSet rs=psmt.executeQuery();
            ArrayList<SanPham> list=new ArrayList<>();
            while(rs.next())
            {
                SanPham sp=creatSanPham(rs);
                list.add(sp);
            }
            return list;
        }
      
    }
      public static ArrayList<SanPham> findAllTT0 () throws Exception
    {
        String sql="select * from SAN_PHAM WHERE TRANGTHAI=0  ";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            ResultSet rs=psmt.executeQuery();
            ArrayList<SanPham> list=new ArrayList<>();
            while(rs.next())
            {
                SanPham sp=creatSanPham(rs);
                list.add(sp);
            }
            return list;
        }
      
    }
     

    private static SanPham creatSanPham(ResultSet rs) throws SQLException {
         SanPham sp=new SanPham();
        
        sp.setMaSp(rs.getString("MASP"));
        sp.setTenSp(rs.getString("TENSP"));
        sp.setGia(rs.getInt("GIA"));
        sp.setHang(rs.getString("HANG"));
        sp.setRom(rs.getString("ROM"));
        sp.setRam(rs.getString("RAM"));
        sp.setManHinh(rs.getString("MANHINH"));
        sp.setKichThuoc(rs.getString("KICHTHUOC"));
        sp.setCamera(rs.getString("CAMERA"));
        sp.setMausac(rs.getString("MAUSAC"));
        sp.setHDH(rs.getString("HDH"));
        sp.setCPU(rs.getString("CPU"));
        sp.setPin(rs.getString("PIN"));
        sp.setLoai(rs.getString("LOAI"));
        sp.setSacNhanh(rs.getString("MOTASP"));
        sp.setXuatxu(rs.getString("XUATXU"));
        sp.setTrangthai(rs.getInt("TRANGTHAI"));
        sp.setHinhAnh(rs.getString("HINHANH"));
        return sp;
    }
    
    
     public static SanPham findById (String maSP) throws Exception
    {
        //? tìm thấy ..hỏi có muốn khôi phục kh
        String sql="select * from SAN_PHAM  where MASP=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, maSP);
            ResultSet rs=psmt.executeQuery();
            if(rs.next())
            {
                SanPham sp=creatSanPham(rs);
                return sp;
            }
        }
        return null;
    }
     public static SanPham findByIdTT0 (String maSP) throws Exception
    {
        //? tìm thấy ..hỏi có muốn khôi phục kh
        String sql="select * from SAN_PHAM  where MASP=? and TRANGTHAI=0";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, maSP);
            ResultSet rs=psmt.executeQuery();
            if(rs.next())
            {
                SanPham sp=creatSanPham(rs);
                return sp;
            }
        }
        return null;
    }
     public static SanPham findByIdTT1 (String maSP) throws Exception
    {
        //? tìm thấy ..hỏi có muốn khôi phục kh
        String sql="select * from SAN_PHAM  where MASP=? and TRANGTHAI=1";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, maSP);
            ResultSet rs=psmt.executeQuery();
            if(rs.next())
            {
                SanPham sp=creatSanPham(rs);
                return sp;
            }
        }
        return null;
    }
    
      public static ArrayList<String> getTenSpCungHang (String hang) throws Exception
    {
        String sql="select DISTINCT TENSP from SAN_PHAM  where HANG=?";
        ArrayList<String> tmp=new ArrayList<>();
      
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, hang);
            ResultSet rs=psmt.executeQuery();
            while(rs.next())
            {
                tmp.add(rs.getString("TENSP"));
               
               
              
            }
            return tmp;
        }
        //
        
       
    }
      public static ArrayList<SanPham> findNameSanPham (String tsp) throws Exception
    {
        String sql="select * from SAN_PHAM WHERE TRANGTHAI=1 AND TENSP LIKE '%"+tsp+"%' ";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            // psmt.setString(1,tsp );
            ResultSet rs=psmt.executeQuery();
            ArrayList<SanPham> list=new ArrayList<>();
            while(rs.next())
            {
                SanPham sp=creatSanPham(rs);
                list.add(sp);
            }
            return list;
        }
      
    }
       public static ArrayList<SanPham> findNameSanPhamDaXoaTT0 (String tsp) throws Exception
    {
        String sql="select * from SAN_PHAM WHERE TRANGTHAI=0 AND TENSP LIKE '%"+tsp+"%' ";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            // psmt.setString(1,tsp );
            ResultSet rs=psmt.executeQuery();
            ArrayList<SanPham> list=new ArrayList<>();
            while(rs.next())
            {
                SanPham sp=creatSanPham(rs);
                list.add(sp);
            }
            return list;
        }
      
    }
        public static ArrayList<SanPham> SortBy (String giatri,String chucnang) throws Exception
    {
        String sql="select * from SAN_PHAM WHERE TRANGTHAI=1 ORDER BY "+giatri+" "+chucnang;
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            ResultSet rs=psmt.executeQuery();
            ArrayList<SanPham> list=new ArrayList<>();
            while(rs.next())
            {
                SanPham sp=creatSanPham(rs);
                list.add(sp);
              
            }
            return list;
        }
        
      
    }

        public static ArrayList<SanPham> SortBy_A_9 (String giatri,String chucnang) throws Exception
    {
        
        String sql="select sp.*, cast(replace ("+giatri+",'GB','') as int ) AS A  FROM SAN_PHAM sp WHERE TRANGTHAI=1 ORDER BY A "+chucnang;
       
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            ResultSet rs=psmt.executeQuery();
            ArrayList<SanPham> list=new ArrayList<>();
            while(rs.next())
            {
                SanPham sp=creatSanPham(rs);
               
                list.add(sp);
              
            }
            return list;
        }
        
      
    }

}
