/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import helper.ShareData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.transaction.xa.Xid;
import javax.xml.crypto.Data;
import  helper.ShareData;
/**
 *
 * @author trieu
 */
public class DAO {

    private Connection conn;
    PreparedStatement sttm = null;

// Kết nối db
    public DAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS01:1433;database=Mobile_Store;trustServerCertificate=true;";
            String username = "sa";
            String password = "19001005";
            conn = DriverManager.getConnection(connectionUrl, username, password);
            // Statement stmt = conn.createStatement();
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DAO();
    }

    // Thêm dữ liệu Khách Hàng vào DB
    public boolean addKH(KhachHang kh) {
        try {
            String sql = "insert KHACH_HANG(MAKH, HOKH, TENKH, SDTKH, CMNDKH, EMAIL, GIOITINH, DIACHI)"
                    + "values(?,?,?,?,?,?,?,?)";
            sttm = conn.prepareStatement(sql);
            sttm.setString(1, kh.getMaKH());
            sttm.setString(2, kh.getHoKH());
            sttm.setString(3, kh.getTenKH());
            sttm.setString(4, kh.getSoDT());
            sttm.setString(5, kh.getCmnd());
            sttm.setString(6, kh.getEmail());
            sttm.setString(7, kh.getGioiTinh());
            sttm.setString(8, kh.getDiaChi());
            //sttm.setString(9, kh.getGhiChu());
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        return false;
    }

    // Lấy Dữ liệu Khách Hàng từ DB
    public ArrayList<KhachHang> getListKhachHang() {
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KHACH_HANG WHERE TRANGTHAI = 1";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            //rs = sttm.executeQuery(sql);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoKH(rs.getString(2));
                kh.setTenKH(rs.getString(3));
                kh.setCmnd(rs.getString(5));
                kh.setEmail(rs.getString(6));
                kh.setSoDT(rs.getString(4));
                kh.setGioiTinh(rs.getString(7));
                kh.setDiaChi(rs.getString(8));
                //kh.setGhiChu(rs.getString(9));

                list.add(kh);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
        }
        return list;
    }

    // Lấy mã những sp còn hàng ở tròn EMEI
    public ArrayList<String> getMSP() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT MASP, COUNT(MASP) AS SOLUONG FROM IMEI WHERE TRANGTHAI = 1 GROUP BY MASP HAVING COUNT(MASP) > 0";

        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                String sp = (rs.getString("MASP"));
                list.add(sp);
                //System.out.println(sp.getTenSp());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi truy xuất!");
            System.out.println("137");
        }
        return list;
    }
    //Lấy TT SP từ DB
    public ArrayList<SanPham> getSPModel(String loai) {
        ArrayList<String> tmp = getMSP();
        ArrayList<SanPham> list = new ArrayList<>();
        for (int i = 0; i < tmp.size(); i++) {
            String sql = "SELECT MASP, TENSP,GIA, LOAI FROM SAN_PHAM  WHERE MASP ='" + tmp.get(i) + "' AND TRANGTHAI = 1 AND LOAI = N'"+loai+"'";
            try {
                sttm = conn.prepareStatement(sql);
                ResultSet rs = sttm.executeQuery();
                while (rs.next()) {
                    SanPham sp = new SanPham();
                    sp.setMaSp(rs.getString("MASP"));
                    sp.setTenSp(rs.getString("TENSP"));
                    sp.setGia(rs.getInt("GIA"));
                    sp.setLoai(rs.getString("LOAI"));
                    list.add(sp);
                }
            } catch (Exception e) {
            }
        }

        return list;
    }

    // ĐỔ vào bảng SP
    public void LoadTable_SP(JTable table,String loai) {
        ArrayList<SanPham> list = getSPModel( loai);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{
                list.get(i).getTenSp(), list.get(i).getGia(), "Thêm"
            });
        }
        model.fireTableDataChanged();
    }

    //Lấy tên Sản Phẩm từ DB
    public ArrayList<SanPham> TimKiemTheoTen(String s) {
        ArrayList<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SAN_PHAM WHERE TENSP LIKE '%" + s + "%' AND TRANGTHAI = 1";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();

            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setTenSp(rs.getString("TENSP"));
                sp.setGia(rs.getInt("GIA"));
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("Tim KIem SP");
        }
        return list;
    }

    // Hàm để chỉnh ảnh cho phù hợp
    public ImageIcon reSizeImage(String path) {
        ImageIcon myImage = new ImageIcon(path);
        Image img = myImage.getImage();
        Image newImg = img.getScaledInstance(120, 35, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

//    public String Load_Anh()
//    {
//        try {
//            JFileChooser f=new JFileChooser("D:\\HK1_Năm 3\\Neatbeans OOP\\Mobile_Store\\src\\main\\java\\Icon_DoAn_DienThoai\\ip11.jpg");
//            f.setDialogTitle("Mở File");
//            f.showOpenDialog(null);
//            File ftenAnh= f.getSelectedFile();
//            anh_Address=ftenAnh.getAbsolutePath();  
//        } catch (Exception e) {
//        }
//        return anh_Address;
//    }
    // Đổ vào bảng gợi ý sản phẩm
    public void LoadTable_GoiYSP(JTable table, JTextField txt) {
        //table.getModel();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ArrayList<SanPham> tmp = TimKiemTheoTen(txt.getText());
        for (int i = 0; i < tmp.size(); i++) {
            model.addRow(new Object[]{
                tmp.get(i).getTenSp(), tmp.get(i).getGia(), "Thêm"
            });
        }

        model.fireTableDataChanged();
    }

    //đổ sản phẩm đã chọn khi bấm nút giỏ hàng vào bảng đơn hàng
    // col: cột 3 ( cột giỏ hàng)
    // row: hàng đang chọn
//    static int set_row_table_DonHang = 0;           // biến static để xóa row 0
//    int flag_tmp = 0;                               // biến để ktra có add đucợ row nào chưa
    static int soluong_sp = 1;
    static int k = 0;
    static int sum_total = 0;
    static int danh_dau = 0;
    static int set_row_table_DonHang = 0;           // biến static để xóa row 0
    static int flag_tmp = 0;

    public void Load_to_DonHang(int col, int row, JTable table1, JTable table2) {
        DefaultTableModel model = (DefaultTableModel) table2.getModel();        // txt1: Tongtienhang, txt2: Khachcantra, txt3: phivanchuyen
        if (table2.getRowCount() == 0) {
            set_row_table_DonHang = 0;
        }
        if (set_row_table_DonHang == 0) {
            model.setRowCount(1);
            table2.setValueAt("", 0, 0);
            table2.setValueAt(0, 0, 2);
        }
        int j = 0;

        //menu.show(table1, 330, 17);
        String tmp = (String) table1.getValueAt(row, 0);
//            sum_total += (int) table1.getValueAt(row, 1);
//            txt1.setText(String.valueOf(sum_total));
//            txt2.setText(String.valueOf(Integer.parseInt(txt1.getText()) + Integer.parseInt(txt3.getText())));
        while (k < table2.getRowCount()) {
            if (tmp.compareTo((String) table2.getValueAt(j, 0)) != 0) {
                k++;
                j++;
            } else {
                k = 0;
                break;
            }
        }
        if (k != 0) {
            model.addRow(new Object[]{
                table1.getValueAt(row, 0), table1.getValueAt(row, 1), "", soluong_sp, table1.getValueAt(row, 1), "Bỏ", ""
            });
            flag_tmp = 1;
            k = 0;
        } else {
            soluong_sp = (int) table2.getValueAt(j, 3);
            soluong_sp++;
            table2.setValueAt(soluong_sp, j, 3);
            table2.setValueAt(soluong_sp * (int) table2.getValueAt(j, 1), j, 4);
            soluong_sp = 1;
            k = 0;
        }
        //JOptionPane.showMessageDialog(null, "Thêm vào giỏ hàng thành công");

        // ktra nếu đã có row trong table DonHang thì mình  xóa row[0] 1 lần duy nhất
        if (flag_tmp == 1 && set_row_table_DonHang == 0) {
            model.removeRow(0);
            set_row_table_DonHang = 1;
            //danh_dau=1;
            //danh_dau=1;
        }

        model.fireTableDataChanged();

    }
    // Lấy mã KM từ sql 

    public ArrayList<String> get_MaKM_From_CTKM(String tensp) {

        String sql = "SELECT MAKM FROM CT_KHUYEN_MAI WHERE MASP = (SELECT MASP FROM SAN_PHAM WHERE TENSP = '" + tensp + "' AND TRANGTHAI =1) ";
        ArrayList<String> res = new ArrayList<>();

        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                res.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("Lỗi");
        }
        return res;
    }

    // lấy mã giảm giá theo sản phẩm đổ vào combobox
    public JComboBox Show_MaGG_TheoSP(int col, int row, JTable table) {
        //DefaultTableModel model = (DefaultTableModel) table.getModel();
        JComboBox cbb = new JComboBox();
        if (col == 2) {
            String tensp = (String) table.getValueAt(row, 0);  // lay duoc ten sp tu tableSanPham
            ArrayList<String> res = get_MaKM_From_CTKM(tensp);
            cbb.removeAllItems();
            for (int i = 0; i < res.size(); i++) {
                String sql = "SELECT MUCGG,DONVI FROM KHUYEN_MAI WHERE MAKM = '" + res.get(i) + "'AND TRANGTHAI = 1"; // lấy ra mucgg và đon vị
                try {
                    sttm = conn.prepareStatement(sql);
                    ResultSet rs = sttm.executeQuery();
                    //rs = sttm.executeQuery(sql);
                    while (rs.next()) {
                        int mucgg = rs.getInt(1);
                        String donvi = rs.getString(2);
                        if (mucgg <= 100) {
                            donvi = " percent";
                        } else {
                            donvi = "VND";
                        }
                        cbb.addItem(String.valueOf(mucgg) + donvi);
                    }
                } catch (Exception e) {
                    System.out.println("Error:" + e.toString());
                    System.out.println("get_Tt_GG");
                }
            }

        }
        return cbb;
    }

    // Lấy dữ liệu vào bảng INFO KHÁCH HÀNG
    public void Load_to_InFohachHang(JTable table) {
        try {
            ArrayList<KhachHang> tmp = getListKhachHang();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (int i = 0; i < tmp.size(); i++) {
                //String fullname = tmp.get(i).getHoKH()+" "+ tmp.get(i).getTenKH();
                model.addRow(new Object[]{
                    tmp.get(i).getMaKH(), tmp.get(i).getHoKH() + " " + tmp.get(i).getTenKH(), tmp.get(i).getSoDT()
                });
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            System.out.println("Load_to_InfoKH");
        }
    }

    public void Load_to_lable_InfoKH(JLabel lbl1, JLabel lbl2, JTable table, int row) {
        String tmp1 = (String) table.getValueAt(row, 1);
        lbl1.setText(tmp1);
        String tmp2 = (String) table.getValueAt(row, 2);
        lbl2.setText(tmp2);
    }

    // Lấy form để điền vào Combobox infoMGGKH
    public ArrayList<String> get_TT_GG(String makm) {
        ArrayList<String> res = new ArrayList<>();
        String sql = "SELECT MUCGG,DONVI FROM KHUYEN_MAI WHERE MAKM = '" + makm + "' AND TRANGTHAI =1";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            //rs = sttm.executeQuery(sql);
            while (rs.next()) {
                int mucgg = rs.getInt(1);
                String donvi = rs.getString(2);
                if (mucgg <= 100) {
                    donvi = " percent";
                } else {
                    donvi = "VND";
                }
                res.add(String.valueOf(mucgg) + donvi);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("get_Tt_GG");
        }
        return res;
    }

    // Điền vào cbb InfoMGGKH
    public void Load_to_CBBMGG_of_KH(JTable table, JComboBox cbb, int row) {

        String tmp = (String) table.getValueAt(row, 0);
        //ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT MAKM FROM SO_HUU WHERE MAKH = '" + tmp + "'";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            //rs = sttm.executeQuery(sql);
            ArrayList<String> res;
            cbb.removeAllItems();
            while (rs.next()) {
                String makm = rs.getString(1);
                res = get_TT_GG(makm);
                for (int i = 0; i < res.size(); i++) {
                    cbb.addItem(res.get(i));
                }
            }

        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("Load_to_CBBMGG");
        }

    }

//    //điền vào cbb Emei
//    public JComboBox Load_to_CBBEMEI(String tensp) {
//        JComboBox cbb = new JComboBox();
//        String sql = "SELECT SOSERI FROM EMEI WHERE MASP = (SELECT MASP FROM SAN_PHAM WHERE TENSP = '" + tensp + "')AND TRANGTHAI = 1 ";
//        try {
//            sttm = conn.prepareStatement(sql);
//            ResultSet rs = sttm.executeQuery();
//            //rs = sttm.executeQuery(sql);
//            ArrayList<String> res;
//            cbb.removeAllItems();
//            while (rs.next()) {
//                String seri = rs.getString(1);
//                res = get_TT_GG(seri);
//                cbb.addItem(res);
//            }
//        } catch (Exception e) {
//            System.out.println("Error:" + e.toString());
//            System.out.println("Load_to_CBBMGG");
//        }
//        return cbb;
//    }
    // Lấy tất cả số emei từ EMEI thồn qua tên sp -> masp
    public JComboBox get_Emei(String tensp,JComboBox cbb) {
        //JComboBox cbb = new JComboBox();
        String sql = "SELECT SOSERI FROM IMEI WHERE MASP = (SELECT MASP FROM SAN_PHAM WHERE TENSP = '" + tensp + "')AND TRANGTHAI = 1 ";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            cbb.removeAllItems();
            while (rs.next()) {
                String seri = rs.getString(1);
                cbb.addItem(seri);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("468");
        }
        return cbb;
    }

    // Insert vào phiếu bao hành
    public boolean add_PBH(String soseri, String sdt) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 6);
        Date date = cal.getTime();
        String tmp = String.valueOf(cal);
        String makh = get_maKH_of_SDT(sdt);
        try {
            String bh = "BH";
            String sql = "insert PHIEU_BAO_HANH(SOBH,NGAYHETHAN,SOSERI,MAKH)"
                    + "values(?,?,?,?)";
            sttm = conn.prepareStatement(sql);
            //sttm.setString(1, tmp);
            sttm.setString(1, bh + soseri);
            sttm.setDate(2, new java.sql.Date(date.getTime()));
            sttm.setString(3, soseri);
            sttm.setString(4, makh);
            //sttm.setInt(3, 0);
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }

        return false;
    }
// Insert vào phiếu bao hành

    public boolean edit_trangthai_inEmei(String soseri, String tt) {
        try {
            String sql = "update IMEI SET TRANGTHAI = " + tt + "  WHERE SOSERI ='" + soseri + "'";
            sttm = conn.prepareStatement(sql);
            //sttm.setInt(2, 0);
            //sttm.setInt(3, 0);
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }

        return false;
    }
//    //Lấy địa chỉ của Khách hàng
//    public String get_DiaChi(String sdt) {
//        String tmp = "";
//        String sql = "SELECT DIACHI FROM KHACH_HANG WHERE SDTKH = '"+sdt+"'"; 
//        try {
//            sttm = conn.prepareStatement(sql);
//            ResultSet rs = sttm.executeQuery();
//            tmp = rs.getString(1);
//        } catch (Exception e) {
//            System.out.println("Error:" + e.toString());
//            System.out.println("Tại đây");
//        }
//        return tmp;
//    }
    //Lấy mã sp thông qua tên sp

    public ArrayList<String> get_soseri(String tensp) {

        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT SOSERI FROM IMEI WHERE TRANGTHAI = 0  AND MASP = (SELECT MASP FROM SAN_PHAM WHERE TENSP = '" + tensp + "')";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                String seri = rs.getString(1);
                list.add(seri);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
        }
        return list;
    }

    // xóa bảo hành khi xóa khỏi giỏ hàng
    public boolean delete_baohanh(String soseri) {
        try {
            String bh = "BH";
            String sql = "delete FROM PHIEU_BAO_HANH WHERE SOBH = '" + bh + soseri + "'";
            sttm = conn.prepareStatement(sql);

            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        return false;
    }

    // update trạng thái của phiếu bảo hành
    public boolean update_tt_of_PBH(String soseri, String tt) {

        try {
            String sql = "update PHIEU_BAO_HANH SET TRANGTHAI =" + tt + "where SOSERI = '" + soseri + "'";
            sttm = conn.prepareStatement(sql);
            //sttm.setInt(2, 0);
            //sttm.setInt(3, 0);
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());

        }
        return false;
    }

    //lấy mã KH từ tên SDT
    public String get_maKH_of_SDT(String sdt) {
        String makh = "";
        String sql = "SELECT MAKH FROM KHACH_HANG WHERE SDTKH = '" + sdt + "' AND TRANGTHAI = 1";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                makh = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
        }
        return makh;
    }

    // THêm dữ liệu vào hóa đơn
    public boolean add_HD(String sdt, String tongtien, String phivanchuyen, String tonggiamgia, String thanhtien,String mggkh) {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String ngaylap_hd = String.valueOf(cal);
        String makh = get_maKH_of_SDT(sdt);
        String manv = ShareData.taikhoandangnhap.getMaNV();
        System.out.println(manv);
        try {
            //String hd = "HD";
            String sql = "insert HOA_DON(NGAYLAP,TONGTIEN,MANV,MAKH,PHIVANCHUYEN,TONGGIAMGIA,THANHTIEN,MAGGKH)"
                    + "values(?,?,?,?,?,?,?,?)";
            sttm = conn.prepareStatement(sql);
            //sttm.setString(1, tmp);
            //sttm.setString(1, hd+"_"+String.valueOf(i));
            sttm.setDate(1, new java.sql.Date(date.getTime()));
            sttm.setInt(2, Integer.parseInt(tongtien));
            sttm.setString(3, manv);
            sttm.setString(4, makh);
            sttm.setInt(5, Integer.parseInt(phivanchuyen));
            sttm.setInt(6, Integer.parseInt(tonggiamgia));
            sttm.setInt(7, Integer.parseInt(thanhtien));
            sttm.setString(8, mggkh);
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            System.out.println("614");
        }

        return false;
    }
    public String get_masp_From_emei(String soseri) {

        String masp ="";
        String sql = "SELECT MASP FROM IMEI WHERE SOSERI = '" + soseri + "' AND TRANGTHAI = 2 ";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                masp = rs.getString(1);
                
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("631");
        }
        return masp;
    }
    // thêm dữ liêu jvaof bảng đổi trả khi mua sp
    public boolean add_Doi_Tra(String soseri)
    {
        Date date = new Date();
        String masp = get_masp_From_emei(soseri);
        try {  
            String sql = "insert DOI_TRA(MASP,SOSERI,NGAYHETHAN)"
                    + "values(?,?,?)";
            sttm = conn.prepareStatement(sql);
            sttm.setString(1, masp);
            sttm.setString(2, soseri);
            sttm.setDate(3, new java.sql.Date(date.getTime()));
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            System.out.println("650");
        }
        return false;
    }
    //Lấy mã sản phẩm từ tên sp có trong bảng Dơn Hàng
    public String get_masp_from_tensp(String tensp) {

        String sql = "SELECT MASP FROM SAN_PHAM WHERE TENSP = '" + tensp + "' AND TRANGTHAI = 1";
        String masp = "";
        try {

            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                masp = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("669");
        }
        return masp;
    }

    // Lấy số hóa đơn từ mã kh và ngày lập
    public int get_sohd_from_hd(String sdt) {
        String makh = get_maKH_of_SDT(sdt);
        String sql = "SELECT SOHD FROM HOA_DON WHERE MAKH = '" + makh + "' AND TRANGTHAI = 1";
        int sohdString = 0;
        try {

            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                sohdString = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
        }
        return sohdString;
    }

    // add vào CTHD
    public boolean add_CT_HD(String tensp, String sdt, int soluong, int dongia, String sale) {
        String masp = get_masp_from_tensp(tensp);
        int sohd = get_sohd_from_hd(sdt);
        try {
            String sql = "insert CT_HOA_DON(MASP,SOHD,DONGIA,SOLUONG,SALE)"
                    + "values(?,?,?,?,?)";
            sttm = conn.prepareStatement(sql);
            sttm.setString(1, masp);
            sttm.setInt(2, sohd);
            sttm.setInt(3, dongia);
            sttm.setInt(4, soluong);
            sttm.setString(5, sale);
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            System.out.println("708");
        }
        return false;
    }

//    //Lấy mã kh từ hóa đơn
//    public ArrayList<String> get_MaKH_From_HoaDon() {
//        ArrayList<String> list = new ArrayList<>();
//        String sql = "SELECT MAKH FROM HOA_DON ";
//        String makh = "";
//        try {
//
//            sttm = conn.prepareStatement(sql);
//            ResultSet rs = sttm.executeQuery();
//            while (rs.next()) {
//                makh = rs.getString(1);
//                list.add(makh);
//            }
//        } catch (Exception e) {
//            System.out.println("Error:" + e.toString());
//        }
//        return list;
//    }
    // Lấy tên Kh  từ MAKH
    public String getTenKH_From_MAKH(String makh) {
        //ArrayList<String> list_makh = get_MaKH_From_HoaDon();
        String list = "";
        String sql = "SELECT HOTEN=HOKH+' '+TENKH FROM KHACH_HANG WHERE MAKH = '" + makh + "' AND TRANGTHAI = 1";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDonmodel hd = new HoaDonmodel();
                list = rs.getString(1);
//                    hd.setSdtKH(rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
        }
        return list;
    }
// Lấy sdt Kh  từ MAKH

    public String getSDT_From_MAKH(String makh) {
        //ArrayList<String> list_makh = get_MaKH_From_HoaDon();
        String list = "";
        String sql = "SELECT SDTKH FROM KHACH_HANG WHERE MAKH = '" + makh + "' AND TRANGTHAI = 1";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDonmodel hd = new HoaDonmodel();
                list = rs.getString(1);
//                    hd.setSdtKH(rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
        }
        return list;
    }

    //Lấy tất cả còn lại của HOADON
    public ArrayList<HoaDonmodel> getALL_From_HOADON() {
        ArrayList<HoaDonmodel> list = new ArrayList<>();

        String sql = "SELECT SOHD,NGAYLAP,TONGTIEN,MANV,MAKH,PHIVANCHUYEN,TONGGIAMGIA,THANHTIEN FROM HOA_DON WHERE TRANGTHAI = 1";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDonmodel hd = new HoaDonmodel();
                hd.setSoHD((rs.getInt(1)));
                hd.setNgaylapHD(rs.getDate(2));
                hd.setTongTienHang(rs.getInt(3));
                hd.setMaNV(rs.getString(4));
                hd.setMaKH(rs.getString(5));
                String tenkh = getTenKH_From_MAKH(hd.getMaKH());
                String sdtkh = getSDT_From_MAKH(hd.getMaKH());
                
                hd.setTenKH(tenkh);
                hd.setSdtKH(sdtkh);
                hd.setPhiShip(rs.getInt(6));
                hd.setGiamGia(rs.getInt(7));
                hd.setTongTienHD(rs.getInt(8));
                list.add(hd);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("791");
        }
        return list;
    }

    //Gộp 2 dữ liệu vào 1 hóa đơn model
    public ArrayList<HoaDonmodel> get_HoaDonmodels() {
        ArrayList<HoaDonmodel> list = new ArrayList<>();
        //ArrayList<HoaDonmodel> list_ten_sdt = getTenKH_SDT_From_MAKH();
        ArrayList<HoaDonmodel> list_All = getALL_From_HOADON();
        for (int i = 0; i < list_All.size(); i++) {
            HoaDonmodel hd = new HoaDonmodel();
            hd.setSoHD(list_All.get(i).getSoHD());
            hd.setTenKH(list_All.get(i).getTenKH());
            hd.setMaNV(list_All.get(i).getMaNV());
            hd.setSdtKH(list_All.get(i).getSdtKH());
            hd.setNgaylapHD(list_All.get(i).getNgaylapHD());
            hd.setTongTienHang(list_All.get(i).getTongTienHang());
            hd.setPhiShip(list_All.get(i).getPhiShip());
            hd.setGiamGia(list_All.get(i).getGiamGia());
            hd.setTongTienHD(list_All.get(i).getTongTienHD());
            list.add(hd);
        }
        return list;
    }

    //Đổ vào bảng hóa đơn
    public void Load_Table_HoaDon(JTable table) {
        ArrayList<HoaDonmodel> list = get_HoaDonmodels();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{
                list.get(i).getSoHD(), list.get(i).getTenKH(), list.get(i).getSdtKH(), list.get(i).getMaNV(), list.get(i).getNgaylapHD(), list.get(i).getTongTienHang(), list.get(i).getPhiShip(), list.get(i).getGiamGia(), list.get(i).getTongTienHD(), "", "Xóa HD"
            });
        }
        model.fireTableDataChanged();
    }

    //Đổ dữ liệu của từng hóa đơn vào hóa đơn tạm
    public boolean add_Hoa_Don_Tmp(String hotenkh, String sdt, String tongtienhang, String phivanchuyen, String giamgia, String thanhtien) {
        String manv = ShareData.taikhoandangnhap.getMaNV();
        try {
            String sql = "insert HOA_DON_TMP(HOTENKH,SDT,MANV,NGAYLAPHD,TONGTIENHANG,PHIVANCHUYEN,GIAMGIA,THANHTIEN)"
                    + "values(?,?,?,?,?,?,?,?)";
            Date date = new Date();
            sttm = conn.prepareStatement(sql);
            sttm.setString(1, hotenkh);
            sttm.setString(2, sdt);
            sttm.setString(3, manv);
            sttm.setDate(4, new java.sql.Date(date.getTime()));
            sttm.setInt(5, Integer.parseInt(tongtienhang));
            sttm.setInt(6, Integer.parseInt(phivanchuyen));
            sttm.setInt(7, Integer.parseInt(giamgia));
            sttm.setInt(8, Integer.parseInt(thanhtien));
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            System.out.println("848");
        }

        return false;
    }

    //
    //Lấy dwxl iệu từ HOA_DON_TMp
    public ArrayList<HoaDonmodel> TimKiem_KH(String tenkh) {
        ArrayList<HoaDonmodel> list = new ArrayList<>();
        String sql = "SELECT MAHDTMP,HOTENKH,SDT,NGAYLAPHD,TONGTIENHANG,PHIVANCHUYEN,GIAMGIA,THANHTIEN FROM HOA_DON_TMP WHERE HOTENKH LIKE '%" + tenkh + "%' AND TRANGTHAI = 1";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDonmodel hd = new HoaDonmodel();
                hd.setSoHD(rs.getInt(1));
                hd.setTenKH(rs.getString(2));
                hd.setSdtKH(rs.getString(3));
                hd.setNgaylapHD(rs.getDate(4));
                hd.setTongTienHang(rs.getInt(5));
                hd.setPhiShip(rs.getInt(6));
                hd.setGiamGia(rs.getInt(7));
                hd.setTongTienHD(rs.getInt(8));
                list.add(hd);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("876");
        }
        return list;
    }

    // Đổ vào bảng gợi ý Hóa ĐƠn
    public void LoadTable_GoiYHD(JTable table, JTextField txt) {

        //table.getModel();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ArrayList<HoaDonmodel> list = TimKiem_KH(txt.getText());

        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{
                list.get(i).getSoHD(), list.get(i).getTenKH(), list.get(i).getSdtKH(), "", list.get(i).getNgaylapHD(), list.get(i).getTongTienHang(), list.get(i).getPhiShip(), list.get(i).getGiamGia(), list.get(i).getTongTienHD(), "", "Xóa HD"
            });
        }
        model.fireTableDataChanged();
    }

    //Lấy mã sản phẩm từ tên sp có trong bảng Dơn Hàng
    public String get_tensp_from_masp(String masp) {

        String sql = "SELECT TENSP FROM SAN_PHAM WHERE masp = '" + masp + "' AND TRANGTHAI = 1";
        String tensp = "";
        try {

            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                tensp = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("916");
        }
        return tensp;
    }

    //Lấy dữ liệu bỏ vào table sản phẩm trong HÓA ĐƠN
    public ArrayList<CT_Hoa_Donmodel> get_CT_HoaDon(int sohd) {
        String sql = "SELECT * FROM CT_HOA_DON WHERE SOHD = '" + sohd + "'";
        ArrayList<CT_Hoa_Donmodel> list = new ArrayList<>();
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                CT_Hoa_Donmodel cthd = new CT_Hoa_Donmodel();
                String masp = rs.getString(1);
                cthd.setTensp(get_tensp_from_masp(masp));
                cthd.setGia(rs.getInt(3));
                cthd.setSoluong(rs.getInt(4));
                cthd.setSale(rs.getString(5));
                int tmp2 = 0;
                for (int i = 0; i < cthd.getSale().length() - 8; i++) {
                    tmp2 = tmp2 * 10 + Integer.parseInt(String.valueOf(cthd.getSale().charAt(i)));
                }
                int tongtien = (cthd.getGia() * cthd.getSoluong()) - ((cthd.getGia() * cthd.getSoluong()) * tmp2 / 100);
                cthd.setThanhtien(tongtien);
                list.add(cthd);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("load to SP in hoa don");
        }
        return list;
    }
    //đổ vào bảng sản phẩm trong hóa đơn

    public void LoadTable_SP_in_HoaDon(int sohd, JTable table) {
        ArrayList<CT_Hoa_Donmodel> list = get_CT_HoaDon(sohd);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{
                list.get(i).getTensp(), list.get(i).getGia(), list.get(i).getSale(), list.get(i).getSoluong(), list.get(i).getThanhtien()
            });
        }
        model.fireTableDataChanged();
    }

    // Xóa Hóa đơn trong Hóa Đơn
    public boolean edit_trangthai_inHoaDon(int sohd, String tt) {
        try {
            String sql = "update HOA_DON SET TRANGTHAI = " + tt + "  WHERE SOHD ='" + sohd + "'";
            sttm = conn.prepareStatement(sql);
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }

        return false;
    }
    // Xóa CTHD

    public boolean delete_CTHD(int sohd) {
        try {
            String sql = "DELETE FROM CT_HOA_DON WHERE SOHD ='" + sohd + "'";
            sttm = conn.prepareStatement(sql);
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        return false;
    }
    // edit TRANGTHAI trog HOA_DON_TMP

    public boolean edit_trangthai_inHoaDon_TMP(int sohd, String tt) {
        try {
            String sql = "update HOA_DON_TMP SET TRANGTHAI = " + tt + "  WHERE MAHDTMP ='" + sohd + "'";
            sttm = conn.prepareStatement(sql);
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }

        return false;
    }
//     // Lấy tất cả số emei của sp đó ở tt 2
//     public ArrayList<String> get_all_emeitt2_from_masp(String tensp)
//     {
//         String masp = get_masp_from_tensp(tensp);
//         ArrayList<String> list = new ArrayList<>();
//         String sql = "SELECT SOSERI FROM EMEI WHERE MASP = '" + masp + "' AND TRANGTHAI = 2";
//        try {
//            String soseri ="";
//            sttm = conn.prepareStatement(sql);
//            ResultSet rs = sttm.executeQuery();
//            while (rs.next()) {
//                soseri = rs.getString(1);
//                list.add(soseri);
//            }
//        } catch (Exception e) {
//            System.out.println("Error:" + e.toString());
//        }
//         return list;
//     }
    //Bấm vào Kh -> trả về list số seri của KH đó từ PBH

    public ArrayList<String> get_emei_of_KH(String sdt) {
        String makh = get_maKH_of_SDT(sdt);
        String sql = "SELECT SOSERI FROM PHIEU_BAO_HANH WHERE MAKH = '" + makh + "' AND TRANGTHAI = 2";
        ArrayList<String> list = new ArrayList<>();
        try {
            String soseri = "";
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                soseri = rs.getString(1);
                list.add(soseri);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
        }
        return list;
    }
    // bấm vào HD -> trả về tất cả mã sp của hd đó

    public ArrayList<String> getALL_MASP_From_HOADON(int sohd) {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT MASP FROM CT_HOA_DON WHERE SOHD = '" + sohd + "'";
        try {
            String masp = "";
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                masp = rs.getString(1);
                list.add(masp);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("1053");
        }
        return list;
    }

    //Lấy tất cả số seri của sp đó ở tt 2
    public ArrayList<String> get_all_emeitt2_from_masp(int sohd) {
        ArrayList<String> list_masp = getALL_MASP_From_HOADON(sohd);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < list_masp.size(); i++) {
            String sql = "SELECT SOSERI FROM IMEI WHERE MASP = '" + list_masp.get(i) + "' AND TRANGTHAI = 2";
            try {
                String soseri = "";
                sttm = conn.prepareStatement(sql);
                ResultSet rs = sttm.executeQuery();
                while (rs.next()) {
                    soseri = rs.getString(1);
                    list.add(soseri);
                }
            } catch (Exception e) {
                System.out.println("Error:" + e.toString());
                System.out.println("1074");
            }
        }
        return list;
    }

    // Lấy số seri trùng nhau của 2 list trên ( số seri của Kh đó và số seri của HD phát sinh bởi KH đó )
    public ArrayList<String> get_thesameseri_of_hd_and_kh(int sohd, String sdt) {
        ArrayList<String> list_seri_of_HD = get_all_emeitt2_from_masp(sohd);
        ArrayList<String> list_seri_of_KH = get_emei_of_KH(sdt);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < list_seri_of_HD.size(); i++) {
            for (int j = 0; j < list_seri_of_KH.size(); j++) {
                if (list_seri_of_HD.get(i).equals(list_seri_of_KH.get(j))) {
                    list.add(list_seri_of_HD.get(i));
                }
            }
        }
        return list;
    }

    // Bấm vào sp -> Lấy tất cả số seri của sp đó ở tt 2
    // Lấy tất cả số emei từ EMEI thồn qua tên sp -> masp
    public ArrayList<String> get_Emei_From_tensp(String tensp) {

        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT SOSERI FROM IMEI WHERE MASP = (SELECT MASP FROM SAN_PHAM WHERE TENSP = '" + tensp + "' AND TRANGTHAI = 1)AND TRANGTHAI = 2 ";
        try {
            sttm = conn.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();

            while (rs.next()) {
                String seri = rs.getString(1);
                list.add(seri);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.toString());
            System.out.println("1111");
        }
        return list;
    }

    // Lấy tất cả những số seri trung nhau ở 2 list trên(soseri = của kh đã mua trong hd đó và số seri của sp đó trong emei )
    public void get_thesameseri_of_hd_kh_andsp(int sohd, String sdt, String tensp, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ArrayList<String> list_seri_of_HD_and_kh = get_thesameseri_of_hd_and_kh(sohd, sdt);
        ArrayList<String> list_seri_of_SP = get_Emei_From_tensp(tensp);
//        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < list_seri_of_HD_and_kh.size(); i++) {
            for (int j = 0; j < list_seri_of_SP.size(); j++) {
                if (list_seri_of_HD_and_kh.get(i).equals(list_seri_of_SP.get(j))) {
//                    list.add(list_seri_of_HD_and_kh.get(i));
                    model.addRow(new Object[]{
                        list_seri_of_HD_and_kh.get(i)
                    });                    
                }
                
            }
        }
        model.fireTableDataChanged();
    }
    //Thay đổi số seri trong Đổi trả
    public boolean edit_seri_and_tt_DoiTra(String soseri_muondoi,String soseri_bandau) {
        try {
            String sql = "update DOI_TRA SET SOSERI = '" + soseri_muondoi + "' WHERE SOSERI ='" + soseri_bandau + "' AND TRANGTHAI = 1";
            sttm = conn.prepareStatement(sql);
            return sttm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        return false;
    }
    
    //Đổi lại trạng thái emei sau khi đổi máy
    public void edit_emei_pbh(String soseri_muondoi,String soseri_bandau,String sdt)
    {
        boolean tt_emei_saukhidoi = edit_trangthai_inEmei(soseri_muondoi, "2");
        boolean tt_emei_bandau = edit_trangthai_inEmei(soseri_bandau, "1");
        boolean del_pbh = delete_baohanh(soseri_bandau);
        boolean add_pbh=add_PBH(soseri_muondoi, sdt);
        boolean update_tt_pbh = update_tt_of_PBH(soseri_muondoi, "2");
        boolean edit_doitra = edit_seri_and_tt_DoiTra(soseri_muondoi, soseri_bandau);
        
    }
}

