/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BanHang_HoaDon;

import Check.TestDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.KhachHang;

/**
 *
 * @author ACER
 */
public class KhachHangDAO {
    Connection con = null;
    PreparedStatement sttm = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public int addKH(KhachHang kh){
        try {
            String sSQL = "insert KHACH_HANG(MAKH, HOKH, TENKH, SDTKH, CMNDKH, EMAIL, GIOITINH, DIACHI, NGAYTAO, TRANGTHAI, GHICHU)"
                    + "values(?,?,?,?,?,?,?,?,?,?,?)";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(1, kh.getMaKH());
            sttm.setString(2, kh.getHoKH());
            sttm.setString(3, kh.getTenKH());
            sttm.setString(4, kh.getSoDT());
            sttm.setString(5, kh.getCmnd());
            sttm.setString(6, kh.getEmail());
            sttm.setString(7, kh.getGioiTinh());
            sttm.setString(8, kh.getDiaChi());
            sttm.setString(9, sdf.format(kh.getNgayTao()));
            sttm.setInt(10, 1);            
            sttm.setString(11, kh.getGhiChu());
            if (sttm.executeUpdate()>0){
                System.out.println("Insert thanh cong"); 
                return 1;
            }            
        } catch (Exception e) {
            System.out.println("ErrorKH1: "+ e.toString());
        }
        return -1;
    }
    
    public int updateKH(KhachHang kh){
        try {
            String sSQL = "update KHACH_HANG set HOKH = ?, TENKH = ?, SDTKH = ?, CMNDKH = ?, EMAIL = ?, GIOITINH = ?, DIACHI = ?, NGAYTAO = ?, TRANGTHAI = ?, GHICHU = ? where MAKH = ?";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(11, kh.getMaKH());
            sttm.setString(1, kh.getHoKH());
            sttm.setString(2, kh.getTenKH());
            sttm.setString(3, kh.getSoDT());
            sttm.setString(4, kh.getCmnd());
            sttm.setString(5, kh.getEmail());
            sttm.setString(6, kh.getGioiTinh());
            sttm.setString(7, kh.getDiaChi());
            sttm.setString(8, sdf.format(kh.getNgayTao()));
            sttm.setInt(9, 1);
            sttm.setString(10, kh.getGhiChu());
            if (sttm.executeUpdate()>0){
                System.out.println("Update thanh cong");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("ErrorKH2: "+ e.toString());
        }
        return -1;
    }
    
    public int deleteKH(String maKH){
        try {
            String sSQL = "update KHACH_HANG set TRANGTHAI = 0 where MAKH = ?";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(1, maKH);
            if (sttm.executeUpdate()>0){
                System.out.println("Delete thanh cong");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("ErrorKH3: "+ e.toString());
        }
        return -1;
    }
    
    public int KhoiPhucKHDaXoa(String maKH){
        try {
            String sSQL = "update KHACH_HANG set TRANGTHAI = 1 where MAKH = ?";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(1, maKH);
            if (sttm.executeUpdate()>0){               
                return 1;
            }
        } catch (Exception e) {
            System.out.println("ErrorKH4: "+ e.toString());
        }
        return -1;
    }    

    public int deleteKHVinhVien(String maKH){
        try {
            String sSQL = "DELETE KHACH_HANG WHERE MAKH = ? AND TRANGTHAI = 0";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(1, maKH);
            if (sttm.executeUpdate()>0){
                
                return 1;
            }
        } catch (Exception e) {
            System.out.println("ErrorKH5: "+ e.toString());
        }
        return -1;
    }
    
    public List<KhachHang> TimKiemKhachHang(String str){
        List<KhachHang> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;
        
        try {
            String sSQL = "SELECT * FROM KHACH_HANG WHERE TENKH LIKE '%"+str+"%' AND TRANGTHAI = 1";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoKH(rs.getString(2));
                kh.setTenKH(rs.getString(3));
                kh.setSoDT(rs.getString(4));
                kh.setCmnd(rs.getString(5));
                kh.setEmail(rs.getString(6));
                kh.setGioiTinh(rs.getString(7));
                kh.setDiaChi(rs.getString(8));
                kh.setNgayTao(rs.getDate(9));
                kh.setTrangThai(rs.getInt(10));
                kh.setGhiChu(rs.getString(11));
                
                list.add(kh);
            }
        } catch (Exception e) {
            System.out.println("ErrorKH6:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }  

    public List<KhachHang> TimKiemKhachHangDaXoa(String str){
        List<KhachHang> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;
        
        try {
            String sSQL = "SELECT * FROM KHACH_HANG WHERE TENKH LIKE '%"+str+"%' AND TRANGTHAI = 0";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoKH(rs.getString(2));
                kh.setTenKH(rs.getString(3));
                kh.setSoDT(rs.getString(4));
                kh.setCmnd(rs.getString(5));
                kh.setEmail(rs.getString(6));
                kh.setGioiTinh(rs.getString(7));
                kh.setDiaChi(rs.getString(8));
                kh.setNgayTao(rs.getDate(9));
                kh.setTrangThai(1);
                kh.setGhiChu(rs.getString(11));
                
                list.add(kh);
            }
        } catch (Exception e) {
            System.out.println("ErrorKH7:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }     

    public KhachHang getKhachHang(String maKH){
        ResultSet rs = null;
        Statement sttm = null;
        
        try {
            String sSQL = "select * from KHACH_HANG where MAKH LIKE '%"+maKH+"%' AND TRANGTHAI = 1";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoKH(rs.getString(2));
                kh.setTenKH(rs.getString(3));
                kh.setSoDT(rs.getString(4));
                kh.setCmnd(rs.getString(5));
                kh.setEmail(rs.getString(6));
                kh.setGioiTinh(rs.getString(7));
                kh.setDiaChi(rs.getString(8));
                kh.setNgayTao(rs.getDate(9));
                kh.setTrangThai(rs.getInt(10));
                kh.setGhiChu(rs.getString(11));
                
                return kh;
            }
        } catch (Exception e) {
            System.out.println("ErrorKH8:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return null;
    } 
    
    public KhachHang getKhachHangDaXoa(String maKH){
        ResultSet rs = null;
        Statement sttm = null;
        
        try {
            String sSQL = "SELECT * FROM KHACH_HANG WHERE MAKH LIKE '%"+maKH+"%' AND TRANGTHAI = 0";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoKH(rs.getString(2));
                kh.setTenKH(rs.getString(3));
                kh.setSoDT(rs.getString(4));
                kh.setCmnd(rs.getString(5));
                kh.setEmail(rs.getString(6));
                kh.setGioiTinh(rs.getString(7));
                kh.setDiaChi(rs.getString(8));
                kh.setNgayTao(rs.getDate(9));
                kh.setTrangThai(rs.getInt(10));
                kh.setGhiChu(rs.getString(11));
                
                return kh;
            }
        } catch (Exception e) {
            System.out.println("ErrorKH9:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return null;
    }     

    public ArrayList<KhachHang> getListKhachHang(){
        ArrayList<KhachHang> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;  
        try {
            String sSQL = "SELECT * FROM KHACH_HANG WHERE TRANGTHAI = 1";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoKH(rs.getString(2));
                kh.setTenKH(rs.getString(3));
                kh.setSoDT(rs.getString(4));
                kh.setCmnd(rs.getString(5));
                kh.setEmail(rs.getString(6));
                kh.setGioiTinh(rs.getString(7));
                kh.setDiaChi(rs.getString(8));
                kh.setDiaChi(rs.getString(8));
                kh.setNgayTao(rs.getDate(9));
                kh.setTrangThai(rs.getInt(10));
                kh.setGhiChu(rs.getString(11));
                
                list.add(kh);
            }
        } catch (Exception e) {
            System.out.println("ErrorKH10:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }
    
    public ArrayList<KhachHang> getListKhachHangDaXoa(){
        ArrayList<KhachHang> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;  
        try {
            String sSQL = "SELECT * FROM KHACH_HANG WHERE TRANGTHAI = 0";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoKH(rs.getString(2));
                kh.setTenKH(rs.getString(3));
                kh.setSoDT(rs.getString(4));
                kh.setCmnd(rs.getString(5));
                kh.setEmail(rs.getString(6));
                kh.setGioiTinh(rs.getString(7));
                kh.setDiaChi(rs.getString(8));
                kh.setDiaChi(rs.getString(8));
                kh.setNgayTao(rs.getDate(9));
                kh.setTrangThai(rs.getInt(10));
                kh.setGhiChu(rs.getString(11));
                
                list.add(kh);
            }
        } catch (Exception e) {
            System.out.println("ErrorKH11:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }

    public ArrayList<String> checkMaKH() {
        ArrayList<String> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;

        try {
            String sSQL = "SELECT MAKH FROM KHACH_HANG";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("ErrorKH12:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }
 
    public ArrayList<String> checkCMNDKH() {
        ArrayList<String> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;

        try {
            String sSQL = "SELECT CMNDKH FROM KHACH_HANG";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("ErrorKH13:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }   
    
    public void deleteKHSoHuu(String maGG){
        try {
            String sSQL = "delete SO_HUU where MAKH = ?";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(1, maGG);
            sttm.executeUpdate();
            sttm.close();
        } catch (Exception e) {
            System.out.println("ErrorKH14: "+ e.toString());
        }
    }     
}
