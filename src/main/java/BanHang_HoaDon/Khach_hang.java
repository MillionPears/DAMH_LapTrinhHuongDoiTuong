/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package BanHang_HoaDon;

import Check.MessageDialog;
import Check.TestInputData;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.KhachHang;


/**
 *
 * @author ACER
 */
public class Khach_hang extends javax.swing.JPanel {
    private ArrayList<KhachHang> list = new ArrayList<>();
    private KhachHangDAO dao = new KhachHangDAO();
    private String TKTheoTen = "";
    private String TKTheoTenDaXoa = "";
    private String strMaKH = "";
    private String strMaKHDaXoa = "";   
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Khach_hang() {
        initComponents();
        PanelTimKiem.setVisible(false);
        PanelTKKHDaXoa.setVisible(false);
        fillDataTable();
    }

    
    public void fillDataTable() {
        try {
            DefaultTableModel tbModel = (DefaultTableModel) TbKhachHang.getModel();
            TbKhachHang.getTableHeader().setForeground(Color.BLUE);
            tbModel.setRowCount(0);
            for (KhachHang kh : dao.TimKiemKhachHang(TKTheoTen)) {
                Object dataRow[] = new Object[9];
                dataRow[0] = kh.getMaKH();
                dataRow[1] = kh.getHoKH()+ " " + kh.getTenKH();
                dataRow[2] = kh.getSoDT();
                dataRow[3] = kh.getCmnd();
                dataRow[4] = kh.getEmail();
                dataRow[5] = kh.getGioiTinh();
                dataRow[6] = kh.getDiaChi();
                dataRow[7] = kh.getNgayTao();
                dataRow[8] = kh.getGhiChu();

                tbModel.addRow(dataRow);
                TbKhachHang.setModel(tbModel);
            }
            tbModel.fireTableDataChanged();
            
            DefaultTableModel tbModelDaXoa = (DefaultTableModel) TbDaXoa.getModel();
            TbDaXoa.getTableHeader().setForeground(Color.BLUE);
            tbModelDaXoa.setRowCount(0);
            for (KhachHang kh : dao.TimKiemKhachHangDaXoa(TKTheoTenDaXoa)) {
                Object dataRow[] = new Object[9];
                dataRow[0] = kh.getMaKH();
                dataRow[1] = kh.getHoKH() + " " + kh.getTenKH();
                dataRow[2] = kh.getSoDT();
                dataRow[3] = kh.getCmnd();
                dataRow[4] = kh.getEmail();
                dataRow[5] = kh.getGioiTinh();
                dataRow[6] = kh.getDiaChi();
                dataRow[7] = kh.getNgayTao();
                dataRow[8] = kh.getGhiChu();

                tbModelDaXoa.addRow(dataRow);
                TbDaXoa.setModel(tbModelDaXoa);
            }
            tbModelDaXoa.fireTableDataChanged();         
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialog.showErrorDialog(this, "ERROR", "Lỗi truy xuất dữ liệu");
        }
    }   
    
    public KhachHang getModel() throws ParseException {
        KhachHang kh = new KhachHang();
        kh.setMaKH(TxtMaKH.getText());
        kh.setHoKH(TestInputData.xuLiChuoi(TxtHo.getText()));
        kh.setTenKH(TestInputData.xuLiChuoi(TxtTen.getText()));
        kh.setSoDT(TxtSDT.getText());
        kh.setCmnd(TxtCMND.getText());
        kh.setEmail(TxtEmail.getText());
        kh.setGioiTinh(TestInputData.xuLiChuoi(TxtGioiTinh.getText()));
        kh.setDiaChi(TestInputData.xuLiChuoi(TxtDiaChi.getText()));
        kh.setNgayTao(sdf.parse(sdf.format(DateChooserNgayTao.getDate())));
        kh.setTrangThai(1);
        kh.setGhiChu(TxtGhiChu.getText());

        return kh;
    }

    public void setModel(KhachHang kh) throws ParseException {
        TxtMaKH.setText(kh.getMaKH());
        TxtHo.setText(kh.getHoKH());
        TxtTen.setText(kh.getTenKH());
        TxtSDT.setText(kh.getSoDT());
        TxtCMND.setText(kh.getCmnd());
        TxtEmail.setText(kh.getEmail());
        TxtGioiTinh.setText(kh.getGioiTinh());
        TxtDiaChi.setText(kh.getDiaChi());
        DateChooserNgayTao.setDate(sdf.parse(sdf.format(kh.getNgayTao())));
        TxtGhiChu.setText(kh.getGhiChu());
    }

    public boolean checkEmpty() {
        if (TxtMaKH.getText().equals("")
                && TxtHo.getText().equals("")
                && TxtTen.getText().equals("")
                && TxtSDT.getText().equals("")
                && TxtCMND.getText().equals("")
                && TxtEmail.getText().equals("")
                && TxtGioiTinh.getText().equals("")
                && TxtDiaChi.getText().equals("")
                && TxtGhiChu.getText().equals("")) {

            return true;
        }
        return false;
    }
    
    public void resetForm() {
        TxtMaKH.setText("");
        TxtHo.setText("");
        TxtTen.setText("");
        TxtSDT.setText("");
        TxtCMND.setText("");
        TxtEmail.setText("");
        TxtGioiTinh.setText("");
        TxtDiaChi.setText("");
        DateChooserNgayTao.setDate(null);
        TxtGhiChu.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        TabbedPaneKhachHang = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbKhachHang = new javax.swing.JTable();
        PanelTimKiem = new javax.swing.JPanel();
        LabelTenTimKiem = new javax.swing.JLabel();
        TxtTimKiem = new javax.swing.JTextField();
        BtnTimKiemKhachHang = new javax.swing.JButton();
        PanelXldlKhachHang = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        LabelDiaChi = new javax.swing.JLabel();
        TxtDiaChi = new javax.swing.JTextField();
        LabelCMND = new javax.swing.JLabel();
        TxtCMND = new javax.swing.JTextField();
        LabelEmail = new javax.swing.JLabel();
        LabelGhiChu = new javax.swing.JLabel();
        BtnNew = new javax.swing.JButton();
        BtnXoa = new javax.swing.JButton();
        BtnUpdate = new javax.swing.JButton();
        BtnThem = new javax.swing.JButton();
        TxtGhiChu = new javax.swing.JTextField();
        LabelNgayTao = new javax.swing.JLabel();
        TxtEmail = new javax.swing.JTextField();
        DateChooserNgayTao = new com.toedter.calendar.JDateChooser();
        BtnLast = new javax.swing.JButton();
        BtnNext = new javax.swing.JButton();
        BtnBack = new javax.swing.JButton();
        BtnFirst = new javax.swing.JButton();
        LabelGioiTinh = new javax.swing.JLabel();
        TxtGioiTinh = new javax.swing.JTextField();
        LabelSDT = new javax.swing.JLabel();
        TxtSDT = new javax.swing.JTextField();
        LabelHo = new javax.swing.JLabel();
        TxtHo = new javax.swing.JTextField();
        LabelTen = new javax.swing.JLabel();
        TxtTen = new javax.swing.JTextField();
        LabelMaKH = new javax.swing.JLabel();
        TxtMaKH = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TbDaXoa = new javax.swing.JTable();
        BtnTKKHDaXoa = new javax.swing.JButton();
        PanelTKKHDaXoa = new javax.swing.JPanel();
        TxtTKKHDaXoaTheoTen = new javax.swing.JTextField();
        LabelTKKHDaXoaTheoTen = new javax.swing.JLabel();
        BtnKhoiPhucKH = new javax.swing.JButton();
        BtnXoaVinhVien = new javax.swing.JButton();
        LabelKhoiPhuc = new javax.swing.JLabel();
        LabelXoaVinhVien = new javax.swing.JLabel();

        TabbedPaneKhachHang.setForeground(new java.awt.Color(255, 0, 255));
        TabbedPaneKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        TbKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã KH", "Họ và Tên ", "SDT", "CMND", "Email", "Giới tính", "Địa chỉ", "Ngày tạo", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbKhachHang.setPreferredSize(new java.awt.Dimension(765, 500));
        TbKhachHang.setSelectionBackground(new java.awt.Color(255, 0, 51));
        TbKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbKhachHang);
        if (TbKhachHang.getColumnModel().getColumnCount() > 0) {
            TbKhachHang.getColumnModel().getColumn(1).setMinWidth(120);
            TbKhachHang.getColumnModel().getColumn(4).setMinWidth(120);
        }

        PanelTimKiem.setLayout(null);

        LabelTenTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTenTimKiem.setText("Tên KH");
        PanelTimKiem.add(LabelTenTimKiem);
        LabelTenTimKiem.setBounds(8, 8, 50, 16);

        TxtTimKiem.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TxtTimKiemCaretUpdate(evt);
            }
        });
        PanelTimKiem.add(TxtTimKiem);
        TxtTimKiem.setBounds(8, 30, 260, 30);

        BtnTimKiemKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnTimKiemKhachHang.setText("Tìm Kiếm");
        BtnTimKiemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTimKiemKhachHangActionPerformed(evt);
            }
        });

        PanelXldlKhachHang.setLayout(null);
        PanelXldlKhachHang.add(jLabel3);
        jLabel3.setBounds(1045, 6, 0, 0);

        LabelDiaChi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelDiaChi.setText("Địa chỉ");
        PanelXldlKhachHang.add(LabelDiaChi);
        LabelDiaChi.setBounds(409, 9, 47, 30);
        PanelXldlKhachHang.add(TxtDiaChi);
        TxtDiaChi.setBounds(470, 10, 230, 30);

        LabelCMND.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelCMND.setText("CMND");
        PanelXldlKhachHang.add(LabelCMND);
        LabelCMND.setBounds(410, 50, 40, 30);

        TxtCMND.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                TxtCMNDFocusLost(evt);
            }
        });
        PanelXldlKhachHang.add(TxtCMND);
        TxtCMND.setBounds(470, 50, 230, 30);

        LabelEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelEmail.setText("Email");
        PanelXldlKhachHang.add(LabelEmail);
        LabelEmail.setBounds(410, 90, 40, 30);

        LabelGhiChu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelGhiChu.setText("Ghi chú");
        PanelXldlKhachHang.add(LabelGhiChu);
        LabelGhiChu.setBounds(770, 10, 42, 30);

        BtnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/star.png"))); // NOI18N
        BtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNewActionPerformed(evt);
            }
        });
        PanelXldlKhachHang.add(BtnNew);
        BtnNew.setBounds(1010, 160, 54, 55);

        BtnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/recycle-bin.png"))); // NOI18N
        BtnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnXoaActionPerformed(evt);
            }
        });
        PanelXldlKhachHang.add(BtnXoa);
        BtnXoa.setBounds(950, 160, 54, 55);

        BtnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pen.png"))); // NOI18N
        BtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdateActionPerformed(evt);
            }
        });
        PanelXldlKhachHang.add(BtnUpdate);
        BtnUpdate.setBounds(890, 160, 54, 55);

        BtnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add.png"))); // NOI18N
        BtnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnThemActionPerformed(evt);
            }
        });
        PanelXldlKhachHang.add(BtnThem);
        BtnThem.setBounds(830, 160, 54, 55);

        TxtGhiChu.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        PanelXldlKhachHang.add(TxtGhiChu);
        TxtGhiChu.setBounds(830, 10, 231, 140);

        LabelNgayTao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelNgayTao.setText("Ngày tạo");
        PanelXldlKhachHang.add(LabelNgayTao);
        LabelNgayTao.setBounds(410, 130, 60, 30);

        TxtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                TxtEmailFocusLost(evt);
            }
        });
        PanelXldlKhachHang.add(TxtEmail);
        TxtEmail.setBounds(470, 90, 230, 30);
        PanelXldlKhachHang.add(DateChooserNgayTao);
        DateChooserNgayTao.setBounds(470, 130, 230, 30);

        BtnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/next (1).png"))); // NOI18N
        BtnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLastActionPerformed(evt);
            }
        });
        PanelXldlKhachHang.add(BtnLast);
        BtnLast.setBounds(240, 170, 59, 50);

        BtnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/next.png"))); // NOI18N
        BtnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNextActionPerformed(evt);
            }
        });
        PanelXldlKhachHang.add(BtnNext);
        BtnNext.setBounds(170, 170, 57, 50);

        BtnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/back.png"))); // NOI18N
        BtnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBackActionPerformed(evt);
            }
        });
        PanelXldlKhachHang.add(BtnBack);
        BtnBack.setBounds(100, 170, 56, 50);

        BtnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/next (2).png"))); // NOI18N
        BtnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFirstActionPerformed(evt);
            }
        });
        PanelXldlKhachHang.add(BtnFirst);
        BtnFirst.setBounds(20, 170, 64, 50);

        LabelGioiTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelGioiTinh.setText("Giới tính");
        PanelXldlKhachHang.add(LabelGioiTinh);
        LabelGioiTinh.setBounds(20, 130, 48, 30);
        PanelXldlKhachHang.add(TxtGioiTinh);
        TxtGioiTinh.setBounds(80, 130, 218, 30);

        LabelSDT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelSDT.setText("SDT");
        PanelXldlKhachHang.add(LabelSDT);
        LabelSDT.setBounds(20, 90, 23, 30);

        TxtSDT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                TxtSDTFocusLost(evt);
            }
        });
        PanelXldlKhachHang.add(TxtSDT);
        TxtSDT.setBounds(80, 90, 218, 30);

        LabelHo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelHo.setText("Họ ");
        PanelXldlKhachHang.add(LabelHo);
        LabelHo.setBounds(20, 50, 22, 30);

        TxtHo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                TxtHoFocusLost(evt);
            }
        });
        PanelXldlKhachHang.add(TxtHo);
        TxtHo.setBounds(50, 50, 103, 30);

        LabelTen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTen.setText("Tên");
        PanelXldlKhachHang.add(LabelTen);
        LabelTen.setBounds(160, 50, 21, 30);

        TxtTen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                TxtTenFocusLost(evt);
            }
        });
        PanelXldlKhachHang.add(TxtTen);
        TxtTen.setBounds(190, 50, 108, 30);

        LabelMaKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelMaKH.setText("Mã");
        PanelXldlKhachHang.add(LabelMaKH);
        LabelMaKH.setBounds(20, 10, 30, 30);

        TxtMaKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                TxtMaKHFocusLost(evt);
            }
        });
        PanelXldlKhachHang.add(TxtMaKH);
        TxtMaKH.setBounds(50, 10, 250, 30);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(PanelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(BtnTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(94, 94, 94))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(PanelXldlKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 1070, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 284, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1069, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 291, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(BtnTimKiemKhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 367, Short.MAX_VALUE)
                .addComponent(PanelXldlKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 230, Short.MAX_VALUE)))
        );

        TabbedPaneKhachHang.addTab("Khách Hàng", jPanel3);

        TbDaXoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã KH", "Họ và Tên", "SDT", "CMND", "Email", "Giới tính", "Địa chỉ", "Ngày tạo", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbDaXoa.setPreferredSize(new java.awt.Dimension(765, 500));
        TbDaXoa.setSelectionBackground(new java.awt.Color(255, 0, 51));
        TbDaXoa.getTableHeader().setReorderingAllowed(false);
        TbDaXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbDaXoaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TbDaXoa);
        if (TbDaXoa.getColumnModel().getColumnCount() > 0) {
            TbDaXoa.getColumnModel().getColumn(1).setMinWidth(120);
            TbDaXoa.getColumnModel().getColumn(4).setMinWidth(120);
        }

        BtnTKKHDaXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnTKKHDaXoa.setText("Tìm kiếm");
        BtnTKKHDaXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTKKHDaXoaActionPerformed(evt);
            }
        });

        TxtTKKHDaXoaTheoTen.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TxtTKKHDaXoaTheoTenCaretUpdate(evt);
            }
        });

        LabelTKKHDaXoaTheoTen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTKKHDaXoaTheoTen.setText("Tên KH");

        javax.swing.GroupLayout PanelTKKHDaXoaLayout = new javax.swing.GroupLayout(PanelTKKHDaXoa);
        PanelTKKHDaXoa.setLayout(PanelTKKHDaXoaLayout);
        PanelTKKHDaXoaLayout.setHorizontalGroup(
            PanelTKKHDaXoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTKKHDaXoaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelTKKHDaXoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TxtTKKHDaXoaTheoTen, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                    .addGroup(PanelTKKHDaXoaLayout.createSequentialGroup()
                        .addComponent(LabelTKKHDaXoaTheoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelTKKHDaXoaLayout.setVerticalGroup(
            PanelTKKHDaXoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTKKHDaXoaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelTKKHDaXoaTheoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtTKKHDaXoaTheoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BtnKhoiPhucKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKhoiPhucKHActionPerformed(evt);
            }
        });

        BtnXoaVinhVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnXoaVinhVienActionPerformed(evt);
            }
        });

        LabelKhoiPhuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelKhoiPhuc.setText("Khôi Phục");

        LabelXoaVinhVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelXoaVinhVien.setText("Xóa Vĩnh Viễn");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(715, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(PanelTKKHDaXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(BtnTKKHDaXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(LabelKhoiPhuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnKhoiPhucKH, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(LabelXoaVinhVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnXoaVinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(327, 327, 327))))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1071, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 289, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(BtnTKKHDaXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelTKKHDaXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 386, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LabelXoaVinhVien, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(BtnXoaVinhVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnKhoiPhucKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelKhoiPhuc, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addGap(165, 165, 165))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 234, Short.MAX_VALUE)))
        );

        TabbedPaneKhachHang.addTab("Đã Xóa", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPaneKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPaneKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBackActionPerformed
        // TODO add your handling code here:
        int position = TbKhachHang.getSelectedRow();
        position = position - 1;
        if (position <= 0) {
            position = 0;
        }
        TbKhachHang.setRowSelectionInterval(position, position);
        KhachHang kh = dao.getListKhachHang().get(position);
        try {
            setModel(kh);
        } catch (ParseException ex) {
        }
    }//GEN-LAST:event_BtnBackActionPerformed

    private void BtnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNextActionPerformed
        // TODO add your handling code here:
        int position = TbKhachHang.getSelectedRow();
        position = position + 1;
        if (position >= dao.getListKhachHang().size() - 1) {
            position = dao.getListKhachHang().size() - 1;
        }
        TbKhachHang.setRowSelectionInterval(position, position);
        KhachHang kh = dao.getListKhachHang().get(position);
        try {
            setModel(kh);
        } catch (ParseException ex) {
        }
    }//GEN-LAST:event_BtnNextActionPerformed

    private void BtnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnThemActionPerformed
        if (checkEmpty()){
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập liệu");
            return;
        }
        
        Date date = new Date();
        if (DateChooserNgayTao.getDate() == null){
            try {
                DateChooserNgayTao.setDate(sdf.parse(sdf.format(date)));
            } catch (ParseException ex) {
            }
        }
        
        for (String str : dao.checkMaKH())
        {
            if (str.equals(TxtMaKH.getText())) {
                JOptionPane.showMessageDialog(this, "Mã KH đã tồn tại");
                resetForm();
                return;
            }
        }
        
        if (TxtMaKH.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mã KH không được bỏ trống");
            return;
        }
        
        if (MessageDialog.showConfirmDialog(this, "ADD", "Bạn có muốn thêm hay không?") == JOptionPane.NO_OPTION) {
            resetForm();
            return;
        }
        
        
        try { 
            KhachHang kh = getModel();
            if (dao.addKH(kh) > 0) {

                fillDataTable();
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                resetForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }//GEN-LAST:event_BtnThemActionPerformed
   
    private void BtnTimKiemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTimKiemKhachHangActionPerformed
        // TODO add your handling code here:
        PanelTimKiem.setVisible(true);
        fillDataTable();
        resetForm();
    }//GEN-LAST:event_BtnTimKiemKhachHangActionPerformed

    private void TxtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TxtTimKiemCaretUpdate
        // TODO add your handling code here:
        TKTheoTen = TxtTimKiem.getText();
        fillDataTable();
        resetForm();
    }//GEN-LAST:event_TxtTimKiemCaretUpdate

    private void BtnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnXoaActionPerformed
        // TODO add your handling code here:            
        if (strMaKH.equals("") && TxtMaKH.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn khách hàng để xóa");
            return;
        }

        if (MessageDialog.showConfirmDialog(this, "DELETE", "Bạn có muốn xóa hay không?") == JOptionPane.NO_OPTION) {
            return;
        }
        
        try {
            if ((dao.deleteKH(strMaKH) > 0)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                fillDataTable();
            } else if (!TxtMaKH.getText().equals("")) {
                for (String str : dao.checkMaKH()) {
                    if (str.equals(TxtMaKH.getText())) {
                        dao.deleteKH(str);
                    }
                }
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                fillDataTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }
    }//GEN-LAST:event_BtnXoaActionPerformed

    private void TbKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbKhachHangMouseClicked
        // TODO add your handling code here:
        int position = TbKhachHang.getSelectedRow();
        strMaKH = TbKhachHang.getValueAt(position, 0).toString();
        TbKhachHang.setRowSelectionInterval(position, position);
        KhachHang kh = dao.getKhachHang(strMaKH);
        if (kh != null){
            try {
                setModel(kh);
            } catch (ParseException ex) {
            }
        }
    }//GEN-LAST:event_TbKhachHangMouseClicked

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateActionPerformed
        // TODO add your handling code here:
        if (strMaKH.equals("") && TxtMaKH.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn khách hàng để update");
            return;
        }
        if (MessageDialog.showConfirmDialog(this, "UPDATE", "Bạn có muốn sửa hay không?") == JOptionPane.NO_OPTION) {
            resetForm();
            return;
        }     
        try {
            KhachHang kh = getModel();
            
            if (dao.updateKH(kh) > 0) {
                fillDataTable();
                JOptionPane.showMessageDialog(this, "Update thành công");
                resetForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update thất bại");
        }
    }//GEN-LAST:event_BtnUpdateActionPerformed

    private void BtnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFirstActionPerformed
        // TODO add your handling code here:
        int position = 0;
        TbKhachHang.setRowSelectionInterval(position, position);
        KhachHang kh = dao.getListKhachHang().get(position);
        try {
            setModel(kh);
        } catch (ParseException ex) {
        }
    }//GEN-LAST:event_BtnFirstActionPerformed

    private void BtnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLastActionPerformed
        // TODO add your handling code here:
        int position = dao.getListKhachHang().size() - 1;
        TbKhachHang.setRowSelectionInterval(position, position);
        KhachHang kh = dao.getListKhachHang().get(position);
        try {
            setModel(kh);
        } catch (ParseException ex) {
        }
    }//GEN-LAST:event_BtnLastActionPerformed

    private void TxtSDTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtSDTFocusLost
        // TODO add your handling code here:
        String sdt = TxtSDT.getText().trim();
        String dinhdang = "^0\\d{9}$";
        if (!sdt.matches(dinhdang) && (!sdt.equals(""))) {
            JOptionPane.showMessageDialog(this, "SĐT KH gồm 10 số (bắt đầu từ số 0)");
            TxtSDT.setBackground(Color.red);
            TxtSDT.requestFocus();
        } else {
            TxtSDT.setBackground(Color.white);
        }
    }//GEN-LAST:event_TxtSDTFocusLost

    private void TxtCMNDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtCMNDFocusLost
        // TODO add your handling code here:
        String cmnd = TxtCMND.getText().trim();
        String dinhdang = "^\\d{10}$";
        if (!cmnd.matches(dinhdang) && (!cmnd.equals(""))) {
            JOptionPane.showMessageDialog(this, "CMND KH gồm 10 số");
            TxtCMND.setBackground(Color.red);
            TxtCMND.requestFocus();
        } else {
            TxtCMND.setBackground(Color.white);
        }
        for (String c : dao.checkCMNDKH()) {
            if (c.equals(cmnd) && (!cmnd.equals(""))) {
                JOptionPane.showMessageDialog(this, "CMND KH không đc trùng");
                TxtCMND.setBackground(Color.red);
                TxtCMND.requestFocus();
            }
        }
    }//GEN-LAST:event_TxtCMNDFocusLost

    private void TxtTenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtTenFocusLost
        // TODO add your handling code here:
        String ten = TxtTen.getText().trim();
        Pattern pattern = Pattern.compile("\\D[a-z]");
        Matcher matcher = pattern.matcher(TxtTen.getText().toLowerCase());
        if (!matcher.find() && (!ten.equals(""))) {
            JOptionPane.showMessageDialog(this, "Tên KH không đúng định dạng. Tên KH không được chứa số hay kí tự đặc biệt");
            TxtTen.setBackground(Color.red);
            TxtTen.requestFocus();
        } else {
            TxtTen.setBackground(Color.white);
        }
    }//GEN-LAST:event_TxtTenFocusLost

    private void TxtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtEmailFocusLost
        // TODO add your handling code here:
        String email = TxtEmail.getText().trim();
        String dinhdang = "^(.+)@(.+)$";
        if (!email.matches(dinhdang) && (!email.equals(""))) {
            JOptionPane.showMessageDialog(this, "Định dạng email KH (vd: abc@gmail.com, b12@hotmail.con, n20@student.ptithcm.edu.vn)");
            TxtEmail.setBackground(Color.red);
            TxtEmail.requestFocus();
        } else {
            TxtEmail.setBackground(Color.white);
        }
    }//GEN-LAST:event_TxtEmailFocusLost

    private void BtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNewActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_BtnNewActionPerformed

    private void TxtMaKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtMaKHFocusLost
        // TODO add your handling code here:
        String makh = TxtMaKH.getText().trim();
        String dinhdangMaKH = "^\\w{10}$";
        if (!makh.matches(dinhdangMaKH)) {
            if (makh.equals("")) {
                if (MessageDialog.showConfirmDialog(this,"THÔNG BÁO","Mã KH không được bỏ trống. Bạn có muốn nhập nữa không?") == JOptionPane.NO_OPTION) {
                    resetForm();
                    TxtMaKH.setBackground(Color.white);
                    return;
                }
            }
            MessageDialog.showMessageDialog(this, null, "Định dạng mã KH (vd: KH13543656, D5davgsfsd, b6aHssn783)");
            TxtMaKH.setBackground(Color.red);
            TxtMaKH.requestFocus();

        } else {
            TxtMaKH.setBackground(Color.white);
        }   
    }//GEN-LAST:event_TxtMaKHFocusLost

    private void TbDaXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbDaXoaMouseClicked
        // TODO add your handling code here:
        int position = TbDaXoa.getSelectedRow();
        strMaKHDaXoa = TbDaXoa.getValueAt(position, 0).toString();
        TbDaXoa.setRowSelectionInterval(position, position);
        KhachHang kh = dao.getKhachHangDaXoa(strMaKHDaXoa);
        if (kh != null){
            try {
                setModel(kh);
            } catch (ParseException ex) {
            }
        }        
    }//GEN-LAST:event_TbDaXoaMouseClicked

    private void TxtTKKHDaXoaTheoTenCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TxtTKKHDaXoaTheoTenCaretUpdate
        // TODO add your handling code here:
        TKTheoTenDaXoa = TxtTKKHDaXoaTheoTen.getText();
        fillDataTable();
    }//GEN-LAST:event_TxtTKKHDaXoaTheoTenCaretUpdate

    private void BtnTKKHDaXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTKKHDaXoaActionPerformed
        // TODO add your handling code here:
        PanelTKKHDaXoa.setVisible(true);
        fillDataTable();
    }//GEN-LAST:event_BtnTKKHDaXoaActionPerformed

    private void BtnKhoiPhucKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKhoiPhucKHActionPerformed
        // TODO add your handling code here:
        if (strMaKHDaXoa.equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn khách hàng để khôi phục");
            return;
        }

        if (MessageDialog.showConfirmDialog(this, null, "Bạn có muốn khôi phục hay không?") == JOptionPane.NO_OPTION) {
            return;
        }
        
        try {
            if ((dao.KhoiPhucKHDaXoa(strMaKHDaXoa) > 0)) {
                JOptionPane.showMessageDialog(this, "Khôi phục thành công");
                fillDataTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Khôi phục thất bại");
        }
    }//GEN-LAST:event_BtnKhoiPhucKHActionPerformed

    private void BtnXoaVinhVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnXoaVinhVienActionPerformed
        // TODO add your handling code here:
        if (strMaKHDaXoa.equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn khách hàng để xóa vĩnh viễn");
            return;
        }

        if (MessageDialog.showConfirmDialog(this, null, "Bạn có muốn xóa vĩnh viễn hay không?") == JOptionPane.NO_OPTION) {
            return;
        }
        
        try {
            dao.deleteKHSoHuu(strMaKHDaXoa);
            if ((dao.deleteKHVinhVien(strMaKHDaXoa) > 0)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                fillDataTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }
    }//GEN-LAST:event_BtnXoaVinhVienActionPerformed

    private void TxtHoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtHoFocusLost
        // TODO add your handling code here:
        String ho = TxtHo.getText().trim();
        Pattern pattern = Pattern.compile("\\D[a-z]");
        Matcher matcher = pattern.matcher(TxtHo.getText().toLowerCase());
        if (!matcher.find() && (!ho.equals(""))) {
            JOptionPane.showMessageDialog(this, "Họ KH không đúng định dạng. Họ KH không được chứa số hay kí tự đặc biệt");
            TxtHo.setBackground(Color.red);
            TxtHo.requestFocus();
        } else {
            TxtHo.setBackground(Color.white);
        }        
    }//GEN-LAST:event_TxtHoFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBack;
    private javax.swing.JButton BtnFirst;
    private javax.swing.JButton BtnKhoiPhucKH;
    private javax.swing.JButton BtnLast;
    private javax.swing.JButton BtnNew;
    private javax.swing.JButton BtnNext;
    private javax.swing.JButton BtnTKKHDaXoa;
    private javax.swing.JButton BtnThem;
    private javax.swing.JButton BtnTimKiemKhachHang;
    private javax.swing.JButton BtnUpdate;
    private javax.swing.JButton BtnXoa;
    private javax.swing.JButton BtnXoaVinhVien;
    private com.toedter.calendar.JDateChooser DateChooserNgayTao;
    private javax.swing.JLabel LabelCMND;
    private javax.swing.JLabel LabelDiaChi;
    private javax.swing.JLabel LabelEmail;
    private javax.swing.JLabel LabelGhiChu;
    private javax.swing.JLabel LabelGioiTinh;
    private javax.swing.JLabel LabelHo;
    private javax.swing.JLabel LabelKhoiPhuc;
    private javax.swing.JLabel LabelMaKH;
    private javax.swing.JLabel LabelNgayTao;
    private javax.swing.JLabel LabelSDT;
    private javax.swing.JLabel LabelTKKHDaXoaTheoTen;
    private javax.swing.JLabel LabelTen;
    private javax.swing.JLabel LabelTenTimKiem;
    private javax.swing.JLabel LabelXoaVinhVien;
    private javax.swing.JPanel PanelTKKHDaXoa;
    private javax.swing.JPanel PanelTimKiem;
    private javax.swing.JPanel PanelXldlKhachHang;
    private javax.swing.JTabbedPane TabbedPaneKhachHang;
    private javax.swing.JTable TbDaXoa;
    private javax.swing.JTable TbKhachHang;
    private javax.swing.JTextField TxtCMND;
    private javax.swing.JTextField TxtDiaChi;
    private javax.swing.JTextField TxtEmail;
    private javax.swing.JTextField TxtGhiChu;
    private javax.swing.JTextField TxtGioiTinh;
    private javax.swing.JTextField TxtHo;
    private javax.swing.JTextField TxtMaKH;
    private javax.swing.JTextField TxtSDT;
    private javax.swing.JTextField TxtTKKHDaXoaTheoTen;
    private javax.swing.JTextField TxtTen;
    private javax.swing.JTextField TxtTimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
