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
import javax.swing.JComboBox;
import javax.swing.JTable;
import model.*;

/**
 *
 * @author ACER
 */
public class GiamGiaDAO {
    Connection con = null;
    PreparedStatement sttm = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public int addGG(GiamGia gg){
        try {
            String sSQL = "insert KHUYEN_MAI(MAKM, TENKM, LOAI, TRANGTHAI, MUCGG, NGAYAD, NGAYHETHAN, DONVI)"
                    + "values(?,?,?,?,?,?,?,?)";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(1, gg.getMaGG());
            sttm.setString(2, gg.getTenGG());
            sttm.setString(3, gg.getLoaiMa());
            sttm.setInt(4, gg.getTrangThai());            
            sttm.setInt(5, gg.getMucGG());
            sttm.setString(6, sdf.format(gg.getTgDienRa()));
            sttm.setString(7, sdf.format(gg.getTgKetThuc()));
            sttm.setString(8, checkDonGia(gg));
            if (sttm.executeUpdate()>0){
                System.out.println("Insert thanh cong"); 
                return 1;
            }            
        } catch (Exception e) {
            System.out.println("Error_add: "+ e.toString());
        }
        return -1;
    }

    public int updateGG(GiamGia gg){
        try {
            String sSQL = "update KHUYEN_MAI set TENKM = ?, LOAI = ?, TRANGTHAI = ?, MUCGG = ?, NGAYAD = ?, NGAYHETHAN = ?, DONVI = ? where MAKM = ?";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(8, gg.getMaGG());
            sttm.setString(1, gg.getTenGG());
            sttm.setString(2, gg.getLoaiMa());
            sttm.setInt(3, gg.getTrangThai());            
            sttm.setInt(4, gg.getMucGG());
            sttm.setString(5, sdf.format(gg.getTgDienRa()));
            sttm.setString(6, sdf.format(gg.getTgKetThuc()));
            sttm.setString(7, checkDonGia(gg));

            if (sttm.executeUpdate()>0){
                System.out.println("Update thanh cong");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Error_update: "+ e.toString());
        }
        return -1;
    }

    public int deleteGG(String maGG){
        try {
            String sSQL = "delete KHUYEN_MAI where MAKM = ?";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(1, maGG);
            if (sttm.executeUpdate()>0){
                System.out.println("Delete thanh cong");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Error_delete: "+ e.toString());
        }
        return -1;
    }

    public GiamGia getGiamGiaTatCa(String maGG){
        ResultSet rs = null;
        Statement sttm = null;
        
        try {
            String sSQL = "select MAKM,TENKM,LOAI,TRANGTHAI,MUCGG,NGAYAD,NGAYHETHAN from KHUYEN_MAI where MAKM LIKE '%"+maGG+"%'";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                GiamGia gg = new GiamGia();
                gg.setMaGG(rs.getString(1));
                gg.setTenGG(rs.getString(2));
                gg.setLoaiMa(rs.getString(3));
                gg.setTrangThai(rs.getInt(4));
                gg.setMucGG(rs.getInt(5));
                gg.setTgDienRa(rs.getDate(6));
                gg.setTgKetThuc(rs.getDate(7));
                
                return gg;
            }
        } catch (Exception e) {
            System.out.println("Error_GG1:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return null;
    } 

    public GiamGia getGiamGiaSDR(String maGG){
        ResultSet rs = null;
        Statement sttm = null;
        
        try {
            String sSQL = "select MAKM,TENKM,LOAI,TRANGTHAI,MUCGG,NGAYAD,NGAYHETHAN from KHUYEN_MAI where MAKM LIKE '%"+maGG+"%' AND TRANGTHAI = 0";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                GiamGia gg = new GiamGia();
                gg.setMaGG(rs.getString(1));
                gg.setTenGG(rs.getString(2));
                gg.setLoaiMa(rs.getString(3));
                gg.setTrangThai(rs.getInt(4));
                gg.setMucGG(rs.getInt(5));
                gg.setTgDienRa(rs.getDate(6));
                gg.setTgKetThuc(rs.getDate(7));
                
                return gg;
            }
        } catch (Exception e) {
            System.out.println("Error_GG2:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return null;
    }     
    
    public GiamGia getGiamGiaDDR(String maGG){
        ResultSet rs = null;
        Statement sttm = null;
        
        try {
            String sSQL = "select MAKM,TENKM,LOAI,TRANGTHAI,MUCGG,NGAYAD,NGAYHETHAN from KHUYEN_MAI where MAKM LIKE '%"+maGG+"%' AND TRANGTHAI = 1";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                GiamGia gg = new GiamGia();
                gg.setMaGG(rs.getString(1));
                gg.setTenGG(rs.getString(2));
                gg.setLoaiMa(rs.getString(3));
                gg.setTrangThai(rs.getInt(4));
                gg.setMucGG(rs.getInt(5));
                gg.setTgDienRa(rs.getDate(6));
                gg.setTgKetThuc(rs.getDate(7));
                
                return gg;
            }
        } catch (Exception e) {
            System.out.println("Error_GG3:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return null;
    }     

    public GiamGia getGiamGiaDKT(String maGG){
        ResultSet rs = null;
        Statement sttm = null;
        
        try {
            String sSQL = "select MAKM,TENKM,LOAI,TRANGTHAI,MUCGG,NGAYAD,NGAYHETHAN from KHUYEN_MAI where MAKM LIKE '%"+maGG+"%' AND TRANGTHAI = 2";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                GiamGia gg = new GiamGia();
                gg.setMaGG(rs.getString(1));
                gg.setTenGG(rs.getString(2));
                gg.setLoaiMa(rs.getString(3));
                gg.setTrangThai(rs.getInt(4));
                gg.setMucGG(rs.getInt(5));
                gg.setTgDienRa(rs.getDate(6));
                gg.setTgKetThuc(rs.getDate(7));
                
                return gg;
            }
        } catch (Exception e) {
            System.out.println("Error_GG4:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return null;
    } 
    
    public ArrayList<GiamGia> getListGiamGiaTC(String str){
        ArrayList<GiamGia> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;  
        try {
            String sSQL = "SELECT * FROM KHUYEN_MAI WHERE TENKM LIKE '%"+str+"%'";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                GiamGia gg = new GiamGia();
                gg.setMaGG(rs.getString(1));
                gg.setTenGG(rs.getString(2));
                gg.setLoaiMa(rs.getString(3));
                gg.setTrangThai(rs.getInt(4));
                gg.setMucGG(rs.getInt(5));
                gg.setTgDienRa(rs.getDate(6));
                gg.setTgKetThuc(rs.getDate(7));
                
                list.add(gg);
            }
        } catch (Exception e) {
            System.out.println("Error_GG5:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }
    
    public ArrayList<GiamGia> getListGiamGiaSapDienRa(String str){
        ArrayList<GiamGia> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;  
        try {
            String sSQL = "SELECT * FROM KHUYEN_MAI WHERE TENKM LIKE '%"+str+"%' AND TRANGTHAI = 0";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                GiamGia gg = new GiamGia();
                gg.setMaGG(rs.getString(1));
                gg.setTenGG(rs.getString(2));
                gg.setLoaiMa(rs.getString(3));
                gg.setTrangThai(rs.getInt(4));                
                gg.setMucGG(rs.getInt(5));
                gg.setTgDienRa(rs.getDate(6));
                gg.setTgKetThuc(rs.getDate(7));
                
                list.add(gg);
            }
        } catch (Exception e) {
            System.out.println("Error_GG6:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    } 
    
    public ArrayList<GiamGia> getListGiamGiaDangDienRa(String str){
        ArrayList<GiamGia> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;  
        try {
            String sSQL = "SELECT * FROM KHUYEN_MAI WHERE TENKM LIKE '%"+str+"%' AND TRANGTHAI = 1";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                GiamGia gg = new GiamGia();
                gg.setMaGG(rs.getString(1));
                gg.setTenGG(rs.getString(2));
                gg.setLoaiMa(rs.getString(3));
                gg.setTrangThai(rs.getInt(4));                
                gg.setMucGG(rs.getInt(5));
                gg.setTgDienRa(rs.getDate(6));
                gg.setTgKetThuc(rs.getDate(7));
                
                list.add(gg);
            }
        } catch (Exception e) {
            System.out.println("Error_GG7:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }
    
    public ArrayList<GiamGia> getListGiamGiaDaKetThuc(String str){
        ArrayList<GiamGia> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;  
        try {
            String sSQL = "SELECT * FROM KHUYEN_MAI WHERE TENKM LIKE '%"+str+"%' AND TRANGTHAI = 2";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                GiamGia gg = new GiamGia();
                gg.setMaGG(rs.getString(1));
                gg.setTenGG(rs.getString(2));
                gg.setLoaiMa(rs.getString(3));
                gg.setTrangThai(rs.getInt(4));                
                gg.setMucGG(rs.getInt(5));
                gg.setTgDienRa(rs.getDate(6));
                gg.setTgKetThuc(rs.getDate(7));
                
                list.add(gg);
            }
        } catch (Exception e) {
            System.out.println("Error_GG8:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }
    
    public ArrayList<String> listMaGG() {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = null;
        Statement sttm = null;

        try {
            String sSQL = "SELECT MAKM FROM KHUYEN_MAI";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Error_GG9:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }    
    
    public String checkDonGia(GiamGia gg){
        String sotien = "VND";
        String phantram = "%";
        if (gg.getMucGG() > 100){
            return sotien;
        }else{
            return phantram;
        }
    }

    public void addGGTheoKH(String gg, JTable tb){
        try {
            for (int i = 0; i < tb.getModel().getRowCount(); i++) {
                for (int j = 1; j < tb.getModel().getColumnCount(); j++) {
                    String sSQL = "insert SO_HUU(MAKH, MAKM)"
                            +"values(?,?)";
                    con = TestDatabase.getDBConnect();
                    sttm = con.prepareStatement(sSQL);                    
                    String val = tb.getModel().getValueAt(i, j).toString();
                    if (val.equals("true")){
                        Giam_gia ggif = new Giam_gia();
                        if (ggif.checkBooleanKH(gg, tb.getModel().getValueAt(i, 0).toString()) == false) {
                            sttm.setString(1, tb.getModel().getValueAt(i, 0).toString());
                            sttm.setString(2, gg);
                            sttm.executeUpdate();
                            sttm.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error_GG10: "+ e.toString());
        } 
    } 

    public void deleteGGTheoKH(String gg, JTable tb){           
        try {
            for (int i = 0; i < tb.getModel().getRowCount(); i++) {
                for (int j = 1; j < tb.getModel().getColumnCount(); j++) {
                    String sSQL = "delete SO_HUU where MAKH = ?";
                    con = TestDatabase.getDBConnect();
                    sttm = con.prepareStatement(sSQL);                    
                    String val = tb.getModel().getValueAt(i, j).toString();
                    if (val.equals("false")){
                        Giam_gia ggif = new Giam_gia();
                        if (ggif.checkBooleanKH(gg, tb.getModel().getValueAt(i, 0).toString()) == true) {
                            sttm.setString(1, tb.getModel().getValueAt(i, 0).toString());
                            sttm.executeUpdate();
                            sttm.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error_GG11: "+ e.toString());
        } 
    }

    public ArrayList<String> listMaGGTrongSoHuu() {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = null;
        Statement sttm = null;
        try {
            String sSQL = "select MAKM from SO_HUU";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){               
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Error_GG12:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }
    
    public ArrayList<String> listMaKHTrongSoHuu(String maGG) {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = null;
        Statement sttm = null;
        try {
            String sSQL = "SELECT MAKH FROM SO_HUU where MAKM LIKE '%"+maGG+"%'";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Error_GG13:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    } 
 
    public ArrayList<String> listMaGGTrongCTKM() {
        ArrayList<String> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;

        try {
            String sSQL = "select MAKM from CT_KHUYEN_MAI";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){                
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Error_GG14:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }    
    
    public ArrayList<String> listMaSPTrongCTKM(String maGG) {
        ArrayList<String> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;

        try {
            String sSQL = "SELECT MASP FROM CT_KHUYEN_MAI where MAKM LIKE '%"+maGG+"%'";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Error_GG15:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }     
    
    public void listComboboxHangSP(JComboBox cbo) {
        ResultSet rs = null;
        Statement sttm = null;

        try {
            String sSQL = "SELECT DISTINCT HANG FROM SAN_PHAM";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){                
                String hang = rs.getString(1);
                cbo.addItem(hang);
            }
        } catch (Exception e) {
            System.out.println("Error_GG16:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
    }
    
    public ArrayList<SanPham> getListSPGiamGia(String str){
        ArrayList<SanPham> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;  
        try {
            String sSQL = "SELECT * FROM SAN_PHAM WHERE TENSP LIKE '%"+str+"%'";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                SanPham sp = new SanPham();                
                sp.setMaSp(rs.getString(1));
                sp.setTenSp(rs.getString(2));
                sp.setHang(rs.getString(7));
                
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println("Error_GG17:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }

    public ArrayList<SanPham> listTheoHangSP(String hang) {
        ArrayList<SanPham> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;

        try {
            String sSQL = "SELECT * FROM SAN_PHAM WHERE HANG LIKE '%"+hang+"%'";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){
                SanPham sp = new SanPham();
                sp.setMaSp(rs.getString(1));
                sp.setTenSp(rs.getString(2));
                sp.setHang(rs.getString(7));
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println("Error_GG18:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }      
    
    public void addGGTheoSP(String gg, JTable tb){
        try {
            for (int i = 0; i < tb.getModel().getRowCount(); i++) {
                for (int j = 1; j < tb.getModel().getColumnCount(); j++) {
                    String sSQL = "insert CT_KHUYEN_MAI(MASP, MAKM)"
                            +"values(?,?)";
                    con = TestDatabase.getDBConnect();
                    sttm = con.prepareStatement(sSQL);                    
                    String val = tb.getModel().getValueAt(i, j).toString();
                    if (val.equals("true")){
                        Giam_gia ggif = new Giam_gia();
                        if (ggif.checkBooleanSP(gg, tb.getModel().getValueAt(i, 0).toString()) == false) {
                            sttm.setString(1, tb.getModel().getValueAt(i, 0).toString());
                            sttm.setString(2, gg);
                            sttm.executeUpdate();
                            sttm.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error_GG19: "+ e.toString());
        } 
    } 

    public void deleteGGTheoSP(String gg, JTable tb){           
        try {
            for (int i = 0; i < tb.getModel().getRowCount(); i++) {
                for (int j = 1; j < tb.getModel().getColumnCount(); j++) {
                    String sSQL = "delete CT_KHUYEN_MAI where MASP = ?";
                    con = TestDatabase.getDBConnect();
                    sttm = con.prepareStatement(sSQL);                    
                    String val = tb.getModel().getValueAt(i, j).toString();
                    if (val.equals("false")){
                        Giam_gia ggif = new Giam_gia();
                        if (ggif.checkBooleanSP(gg, tb.getModel().getValueAt(i, 0).toString()) == true) {
                            sttm.setString(1, tb.getModel().getValueAt(i, 0).toString());
                            sttm.executeUpdate();
                            sttm.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error_GG20: "+ e.toString());
        } 
    } 
    
    public void deleteGGSoHuu(String maGG){
        try {
            String sSQL = "delete SO_HUU where MAKM = ?";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(1, maGG);
            sttm.executeUpdate();
            sttm.close();
        } catch (Exception e) {
            System.out.println("Error_GG21: "+ e.toString());
        }
    }  
    
    public void deleteGGCTKM(String maGG){
        try {
            String sSQL = "delete CT_KHUYEN_MAI where MAKM = ?";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(1, maGG);
            sttm.executeUpdate();
            sttm.close();
        } catch (Exception e) {
            System.out.println("Error_GG22: "+ e.toString());
        }
    }  
    
    public void addGGToanShop(String gg, String sp){
        try {
            String sSQL = "insert CT_KHUYEN_MAI(MASP, MAKM)"
                    + "values(?,?)";
            con = TestDatabase.getDBConnect();
            sttm = con.prepareStatement(sSQL);
            sttm.setString(1, sp);
            sttm.setString(2, gg);
            sttm.executeUpdate();
            sttm.close();
        } catch (Exception e) {
            System.out.println("Error_GG22: " + e.toString());
        }
    }    
    
    public ArrayList<String> getListMaSP(){
        ArrayList<String> list = new ArrayList<>();
        ResultSet rs = null;
        Statement sttm = null;  
        try {
            String sSQL = "SELECT MASP FROM SAN_PHAM";
            con = TestDatabase.getDBConnect();
            sttm = con.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()){                
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Error_GG23:"+e.toString());
        }
        finally{
            try {
                rs.close(); sttm.close(); con.close();
            } catch (Exception e) {
            }
        }
        return list;
    }    
}
