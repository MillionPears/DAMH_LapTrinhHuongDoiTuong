/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package BanHang_HoaDon;

import Check.MessageDialog;
import Check.TestInputData;
import java.awt.Color;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.*;

/**
 *
 * @author ACER
 */
public class Giam_gia extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private ArrayList<GiamGia> list = new ArrayList<>();
    private GiamGiaDAO daoGG = new GiamGiaDAO();
    private KhachHangDAO dao = new KhachHangDAO();
    private String strMaGGTC = "";
    private String strMaGGDDR = "";
    private String strMaGGSDR = "";
    private String strMaGGDKT = "";
    private String TKTbTC = "";
    private String TKTbDDR = "";
    private String TKTbSDR = "";
    private String TKTbDKT = "";
    private String TKKHTbGG = "";
    private String TKSPTbGG = "";
    
    public Giam_gia() {
        initComponents();
        unvible();
        fillDataTable();
    }
    
    public void fillDataTable() {
        try {
            String trangthai = "";
            DefaultTableModel tbModelTC = (DefaultTableModel) TbTatCa.getModel();
            tbModelTC.setRowCount(0);
            for (GiamGia gg : daoGG.getListGiamGiaTC(TKTbTC)) {
                Object dataRow[] = new Object[6];
                dataRow[0] = gg.getMaGG();
                dataRow[1] = gg.getTenGG();
                dataRow[2] = gg.getLoaiMa();
                if (gg.getTrangThai() ==0 ){
                    trangthai = "Sắp diễn ra";
                }else if (gg.getTrangThai() == 1){
                    trangthai = "Đang diễn ra";
                }else {
                    trangthai = "Đã kết thúc";
                }
                dataRow[3] = trangthai;
                dataRow[4] = gg.getMucGG() + " " + daoGG.checkDonGia(gg);
                dataRow[5] = gg.getTgDienRa() + " đến " + gg.getTgKetThuc();

                tbModelTC.addRow(dataRow);
                TbTatCa.setModel(tbModelTC);
            }
            tbModelTC.fireTableDataChanged();
            
            DefaultTableModel tbModelDDR = (DefaultTableModel) TbDangDienRa.getModel();
            tbModelDDR.setRowCount(0);
            for (GiamGia gg : daoGG.getListGiamGiaDangDienRa(TKTbDDR)) {
                Object dataRow[] = new Object[6];
                dataRow[0] = gg.getMaGG();
                dataRow[1] = gg.getTenGG();
                dataRow[2] = gg.getLoaiMa();
                dataRow[3] = "Đang diễn ra";
                dataRow[4] = gg.getMucGG() + " " + daoGG.checkDonGia(gg);
                dataRow[5] = gg.getTgDienRa() + " đến " + gg.getTgKetThuc();

                tbModelDDR.addRow(dataRow);
                TbDangDienRa.setModel(tbModelDDR);
            }
            tbModelDDR.fireTableDataChanged();
            
            DefaultTableModel tbModelSDR = (DefaultTableModel) TbSapDienRa.getModel();
            tbModelSDR.setRowCount(0);
            for (GiamGia gg : daoGG.getListGiamGiaSapDienRa(TKTbSDR)) {
                Object dataRow[] = new Object[6];
                dataRow[0] = gg.getMaGG();
                dataRow[1] = gg.getTenGG();
                dataRow[2] = gg.getLoaiMa();
                dataRow[3] = "Sắp diễn ra";
                dataRow[4] = gg.getMucGG() + " " + daoGG.checkDonGia(gg);
                dataRow[5] = gg.getTgDienRa() + " đến " + gg.getTgKetThuc();

                tbModelSDR.addRow(dataRow);
                TbSapDienRa.setModel(tbModelSDR);
            }
            tbModelSDR.fireTableDataChanged();    
            
            DefaultTableModel tbModelDKT = (DefaultTableModel) TbDaKetThuc.getModel();
            tbModelDKT.setRowCount(0);
            for (GiamGia gg : daoGG.getListGiamGiaDaKetThuc(TKTbDKT)) {
                Object dataRow[] = new Object[6];
                dataRow[0] = gg.getMaGG();
                dataRow[1] = gg.getTenGG();
                dataRow[2] = gg.getLoaiMa();
                dataRow[3] = "Đã kết thúc";
                dataRow[4] = gg.getMucGG() + " " + daoGG.checkDonGia(gg);
                dataRow[5] = gg.getTgDienRa() + " đến " + gg.getTgKetThuc();

                tbModelDKT.addRow(dataRow);
                TbDaKetThuc.setModel(tbModelDKT);
            }
            tbModelDKT.fireTableDataChanged();   
            
        } catch (Exception e) {
            MessageDialog.showErrorDialog(this, "ERROR", "Lỗi truy xuất dữ liệu");
        }
    }   
    
    public GiamGia getModel() throws ParseException {
        GiamGia gg = new GiamGia();
        gg.setMaGG(TxtMaGG.getText());
        gg.setLoaiMa((String) CboLoaiMa.getSelectedItem());
        gg.setTenGG(TestInputData.xuLiChuoi(TxtTenCTGiamGia.getText()));
        gg.setTgDienRa(sdf.parse(sdf.format(DateChooserTuNgay.getDate())));
        gg.setTgKetThuc(sdf.parse(sdf.format(DateChooserDenNgay.getDate())));
        gg.setMucGG(Integer.parseInt(TxtMucGiamGia.getText()));
        gg.setTrangThai(checkTrangThai());

        return gg;
    }
    
    public void setModel(GiamGia gg) throws ParseException {
        TxtMaGG.setText(gg.getMaGG());
        CboLoaiMa.setSelectedItem(gg.getLoaiMa());
        TxtTenCTGiamGia.setText(gg.getTenGG());
        DateChooserTuNgay.setDate(sdf.parse(sdf.format(gg.getTgDienRa())));
        DateChooserDenNgay.setDate(sdf.parse(sdf.format(gg.getTgKetThuc())));
        CboLoaiGG.setSelectedItem(daoGG.checkDonGia(gg));
        TxtMucGiamGia.setText(Integer.toString(gg.getMucGG()));
    }
    
    public void resetForm() {
        TxtMaGG.setText("");
        CboLoaiMa.setSelectedItem("Giảm giá toàn shop");
        TxtTenCTGiamGia.setText("");
        DateChooserTuNgay.setDate(null);
        DateChooserDenNgay.setDate(null);
        CboLoaiGG.setSelectedItem("Giảm Giá Theo Số Tiền");
        TxtMucGiamGia.setText("");
        CboHangSP.setSelectedItem("Tất cả");
        TxtTKTenSP.setText("");
        TxtSPDuocAD.setText("");
    }
    
    public boolean checkEmpty() {
        if (TxtMaGG.getText().equals("")
                && CboLoaiMa.getSelectedItem().equals("Giảm giá toàn shop")
                && TxtTenCTGiamGia.getText().equals("")
                && TxtTKTenSP.getText().equals("")
                && CboLoaiGG.getSelectedItem().equals("Giảm Giá Theo Số Tiền")) {
            
            return true;
        }
        return false;
    }
    
    public boolean checkNgay() throws ParseException{
        String dateTuNgay = sdf.format(DateChooserTuNgay.getDate());
        Date tungay = sdf.parse(dateTuNgay);
        String dateDenNgay = sdf.format(DateChooserDenNgay.getDate());
        Date denngay = sdf.parse(dateDenNgay);
        if (tungay.compareTo(denngay) > 0){
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày diễn ra");
            return true;
        }
        return false;
    }
    
    public int checkTrangThai() throws ParseException{
        Date date = new Date();
        String dateTuNgay = sdf.format(DateChooserTuNgay.getDate());
        Date tungay = sdf.parse(dateTuNgay);
        String dateDenNgay = sdf.format(DateChooserDenNgay.getDate());
        Date denngay = sdf.parse(dateDenNgay);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        c1.setTime(tungay);
        c2.setTime(denngay);
        c3.setTime(date);
        if (tungay.compareTo(date) > 0) {
            return 0; //sắp diễn ra
//        }else if ((tungay.compareTo(date) <= 0) && (denngay.compareTo(date) >= 0) && (((c2.getTime().getTime() - c1.getTime().getTime())/(24*3600*1000))  <= 30) && (((c2.getTime().getTime() - c3.getTime().getTime())/(24*3600*1000)) <= 30) || (tungay.compareTo(denngay) == 0)){
        } else if (((tungay.compareTo(date) <= 0) && (denngay.compareTo(date) >= 0)) || (tungay.compareTo(denngay) == 0)) {
            return 1; //đang diễn ra
        } else {
            return 2; //đã kết thúc
        }
    }
    
    public boolean checkBooleanKH(String maGG, String maKH) {
        for (String tt : daoGG.listMaGGTrongSoHuu()) {
            if (maGG.equals(tt)) {
                for (String kh : daoGG.listMaKHTrongSoHuu(tt)) {
                    if (maKH.equals(kh)) {
                        return true;                   
                    }
                }
            }
        }
        return false;
    }

    public boolean checkBooleanSP(String maGG, String maSP) {
        for (String tt : daoGG.listMaGGTrongCTKM()) {
            if (maGG.equals(tt)) {
                for (String sp : daoGG.listMaSPTrongCTKM(tt)) {
                    if (maSP.equals(sp)) {
                        return true;                   
                    }
                }
            }
        }
        return false;
    }
    
    public void unvible(){
        PanelGiamGiaTheoTungSP.setVisible(false);
        PanelGiamGiaTheoKH.setVisible(false);  
        daoGG.listComboboxHangSP(CboHangSP);
        LabelThongBaoGGTS.setVisible(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelNhapGG = new javax.swing.JPanel();
        LabelLoaiMa = new javax.swing.JLabel();
        CboLoaiMa = new javax.swing.JComboBox<>();
        LabelTenCTGiamGia = new javax.swing.JLabel();
        TxtTenCTGiamGia = new javax.swing.JTextField();
        CboLoaiGG = new javax.swing.JComboBox<>();
        LabelDenNgay = new javax.swing.JLabel();
        LabelTuNgay = new javax.swing.JLabel();
        LabelLoaiGiamGia = new javax.swing.JLabel();
        LabelMucGiamGia = new javax.swing.JLabel();
        TxtMucGiamGia = new javax.swing.JTextField();
        DateChooserTuNgay = new com.toedter.calendar.JDateChooser();
        DateChooserDenNgay = new com.toedter.calendar.JDateChooser();
        LabelMaGG = new javax.swing.JLabel();
        TxtMaGG = new javax.swing.JTextField();
        TxtDonGia = new javax.swing.JTextField();
        LabelDSMaGiamGia = new javax.swing.JLabel();
        LabelTaoMaGiam = new javax.swing.JLabel();
        TabbedPaneMaGiamGia = new javax.swing.JTabbedPane();
        PanelTatCa = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbTatCa = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TbDangDienRa = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TbSapDienRa = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TbDaKetThuc = new javax.swing.JTable();
        BtnUpdate = new javax.swing.JButton();
        BtnXoa = new javax.swing.JButton();
        BtnThem = new javax.swing.JButton();
        LabelSPDuocAP = new javax.swing.JLabel();
        TxtSPDuocAD = new javax.swing.JTextField();
        PanelGiamGiaTheoTungSP = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TbSPGG = new javax.swing.JTable();
        BtnChonLaiSPSale = new javax.swing.JButton();
        BtnChonTatCaSP = new javax.swing.JButton();
        TxtTKTenSP = new javax.swing.JTextField();
        LabelTen = new javax.swing.JLabel();
        CboHangSP = new javax.swing.JComboBox<>();
        LabelHangSP = new javax.swing.JLabel();
        TxtTKGG = new javax.swing.JTextField();
        LabelTKGG = new javax.swing.JLabel();
        LabelTenGGTK = new javax.swing.JLabel();
        PanelGiamGiaTheoKH = new javax.swing.JPanel();
        BtnChonTatCaKH = new javax.swing.JButton();
        BtnChonLaiKH = new javax.swing.JButton();
        LabelTenKH = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TbKHGG = new javax.swing.JTable();
        TxtTKKHGG = new javax.swing.JTextField();
        LabelThongBaoGGTS = new javax.swing.JLabel();
        BtnNew = new javax.swing.JButton();

        LabelLoaiMa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelLoaiMa.setText("Loại Mã:");

        CboLoaiMa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CboLoaiMa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm giá toàn shop", "Giảm giá theo từng sản phẩm", "Giảm giá theo khách hàng" }));
        CboLoaiMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboLoaiMaActionPerformed(evt);
            }
        });

        LabelTenCTGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTenCTGiamGia.setText("Tên CT giảm giá:");

        TxtTenCTGiamGia.setBorder(null);

        CboLoaiGG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CboLoaiGG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm Giá Theo Số Tiền", "Giảm Giá Theo Phần Trăm" }));
        CboLoaiGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboLoaiGGActionPerformed(evt);
            }
        });

        LabelDenNgay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelDenNgay.setText("Đến Ngày:");

        LabelTuNgay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTuNgay.setText("Từ Ngày:");

        LabelLoaiGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelLoaiGiamGia.setText("Loại Giảm Giá:");

        LabelMucGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelMucGiamGia.setText("Mức giảm giá:");

        TxtMucGiamGia.setBorder(null);
        TxtMucGiamGia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                TxtMucGiamGiaFocusLost(evt);
            }
        });

        LabelMaGG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelMaGG.setText("Mã Giảm Giá:");

        TxtMaGG.setBorder(null);
        TxtMaGG.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                TxtMaGGFocusLost(evt);
            }
        });

        TxtDonGia.setEditable(false);
        TxtDonGia.setBackground(new java.awt.Color(214, 214, 214));
        TxtDonGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtDonGia.setBorder(null);

        javax.swing.GroupLayout PanelNhapGGLayout = new javax.swing.GroupLayout(PanelNhapGG);
        PanelNhapGG.setLayout(PanelNhapGGLayout);
        PanelNhapGGLayout.setHorizontalGroup(
            PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNhapGGLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelNhapGGLayout.createSequentialGroup()
                        .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(LabelTenCTGiamGia)
                                .addGroup(PanelNhapGGLayout.createSequentialGroup()
                                    .addComponent(LabelTuNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(28, 28, 28)))
                            .addComponent(LabelMaGG))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(TxtTenCTGiamGia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                .addComponent(TxtMaGG, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(DateChooserTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelNhapGGLayout.createSequentialGroup()
                                .addComponent(LabelDenNgay)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DateChooserDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelNhapGGLayout.createSequentialGroup()
                                .addComponent(LabelLoaiMa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CboLoaiMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PanelNhapGGLayout.createSequentialGroup()
                        .addComponent(LabelLoaiGiamGia)
                        .addGap(18, 18, 18)
                        .addComponent(CboLoaiGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelNhapGGLayout.createSequentialGroup()
                        .addComponent(LabelMucGiamGia)
                        .addGap(18, 18, 18)
                        .addComponent(TxtMucGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        PanelNhapGGLayout.setVerticalGroup(
            PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNhapGGLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TxtMaGG, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelLoaiMa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LabelMaGG)
                        .addComponent(CboLoaiMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelTenCTGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtTenCTGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DateChooserTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DateChooserDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CboLoaiGG)
                    .addComponent(LabelLoaiGiamGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(PanelNhapGGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelMucGiamGia)
                    .addComponent(TxtMucGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        LabelDSMaGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LabelDSMaGiamGia.setForeground(new java.awt.Color(255, 0, 51));
        LabelDSMaGiamGia.setText("Danh Sách Mã Giảm Giá");

        LabelTaoMaGiam.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        LabelTaoMaGiam.setText("Tạo Mã giảm giá toàn shop hoặc Mã giảm giá sản phẩm ngay bây giờ để thu hút người mua");

        TabbedPaneMaGiamGia.setForeground(new java.awt.Color(255, 0, 51));
        TabbedPaneMaGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        TbTatCa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Giảm Giá", "Tên Mã", "Loại Mã", "Trạng Thái", "Giảm Giá", "Thời Gian Diễn Ra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbTatCa.setPreferredSize(new java.awt.Dimension(630, 600));
        TbTatCa.setSelectionBackground(new java.awt.Color(255, 0, 51));
        TbTatCa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbTatCaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbTatCa);
        if (TbTatCa.getColumnModel().getColumnCount() > 0) {
            TbTatCa.getColumnModel().getColumn(2).setMinWidth(170);
            TbTatCa.getColumnModel().getColumn(3).setMinWidth(50);
            TbTatCa.getColumnModel().getColumn(5).setMinWidth(160);
        }

        javax.swing.GroupLayout PanelTatCaLayout = new javax.swing.GroupLayout(PanelTatCa);
        PanelTatCa.setLayout(PanelTatCaLayout);
        PanelTatCaLayout.setHorizontalGroup(
            PanelTatCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
        );
        PanelTatCaLayout.setVerticalGroup(
            PanelTatCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTatCaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        TabbedPaneMaGiamGia.addTab("Tất Cả", PanelTatCa);

        TbDangDienRa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Giảm Giá", "Tên Mã", "Loại Mã", "Trạng Thái", "Giảm Giá", "Thời Gian Diễn Ra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbDangDienRa.setPreferredSize(new java.awt.Dimension(630, 600));
        TbDangDienRa.setSelectionBackground(new java.awt.Color(255, 0, 51));
        TbDangDienRa.getTableHeader().setReorderingAllowed(false);
        TbDangDienRa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbDangDienRaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TbDangDienRa);
        if (TbDangDienRa.getColumnModel().getColumnCount() > 0) {
            TbDangDienRa.getColumnModel().getColumn(2).setMinWidth(170);
            TbDangDienRa.getColumnModel().getColumn(3).setMinWidth(50);
            TbDangDienRa.getColumnModel().getColumn(5).setMinWidth(160);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TabbedPaneMaGiamGia.addTab("Đang Diễn Ra", jPanel1);

        TbSapDienRa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Giảm Giá", "Tên Mã", "Loại Mã", "Trạng Thái", "Giảm Giá", "Thời Gian Diễn Ra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbSapDienRa.setPreferredSize(new java.awt.Dimension(630, 600));
        TbSapDienRa.setSelectionBackground(new java.awt.Color(255, 0, 51));
        TbSapDienRa.getTableHeader().setReorderingAllowed(false);
        TbSapDienRa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbSapDienRaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TbSapDienRa);
        if (TbSapDienRa.getColumnModel().getColumnCount() > 0) {
            TbSapDienRa.getColumnModel().getColumn(2).setMinWidth(170);
            TbSapDienRa.getColumnModel().getColumn(3).setMinWidth(50);
            TbSapDienRa.getColumnModel().getColumn(5).setMinWidth(160);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabbedPaneMaGiamGia.addTab("Sắp Diễn Ra", jPanel3);

        TbDaKetThuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Giảm Giá", "Tên Mã", "Loại Mã", "Trạng Thái", "Giảm Giá", "Thời Gian Diễn Ra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbDaKetThuc.setPreferredSize(new java.awt.Dimension(630, 600));
        TbDaKetThuc.setSelectionBackground(new java.awt.Color(255, 0, 51));
        TbDaKetThuc.getTableHeader().setReorderingAllowed(false);
        TbDaKetThuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbDaKetThucMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TbDaKetThuc);
        if (TbDaKetThuc.getColumnModel().getColumnCount() > 0) {
            TbDaKetThuc.getColumnModel().getColumn(2).setMinWidth(170);
            TbDaKetThuc.getColumnModel().getColumn(3).setMinWidth(50);
            TbDaKetThuc.getColumnModel().getColumn(5).setMinWidth(160);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
        );

        TabbedPaneMaGiamGia.addTab("Đã Kết Thúc", jPanel4);

        BtnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pen.png"))); // NOI18N
        BtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdateActionPerformed(evt);
            }
        });

        BtnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/recycle-bin.png"))); // NOI18N
        BtnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnXoaActionPerformed(evt);
            }
        });

        BtnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add.png"))); // NOI18N
        BtnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnThemActionPerformed(evt);
            }
        });

        LabelSPDuocAP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelSPDuocAP.setText("Sản Phẩm Được Áp Dụng:");

        TxtSPDuocAD.setEditable(false);
        TxtSPDuocAD.setBackground(new java.awt.Color(214, 214, 214));
        TxtSPDuocAD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtSPDuocAD.setForeground(new java.awt.Color(255, 0, 0));
        TxtSPDuocAD.setBorder(null);

        TbSPGG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbSPGG.setPreferredSize(new java.awt.Dimension(225, 250));
        TbSPGG.setSelectionBackground(new java.awt.Color(255, 0, 51));
        TbSPGG.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(TbSPGG);

        BtnChonLaiSPSale.setBackground(new java.awt.Color(0, 153, 255));
        BtnChonLaiSPSale.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnChonLaiSPSale.setText("Chọn Lại Sản Phẩm Sale");
        BtnChonLaiSPSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChonLaiSPSaleActionPerformed(evt);
            }
        });

        BtnChonTatCaSP.setBackground(new java.awt.Color(0, 153, 255));
        BtnChonTatCaSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnChonTatCaSP.setText("Chọn Tất Cả Sản Phẩm Cửa Hàng");
        BtnChonTatCaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChonTatCaSPActionPerformed(evt);
            }
        });

        TxtTKTenSP.setBorder(null);
        TxtTKTenSP.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TxtTKTenSPCaretUpdate(evt);
            }
        });
        TxtTKTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTKTenSPActionPerformed(evt);
            }
        });

        LabelTen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTen.setText("Tên Tìm Kiếm:");

        CboHangSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CboHangSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        CboHangSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboHangSPActionPerformed(evt);
            }
        });

        LabelHangSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelHangSP.setText("Hãng SP:");

        javax.swing.GroupLayout PanelGiamGiaTheoTungSPLayout = new javax.swing.GroupLayout(PanelGiamGiaTheoTungSP);
        PanelGiamGiaTheoTungSP.setLayout(PanelGiamGiaTheoTungSPLayout);
        PanelGiamGiaTheoTungSPLayout.setHorizontalGroup(
            PanelGiamGiaTheoTungSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGiamGiaTheoTungSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BtnChonTatCaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(BtnChonLaiSPSale, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(PanelGiamGiaTheoTungSPLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(PanelGiamGiaTheoTungSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTen, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelHangSP, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelGiamGiaTheoTungSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CboHangSP, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtTKTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        PanelGiamGiaTheoTungSPLayout.setVerticalGroup(
            PanelGiamGiaTheoTungSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGiamGiaTheoTungSPLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(PanelGiamGiaTheoTungSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelHangSP)
                    .addComponent(CboHangSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(PanelGiamGiaTheoTungSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTen, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtTKTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelGiamGiaTheoTungSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnChonLaiSPSale)
                    .addComponent(BtnChonTatCaSP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );

        TxtTKGG.setBorder(null);
        TxtTKGG.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TxtTKGGCaretUpdate(evt);
            }
        });

        LabelTKGG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTKGG.setForeground(new java.awt.Color(255, 0, 0));
        LabelTKGG.setText("Tìm Kiếm");

        LabelTenGGTK.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTenGGTK.setText("Tên Giảm Giá");

        PanelGiamGiaTheoKH.setMinimumSize(new java.awt.Dimension(522, 380));
        PanelGiamGiaTheoKH.setPreferredSize(new java.awt.Dimension(522, 380));

        BtnChonTatCaKH.setBackground(new java.awt.Color(0, 153, 255));
        BtnChonTatCaKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnChonTatCaKH.setText("Chọn tất cả khách hàng");
        BtnChonTatCaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChonTatCaKHActionPerformed(evt);
            }
        });

        BtnChonLaiKH.setBackground(new java.awt.Color(0, 153, 255));
        BtnChonLaiKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnChonLaiKH.setText("Chọn lại khách hàng");
        BtnChonLaiKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChonLaiKHActionPerformed(evt);
            }
        });

        LabelTenKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTenKH.setText("Tên Tìm Kiếm:");

        TbKHGG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã KH", "Tên KH", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TbKHGG.setPreferredSize(new java.awt.Dimension(225, 300));
        TbKHGG.setSelectionBackground(new java.awt.Color(255, 0, 51));
        TbKHGG.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(TbKHGG);

        TxtTKKHGG.setBorder(null);
        TxtTKKHGG.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TxtTKKHGGCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout PanelGiamGiaTheoKHLayout = new javax.swing.GroupLayout(PanelGiamGiaTheoKH);
        PanelGiamGiaTheoKH.setLayout(PanelGiamGiaTheoKHLayout);
        PanelGiamGiaTheoKHLayout.setHorizontalGroup(
            PanelGiamGiaTheoKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGiamGiaTheoKHLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(PanelGiamGiaTheoKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelGiamGiaTheoKHLayout.createSequentialGroup()
                        .addComponent(BtnChonTatCaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(BtnChonLaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelGiamGiaTheoKHLayout.createSequentialGroup()
                        .addComponent(LabelTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtTKKHGG, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        PanelGiamGiaTheoKHLayout.setVerticalGroup(
            PanelGiamGiaTheoKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGiamGiaTheoKHLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelGiamGiaTheoKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnChonTatCaKH)
                    .addComponent(BtnChonLaiKH))
                .addGap(6, 6, 6)
                .addGroup(PanelGiamGiaTheoKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtTKKHGG, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        LabelThongBaoGGTS.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelThongBaoGGTS.setForeground(new java.awt.Color(255, 0, 51));
        LabelThongBaoGGTS.setText("(Mã Giảm Giá áp dụng cho toàn bộ sản phẩm của shop)");

        BtnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/star.png"))); // NOI18N
        BtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(LabelTKGG, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TabbedPaneMaGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(LabelTenGGTK, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(TxtTKGG, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PanelNhapGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelGiamGiaTheoTungSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelGiamGiaTheoKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelThongBaoGGTS, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(BtnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelSPDuocAP, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtSPDuocAD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelDSMaGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTaoMaGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(LabelDSMaGiamGia)
                .addGap(6, 6, 6)
                .addComponent(LabelTaoMaGiam)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(635, 635, 635)
                                .addComponent(LabelTKGG, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(TabbedPaneMaGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelTenGGTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(TxtTKGG, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(PanelNhapGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LabelThongBaoGGTS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelGiamGiaTheoTungSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelGiamGiaTheoKH, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelSPDuocAP)
                            .addComponent(TxtSPDuocAD, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(BtnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(6, 6, 6))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void CboLoaiMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboLoaiMaActionPerformed
        String Chonloai = (String) CboLoaiMa.getSelectedItem();
        if (Chonloai.equals("Giảm giá toàn shop")) {
            PanelGiamGiaTheoTungSP.setVisible(false);
            PanelGiamGiaTheoKH.setVisible(false);
            TxtSPDuocAD.setText(" Toàn Shop"); 
            LabelThongBaoGGTS.setVisible(true);
        } else if (Chonloai.equals("Giảm giá theo từng sản phẩm")) {
            PanelGiamGiaTheoTungSP.setVisible(true);
            PanelGiamGiaTheoKH.setVisible(false);
            TxtSPDuocAD.setText(" Cho Từng Sản Phẩm");
            
            DefaultTableModel tbModelSPKM = (DefaultTableModel) TbSPGG.getModel();
            tbModelSPKM.setRowCount(0);
            for (SanPham sp : daoGG.getListSPGiamGia(TKSPTbGG)) {
                Object dataRow[] = new Object[3];
                dataRow[0] = sp.getMaSp();
                dataRow[1] = sp.getTenSp();
                dataRow[2] = checkBooleanSP(TxtMaGG.getText(), sp.getMaSp());

                tbModelSPKM.addRow(dataRow);
                TbSPGG.setModel(tbModelSPKM);
            }
            tbModelSPKM.fireTableDataChanged();
        } else if (Chonloai.equals("Giảm giá theo khách hàng")) {
            PanelGiamGiaTheoTungSP.setVisible(false);
            PanelGiamGiaTheoKH.setVisible(true);
            TxtSPDuocAD.setText(" Cho Từng Khách Hàng");

            DefaultTableModel tbModelKHKM = (DefaultTableModel) TbKHGG.getModel();
            tbModelKHKM.setRowCount(0);
            for (KhachHang kh : dao.TimKiemKhachHang(TKKHTbGG)) {
                Object dataRow[] = new Object[3];
                dataRow[0] = kh.getMaKH();
                dataRow[1] = kh.getTenKH();
                dataRow[2] = checkBooleanKH(TxtMaGG.getText(), kh.getMaKH());

                tbModelKHKM.addRow(dataRow);
                TbKHGG.setModel(tbModelKHKM);
            }
            tbModelKHKM.fireTableDataChanged();
        }
    }//GEN-LAST:event_CboLoaiMaActionPerformed

    public void updateSelectGG() {
        if (CboLoaiMa.getSelectedItem().equals("Giảm giá toàn shop")) {
            for (String ts : daoGG.getListMaSP()) {
                if (!checkBooleanSP(TxtMaGG.getText(), ts)) {
                    daoGG.deleteGGSoHuu(TxtMaGG.getText());
                    daoGG.addGGToanShop(TxtMaGG.getText(), ts);                   
                }
            }
        } else if (CboLoaiMa.getSelectedItem().equals("Giảm giá theo khách hàng")) {
            daoGG.addGGTheoKH(TxtMaGG.getText(), TbKHGG);
            daoGG.deleteGGTheoKH(TxtMaGG.getText(), TbKHGG);
        } else if (CboLoaiMa.getSelectedItem().equals("Giảm giá theo từng sản phẩm")) {
            daoGG.addGGTheoSP(TxtMaGG.getText(), TbSPGG);
            daoGG.deleteGGTheoSP(TxtMaGG.getText(), TbSPGG);
        }
    }

    private void BtnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnThemActionPerformed
        try {
            if (checkEmpty()) {
                JOptionPane.showMessageDialog(this, "Lỗi chưa nhập liệu");
                return;
            }

            for (String str : daoGG.listMaGG()) {
                if (str.equals(TxtMaGG.getText())) {
                    JOptionPane.showMessageDialog(this, "Mã GG đã tồn tại");
                    resetForm();
                    return;
                }
            }

            if (TxtMaGG.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Mã GG không được bỏ trống");
                return;
            }

            if (MessageDialog.showConfirmDialog(this, "ADD", "Bạn có muốn thêm hay không?") == JOptionPane.NO_OPTION) {
                resetForm();
                return;
            }
            Date date = new Date();
            if (DateChooserTuNgay.getDate() == null || DateChooserDenNgay.getDate() == null) {
                DateChooserTuNgay.setDate(sdf.parse(sdf.format(date)));
                DateChooserDenNgay.setDate(sdf.parse(sdf.format(date)));
            }
            if (checkNgay()) {
                return;
            }
            GiamGia gg = getModel();
            checkTrangThai();
            if (daoGG.addGG(gg) > 0) { 
                updateSelectGG();
                fillDataTable();
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                resetForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }        
    }//GEN-LAST:event_BtnThemActionPerformed

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateActionPerformed
        // TODO add your handling code here:
        if (strMaGGTC.equals("") && TxtMaGG.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn giảm giá để update");
            return;
        }
        if (MessageDialog.showConfirmDialog(this, "UPDATE", "Bạn có muốn sửa hay không?") == JOptionPane.NO_OPTION) {
            resetForm();
            return;
        }           
        
        try {
            if (checkNgay()) {
                return;
            }
            GiamGia gg = getModel();           
            if (daoGG.updateGG(gg) > 0) {
                updateSelectGG();
                fillDataTable();
                JOptionPane.showMessageDialog(this, "Update thành công");
                resetForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update thất bại");
        }
    }//GEN-LAST:event_BtnUpdateActionPerformed

    private void BtnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnXoaActionPerformed
        // TODO add your handling code here:        
        if (strMaGGTC.equals("") && TxtMaGG.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn giảm giá để xóa");
        }
        if (MessageDialog.showConfirmDialog(this, "DELETE", "Bạn có muốn xóa hay không?") == JOptionPane.NO_OPTION) {
            resetForm();
            return;
        }
        
        try {
            if (CboLoaiMa.getSelectedItem().equals("Giảm giá toàn shop") || CboLoaiMa.getSelectedItem().equals("Giảm giá theo từng sản phẩm")) {
                daoGG.deleteGGCTKM(strMaGGTC);
                if (daoGG.deleteGG(strMaGGTC) > 0) {                    
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    fillDataTable();
                    resetForm();
                } else if (!TxtMaGG.getText().equals("")) {
                    for (String str : daoGG.listMaGG()) {
                        if (str.equals(TxtMaGG.getText())) {
                            daoGG.deleteGG(str);
                            resetForm();
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    fillDataTable();
                    resetForm();
                }
            } else if (CboLoaiMa.getSelectedItem().equals("Giảm giá theo khách hàng")) {                
                daoGG.deleteGGSoHuu(strMaGGTC);
                if (daoGG.deleteGG(strMaGGTC) > 0) {                    
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    fillDataTable();
                    resetForm();
                } else if (!TxtMaGG.getText().equals("")) {
                    for (String str : daoGG.listMaGG()) {
                        if (str.equals(TxtMaGG.getText())) {
                            daoGG.deleteGG(str);
                            resetForm();
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    fillDataTable();
                    resetForm();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }
    }//GEN-LAST:event_BtnXoaActionPerformed

    public void ModelTableMouseClicked(GiamGia gg) throws ParseException{
        setModel(gg);
        String ChonLoaiMa = (String) CboLoaiMa.getSelectedItem();
        if (ChonLoaiMa.equals("Giảm giá toàn shop")) {
            TxtSPDuocAD.setText(" Toàn Shop");
        } else if (ChonLoaiMa.equals("Giảm giá theo từng sản phẩm")) {
            TxtSPDuocAD.setText(" Cho Từng Sản Phẩm");
        } else {
            TxtSPDuocAD.setText(" Cho Từng Khách Hàng");
        }

        TxtDonGia.setText(daoGG.checkDonGia(gg));

        if (TxtDonGia.getText().equals("%")) {
            CboLoaiGG.setSelectedItem("Giảm Giá Theo Phần Trăm");
        } else {
            CboLoaiGG.setSelectedItem("Giảm Giá Theo Số Tiền");
        }

        DefaultTableModel tbModelKHKM = (DefaultTableModel) TbKHGG.getModel();
        tbModelKHKM.setRowCount(0);
        for (KhachHang kh : dao.TimKiemKhachHang(TKKHTbGG)) {
            Object dataRow[] = new Object[3];
            dataRow[0] = kh.getMaKH();
            dataRow[1] = kh.getTenKH();
            dataRow[2] = checkBooleanKH(TxtMaGG.getText(), kh.getMaKH());

            tbModelKHKM.addRow(dataRow);
            TbKHGG.setModel(tbModelKHKM);
        }
        tbModelKHKM.fireTableDataChanged(); 
        
        DefaultTableModel tbModelSPKM = (DefaultTableModel) TbSPGG.getModel();
        tbModelSPKM.setRowCount(0);
        for (SanPham sp : daoGG.getListSPGiamGia(TKSPTbGG)) {
            Object dataRow[] = new Object[3];
            dataRow[0] = sp.getMaSp();
            dataRow[1] = sp.getTenSp();
            dataRow[2] = checkBooleanSP(TxtMaGG.getText(), sp.getMaSp());

            tbModelSPKM.addRow(dataRow);
            TbSPGG.setModel(tbModelSPKM);
        }
        tbModelSPKM.fireTableDataChanged();        
    }
        
    private void TbTatCaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbTatCaMouseClicked
        // TODO add your handling code here:
        int position = TbTatCa.getSelectedRow();
        strMaGGTC = TbTatCa.getValueAt(position, 0).toString();
        TbTatCa.setRowSelectionInterval(position, position);
        GiamGia gg = daoGG.getGiamGiaTatCa(strMaGGTC);
        if (gg != null) {
            try {
                ModelTableMouseClicked(gg);
            } catch (ParseException ex) {
            }
        }
    }//GEN-LAST:event_TbTatCaMouseClicked

    private void TxtMaGGFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtMaGGFocusLost
        // TODO add your handling code here:
        String magg = TxtMaGG.getText().trim();
        String dinhdang = "^\\w{10}$";
        if (!magg.matches(dinhdang)) {
            if (magg.equals("")) {
                if (MessageDialog.showConfirmDialog(this,"THÔNG BÁO","Mã GG không được bỏ trống. Bạn có muốn nhập nữa không?") == JOptionPane.NO_OPTION) {
                    resetForm();
                    TxtMaGG.setBackground(Color.white);
                    return;
                }
            }
            MessageDialog.showMessageDialog(this, null, "Định dạng mã GG (vd: GG13456744, BHaaLgsMfd, A6gsaen847)");
            TxtMaGG.setBackground(Color.red);
            TxtMaGG.requestFocus();

        } else {
            TxtMaGG.setBackground(Color.white);
        }
    }//GEN-LAST:event_TxtMaGGFocusLost

    private void CboLoaiGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboLoaiGGActionPerformed
        // TODO add your handling code here:
        String Chon = (String) CboLoaiGG.getSelectedItem();
        if (Chon.equals("Giảm Giá Theo Số Tiền")){
            TxtDonGia.setText("VND");
        }else{
            TxtDonGia.setText("%");
        }
    }//GEN-LAST:event_CboLoaiGGActionPerformed

    private void TxtMucGiamGiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtMucGiamGiaFocusLost
        // TODO add your handling code here:
        String MucGG = TxtMucGiamGia.getText();
        if (MucGG.contains("đ") || MucGG.contains("VNĐ") || MucGG.contains("VND")) {
            CboLoaiGG.setSelectedItem("Giảm Giá Theo Số Tiền");
            TxtMucGiamGia.requestFocus();
        }else if (MucGG.contains("%")) {
            CboLoaiGG.setSelectedItem("Giảm Giá Theo Phần Trăm");
            TxtMucGiamGia.requestFocus();
        }     
        StringBuilder sb = new StringBuilder();
        if (!TxtMucGiamGia.getText().equals("")){      
            TestInputData.isNumber(TxtMucGiamGia, sb, "Mức giảm giá phải là 1 số");
            if (sb.length() > 0) {
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        }else{
            TxtMucGiamGia.setBackground(Color.WHITE);
        }
    }//GEN-LAST:event_TxtMucGiamGiaFocusLost

    private void TbDangDienRaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbDangDienRaMouseClicked
        // TODO add your handling code here:
        int position = TbDangDienRa.getSelectedRow();
        strMaGGDDR = TbDangDienRa.getValueAt(position, 0).toString();
        TbDangDienRa.setRowSelectionInterval(position, position);
        GiamGia gg = daoGG.getGiamGiaDDR(strMaGGDDR);
        if (gg != null) {
            try {
                ModelTableMouseClicked(gg);
            } catch (ParseException ex) {
            }
        }
    }//GEN-LAST:event_TbDangDienRaMouseClicked

    private void TbDaKetThucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbDaKetThucMouseClicked
        // TODO add your handling code here:
        int position = TbDaKetThuc.getSelectedRow();
        strMaGGDKT = TbDaKetThuc.getValueAt(position, 0).toString();
        TbDaKetThuc.setRowSelectionInterval(position, position);
        GiamGia gg = daoGG.getGiamGiaDKT(strMaGGDKT);
        if (gg != null) {
            try {
                ModelTableMouseClicked(gg);
            } catch (ParseException ex) {
            }
        }
    }//GEN-LAST:event_TbDaKetThucMouseClicked

    private void TbSapDienRaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbSapDienRaMouseClicked
        // TODO add your handling code here:
        int position = TbSapDienRa.getSelectedRow();
        strMaGGSDR = TbSapDienRa.getValueAt(position, 0).toString();
        TbSapDienRa.setRowSelectionInterval(position, position);
        GiamGia gg = daoGG.getGiamGiaSDR(strMaGGSDR);
        if (gg != null) {
            try {
                ModelTableMouseClicked(gg);
            } catch (ParseException ex) {
            }
        }
    }//GEN-LAST:event_TbSapDienRaMouseClicked

    private void BtnChonTatCaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChonTatCaKHActionPerformed
        // TODO add your handling code here:        
        DefaultTableModel tbModelKHKM = (DefaultTableModel) TbKHGG.getModel();
        tbModelKHKM.setRowCount(0);
        for (KhachHang kh : dao.TimKiemKhachHang(TKKHTbGG)) {
            Object dataRow[] = new Object[3];
            dataRow[0] = kh.getMaKH();
            dataRow[1] = kh.getTenKH();
            dataRow[2] = true;

            tbModelKHKM.addRow(dataRow);
            TbKHGG.setModel(tbModelKHKM);
        }
        tbModelKHKM.fireTableDataChanged();
    }//GEN-LAST:event_BtnChonTatCaKHActionPerformed

    private void BtnChonLaiKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChonLaiKHActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tbModelKHKM = (DefaultTableModel) TbKHGG.getModel();
        tbModelKHKM.setRowCount(0);
        for (KhachHang kh : dao.TimKiemKhachHang(TKKHTbGG)) {
            Object dataRow[] = new Object[3];
            dataRow[0] = kh.getMaKH();
            dataRow[1] = kh.getTenKH();
            dataRow[2] = checkBooleanKH(TxtMaGG.getText(), kh.getMaKH());

            tbModelKHKM.addRow(dataRow);
            TbKHGG.setModel(tbModelKHKM);
        }
        tbModelKHKM.fireTableDataChanged();
    }//GEN-LAST:event_BtnChonLaiKHActionPerformed

    private void TxtTKKHGGCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TxtTKKHGGCaretUpdate
        // TODO add your handling code here:
        TKKHTbGG = TxtTKKHGG.getText();
        DefaultTableModel tbModelKHKM = (DefaultTableModel) TbKHGG.getModel();
        tbModelKHKM.setRowCount(0);
        for (KhachHang kh : dao.TimKiemKhachHang(TKKHTbGG)) {
            Object dataRow[] = new Object[3];
            dataRow[0] = kh.getMaKH();
            dataRow[1] = kh.getTenKH();
            dataRow[2] = checkBooleanKH(TxtMaGG.getText(), kh.getMaKH());

            tbModelKHKM.addRow(dataRow);
            TbKHGG.setModel(tbModelKHKM);
        }
        tbModelKHKM.fireTableDataChanged();        
    }//GEN-LAST:event_TxtTKKHGGCaretUpdate

    private void TxtTKGGCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TxtTKGGCaretUpdate
        // TODO add your handling code here:
        TKTbTC = TxtTKGG.getText();
        TKTbDDR = TxtTKGG.getText();
        TKTbSDR = TxtTKGG.getText();
        TKTbDKT = TxtTKGG.getText();
        fillDataTable();
        resetForm();        
    }//GEN-LAST:event_TxtTKGGCaretUpdate

    private void CboHangSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboHangSPActionPerformed
        // TODO add your handling code here:
        
        String TenHang = (String) CboHangSP.getSelectedItem();
        
        DefaultTableModel tbModelSPKM = (DefaultTableModel) TbSPGG.getModel();
        tbModelSPKM.setRowCount(0);
        
        if (TenHang.equals("Tất cả")) {
            for (SanPham sp : daoGG.getListSPGiamGia(TKSPTbGG)) {
                Object dataRow[] = new Object[3];
                dataRow[0] = sp.getMaSp();
                dataRow[1] = sp.getTenSp();
                dataRow[2] = checkBooleanSP(TxtMaGG.getText(), sp.getMaSp());

                tbModelSPKM.addRow(dataRow);
                TbSPGG.setModel(tbModelSPKM);
            }
            tbModelSPKM.fireTableDataChanged();
        } else {
            for (SanPham hang : daoGG.listTheoHangSP(TenHang)) {
                Object dataRow[] = new Object[3];
                dataRow[0] = hang.getMaSp();
                dataRow[1] = hang.getTenSp();
                dataRow[2] = checkBooleanSP(TxtMaGG.getText(), hang.getMaSp());

                tbModelSPKM.addRow(dataRow);
                TbSPGG.setModel(tbModelSPKM);
            }
            tbModelSPKM.fireTableDataChanged();
        }
    }//GEN-LAST:event_CboHangSPActionPerformed

    private void TxtTKTenSPCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TxtTKTenSPCaretUpdate
        // TODO add your handling code here:
        TKSPTbGG = TxtTKTenSP.getText();
        DefaultTableModel tbModelSPKM = (DefaultTableModel) TbSPGG.getModel();
        tbModelSPKM.setRowCount(0);
        for (SanPham sp : daoGG.getListSPGiamGia(TKSPTbGG)) {
            Object dataRow[] = new Object[3];
            dataRow[0] = sp.getMaSp();
            dataRow[1] = sp.getTenSp();
            dataRow[2] = checkBooleanSP(TxtMaGG.getText(), sp.getMaSp());

            tbModelSPKM.addRow(dataRow);
            TbSPGG.setModel(tbModelSPKM);
        }
        tbModelSPKM.fireTableDataChanged();          
    }//GEN-LAST:event_TxtTKTenSPCaretUpdate

    private void BtnChonTatCaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChonTatCaSPActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tbModelSPKM = (DefaultTableModel) TbSPGG.getModel();
        tbModelSPKM.setRowCount(0);
        for (SanPham sp : daoGG.getListSPGiamGia(TKSPTbGG)) {
            Object dataRow[] = new Object[3];
            dataRow[0] = sp.getMaSp();
            dataRow[1] = sp.getTenSp();
            dataRow[2] = true;

            tbModelSPKM.addRow(dataRow);
            TbSPGG.setModel(tbModelSPKM);
        }
        tbModelSPKM.fireTableDataChanged();        
    }//GEN-LAST:event_BtnChonTatCaSPActionPerformed

    private void BtnChonLaiSPSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChonLaiSPSaleActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tbModelSPKM = (DefaultTableModel) TbSPGG.getModel();
        tbModelSPKM.setRowCount(0);
        for (SanPham sp : daoGG.getListSPGiamGia(TKSPTbGG)) {
            Object dataRow[] = new Object[3];
            dataRow[0] = sp.getMaSp();
            dataRow[1] = sp.getTenSp();
            dataRow[2] = checkBooleanSP(TxtMaGG.getText(), sp.getMaSp());

            tbModelSPKM.addRow(dataRow);
            TbSPGG.setModel(tbModelSPKM);
        }
        tbModelSPKM.fireTableDataChanged();
    }//GEN-LAST:event_BtnChonLaiSPSaleActionPerformed

    private void BtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNewActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_BtnNewActionPerformed

    private void TxtTKTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTKTenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTKTenSPActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnChonLaiKH;
    private javax.swing.JButton BtnChonLaiSPSale;
    private javax.swing.JButton BtnChonTatCaKH;
    private javax.swing.JButton BtnChonTatCaSP;
    private javax.swing.JButton BtnNew;
    private javax.swing.JButton BtnThem;
    private javax.swing.JButton BtnUpdate;
    private javax.swing.JButton BtnXoa;
    private javax.swing.JComboBox<String> CboHangSP;
    private javax.swing.JComboBox<String> CboLoaiGG;
    private javax.swing.JComboBox<String> CboLoaiMa;
    private com.toedter.calendar.JDateChooser DateChooserDenNgay;
    private com.toedter.calendar.JDateChooser DateChooserTuNgay;
    private javax.swing.JLabel LabelDSMaGiamGia;
    private javax.swing.JLabel LabelDenNgay;
    private javax.swing.JLabel LabelHangSP;
    private javax.swing.JLabel LabelLoaiGiamGia;
    private javax.swing.JLabel LabelLoaiMa;
    private javax.swing.JLabel LabelMaGG;
    private javax.swing.JLabel LabelMucGiamGia;
    private javax.swing.JLabel LabelSPDuocAP;
    private javax.swing.JLabel LabelTKGG;
    private javax.swing.JLabel LabelTaoMaGiam;
    private javax.swing.JLabel LabelTen;
    private javax.swing.JLabel LabelTenCTGiamGia;
    private javax.swing.JLabel LabelTenGGTK;
    private javax.swing.JLabel LabelTenKH;
    private javax.swing.JLabel LabelThongBaoGGTS;
    private javax.swing.JLabel LabelTuNgay;
    private javax.swing.JPanel PanelGiamGiaTheoKH;
    private javax.swing.JPanel PanelGiamGiaTheoTungSP;
    private javax.swing.JPanel PanelNhapGG;
    private javax.swing.JPanel PanelTatCa;
    private javax.swing.JTabbedPane TabbedPaneMaGiamGia;
    private javax.swing.JTable TbDaKetThuc;
    private javax.swing.JTable TbDangDienRa;
    private javax.swing.JTable TbKHGG;
    private javax.swing.JTable TbSPGG;
    private javax.swing.JTable TbSapDienRa;
    private javax.swing.JTable TbTatCa;
    private javax.swing.JTextField TxtDonGia;
    private javax.swing.JTextField TxtMaGG;
    private javax.swing.JTextField TxtMucGiamGia;
    private javax.swing.JTextField TxtSPDuocAD;
    private javax.swing.JTextField TxtTKGG;
    private javax.swing.JTextField TxtTKKHGG;
    private javax.swing.JTextField TxtTKTenSP;
    private javax.swing.JTextField TxtTenCTGiamGia;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    // End of variables declaration//GEN-END:variables
}

