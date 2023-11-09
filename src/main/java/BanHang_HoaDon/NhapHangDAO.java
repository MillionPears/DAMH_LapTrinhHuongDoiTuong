/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BanHang_HoaDon;

import helper.DataBaseHelper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.SanPham;
import model.NhapHang;

/**
 *
 * @author ASUS
 */
public class NhapHangDAO {
     public static boolean insert (NhapHang nh) throws Exception
    {
        
        
        String sql="insert into PHIEU_NHAP(MASP,GIANHAP,NGAYNHAP,SOLUONG,MOTA,MANV,MANCC) values(?,?,?,?,?,?,?)";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1,nh.getMaSp());
            psmt.setInt(2, nh.getGiaNhap());
            psmt.setDate(3,  new java.sql.Date(nh.getNgayNhap().getTime()));
            psmt.setInt(4, nh.getSoLuong());
            psmt.setString(5, nh.getMoTa());
            psmt.setString(6, nh.getMaNV());
            psmt.setInt(7, NhaCungCapDAO.findByName(nh.getNhaCungCap()));
         
            
            return psmt.executeUpdate()>0;
        }
    }
        public static boolean update (NhapHang nh) throws Exception
        {

            String sql="update PHIEU_NHAP set GIANHAP=?,MOTA=?,MANCC=? where MASP=?";

            try(Connection conn=DataBaseHelper.openConnection();
                    PreparedStatement psmt=conn.prepareStatement(sql);
                    ){
                psmt.setString(4,nh.getMaSp());
                psmt.setInt(1, nh.getGiaNhap());
              //  psmt.setDate(2,  new java.sql.Date(nh.getNgayNhap().getTime()));
             //   psmt.setInt(3, nh.getSoLuong());
                psmt.setString(2, nh.getMoTa());
                psmt.setInt(3, NhaCungCapDAO.findByName(nh.getNhaCungCap()));


                return psmt.executeUpdate()>0;
            }
        }
         public static NhapHang findById (String maSP) throws Exception
    {
        //? tìm thấy ..hỏi có muốn khôi phục kh
        String sql="select * from PHIEU_NHAP  where MASP=?";
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            psmt.setString(1, maSP);
            ResultSet rs=psmt.executeQuery();
            if(rs.next())
            {
                NhapHang nh=createNhapHang(rs);
                return nh;
            }
        }
        return null;
    }
          private static NhapHang createNhapHang(ResultSet rs) throws SQLException, Exception {
         NhapHang nh=new NhapHang();

        nh.setMaSp(rs.getString("MASP"));
        nh.setGiaNhap(rs.getInt("GIANHAP"));
        nh.setNgayNhap(rs.getDate("NGAYNHAP"));
        nh.setSoLuong(rs.getInt("SOLUONG"));
        nh.setMoTa(rs.getString("MOTA"));
        nh.setNhaCungCap(NhaCungCapDAO.findById(rs.getInt("MANCC")).getTenNhaCungCap());
       
        
        return nh;
    }
          
          public static int getSoLuong (String masp) throws Exception
        {
            String sql="SELECT MASP,SL=COUNT(MASP) FROM IMEI WHERE TRANGTHAI=1 OR TRANGTHAI=10 GROUP BY MASP";

            try(Connection conn=DataBaseHelper.openConnection();
                    PreparedStatement psmt=conn.prepareStatement(sql);
                    ){

                ResultSet rs=psmt.executeQuery();

                int sl=0;
                masp=masp.toUpperCase();
                while(rs.next())
                {
                    if(rs.getString("MASP").equalsIgnoreCase(masp))
                    {
                         sl=(rs.getInt("SL"));
                         return sl;
                    }

                }
                return sl;
            }
      
    }
    public static ArrayList<String> SortBySoLuong (String giatri,String chucnang) throws Exception
    {
        // chua fix
        String sql="select masp, soluong= count(masp) from IMEI where TRANGTHAI=1 group by MASP  ORDER BY "+giatri+" "+chucnang;
        
        try(Connection conn=DataBaseHelper.openConnection();
                PreparedStatement psmt=conn.prepareStatement(sql);
                ){
            
            ResultSet rs=psmt.executeQuery();
            ArrayList<String> list=new ArrayList<>();
            while(rs.next())
            {
                String nh= rs.getString("MASP");
                list.add(nh);
              
            }
            return list;
        }
      
    }
}
