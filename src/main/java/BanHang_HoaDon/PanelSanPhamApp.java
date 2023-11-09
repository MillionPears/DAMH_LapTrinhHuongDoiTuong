package BanHang_HoaDon;

//import DAO.ImeiDAO;
//import DAO.NhaCungCapDAO;
//import DAO.NhapHangDAO;
//import DAO.SanPhamDAO;
import BanHang_HoaDon.GiamDanDialog;
import helper.DataBaseHelper;
import helper.DataExcel;
import helper.DataValidator;
import helper.MessageDialogHelper;
import helper.ShareData;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.Imei;
import model.NhaCungCap;
import model.NhapHang;
import model.SanPham;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author ASUS
 */
public class PanelSanPhamApp extends javax.swing.JPanel {

    public DefaultTableModel tblModel= null;
    public  DefaultTableModel tblModelX= null;
    public  DefaultTableModel tblModelCNG= null;
    public DefaultComboBoxModel<String>cbxModel=null;
    // GiamDanDialog giamDanDialog=new GiamDanDialog(null,true);
    String anhMD="D:\\CODE_JAVA\\CuaHangDienThoai\\src\\com\\mycompany\\ql_cuahangdienthoai\\icon\\smartphone128.png";
    String anh_Address="";
     
   // private ArrayList<SanPham> listSP=new ArrayList();
    private ArrayList<NhapHang>listNH=new ArrayList<>();
    
    
    public PanelSanPhamApp() {
     
        initComponents();
        initTable();
        showNgayLapPhieuNhap();
        loadDataToTableDeleteTT0();
        loadDataToTable();
        loadDataToTableCapNhatGia();
        showTenNhaCungCap();
    }
    
    private void initTable()
    {
        tblModel= (DefaultTableModel)tblSanPham.getModel();
        tblModelX=(DefaultTableModel)tblSPDaXoa.getModel();
        tblModelCNG= (DefaultTableModel)tblSPCapNhatGia.getModel();
      
        tblSanPham.setModel(tblModel);
        tblSPDaXoa.setModel(tblModelX);
        tblSPCapNhatGia.setModel(tblModelCNG);
        
        tblSanPham.getTableHeader().setBackground(Color.BLUE);
        tblSanPham.getTableHeader().setForeground(Color.blue);
        
        tblSPDaXoa.getTableHeader().setBackground(Color.BLUE);
        tblSPDaXoa.getTableHeader().setForeground(Color.blue);
        
        tblSPCapNhatGia.getTableHeader().setBackground(Color.BLUE);
        tblSPCapNhatGia.getTableHeader().setForeground(Color.blue);
        
       // tblSanPham.getTableHeader().setb
        
        
        
        
        
       
    }
    private void xoaNhieuDongSPDX() throws Exception
    {
        ArrayList<SanPham>list=SanPhamDAO.findAllTT0();
        ArrayList<String> listSp=new ArrayList<>();
        int i=0;
        for (SanPham sp :list ) {
            if(i>=list.size()) return;
            if(tblSPDaXoa.getValueAt(i, 5)==null) {
                
                 i++;
                 continue;
            }
            if((boolean)tblSPDaXoa.getValueAt(i, 5)==true){
                listSp.add(sp.getMaSp());
                i++;
        }
        }
        if (listSp.size()==0) return;
         txtMaSPDaXoa.setText("");
         txtTenSPDaXoa.setText("");
        if( MessageDialogHelper.showConfirmDialog(this, "Bạn có muốn xóa "+listSp.size()+" Sản Phẩm này không ?", "Xác Nhận")==JOptionPane.YES_OPTION){
             for (String masp : listSp) {
             SanPhamDAO.delete(masp);
             ImeiDAO.delete(masp);
                }
             loadDataToTableDeleteTT0();
             
        }
       
    }
    private void khoiPhucNhieuSPDX() throws Exception
    {
        ArrayList<SanPham>list=SanPhamDAO.findAllTT0();
        ArrayList<String> listSp=new ArrayList<>();
        int i=0;
        for (SanPham sp :list ) {
            if(i>=list.size()) return;
            if(tblSPDaXoa.getValueAt(i, 5)==null) {
                
                 i++;
                 continue;
            }
            if((boolean)tblSPDaXoa.getValueAt(i, 5)==true){
                listSp.add(sp.getMaSp());
                i++;
        }
        }
        if (listSp.size()<2) return;
          txtMaSPDaXoa.setText("");
          txtTenSPDaXoa.setText("");
        if( MessageDialogHelper.showConfirmDialog(this, "Bạn có muốn khôi phục "+listSp.size()+" Sản Phẩm này không ?", "Xác Nhận")==JOptionPane.YES_OPTION){
             for (String masp : listSp) {
                 SanPham sp=SanPhamDAO.findByIdTT0(masp);
                 sp.setTrangthai(1);
                 Imei im=ImeiDAO.findByMaSp(sp.getMaSp());
                 if(im.getTrangThai()==10) im.setTrangThai(1);
                 im.setMaSp(sp.getMaSp());
                
                 ImeiDAO.updateTrangThai0_1(im);
                  SanPhamDAO.updateTrangThai0_1(sp);
                }
             loadDataToTableDeleteTT0();
             loadDataToTable();
           
            
             
        }
       
       
    }
    
    private void showTenNhaCungCap(){
         cbxModel=new DefaultComboBoxModel<>();
        
        
       
         try {
             ArrayList<NhaCungCap>ds=NhaCungCapDAO.findAll();
           
             for (NhaCungCap d : ds) {
                 cbxModel.addElement(d.getTenNhaCungCap());
                
             }
             cbxNhaCungCap.setModel(cbxModel);
             
             
         } catch (Exception ex) {
             ex.printStackTrace();
         }
    }
    public  void loadSort(String giatri,String chucnang){
        try {
             
              ArrayList<SanPham> list=SanPhamDAO.SortBy(giatri,chucnang);
           //  listSP=SanPhamDAO.findAll();
            tblModel.setRowCount(0);
            for (SanPham sp : list) {
            tblModel.addRow(new Object[]{sp.getMaSp(),sp.getTenSp(),sp.getLoai(),sp.getRam(),sp.getPin(),sp.getRom(),sp.getManHinh(),NhapHangDAO.getSoLuong(sp.getMaSp()),sp.getGia()});

            }
            tblModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, "Lỗi truy xuất dữ liệu", "Lỗi");
        }
    }
    public  void loadSortSL(String giatri,String chucnang){
        try {
             ArrayList<String> listsort=NhapHangDAO.SortBySoLuong(giatri, chucnang);
              ArrayList<SanPham> list=new ArrayList<>();
             for (String masp : listsort) {
                 
                list.add(SanPhamDAO.findByIdTT1(masp));
            }
            
              
           //  listSP=SanPhamDAO.findAll();
            tblModel.setRowCount(0);
            for (SanPham sp : list) {
            tblModel.addRow(new Object[]{sp.getMaSp(),sp.getTenSp(),sp.getLoai(),sp.getRam(),sp.getPin(),sp.getRom(),sp.getManHinh(),NhapHangDAO.getSoLuong(sp.getMaSp()),sp.getGia()});

            }
            tblModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, "Lỗi truy xuất dữ liệu", "Lỗi");
        }
    }
    public  void loadSort_A_9(String giatri,String chucnang){
        try {
             
              ArrayList<SanPham> list=SanPhamDAO.SortBy_A_9(giatri,chucnang);
           //  listSP=SanPhamDAO.findAll();
            tblModel.setRowCount(0);
            for (SanPham sp : list) {
            tblModel.addRow(new Object[]{sp.getMaSp(),sp.getTenSp(),sp.getLoai(),sp.getRam(),sp.getPin(),sp.getRom(),sp.getManHinh(),NhapHangDAO.getSoLuong(sp.getMaSp()),sp.getGia()});

            }
            tblModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, "Lỗi truy xuất dữ liệu", "Lỗi");
        }
    }
    
    public  void loadDataToTable() {
        try {
             
              ArrayList<SanPham> list=SanPhamDAO.findAll();
           //  listSP=SanPhamDAO.findAll();
            tblModel.setRowCount(0);
            for (SanPham sp : list) {
            tblModel.addRow(new Object[]{sp.getMaSp(),sp.getTenSp(),sp.getLoai(),sp.getRam(),sp.getPin(),sp.getRom(),sp.getManHinh(),NhapHangDAO.getSoLuong(sp.getMaSp()),sp.getGia()});

            }
            tblModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, "Lỗi truy xuất dữ liệu", "Lỗi");
        }
    }
    public  void loadDataToTableDeleteTT0() {
        try {
             
              ArrayList<SanPham> list=SanPhamDAO.findAllTT0();
          
            tblModelX.setRowCount(0);
            for (SanPham sp : list) {
            tblModelX.addRow(new Object[]{sp.getMaSp(),sp.getTenSp(),sp.getLoai(),NhapHangDAO.getSoLuong(sp.getMaSp()),sp.getGia()});

            }
            tblModelX.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, "Lỗi truy xuất dữ liệu", "Lỗi");
        }
    }
     public  void loadDataToTableCapNhatGia() {
        try {
             
              ArrayList<SanPham> list=SanPhamDAO.findAll();
          
            tblModelCNG.setRowCount(0);
            for (SanPham sp : list) {
            tblModelCNG.addRow(new Object[]{sp.getMaSp(),sp.getTenSp(),sp.getLoai(),NhapHangDAO.getSoLuong(sp.getMaSp()),sp.getGia()});

            }
            tblModelCNG.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, "Lỗi truy xuất dữ liệu", "Lỗi");
        }
    }
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnSortDESC = new javax.swing.JButton();
        btnSortASC = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblDongSp = new javax.swing.JLabel();
        lblCamera = new javax.swing.JLabel();
        lblMauSac = new javax.swing.JLabel();
        cbxBoNhoTrong = new javax.swing.JComboBox<>();
        cbxManHinh = new javax.swing.JComboBox<>();
        cbxCamera = new javax.swing.JComboBox<>();
        cbxMauSac = new javax.swing.JComboBox<>();
        btnEditBNT = new javax.swing.JButton();
        btnEditMH = new javax.swing.JButton();
        btnEditKichThuoc = new javax.swing.JButton();
        btnEditCamera = new javax.swing.JButton();
        btnEditColor = new javax.swing.JButton();
        lblCPU = new javax.swing.JLabel();
        cbxCPU = new javax.swing.JComboBox<>();
        lblXuatxu = new javax.swing.JLabel();
        cbxXuatXu = new javax.swing.JComboBox<>();
        lblPin = new javax.swing.JLabel();
        cbxEditPin = new javax.swing.JComboBox<>();
        lblPhanLoai = new javax.swing.JLabel();
        cbxPhanLoaiHang = new javax.swing.JComboBox<>();
        btnEditRam = new javax.swing.JButton();
        btnEditHDH = new javax.swing.JButton();
        btnCPU = new javax.swing.JButton();
        btnEditXuatXu = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        cbxRam = new javax.swing.JComboBox<>();
        btnEditPin = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        cbxHeDieuHanh = new javax.swing.JComboBox<>();
        btnEditPL = new javax.swing.JButton();
        cbxKichThuoc = new javax.swing.JComboBox<>();
        cbxSacNhanh = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        btnEditSacNhanh = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblAnhSP = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnEditNCC = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAMoTa = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        cbxTenSP = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtNgayNhapHang = new javax.swing.JTextField();
        lblHang = new javax.swing.JLabel();
        cbxHang = new javax.swing.JComboBox<>();
        btnEditHang = new javax.swing.JButton();
        txtTonKho = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnIMEI = new javax.swing.JButton();
        cbxNhaCungCap = new javax.swing.JComboBox<>();
        btnEditTenSP1 = new javax.swing.JButton();
        lblTrangThaiImei = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtSerachDaXoa = new javax.swing.JTextField();
        btnSearchX = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSPDaXoa = new javax.swing.JTable();
        btnRetart = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtMaSPDaXoa = new javax.swing.JTextField();
        txtTenSPDaXoa = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnKhoiPhuc = new javax.swing.JButton();
        btnXoaVinhVien = new javax.swing.JButton();
        chkALLSPDX = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtSerachCNG = new javax.swing.JTextField();
        btnSearchCNG = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSPCapNhatGia = new javax.swing.JTable();
        txtMaSPCNG = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtTenSPCNG = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtGiaCu = new javax.swing.JTextField();
        txtGiaMoi = new javax.swing.JTextField();
        chkALLSPCNG = new javax.swing.JCheckBox();
        btnCapNhatGia = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();

        jLabel10.setText("Tìm Kiếm");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jLabel11.setText("Sắp Xếp Theo");

        btnSortDESC.setBackground(new java.awt.Color(0, 0, 51));
        btnSortDESC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/sort_giam16.png"))); // NOI18N
        btnSortDESC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortDESCActionPerformed(evt);
            }
        });

        btnSortASC.setBackground(new java.awt.Color(0, 0, 51));
        btnSortASC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/sort_tang16.png"))); // NOI18N
        btnSortASC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortASCActionPerformed(evt);
            }
        });

        tblSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Loại", "Ram", "Pin", "Rom", "Màn Hình", "SL", "Giá SP", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblSanPham.setToolTipText("");
        tblSanPham.setRowHeight(25);
        tblSanPham.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblSanPham.setShowGrid(true);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(1).setMinWidth(150);
            tblSanPham.getColumnModel().getColumn(4).setMinWidth(50);
            tblSanPham.getColumnModel().getColumn(6).setMinWidth(50);
            tblSanPham.getColumnModel().getColumn(8).setMinWidth(50);
        }

        jLabel12.setText("Bộ Nhớ Trong");

        jLabel13.setText("Màn Hình");

        lblDongSp.setText("Kích Thước");

        lblCamera.setText("Camera");

        lblMauSac.setText("Màu Sắc");

        cbxBoNhoTrong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "16 GB", "32 GB", "64 GB", "128 GB", "256 GB" }));

        cbxManHinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "AMOLED", "IPS Quantum", "LED-backlit IPS LCD", "ClearBlack", "IPS LCD", "Super LCD", "TFT-LCD", "LCD" }));

        cbxCamera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "2MP (1080p)", "4MP (1440p)", "5MP (1920p)", "8MP (4K / 2160p)" }));

        cbxMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Đen", "Vàng", "Xanh  Dương", "Đỏ", "Vàng", "Cam", "Trắng", " " }));

        btnEditBNT.setBackground(new java.awt.Color(0, 0, 51));
        btnEditBNT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditBNT.setToolTipText("");
        btnEditBNT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditBNTActionPerformed(evt);
            }
        });

        btnEditMH.setBackground(new java.awt.Color(0, 0, 51));
        btnEditMH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditMH.setToolTipText("");
        btnEditMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMHActionPerformed(evt);
            }
        });

        btnEditKichThuoc.setBackground(new java.awt.Color(0, 0, 51));
        btnEditKichThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditKichThuoc.setToolTipText("");
        btnEditKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditKichThuocActionPerformed(evt);
            }
        });

        btnEditCamera.setBackground(new java.awt.Color(0, 0, 51));
        btnEditCamera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditCamera.setToolTipText("");
        btnEditCamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCameraActionPerformed(evt);
            }
        });

        btnEditColor.setBackground(new java.awt.Color(0, 0, 51));
        btnEditColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditColor.setToolTipText("");
        btnEditColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditColorActionPerformed(evt);
            }
        });

        lblCPU.setText("CPU");

        cbxCPU.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Snapdragon", "Apple A series", "Exynos", "Helio", "Kirin", "Intel Atom", "Spreadtrum" }));

        lblXuatxu.setText("Xuất Xứ");

        cbxXuatXu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Trung Quốc", "Hàn Quốc", "Ấn Độ", "Việt Nam", "Anh" }));

        lblPin.setText("Pin");

        cbxEditPin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "3.000 mAh", "3.500 mAh", "4.000 mAh", "4.500 mAh", "5.000 mAh", "6.000 mAh" }));

        lblPhanLoai.setText("Phân Loại Hàng");

        cbxPhanLoaiHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mới", "Cũ" }));

        btnEditRam.setBackground(new java.awt.Color(0, 0, 51));
        btnEditRam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditRam.setToolTipText("");
        btnEditRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditRamActionPerformed(evt);
            }
        });

        btnEditHDH.setBackground(new java.awt.Color(0, 0, 51));
        btnEditHDH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditHDH.setToolTipText("");
        btnEditHDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditHDHActionPerformed(evt);
            }
        });

        btnCPU.setBackground(new java.awt.Color(0, 0, 51));
        btnCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnCPU.setToolTipText("");
        btnCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPUActionPerformed(evt);
            }
        });

        btnEditXuatXu.setBackground(new java.awt.Color(0, 0, 51));
        btnEditXuatXu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditXuatXu.setToolTipText("");
        btnEditXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditXuatXuActionPerformed(evt);
            }
        });

        jLabel22.setText("RAM");

        cbxRam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "1 GB", "2 GB", "4 GB", "8 GB", "16 GB", "32 GB" }));

        btnEditPin.setBackground(new java.awt.Color(0, 0, 51));
        btnEditPin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditPin.setToolTipText("");
        btnEditPin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPinActionPerformed(evt);
            }
        });

        jLabel23.setText("Hệ Điều Hành");

        cbxHeDieuHanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Android ", "IOS ", "Windows Phone" }));

        btnEditPL.setBackground(new java.awt.Color(0, 0, 51));
        btnEditPL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditPL.setToolTipText("");
        btnEditPL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPLActionPerformed(evt);
            }
        });

        cbxKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "4.7 inch", "5.0 inch", "5.5 inch", "5.8 inch", "6.0 inch", "6.1 inch", "6.5 inch", " " }));

        cbxSacNhanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Có ", "Không" }));

        jLabel16.setText(" Sạc Nhanh");

        btnEditSacNhanh.setBackground(new java.awt.Color(0, 0, 51));
        btnEditSacNhanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditSacNhanh.setToolTipText("");
        btnEditSacNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSacNhanhActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/search016.png"))); // NOI18N
        btnSearch.setText("Tìm Kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnEditBNT, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditMH, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditSacNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditColor, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblXuatxu, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblPin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblPhanLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cbxRam, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbxHeDieuHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbxCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbxXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbxEditPin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbxPhanLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnEditRam, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditHDH, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditPin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditPL, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(cbxBoNhoTrong, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbxManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblDongSp, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxSacNhanh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(cbxCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(lblCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(8, 8, 8))
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSortDESC, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSortASC, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSortDESC)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch))
                    .addComponent(btnSortASC, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(lblDongSp)
                    .addComponent(lblCamera)
                    .addComponent(lblMauSac)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxBoNhoTrong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxSacNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditBNT)
                            .addComponent(btnEditMH)
                            .addComponent(btnEditKichThuoc)
                            .addComponent(btnEditCamera)
                            .addComponent(btnEditColor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(lblCPU)
                            .addComponent(lblXuatxu)
                            .addComponent(lblPin)
                            .addComponent(lblPhanLoai))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxRam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxHeDieuHanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxCPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEditPin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxPhanLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditRam)
                            .addComponent(btnEditHDH)
                            .addComponent(btnCPU)
                            .addComponent(btnEditXuatXu)
                            .addComponent(btnEditPin)
                            .addComponent(btnEditPL)))
                    .addComponent(btnEditSacNhanh))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        lblAnhSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnhSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/smartphone128.png"))); // NOI18N
        lblAnhSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhSPMouseClicked(evt);
            }
        });

        jLabel2.setText("Tên Sản Phẩm");

        btnEditNCC.setBackground(new java.awt.Color(0, 0, 51));
        btnEditNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditNCCActionPerformed(evt);
            }
        });

        jLabel3.setText("Giá Nhập");

        jLabel4.setText("Giá Bán");

        txtGiaNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNhapActionPerformed(evt);
            }
        });

        txtGiaBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbxHangMouseEntered(evt);
            }
        });
        txtGiaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaBanActionPerformed(evt);
            }
        });

        jLabel5.setText("VND");

        jLabel6.setText("VND");

        jLabel7.setText("Ngày Nhập Hàng");

        jLabel8.setText("Số Lượng");

        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        jLabel9.setText("Mô Tả");

        txtAMoTa.setColumns(20);
        txtAMoTa.setRows(5);
        jScrollPane1.setViewportView(txtAMoTa);

        btnAdd.setBackground(new java.awt.Color(0, 0, 51));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/add32.png"))); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(0, 0, 51));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/pencil32.png"))); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(0, 0, 51));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/delete32.png"))); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(0, 0, 51));
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/star32.png"))); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        cbxTenSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "" }));
        cbxTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTenSPActionPerformed(evt);
            }
        });

        jLabel15.setText("Mã Sản Phẩm");

        txtMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPActionPerformed(evt);
            }
        });

        txtNgayNhapHang.setEditable(false);
        txtNgayNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayNhapHangActionPerformed(evt);
            }
        });

        lblHang.setText("Hãng");

        cbxHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "GOOGLE", "SAMSUNG", "SONY", "NOKIA", "LG", "XIAOMI", "VIVO", "OPPO", "APPLE" }));
        cbxHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbxHangMouseEntered(evt);
            }
        });
        cbxHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxHangActionPerformed(evt);
            }
        });

        btnEditHang.setBackground(new java.awt.Color(0, 0, 51));
        btnEditHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditHang.setToolTipText("");
        btnEditHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditHangActionPerformed(evt);
            }
        });

        txtTonKho.setEditable(false);

        jLabel17.setText("Tồn Kho");

        jLabel1.setText("IMEI");

        jLabel20.setText("Nhà Cung Cấp");

        btnIMEI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/add16.png"))); // NOI18N
        btnIMEI.setText("ADD");
        btnIMEI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIMEIActionPerformed(evt);
            }
        });

        cbxNhaCungCap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnEditTenSP1.setBackground(new java.awt.Color(0, 0, 51));
        btnEditTenSP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/edit16.png"))); // NOI18N
        btnEditTenSP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTenSP1ActionPerformed(evt);
            }
        });

        lblTrangThaiImei.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTrangThaiImei.setText("Trạng Thái");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(lblHang, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cbxHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cbxTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditTenSP1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                        .addContainerGap(7, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel2)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel7))
                                                    .addGap(24, 24, 24))
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addComponent(txtGiaNhap)
                                                    .addGap(1, 1, 1)))
                                            .addComponent(jLabel5))
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblTrangThaiImei, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnIMEI))
                                            .addComponent(txtNgayNhapHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)))
                                    .addGap(15, 15, 15)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbxNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel20)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnEditNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel4)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel6))
                                            .addComponent(jLabel8)
                                            .addComponent(txtSoLuong)))
                                    .addGap(0, 2, Short.MAX_VALUE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9))
                        .addGap(0, 6, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(lblAnhSP, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnhSP, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(lblHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEditHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditTenSP1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(btnEditNCC))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTrangThaiImei)
                            .addComponent(cbxNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(btnIMEI)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNew, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab(" Sản Phẩm", jPanel2);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tìm Kiếm");

        txtSerachDaXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSerachDaXoaActionPerformed(evt);
            }
        });

        btnSearchX.setBackground(new java.awt.Color(0, 0, 102));
        btnSearchX.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchX.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/search016.png"))); // NOI18N
        btnSearchX.setText("Tìm Kiếm");
        btnSearchX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchXActionPerformed(evt);
            }
        });

        tblSPDaXoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Loại", "Số Lượng", "Giá SP", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSPDaXoa.setRowHeight(25);
        tblSPDaXoa.setShowGrid(true);
        tblSPDaXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPDaXoaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSPDaXoa);
        if (tblSPDaXoa.getColumnModel().getColumnCount() > 0) {
            tblSPDaXoa.getColumnModel().getColumn(1).setMinWidth(180);
            tblSPDaXoa.getColumnModel().getColumn(4).setMinWidth(50);
        }

        btnRetart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/restart32.png"))); // NOI18N
        btnRetart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });

        jLabel18.setText("Mã Sản Phẩm: ");

        jLabel19.setText("Tên Sản Phẩm");

        txtMaSPDaXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPDaXoaActionPerformed(evt);
            }
        });

        txtTenSPDaXoa.setEditable(false);
        txtTenSPDaXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtTenSPDaXoaMouseEntered(evt);
            }
        });

        btnKhoiPhuc.setBackground(new java.awt.Color(0, 102, 0));
        btnKhoiPhuc.setForeground(new java.awt.Color(255, 255, 255));
        btnKhoiPhuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/restart16.png"))); // NOI18N
        btnKhoiPhuc.setText("Khôi Phục");
        btnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });

        btnXoaVinhVien.setBackground(new java.awt.Color(255, 102, 51));
        btnXoaVinhVien.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaVinhVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/delete16.png"))); // NOI18N
        btnXoaVinhVien.setText("Xóa Vĩnh Viễn");
        btnXoaVinhVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaVinhVienActionPerformed(evt);
            }
        });

        chkALLSPDX.setText("Chọn Tất Cả");
        chkALLSPDX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/checkboxes24.png"))); // NOI18N
        chkALLSPDX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkALLSPDXActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkALLSPDX)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel14)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(txtSerachDaXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSearchX)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRetart))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenSPDaXoa)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnXoaVinhVien)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaSPDaXoa)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 151, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSerachDaXoa)
                                    .addComponent(btnSearchX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(btnRetart)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(txtMaSPDaXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(txtTenSPDaXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnXoaVinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkALLSPDX)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Đã Xóa", jPanel5);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Tìm Kiếm");

        txtSerachCNG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSerachCNGActionPerformed(evt);
            }
        });

        btnSearchCNG.setBackground(new java.awt.Color(0, 0, 102));
        btnSearchCNG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchCNG.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchCNG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/search016.png"))); // NOI18N
        btnSearchCNG.setText("Tìm Kiếm");
        btnSearchCNG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCNGActionPerformed(evt);
            }
        });

        tblSPCapNhatGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Loại", "Số Lượng", "Giá SP", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSPCapNhatGia.setRowHeight(25);
        tblSPCapNhatGia.setShowGrid(true);
        tblSPCapNhatGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPCapNhatGiaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSPCapNhatGia);
        if (tblSPCapNhatGia.getColumnModel().getColumnCount() > 0) {
            tblSPCapNhatGia.getColumnModel().getColumn(1).setMinWidth(180);
            tblSPCapNhatGia.getColumnModel().getColumn(4).setMinWidth(50);
        }

        txtMaSPCNG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPCNGActionPerformed(evt);
            }
        });

        jLabel24.setText("Mã Sản Phẩm: ");

        jLabel25.setText("Tên Sản Phẩm");

        txtTenSPCNG.setEditable(false);
        txtTenSPCNG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtTenSPCNGMouseEntered(evt);
            }
        });

        jLabel26.setText("Giá Cũ");

        jLabel27.setText("Giá Mới");

        txtGiaCu.setEditable(false);

        txtGiaMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaMoiActionPerformed(evt);
            }
        });

        chkALLSPCNG.setText("Chọn Tất Cả");
        chkALLSPCNG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/ql_cuahangdienthoai/icon/checkboxes24.png"))); // NOI18N
        chkALLSPCNG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkALLSPCNGActionPerformed(evt);
            }
        });

        btnCapNhatGia.setBackground(new java.awt.Color(0, 102, 51));
        btnCapNhatGia.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatGia.setText("Cập Nhật");
        btnCapNhatGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatGiaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(255, 102, 51));
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm Mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkALLSPCNG)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(txtSerachCNG, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnSearchCNG))))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaSPCNG, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel25)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenSPCNG)
                            .addComponent(txtGiaCu)
                            .addComponent(txtGiaMoi)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCapNhatGia, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSerachCNG)
                    .addComponent(btnSearchCNG))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtMaSPCNG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtTenSPCNG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtGiaCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCapNhatGia, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkALLSPCNG)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cập Nhật Giá", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSortDESCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortDESCActionPerformed
      GiamDanDialog giamDanDialog=new GiamDanDialog(null, true);
      giamDanDialog.setVisible(true);
        try {
         if(ShareData.choieSortString.equals("Gia"))
           {
               loadSort("GIA", "DESC");
           }
          if(ShareData.choieSortString.equals("SoLuong"))
           {
             loadSortSL("SOLUONG", "DESC");
           }
            if(ShareData.choieSortString.equals("Pin"))
           {
               loadSort("PIN", "DESC");
           }
             if(ShareData.choieSortString.equals("Rom"))
           {
               loadSort_A_9("ROM", "DESC");
           }
              if(ShareData.choieSortString.equals("Ram"))
           {
               loadSort_A_9("RAM", "DESC");
           }
        } catch (Exception e) {
        }
      
    }//GEN-LAST:event_btnSortDESCActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        StringBuilder sb=check_Input();
        
         if(sb.length()>0)
        {
            MessageDialogHelper.showErrorDialog(jPanel2, sb.toString(), "EROR");
            return;
        }
        
         try {
             
              NhapHang nhapHang=initNhapHang();
            
            
            
            SanPham Sptmp=SanPhamDAO.findByIdTT1(txtMaSP.getText());
              if(Sptmp!=null)
                {
                 if(Sptmp.getTrangthai()==0){
                   if(MessageDialogHelper.showConfirmDialog(null, "Sản Phẩm đã Xóa khỏi danh sách \nbạn có muốn khôi phục Không?", "Xác Nhận")==JOptionPane.YES_OPTION)
                      {
                          Sptmp.setTrangthai(1);
                        
                         if(SanPhamDAO.updateTrangThai0_1(Sptmp))
                            {
                                
                                btnNewActionPerformed(evt);
                                loadDataToTable();
                                loadDataToTableDeleteTT0();

                                 MessageDialogHelper.showMessageDialog(this, "Sản Phẩm đã được Khôi Phục thành công","Thông Báo");

                                 return;
                             }
                        }else return;
                    }
                  if(MessageDialogHelper.showConfirmDialog(jPanel2, "Mã Sản Phẩm đã tồn tại \nBạn có muốn cấp nhật số lượng không", "Xác Nhận")==JOptionPane.YES_OPTION){
                      // xuất ra số lượng có trong kho
                     // nhapHang.setSoLuong(Integer.parseInt(JOptionPane.showInputDialog("Số Lượng cần Thêm")));
                     AddSoLuongSPDiolog addSoLuongSPDiolog=new AddSoLuongSPDiolog(null,true);
                     addSoLuongSPDiolog.setVisible(true);
                     if(AddSoLuongSPDiolog.check==true)
                     {
                        int sl=AddSoLuongSPDiolog.soluong;
                       nhapHang.setSoLuong(sl);
                       NhapHangDAO.insert(nhapHang);
                       for (String imei : ShareData.listImeiTMP) {
                            ImeiDAO.insert(new Imei(imei, Sptmp.getMaSp()));

                           }
                         btnNewActionPerformed(evt);
                       
                     }
                     
                  }  else return;
                  MessageDialogHelper.showMessageDialog(this, "Sản Phẩm đã được nhập thêm vào kho","Thông Báo");
                  loadDataToTable();
                  return;
                }
                  
                
             SanPham sp=initSanPham();
              
              if(ShareData.listImeiTMP==null)
               {

                   MessageDialogHelper.showMessageDialog(jPanel2, "Vui lòng chọn danh sách Imei","Thông Báo");

               }
              for (String string : ShareData.listImeiTMP) {
                  System.out.print(string+"-");
              }
              for (String string : ShareData.listImeiTMP) {
                 if(ImeiDAO.findById(string)!=null)
                 {
                     MessageDialogHelper.showMessageDialog(jPanel2, "File IMEI đã tồn tại", "Thông Báo");
                     ShareData.listImeiTMP=null;
                     return;
                 }
             }
              if(SanPhamDAO.insert(sp) && NhapHangDAO.insert(nhapHang)){
                 if(NhaCungCapDAO.findByTenNCC(cbxNhaCungCap.getSelectedItem().toString())==null)
                 {
                     NhaCungCap ncc=new NhaCungCap();
                     ncc.setTenNhaCungCap(cbxNhaCungCap.getSelectedItem().toString());
                     NhaCungCapDAO.insert(ncc);
                 }
                  for (String imei : ShareData.listImeiTMP) {
                      ImeiDAO.insert(new Imei(imei, sp.getMaSp()));
                      
                  }
                 MessageDialogHelper.showMessageDialog(this, "Sản Phẩm đã được nhập vào kho","Thông Báo");
                 btnNewActionPerformed(evt);
                 }
              else{
                   MessageDialogHelper.showConfirmDialog(this, "Sự cố kỹ thuật vui lòng thử lại sau!","Lỗi");
                   }
                  loadDataToTable();
           
         } catch (Exception e) {
             System.out.println(e.getMessage());
            e.printStackTrace();
           MessageDialogHelper.showConfirmDialog(this, "Sự cố kỹ thuật vui lòng thử lại sau!","Lỗi");

        }
        
        
    }//GEN-LAST:event_btnAddActionPerformed

    public NhapHang initNhapHang() throws NumberFormatException {
        NhapHang nhapHang=new NhapHang();
        nhapHang.setMaSp(txtMaSP.getText());
        nhapHang.setGiaNhap(Integer.parseInt(txtGiaNhap.getText()));
        nhapHang.setMoTa(txtAMoTa.getText());
        Date now=new Date();
        nhapHang.setNgayNhap(now);
        nhapHang.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        nhapHang.setNhaCungCap(cbxNhaCungCap.getSelectedItem().toString());
        //nhapHang.setMaNV(ShareData.taikhoandangnhap.getMaNV());
        
        return nhapHang;
    }

    private void btnEditBNTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditBNTActionPerformed
       String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxBoNhoTrong.addItem(tmp);
    }//GEN-LAST:event_btnEditBNTActionPerformed

    private void btnEditMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMHActionPerformed
        String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxManHinh.addItem(tmp);
       
    }//GEN-LAST:event_btnEditMHActionPerformed

    private void btnEditKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditKichThuocActionPerformed
        String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
        cbxKichThuoc.addItem(tmp);
    }//GEN-LAST:event_btnEditKichThuocActionPerformed

    private void btnEditHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditHangActionPerformed
        String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxHang.addItem(tmp);
    }//GEN-LAST:event_btnEditHangActionPerformed

    private void btnEditCameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCameraActionPerformed
       String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxCamera.addItem(tmp);
    }//GEN-LAST:event_btnEditCameraActionPerformed

    private void btnEditColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditColorActionPerformed
       String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxMauSac.addItem(tmp);
    }//GEN-LAST:event_btnEditColorActionPerformed

    private void btnEditRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditRamActionPerformed
       String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxRam.addItem(tmp);
    }//GEN-LAST:event_btnEditRamActionPerformed

    private void btnEditHDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditHDHActionPerformed
       String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxHeDieuHanh.addItem(tmp);
    }//GEN-LAST:event_btnEditHDHActionPerformed

    private void btnCPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPUActionPerformed
        String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
        cbxCPU.addItem(tmp);
    }//GEN-LAST:event_btnCPUActionPerformed

    private void btnEditXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditXuatXuActionPerformed
        String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxXuatXu.addItem(tmp);
    }//GEN-LAST:event_btnEditXuatXuActionPerformed

    private void btnEditPinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPinActionPerformed
        String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxEditPin.addItem(tmp);
    }//GEN-LAST:event_btnEditPinActionPerformed

    private void btnEditPLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPLActionPerformed
        String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxPhanLoaiHang.addItem(tmp);
    }//GEN-LAST:event_btnEditPLActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtMaSP.setText("");
        DeleteKhungNhap();
        
        
    }//GEN-LAST:event_btnNewActionPerformed

    public void DeleteKhungNhap() {
        txtSoLuong.setBackground(Color.white);
        txtMaSP.setBackground(Color.white);
        txtGiaNhap.setText("");
        txtGiaNhap.setBackground(Color.white);
        txtGiaBan.setText("");
        txtGiaBan.setBackground(Color.white);
        cbxTenSP.setBackground(Color.white);
        cbxHang.setBackground(Color.white);
//        cbxTenSP.setSelectedIndex(0);
        txtAMoTa.setText("");
        txtSoLuong.setText("");
        cbxBoNhoTrong.setSelectedIndex(0);
        cbxManHinh.setSelectedIndex(0);
        cbxRam.setSelectedIndex(0);
        cbxHeDieuHanh.setSelectedIndex(0);
        cbxKichThuoc.setSelectedIndex(0);
        cbxCPU.setSelectedIndex(0);
        cbxHang.setSelectedIndex(0);
        cbxXuatXu.setSelectedIndex(0);
        cbxCamera.setSelectedIndex(0);
        cbxEditPin.setSelectedIndex(0);
        cbxMauSac.setSelectedIndex(0);
        txtTonKho.setText("");
        
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy ");
        Date now=new Date();
        txtNgayNhapHang.setText(sdf.format(now).toString());
        //  cbxTenSP.setSelectedIndex(0);
        lblAnhSP.setIcon(reSizeImage(String.valueOf(anhMD)));
        lblTrangThaiImei.setText("Trạng Thái");
        lblTrangThaiImei.setForeground(Color.black);
        ShareData.listImeiTMP=null;
         txtSoLuong.setEnabled(true);
         btnIMEI.setEnabled(true);
        
    }

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
//         int index=tblSanPham.getSelectedRow();
//         txtGiaBan.setText(listSP.get(index).getGia()+"");
//         txtMaSP.setText(listSP.get(index).getMaSp());
//         cbxTenSP.setSelectedItem(listSP.get(index).getTenSp());
//         cbxBoNhoTrong.setSelectedItem(listSP.get(index).getRom());
//          cbxRam.setSelectedItem(listSP.get(index).getRam());
//           cbxManHinh.setSelectedItem(listSP.get(index).getManHinh());
//            cbxHeDieuHanh.setSelectedItem(listSP.get(index).getHDH());
//             cbxKichThuoc.setSelectedItem(listSP.get(index).getKichThuoc());
//              cbxCPU.setSelectedItem(listSP.get(index).getCPU());
//               cbxHang.setSelectedItem(listSP.get(index).getHang());
//                cbxXuatXu.setSelectedItem(listSP.get(index).getXuatxu());
//                 cbxCamera.setSelectedItem(listSP.get(index).getCamera());
//                  cbxEditPin.setSelectedItem(listSP.get(index).getPin());
//                   cbxMauSac.setSelectedItem(listSP.get(index).getMausac());
//                    cbxPhanLoaiHang.setSelectedItem(listSP.get(index).getLoai());
                    
//         for (NhapHang nh : listNH) {
//            if(nh.getMaSp().equals(listNH.get(index).getMaSp()))
//            {
//                txtAMoTa.setText(nh.getMoTa());
//                txtGiaNhap.setText(nh.getGiaNhap()+"");
//                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
//                txtNgayNhapHang.setText(sdf.format(nh.getNgayNhap()).toString());
//                txtSoLuong.setText(nh.getSoLuong()+"");
//               break;
//                
//            }
//        }
//         
         
          try {
            int row=tblSanPham.getSelectedRow();
            if(row>=0)
            {
                String id=(String) tblSanPham.getValueAt(row, 0);
                SanPham sp=SanPhamDAO.findByIdTT1(id);
                
                
               
                if(sp!=null )
                { 
                    
                   NhapHang nh=NhapHangDAO.findById(id);
                   loadDataKhungNhap_MaSP(sp, nh);
                   txtSoLuong.setEnabled(false);
                    btnIMEI.setEnabled(false);
                    
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         
    }//GEN-LAST:event_tblSanPhamMouseClicked

    public void loadDataKhungNhap_MaSP(SanPham sp, NhapHang nh) throws Exception {
        txtGiaBan.setText(sp.getGia()+"");
        txtMaSP.setText(sp.getMaSp());
        
        cbxBoNhoTrong.setSelectedItem(sp.getRom());
        cbxRam.setSelectedItem(sp.getRam());
        cbxManHinh.setSelectedItem(sp.getManHinh());
        cbxHeDieuHanh.setSelectedItem(sp.getHDH());
        cbxKichThuoc.setSelectedItem(sp.getKichThuoc());
        cbxCPU.setSelectedItem(sp.getCPU());
        cbxHang.setSelectedItem(sp.getHang());
        cbxTenSP.setSelectedItem(sp.getTenSp());
        cbxXuatXu.setSelectedItem(sp.getXuatxu());
        cbxCamera.setSelectedItem(sp.getCamera());
        cbxEditPin.setSelectedItem(sp.getPin());
        cbxMauSac.setSelectedItem(sp.getMausac());
        cbxPhanLoaiHang.setSelectedItem(sp.getLoai());
      
        txtGiaNhap.setText(nh.getGiaNhap()+"");
        txtTonKho.setText(NhapHangDAO.getSoLuong(sp.getMaSp())+"");
        txtAMoTa.setText(nh.getMoTa());
        txtSoLuong.setText(NhapHangDAO.getSoLuong(sp.getMaSp())+"");
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy ");
        txtNgayNhapHang.setText(sdf.format(nh.getNgayNhap()).toString());
        txtSoLuong.setText("0");
        lblAnhSP.setIcon(reSizeImage(sp.getHinhAnh()));
        cbxSacNhanh.setSelectedItem(sp.getSacNhanh());
        cbxNhaCungCap.setSelectedItem(nh.getNhaCungCap());
    }

    private void txtNgayNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayNhapHangActionPerformed
       
        
    }//GEN-LAST:event_txtNgayNhapHangActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
      txtAMoTa.requestFocus();
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void cbxHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxHangActionPerformed
        String choie=cbxHang.getSelectedItem().toString();
        cbxModel=new DefaultComboBoxModel<>();
        
        
       
         try {
             ArrayList<String>ds=SanPhamDAO.getTenSpCungHang(choie);
           
             for (String d : ds) {
                 cbxModel.addElement(d);
                
             }
             cbxTenSP.setModel(cbxModel);
             
             
         } catch (Exception ex) {
             ex.printStackTrace();
         }
    }//GEN-LAST:event_cbxHangActionPerformed

    private void btnEditSacNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSacNhanhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditSacNhanhActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
       try {
              ArrayList<SanPham> list =SanPhamDAO.findNameSanPham(txtSearch.getText());
             
           //  listSP=SanPhamDAO.findAll();
            tblModel.setRowCount(0);
            for (SanPham sp : list) {
            tblModel.addRow(new Object[]{sp.getMaSp(),sp.getTenSp(),sp.getLoai(),sp.getRam(),sp.getPin(),sp.getRom(),sp.getManHinh(),NhapHangDAO.getSoLuong(sp.getMaSp()),sp.getGia()});

            }
            tblModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, "Lỗi truy xuất dữ liệu", "Lỗi");
        }
    }//GEN-LAST:event_btnSearchActionPerformed

     private void xoaNhieuSP() throws Exception
    {
        ArrayList<SanPham>list=SanPhamDAO.findAll();
        ArrayList<String> listSp=new ArrayList<>();
        int i=0;
        for (SanPham sp :list ) {
            if(i>=list.size()) return;
            if(tblSanPham.getValueAt(i, 9)==null) {
                
                 i++;
                 continue;
            }
            if((boolean)tblSanPham.getValueAt(i, 9)==true){
                listSp.add(sp.getMaSp());
                i++;
        }
        }
        if (listSp.size()<2)
        {
            
            return;
        }
        txtMaSP.setText("");
        DeleteKhungNhap();
        
        if( MessageDialogHelper.showConfirmDialog(this, "Bạn có muốn xóa "+listSp.size()+" Sản Phẩm này không ?", "Xác Nhận")==JOptionPane.YES_OPTION){
             for (String masp : listSp) {
                 SanPham sp=SanPhamDAO.findByIdTT0(masp);
                 sp.setTrangthai(0);
                 Imei im=ImeiDAO.findByMaSp(sp.getMaSp());
                  if(im.getTrangThai()==1){
                    im.setTrangThai(10);
                    im.setMaSp(sp.getMaSp());
                    }
                 SanPhamDAO.updateTrangThai0_1(sp);
                 ImeiDAO.updateTrangThai0_1(im);
                 
                 
                 
                }
             loadDataToTableDeleteTT0();
             loadDataToTable();
              
             
        }else return;
        
       
    }
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
       
         try {
            xoaNhieuSP();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       
        
        StringBuilder sb=new StringBuilder();
        DataValidator.empty_Data(txtMaSP, sb, "Mã Sản Phẩm không được để trống");
        if(sb.length()>0)
        {
            //MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi");
            txtMaSP.setBackground(Color.white);
            return;
        }
        if(MessageDialogHelper.showConfirmDialog(this, "Bạn có muốn xóa Sản Phẩm không?", "Xác nhận")==JOptionPane.YES_OPTION)
        {
           try {
            SanPham sp=SanPhamDAO.findByIdTT0(txtMaSP.getText());
              if(sp!=null){
                  sp.setTrangthai(0);
                  
                  Imei im=ImeiDAO.findByMaSp(sp.getMaSp());
                
                  if(im.getTrangThai()==1){
                    im.setTrangThai(10);
                    im.setMaSp(sp.getMaSp());
                    }
                  
                  
                  if(SanPhamDAO.updateTrangThai0_1(sp) && ImeiDAO.updateTrangThai0_1(im) )
                    {
                        
                        MessageDialogHelper.showMessageDialog(this, "SP đã được xóa","Thông Báo");
                       
                        loadDataToTable();
                        loadDataToTableDeleteTT0();
                        btnNewActionPerformed(evt);
                    }
                  
              }else{
                MessageDialogHelper.showErrorDialog(this, "Sản Phẩm không tồn tại, không thể xóa","Lỗi");
                }
          
         
         
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, e.getMessage(), "Lỗi");
            
            
        }
        }

        
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditNCCActionPerformed
           String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxNhaCungCap.addItem(tmp);
    }//GEN-LAST:event_btnEditNCCActionPerformed

    private void btnSortASCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortASCActionPerformed
        GiamDanDialog giamDanDialog=new GiamDanDialog(null, true);
      giamDanDialog.setVisible(true);
      if(ShareData.choieSortString.equals("Gia"))
       {
           loadSort("GIA", "ASC");
       }
       if(ShareData.choieSortString.equals("SoLuong"))
       {
           loadSortSL("SOLUONG", "ASC");
       }
        if(ShareData.choieSortString.equals("Pin"))
       {
           loadSort("PIN", "ASC");
       }
         if(ShareData.choieSortString.equals("Rom"))
       {
           loadSort_A_9("ROM", "ASC");
       }
          if(ShareData.choieSortString.equals("Ram"))
       {
           loadSort_A_9("RAM", "ASC");
       }
    }//GEN-LAST:event_btnSortASCActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        StringBuilder sb=check_InputEdit();
        
         if(sb.length()>0)
        {
            MessageDialogHelper.showErrorDialog(jPanel2, sb.toString(), "EROR");
            return;
        }
         if(MessageDialogHelper.showConfirmDialog(this, "Bạn có muốn cập nhật Sản Phẩm không?", "Xác nhận")==JOptionPane.YES_OPTION)
        {
             try {
             SanPham sp=initSanPham();

                NhapHang nhapHang=initNhapHang();
             
            SanPham sptmp=SanPhamDAO.findByIdTT1(txtMaSP.getText());
            if(sptmp!=null )
                {
                    
                    if(sptmp.getTrangthai()==0){
                        if(MessageDialogHelper.showConfirmDialog(null, "Sản Phẩm đã Xóa khỏi danh sách \nbạn có muốn khôi phục Không?", "Xác Nhận")==JOptionPane.YES_OPTION)
                           {
                               sptmp.setTrangthai(1);

                              if(SanPhamDAO.updateTrangThai0_1(sptmp))
                                 {
                                     sp.setTrangthai(1);
                                     btnNewActionPerformed(evt);
                                     loadDataToTable();
                                     loadDataToTableDeleteTT0();
                                    
                                      MessageDialogHelper.showMessageDialog(this, "Sản Phẩm đã được Khôi Phục thành công","Thông Báo");
                                     
                                      return;
                                  }
                             }else return;
                    }
                    
                    if(SanPhamDAO.update(sp) && NhapHangDAO.update(nhapHang)){
                        
                         MessageDialogHelper.showMessageDialog(this, "Sản Phẩm đã cập nhật thông tin ","Thông Báo");
                         btnNewActionPerformed(evt);
                        }
                    else
                        {
                          MessageDialogHelper.showConfirmDialog(this, "Sự cố kỹ thuật vui lòng thử lại sau!","Lỗi");
                        }
                    loadDataToTable();
                    return;
                }
             else
                {
                    if(MessageDialogHelper.showConfirmDialog(this, "Sản Phẩm không tồn tại. Bạn có muốn thêm sản phẩm này vào kho?", "Hỏi")==JOptionPane.YES_OPTION){

                        if(SanPhamDAO.insert(sp) &&NhapHangDAO.insert(nhapHang))
                        {
                            MessageDialogHelper.showMessageDialog(this, "Sản Phẩm đã được nhập vào kho","Thông Báo");
                            btnNewActionPerformed(evt);
                        }
                        else{
                            MessageDialogHelper.showConfirmDialog(this, "Sự cố kỹ thuật vui lòng thử lại sau!","Lỗi");
                            }
                        loadDataToTable();

                    }

                }
            
            
         
         
         } catch (Exception e) {
             System.out.println(e.getMessage());
            e.printStackTrace();
             
               MessageDialogHelper.showErrorDialog(jPanel2, "lỗi thêm sản phẩm", "ERROR");
        }
     
           
        }
            
    }//GEN-LAST:event_btnEditActionPerformed

    public SanPham initSanPham() throws NumberFormatException {
        SanPham sp=new SanPham();
        sp.setMaSp(txtMaSP.getText());
        sp.setTenSp(cbxTenSP.getSelectedItem().toString());
        sp.setRom(cbxBoNhoTrong.getSelectedItem().toString());
        sp.setRam(cbxRam.getSelectedItem().toString());
        sp.setManHinh(cbxManHinh.getSelectedItem().toString());
        sp.setHDH(cbxHeDieuHanh.getSelectedItem().toString());
        sp.setKichThuoc(cbxKichThuoc.getSelectedItem().toString());
        sp.setCPU(cbxCPU.getSelectedItem().toString());
        sp.setHang(cbxHang.getSelectedItem().toString());
        sp.setXuatxu(cbxXuatXu.getSelectedItem().toString());
        sp.setCamera(cbxCamera.getSelectedItem().toString());
        sp.setPin(cbxEditPin.getSelectedItem().toString());
        sp.setMausac(cbxMauSac.getSelectedItem().toString());
        sp.setLoai(cbxPhanLoaiHang.getSelectedItem().toString());
        sp.setGia(Integer.parseInt(txtGiaBan.getText()));
        sp.setSacNhanh(cbxSacNhanh.getSelectedItem().toString());
        sp.setHinhAnh(anh_Address);
        // listSP.add(sp);
        return sp;
    }

    public StringBuilder check_Input() {
        StringBuilder sb=new StringBuilder();
        DataValidator.empty_Data(txtMaSP, sb, "Mã Sản Phẩm chưa được nhập!");
        DataValidator.giacheck(txtSoLuong, sb,"Số Lượng");
        DataValidator.giacheck(txtGiaNhap, sb,"Giá Nhập");
        DataValidator.giacheck(txtGiaBan, sb,"Giá Bán");
        DataValidator.input_cbx_check(cbxHang, sb, "Hãng chưa được chọn");
        DataValidator.input_cbx_check(cbxTenSP, sb, "Tên Sản Phẩm chưa được chọn");
        
        return sb;
    }
     public StringBuilder check_InputEdit() {
        StringBuilder sb=new StringBuilder();
        DataValidator.empty_Data(txtMaSP, sb, "Mã Sản Phẩm chưa được nhập!");
       
        DataValidator.giacheck(txtGiaNhap, sb,"Giá Nhập");
        DataValidator.giacheck(txtGiaBan, sb,"Giá Bán");
        DataValidator.input_cbx_check(cbxHang, sb, "Hãng chưa được chọn");
        DataValidator.input_cbx_check(cbxTenSP, sb, "Tên Sản Phẩm chưa được chọn");
        
        return sb;
    }

    private void cbxHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxHangMouseEntered
        try {
            SanPham sp=SanPhamDAO.findByIdTT1(txtMaSP.getText());
            if(sp!=null)
            {
                NhapHang nh=NhapHangDAO.findById(sp.getMaSp());
                loadDataKhungNhap_MaSP(sp, nh);
                txtSoLuong.setEnabled(false);
                btnIMEI.setEnabled(false);
                
              
                
            }
            else{
                txtSoLuong.setEnabled(true);
                btnIMEI.setEnabled(true);
            }
        } catch (Exception ex) {
            
        }
       
    }//GEN-LAST:event_cbxHangMouseEntered

    private void btnSearchXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchXActionPerformed
        try {
              ArrayList<SanPham> list =SanPhamDAO.findNameSanPhamDaXoaTT0(txtSerachDaXoa.getText());
             
           //  listSP=SanPhamDAO.findAll();
            tblModelX.setRowCount(0);
            for (SanPham sp : list) {
            tblModelX.addRow(new Object[]{sp.getMaSp(),sp.getTenSp(),sp.getLoai(),sp.getRam(),sp.getPin(),sp.getRom(),sp.getManHinh(),sp.getSoLuong(),sp.getGia()});

            }
            tblModelX.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, "Lỗi truy xuất dữ liệu", "Lỗi");
        }
    }//GEN-LAST:event_btnSearchXActionPerformed

    private void btnXoaVinhVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaVinhVienActionPerformed
            
         try {
            xoaNhieuDongSPDX();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
          StringBuilder sb=new StringBuilder();
          DataValidator.empty_Data(txtMaSPDaXoa, sb, "Mã Sản Phẩm không được để trống");
        if(sb.length()>0)
        {
          //  MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi");
            txtMaSPDaXoa.setBackground(Color.white);
            
            return;
        }
        

        try {
            SanPham sp=SanPhamDAO.findByIdTT0(txtMaSPDaXoa.getText());
              if(sp!=null){
                
                  if(MessageDialogHelper.showConfirmDialog(this, "Bạn có muốn xóa Sản Phẩm "+sp.getTenSp()+" không?", "Xác nhận")==JOptionPane.YES_OPTION)
                    {
                        
                        if(SanPhamDAO.delete(txtMaSPDaXoa.getText()))
                          {
                               ImeiDAO.delete(txtMaSPDaXoa.getText());
                              MessageDialogHelper.showMessageDialog(this, "SP đã được xóa","Thông Báo");
                              btnNewActionPerformed(evt);
                              loadDataToTable();
                              loadDataToTableDeleteTT0();
                              txtMaSPDaXoa.setText("");
                              txtTenSPDaXoa.setText("");
                          }
                    }
               
                  
                  
              }else{
                MessageDialogHelper.showErrorDialog(this, "Sản Phẩm không tồn tại, không thể xóa","Lỗi");
                }
          
         
         
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, e.getMessage(), "Lỗi");
            
            
        }
    }//GEN-LAST:event_btnXoaVinhVienActionPerformed

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
       
       try {
           khoiPhucNhieuSPDX();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        
        StringBuilder sb=new StringBuilder();
          DataValidator.empty_Data(txtMaSPDaXoa, sb, "Mã Sản Phẩm không được để trống");
        if(sb.length()>0)
        {
            txtMaSPDaXoa.setBackground(Color.white);
           // MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi");
            
            return;
        }
        

         try {
            SanPham sp=SanPhamDAO.findByIdTT0(txtMaSPDaXoa.getText());
              if(sp!=null){
                  if(MessageDialogHelper.showConfirmDialog(this, "Bạn có muốn Khôi Phục Sản Phẩm "+sp.getTenSp()+" không?", "Xác nhận")==JOptionPane.YES_OPTION)
                    {
                        
                        sp.setTrangthai(1);
                        Imei im=ImeiDAO.findByMaSp(sp.getMaSp());
                        if(im.getTrangThai()==10) im.setTrangThai(1);
                         im.setMaSp(sp.getMaSp());
                       

                        if(SanPhamDAO.updateTrangThai0_1(sp)&& ImeiDAO.updateTrangThai0_1(im))
                          {

                              MessageDialogHelper.showMessageDialog(this, "Sản Phẩm đã được Khôi Phục thành công","Thông Báo");
                              btnNewActionPerformed(evt);
                              loadDataToTable();
                              loadDataToTableDeleteTT0();

                              txtMaSPDaXoa.setText("");
                              txtTenSPDaXoa.setText("");
                          }
                    }
               
                  
              }else{
                MessageDialogHelper.showErrorDialog(this, "Sản Phẩm không tồn tại, không thể Khôi Phục","Lỗi");
                }
          
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(this, e.getMessage(), "Lỗi");
            
            
        }
    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void txtTenSPDaXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTenSPDaXoaMouseEntered
       
        try {
            SanPham sp=SanPhamDAO.findByIdTT0(txtMaSPDaXoa.getText());
            if(sp!=null)
               {
                txtTenSPDaXoa.setText(sp.getTenSp());
               }
            else{
                 txtTenSPDaXoa.setText("");
            }
           
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_txtTenSPDaXoaMouseEntered

    private void tblSPDaXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPDaXoaMouseClicked
        try {
            int row=tblSPDaXoa.getSelectedRow();
            if(row>=0)
            {
                String id=(String) tblSPDaXoa.getValueAt(row, 0);
                SanPham sp=SanPhamDAO.findByIdTT0(id);
               
                if(sp!=null)
                { 
                   
                    txtMaSPDaXoa.setText(sp.getMaSp());
                    txtTenSPDaXoa.setText(sp.getTenSp());
                     
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblSPDaXoaMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        btnSearchActionPerformed(evt);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPActionPerformed
       this.requestFocus();
        try {
            SanPham sp=SanPhamDAO.findByIdTT1(txtMaSP.getText());
            if(sp!=null)
            {
                NhapHang nh=NhapHangDAO.findById(sp.getMaSp());
               
                loadDataKhungNhap_MaSP(sp, nh);
                
                
            }
            else{
               DeleteKhungNhap();
                
            }
        } catch (Exception ex) {
            
        }
        
       
       
    }//GEN-LAST:event_txtMaSPActionPerformed

    private void txtSerachDaXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSerachDaXoaActionPerformed
        btnSearchXActionPerformed(evt);
    }//GEN-LAST:event_txtSerachDaXoaActionPerformed

    private void txtMaSPDaXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPDaXoaActionPerformed
        this.requestFocus();
        try {
            SanPham sp=SanPhamDAO.findByIdTT0(txtMaSPDaXoa.getText());
            if(sp!=null)
            {
               
               
               txtTenSPDaXoa.setText(sp.getTenSp());
                
                
            }
            else{
              txtTenSPDaXoa.setText("");
                
            }
        } catch (Exception ex) {
            
        }
        
    }//GEN-LAST:event_txtMaSPDaXoaActionPerformed

    private void lblAnhSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhSPMouseClicked
        try {
            JFileChooser f=new JFileChooser("D:\\CODE_JAVA\\CuaHangDienThoai\\src\\image");
            f.setDialogTitle("Mở File");
            f.showOpenDialog(null);
            File ftenAnh= f.getSelectedFile();
            anh_Address=ftenAnh.getAbsolutePath();
            if(anh_Address!=null)
            {
                lblAnhSP.setIcon(reSizeImage(String.valueOf(anh_Address)));
            }
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_lblAnhSPMouseClicked

    private void txtGiaNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaNhapActionPerformed
      txtGiaBan.requestFocus();
    }//GEN-LAST:event_txtGiaNhapActionPerformed

    private void txtGiaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaBanActionPerformed
        txtSoLuong.requestFocus();
    }//GEN-LAST:event_txtGiaBanActionPerformed

    private void btnEditTenSP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditTenSP1ActionPerformed
         String tmp=JOptionPane.showInputDialog(this, "Nhập nội dung cần thêm");
       cbxTenSP.addItem(tmp);
    }//GEN-LAST:event_btnEditTenSP1ActionPerformed

    private void cbxTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTenSPActionPerformed

    private void chkALLSPDXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkALLSPDXActionPerformed
       this.requestFocus();
        DefaultTableModel model= (DefaultTableModel)tblSPDaXoa.getModel();
        ArrayList<SanPham>list = null;
           try {
               list = SanPhamDAO.findAllTT0();
           } catch (Exception ex) {
           }
       if(chkALLSPDX.isSelected()==true)
       {
            int i=0;
            for (SanPham sp :list ) 
            {
               // if(i>=list.size()) return;
                if(model.getValueAt(i, 5)==null) {
                    model.setValueAt(true, i, 5);
                }
                i++;

            }
            tblSPDaXoa.setModel(model);

           }
       else if(chkALLSPDX.isSelected()==false){
           int i=0;
            for (SanPham sp :list ) 
            {
               // if(i>=list.size()) return;
                if((Boolean)model.getValueAt(i, 5)==true) {
                    model.setValueAt(null, i, 5);
                }
                i++;

            }
            tblSPDaXoa.setModel(model);
       }
       
       
    }//GEN-LAST:event_chkALLSPDXActionPerformed

    private void btnIMEIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIMEIActionPerformed
           
        StringBuilder sb=new StringBuilder();
        if( DataValidator.empty_Data(txtMaSP, sb, ""))
        {
            txtMaSP.requestFocus();
            return;
        }
        if( DataValidator.empty_Data(txtSoLuong, sb, ""))
        {
            txtSoLuong.requestFocus();
            return;
        }
        try
        {
            JFileChooser f=new JFileChooser("D:\\CODE_JAVA\\CuaHangDienThoai\\src\\excelimei");
            f.setDialogTitle("Mở File");
            f.showOpenDialog(null);
            File fExcelEmei= f.getSelectedFile();
            String addressExcel=fExcelEmei.getAbsolutePath();
            ArrayList<String> list=null;
            if(addressExcel!=null)
            {
             list=DataExcel.readExcelFile(addressExcel);   
            }
            if(list.size()!=Integer.parseInt(txtSoLuong.getText()))
            {
                if(MessageDialogHelper.showConfirmDialog(jPanel2, "File IMEI và số lượng không khớp\n Bạn có muốn chọn File Khác", "Thông Báo")==JOptionPane.YES_OPTION){
                    ShareData.listImeiTMP=null;
                    btnIMEIActionPerformed(evt);
                }
                
                    
                return;

            }
            ShareData.listImeiTMP=list;
            lblTrangThaiImei.setForeground(new java.awt.Color(0, 153, 0));
            lblTrangThaiImei.setText("Đã Thêm "+list.size()+" Imei");
            
           
           
            
            
            
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnIMEIActionPerformed

    private void txtSerachCNGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSerachCNGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSerachCNGActionPerformed

    private void btnSearchCNGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCNGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchCNGActionPerformed

    private void tblSPCapNhatGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCapNhatGiaMouseClicked
        try {
            int row=tblSPCapNhatGia.getSelectedRow();
            if(row>=0)
            {
                String id=(String) tblSPCapNhatGia.getValueAt(row, 0);
                SanPham sp=SanPhamDAO.findByIdTT1(id);
               
                if(sp!=null)
                { 
                   
                    txtMaSPCNG.setText(sp.getMaSp());
                    txtTenSPCNG.setText(sp.getTenSp());
                    txtGiaCu.setText(sp.getGia()+"");
                     
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblSPCapNhatGiaMouseClicked

    private void txtMaSPCNGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPCNGActionPerformed
        this.requestFocus();
        try {
            SanPham sp=SanPhamDAO.findByIdTT1(txtMaSPCNG.getText());
            if(sp!=null)
            {
               
               
               txtTenSPCNG.setText(sp.getTenSp());
                txtGiaCu.setText(sp.getGia()+"");
                
                
            }
            else{
              txtTenSPCNG.setText("");
               txtGiaCu.setText("");
                
            }
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_txtMaSPCNGActionPerformed

    private void txtTenSPCNGMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTenSPCNGMouseEntered
        try {
            SanPham sp=SanPhamDAO.findByIdTT1(txtMaSPCNG.getText());
            if(sp!=null)
               {
                txtTenSPCNG.setText(sp.getTenSp());
                 txtGiaCu.setText(sp.getGia()+"");
               }
            else{
                 txtTenSPCNG.setText("");
                  txtGiaCu.setText("");
            }
           
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_txtTenSPCNGMouseEntered

    private void chkALLSPCNGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkALLSPCNGActionPerformed
         this.requestFocus();
        DefaultTableModel model= (DefaultTableModel)tblSPCapNhatGia.getModel();
        ArrayList<SanPham>list = null;
           try {
               list = SanPhamDAO.findAllTT0();
           } catch (Exception ex) {
           }
       if(chkALLSPCNG.isSelected()==true)
       {
            int i=0;
            for (SanPham sp :list ) 
            {
               // if(i>=list.size()) return;
                if(model.getValueAt(i, 5)==null) {
                    model.setValueAt(true, i, 5);
                }
                i++;

            }
            tblSPCapNhatGia.setModel(model);

           }
       else if(chkALLSPCNG.isSelected()==false){
           int i=0;
            for (SanPham sp :list ) 
            {
               // if(i>=list.size()) return;
                if((Boolean)model.getValueAt(i, 5)==true) {
                    model.setValueAt(null, i, 5);
                }
                i++;

            }
            tblSPCapNhatGia.setModel(model);
       }
       
    }//GEN-LAST:event_chkALLSPCNGActionPerformed

     private void capNhatGiaNSP() throws Exception
    {
        ArrayList<SanPham>list=SanPhamDAO.findAll();
        ArrayList<String> listSp=new ArrayList<>();
        int i=0;
        for (SanPham sp :list ) {
            if(i>=list.size()) return;
            if(tblSPCapNhatGia.getValueAt(i, 5)==null) {
                
                 i++;
                 continue;
            }
            if((boolean)tblSPCapNhatGia.getValueAt(i, 5)==true){
                listSp.add(sp.getMaSp());
                i++;
        }
        }
        if (listSp.size()<2) return;
          txtMaSPCNG.setText("");
          txtTenSPCNG.setText("");
          txtGiaCu.setText("");
         
          
        if( MessageDialogHelper.showConfirmDialog(this, "Bạn có muốn cập nhật giá "+listSp.size()+" Sản Phẩm này không ?", "Xác Nhận")==JOptionPane.YES_OPTION){
             for (String masp : listSp) {
                 SanPham sp=SanPhamDAO.findByIdTT1(masp);
                 sp.setGia(Integer.parseInt(txtGiaMoi.getText()));
                 
                  SanPhamDAO.updateGia(sp);
                }
             loadDataToTableCapNhatGia();
             loadDataToTable();
           
            
             
        }
       
       
    }
    private void btnCapNhatGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatGiaActionPerformed
        
       
       
        try {
            
            StringBuilder sb=new StringBuilder();
            DataValidator.giacheck(txtGiaMoi, sb,"Giá Mới");
            if(sb.length()!=0) return;
              try {
                    capNhatGiaNSP();
                  } catch (Exception ex) {
                         ex.printStackTrace();
                    }
        
                
            SanPham sp=SanPhamDAO.findByIdTT1(txtMaSPCNG.getText());
            
            if(sp!=null)
                {
                    if( MessageDialogHelper.showConfirmDialog(jPanel1, "Bạn có muốn thay đổi giá SP: "+sp.getTenSp(), "Xác Nhận")==JOptionPane.YES_OPTION){
                         sp.setGia(Integer.parseInt(txtGiaMoi.getText()));
                            SanPhamDAO.updateGia(sp);
                            loadDataToTableCapNhatGia();
                    }else return;

                   
                }
        } catch (Exception ex) {
            MessageDialogHelper.showErrorDialog(jPanel1, ex.getMessage(), "Eror");
        }
       
    }//GEN-LAST:event_btnCapNhatGiaActionPerformed

    private void txtGiaMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaMoiActionPerformed
        btnCapNhatGiaActionPerformed(evt);
    }//GEN-LAST:event_txtGiaMoiActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        txtMaSPCNG.setText("");
        txtMaSPCNGActionPerformed(evt);
    }//GEN-LAST:event_btnLamMoiActionPerformed
    public  void showAnhSanPham(String address){
         lblAnhSP.setIcon(reSizeImage(String.valueOf(address)));
    }
            
    public ImageIcon reSizeImage(String path){
            ImageIcon myImage= new ImageIcon(path);
            Image img=myImage.getImage();
            Image newImg=img.getScaledInstance(lblAnhSP.getWidth(), lblAnhSP.getWidth(), Image.SCALE_SMOOTH);
            ImageIcon image=new ImageIcon(newImg);
            return image;
            
        }
    
    public void showNgayLapPhieuNhap() {
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy ");
        Date now=new Date();
        txtNgayNhapHang.setText(sdf.format(now).toString());
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCPU;
    private javax.swing.JButton btnCapNhatGia;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEditBNT;
    private javax.swing.JButton btnEditCamera;
    private javax.swing.JButton btnEditColor;
    private javax.swing.JButton btnEditHDH;
    private javax.swing.JButton btnEditHang;
    private javax.swing.JButton btnEditKichThuoc;
    private javax.swing.JButton btnEditMH;
    private javax.swing.JButton btnEditNCC;
    private javax.swing.JButton btnEditPL;
    private javax.swing.JButton btnEditPin;
    private javax.swing.JButton btnEditRam;
    private javax.swing.JButton btnEditSacNhanh;
    private javax.swing.JButton btnEditTenSP1;
    private javax.swing.JButton btnEditXuatXu;
    private javax.swing.JButton btnIMEI;
    private javax.swing.JButton btnKhoiPhuc;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRetart;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchCNG;
    private javax.swing.JButton btnSearchX;
    private javax.swing.JButton btnSortASC;
    private javax.swing.JButton btnSortDESC;
    private javax.swing.JButton btnXoaVinhVien;
    private javax.swing.JComboBox<String> cbxBoNhoTrong;
    private javax.swing.JComboBox<String> cbxCPU;
    private javax.swing.JComboBox<String> cbxCamera;
    private javax.swing.JComboBox<String> cbxEditPin;
    private javax.swing.JComboBox<String> cbxHang;
    private javax.swing.JComboBox<String> cbxHeDieuHanh;
    private javax.swing.JComboBox<String> cbxKichThuoc;
    private javax.swing.JComboBox<String> cbxManHinh;
    private javax.swing.JComboBox<String> cbxMauSac;
    private javax.swing.JComboBox<String> cbxNhaCungCap;
    private javax.swing.JComboBox<String> cbxPhanLoaiHang;
    private javax.swing.JComboBox<String> cbxRam;
    private javax.swing.JComboBox<String> cbxSacNhanh;
    private javax.swing.JComboBox<String> cbxTenSP;
    private javax.swing.JComboBox<String> cbxXuatXu;
    private javax.swing.JCheckBox chkALLSPCNG;
    private javax.swing.JCheckBox chkALLSPDX;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAnhSP;
    private javax.swing.JLabel lblCPU;
    private javax.swing.JLabel lblCamera;
    private javax.swing.JLabel lblDongSp;
    private javax.swing.JLabel lblHang;
    private javax.swing.JLabel lblMauSac;
    private javax.swing.JLabel lblPhanLoai;
    private javax.swing.JLabel lblPin;
    private javax.swing.JLabel lblTrangThaiImei;
    private javax.swing.JLabel lblXuatxu;
    private javax.swing.JTable tblSPCapNhatGia;
    private javax.swing.JTable tblSPDaXoa;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextArea txtAMoTa;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaCu;
    private javax.swing.JTextField txtGiaMoi;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtMaSPCNG;
    private javax.swing.JTextField txtMaSPDaXoa;
    private javax.swing.JTextField txtNgayNhapHang;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSerachCNG;
    private javax.swing.JTextField txtSerachDaXoa;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSPCNG;
    private javax.swing.JTextField txtTenSPDaXoa;
    private javax.swing.JTextField txtTonKho;
    // End of variables declaration//GEN-END:variables

   
}
