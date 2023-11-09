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
import model.NhanVien;
import model.NhapHang;
import model.SanPham;

/**
 *
 * @author ASUS
 */
public class NhanVienDAO {
     public static boolean insert (NhanVien nv) throws Exception
    {
        
        
        String sql="insert into NHAN_VIEN(MANV,TENNV,CHUCVU,NGAYLAM,SDTNV,CMNDNV,NGAYSINH,PHAI,DIACHI,HINHANH,EMAIL,GHICHU,TRANGTHAI,CAPQUYEN,MANVQL) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1,nv.getMaNV());
            psmt.setString(2,nv.getHoten());
            psmt.setString(3,nv.getVaitro());
            psmt.setDate(4,  new java.sql.Date(nv.getNgaylam().getTime()));
            psmt.setString(5,nv.getSdt());
            psmt.setString(6,nv.getCmnd());
            
             psmt.setDate(7,  new java.sql.Date(nv.getNgaysinh().getTime()));
            psmt.setString(8,nv.getGioitinh());
            psmt.setString(9,nv.getDiachi());
            psmt.setString(10,nv.getHinhAnh());
            psmt.setString(11,nv.getEmail());
            psmt.setString(12,nv.getGhichu());
            psmt.setInt(13,nv.getTrangThai());
            psmt.setInt(14,nv.getQuyenDangNhap());
            psmt.setString(15,nv.getMaNV());
                                
           
            return psmt.executeUpdate()>0;
        }
        
    }
     
     public static NhanVien findById (String maNV) throws Exception
    {
        //? tìm thấy ..hỏi có muốn khôi phục kh
        String sql="select * from NHAN_VIEN  where MANV=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, maNV);
            ResultSet rs=psmt.executeQuery();
            if(rs.next())
            {
                NhanVien nv=createNhanVien(rs);
                return nv;
            }
            
        }
       
        return null;
    }

    private static NhanVien createNhanVien(ResultSet rs) throws SQLException {
        NhanVien nv=new NhanVien();

        nv.setMaNV(rs.getString("MANV"));
        nv.setHoten(rs.getString("TENNV"));
        nv.setVaitro(rs.getString("CHUCVU"));
        nv.setNgaylam(rs.getDate("NGAYLAM"));
        nv.setNgayNghi(rs.getDate("NGAYNGHI"));
        nv.setSdt(rs.getString("SDTNV"));
        nv.setCmnd(rs.getString("CMNDNV"));
        nv.setNgaysinh(rs.getDate("NGAYSINH"));
        nv.setGioitinh(rs.getString("PHAI"));
        nv.setDiachi(rs.getString("DIACHI"));
        nv.setHinhAnh(rs.getString("HINHANH"));
        nv.setEmail(rs.getString("EMAIL"));
        nv.setGhichu(rs.getString("GHICHU"));
        nv.setTrangThai(rs.getInt("TRANGTHAI"));
        nv.setQuyenDangNhap(rs.getInt("CAPQUYEN"));
     
        
        return nv;
    }
    public static boolean updateTrangThai0_1 (NhanVien nv) throws Exception
    {

        String sql="update NHAN_VIEN set TRANGTHAI=? where MANV=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(2, nv.getMaNV());
            psmt.setInt(1, nv.getTrangThai());
           
           
            return psmt.executeUpdate()>0;
        }
    }
    public static boolean updateNgayNghi (NhanVien nv) throws Exception
    {

        String sql="update NHAN_VIEN set NGAYNGHI=? where MANV=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(2, nv.getMaNV());
            psmt.setDate(1,  new java.sql.Date(nv.getNgayNghi().getTime()));
           
           
            return psmt.executeUpdate()>0;
        }
    }
     public static ArrayList<NhanVien> findAll () throws Exception
    {
        String sql="select * from NHAN_VIEN WHERE TRANGTHAI=1";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            ResultSet rs=psmt.executeQuery();
            ArrayList<NhanVien> list=new ArrayList<>();
            while(rs.next())
            {
                NhanVien nv=createNhanVien(rs);
                  
                list.add(nv);
            }
         
            return list;
        }
      
    }
     public static ArrayList<NhanVien> findAllTT0 () throws Exception
    {
        String sql="select * from NHAN_VIEN WHERE TRANGTHAI=0  ";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            ResultSet rs=psmt.executeQuery();
            ArrayList<NhanVien> list=new ArrayList<>();
            while(rs.next())
            {
                NhanVien nv=createNhanVien(rs);
                list.add(nv);
            }
            return list;
        }
      
    }
      public static boolean update (NhanVien nv) throws Exception
    {


        String sql="update NHAN_VIEN set  TENNV=?, CHUCVU=?, NGAYLAM=?, SDTNV=? ,CMNDNV=?,NGAYSINH=?,PHAI=?,DIACHI=?,HINHANH=?,EMAIL=?,GHICHU=?,TRANGTHAI=?,CAPQUYEN=? where MANV=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(14, nv.getMaNV());
            psmt.setString(1, nv.getHoten());
            psmt.setString(2, nv.getVaitro());
            psmt.setDate(3,  new java.sql.Date(nv.getNgaylam().getTime()));
            psmt.setString(4, nv.getSdt());
            psmt.setString(5, nv.getCmnd());
           psmt.setDate(6,  new java.sql.Date(nv.getNgaysinh().getTime()));
            psmt.setString(7, nv.getGioitinh());
            psmt.setString(8, nv.getDiachi());
            psmt.setString(9, nv.getHinhAnh());
            psmt.setString(10, nv.getEmail());
            psmt.setString(11, nv.getGhichu());
            psmt.setInt(12, nv.getTrangThai());
            psmt.setInt(13,nv.getQuyenDangNhap());
            
            
            return psmt.executeUpdate()>0;
        }
    }
       public static ArrayList<NhanVien> findNameNhanVien (String tsp) throws Exception
    {
        String sql="select * from NHAN_VIEN WHERE TRANGTHAI=1 AND TENNV LIKE '%"+tsp+"%' ";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            // psmt.setString(1,tsp );
            ResultSet rs=psmt.executeQuery();
            ArrayList<NhanVien> list=new ArrayList<>();
            while(rs.next())
            {
                NhanVien nv=createNhanVien(rs);
                list.add(nv);
            }
            return list;
        }
      
    }
        public static ArrayList<NhanVien> findNameSanPhamDaXoaTT0 (String tsp) throws Exception
    {
            String sql="select * from NHAN_VIEN WHERE TRANGTHAI=0 AND TENNV LIKE '%"+tsp+"%' ";

            try(Connection conn=DataBaseHelper.openConnection();
                    PreparedStatement psmt=conn.prepareStatement(sql);
                    ){

                // psmt.setString(1,tsp );
                ResultSet rs=psmt.executeQuery();
                ArrayList<NhanVien> list=new ArrayList<>();
                while(rs.next())
                {
                    NhanVien nv=createNhanVien(rs);
                    list.add(nv);
                }
                return list;
            }
      
    }
        public static boolean delete (String masp) throws Exception
    {
        String sql="delete from NHAN_VIEN  where MANV=? AND TRANGTHAI=0";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, masp);
            return psmt.executeUpdate()>0;
        }
    }
      
      
}
