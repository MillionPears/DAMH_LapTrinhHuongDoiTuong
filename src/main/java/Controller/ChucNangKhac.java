/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.KhachHang;

import model.SanPham;

public class ChucNangKhac {

    DAO d = new DAO();
    //Ghi_TV gtv;

    private final static String[] HEADER_ARRAY = {"STT", "Tên Sản Phẩm", "Đơn Giá", "Sale/1 SP", "SL", "Thành Tiền"};

    public ChucNangKhac() {
    }

//    //Hàm tính tiền
//    public void Tinh_Tien(JTextField tongtienhang, JTextField khachdua, JTextField giamgia, JTextField khachcantra, JTextField tralai, JTable table, JComboBox cbb, JTextField mgg_dachon) {
//        int tong_tien = 0;
//        float tmp1 = 0;
//        char[] mggsp;
//            for (int j = 0; j < table.getRowCount(); j++) {
//                String tmpString=(String) table.getValueAt(j, 2);
//                mggsp = tmpString.toCharArray();
//                float tmp2 = 0;
//                for (int i = 0; i < mggsp.length - 8; i++) {
//                    tmp2 = tmp2 * 10 + Integer.parseInt(String.valueOf(mggsp[i]));
//                }
//                int tmString2=  (int) table.getValueAt(j, 3);
//                tmp2 *= tmString2;
//                if (tmp2<=100) {
//                    tmp2 /= 100;
//                }
//                tong_tien -= tong_tien * (int)tmp2;
//            }
//            khachcantra.setText(String.valueOf(Integer.parseInt(tongtienhang.getText()) - tong_tien));
//    }
    // Hàm sắp xếp Tăng theo giá
    public void SapXepTangTheoGia(ArrayList<SanPham> list) {
        Collections.sort(list, new Comparator<SanPham>() {
            @Override
            public int compare(SanPham o1, SanPham o2) {
                return o1.getGia() - o2.getGia();
            }
        });
    }
    //Giảm theo giá

    public void SapXepGiamTheoGia(ArrayList<SanPham> list) {
        Collections.sort(list, new Comparator<SanPham>() {
            @Override
            public int compare(SanPham o1, SanPham o2) {
                return o2.getGia() - o1.getGia();
            }
        });
    }

    //Hàm tạo số dòng rỗng cho paragraph
    public static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    // hàm này để thêm tiêu đề cho từng cột 
    public final static Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 30,
            Font.NORMAL);

    public static void addHeaderInTable(String[] headerArray, PdfPTable table) throws DocumentException, IOException {
        Font f = new Font(BaseFont.createFont("D:\\HK1_Năm 3\\Neatbeans OOP\\Font chữ\\Unicode\\Unicode\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
        PdfPCell c1 = null; // tạo đối tượng ô 
        for (String header : headerArray) {
            c1 = new PdfPCell(new Phrase(header, f));  // tạo 1 ô với nội dung là header
            //c1.setBackgroundColor(BaseColor.BLUE);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);  // thêm nội dung ô này vào bảng
        }
        //table.setHeaderRows(1);     // này chưa hiểu - chưa có tác dụng
    }

    // add dữ liệu có trong listdata vào bảng
    public static void addToTable(PdfPTable table, String data) {
        table.addCell(new Phrase(data));
        
    }

    // hàm này để tạo ra 1 table và add list chứa từng dataObjList vào trong đó
    private static void createReportTable(Paragraph paragraph, JTable donhangTable)
            throws BadElementException, DocumentException, IOException {
        Font f = new Font(BaseFont.createFont("D:\\HK1_Năm 3\\Neatbeans OOP\\Font chữ\\Unicode\\Unicode\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
        PdfPTable table = new PdfPTable(6); // tạo bảng có 4 cột
        table.setWidthPercentage(100);  // set chiều rộng cho từng cột
        //paragraph.add(new Chunk("Report Table :- "));  // thêm 1 cụm từ vào paragraph

//        if(null == dataObjList){
//            paragraph.add(new Chunk("No data to display.")); 
//            return;
//        }
        addHeaderInTable(HEADER_ARRAY, table);      // add tiêu đề cho từng cột
        int count = 1;

        for (int i = 0; i < donhangTable.getRowCount(); i++) {
            
            addToTable(table, String.valueOf(count));  // add dữ liệu vào cột đầu
            addToTable(table, (String) donhangTable.getValueAt(i, 0));  // lấy dữ liệu ở cột 2 trong tableDonHang và add dữ liệu vào cột thứ 2
            addToTable(table, String.valueOf(donhangTable.getValueAt(i, 1)));  // add dữ liệu vào cột thứ 3
            addToTable(table, String.valueOf(donhangTable.getValueAt(i, 2)));    // add dữ liệu vào cột 4
            addToTable(table, String.valueOf(donhangTable.getValueAt(i, 3)));
            addToTable(table, String.valueOf(donhangTable.getValueAt(i, 4)));
            count++;
        }
        paragraph.add(table);   // thêm table vừa tạo vào paragraph
    }

    //Add nội dung cho table
    public static void addContent(Document document, JTable table) throws DocumentException, BadElementException, IOException {
        Font f = new Font(BaseFont.createFont("D:\\HK1_Năm 3\\Neatbeans OOP\\Font chữ\\Unicode\\Unicode\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(f);
        createReportTable(paragraph, table);  // tạo ra 1 table
        document.add(paragraph);  // thêm paragraph vừa tạo vào document
    }

    //Hàm tạp Title cho paragraph
    //                                                *title = test_out
    public static void addTitlePage1(Document document, JDateChooser ngaygiaohang, JLabel infoKH, JLabel sdt, String diachiNhanHang, JTextField NhanVien) throws DocumentException, IOException {
        Font f = new Font(BaseFont.createFont("D:\\HK1_Năm 3\\Neatbeans OOP\\Font chữ\\Unicode\\Unicode\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
        Paragraph preface = new Paragraph();
        preface.add(new Chunk("\t\t\tCỬA HÀNG ĐIỆN THOẠI TNT\n ", f));
        preface.add(new Chunk("\t\t\t\tBiên lai thanh toán \n", f));
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        preface.add(new Chunk("\t\t\t\t\t\t\t\t" + date.toString(), f));
        addEmptyLine(preface, 1);   // thêm 1 dòng trống để xuống dòng
        preface.add(new Chunk("\t\t=========================================================================="));
        addEmptyLine(preface, 2);  // thêm 2 dòng trống
        preface.add(new Chunk("Khách hàng: " + infoKH.getText() + "                         Thu Ngân: " + NhanVien.getText(), f));
        //preface.add(new Phrase("Thu Ngân", f));
        addEmptyLine(preface, 1);  // thêm 1 dòng trống để xuống dòng
        preface.add(new Phrase("SĐT Khách Hàng: " + sdt.getText(), f));
        //preface.add(new Phrase(new Date().toString()));  // Date.toString: trả về tgian hiện tại
        addEmptyLine(preface, 1);   // thêm 1 dòng trống để xuống dòng
        preface.add(new Phrase("Địa chỉ nhận hàng: " + diachiNhanHang, f));
        addEmptyLine(preface, 2);   // thêm 2 dòng trống để xuống dòng
        //document.addSubject("PDF : " + title);  // k hiểu code này 
        preface.setAlignment(Element.ALIGN_CENTER);  // căn chỉnh
        document.add(preface);  // thêm paragraph vừa taho vào document
        //document.newPage();  // tạo ra 1 trang mới
    }

    public static void addTitlePage2(Document document, JTextField tongtienhang, JTextField phivanchuyen, JTextField tonggiamgia, JTextField khachcandua, JTextField khachdua, JTextField tienthua) throws DocumentException, IOException {
        Font f = new Font(BaseFont.createFont("D:\\HK1_Năm 3\\Neatbeans OOP\\Font chữ\\Unicode\\Unicode\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
        Paragraph preface = new Paragraph();

        addEmptyLine(preface, 2);  // thêm 2 dòng trống
        preface.add(new Chunk("Tổng tiền hàng:                                              " + tongtienhang.getText() + "vnđ", f));
        //preface.add(new Phrase("Thu Ngân", f));
        addEmptyLine(preface, 1);  // thêm 1 dòng trống để xuống dòng
        preface.add(new Phrase("Phí vận chuyển:                                             " + phivanchuyen.getText() + "vnđ", f));
        //preface.add(new Phrase(new Date().toString()));  // Date.toString: trả về tgian hiện tại
        addEmptyLine(preface, 1);   // thêm 1 dòng trống để xuống dòng
        preface.add(new Phrase("Giảm giá hóa đơn:                                          " + tonggiamgia.getText() + "vnđ", f));
        addEmptyLine(preface, 1);   // thêm 1 dòng trống để xuống dòng
        preface.add(new Phrase("Tổng phải trả:                                                  " + khachcandua.getText() + "vnđ", f));
        addEmptyLine(preface, 1);   // thêm 2 dòng trống để xuống dòng
        preface.add(new Phrase("Khách thanh toán:                                           " + khachdua.getText() + "vnđ", f));
        addEmptyLine(preface, 1);   // thêm 2 dòng trống để xuống dòng
        preface.add(new Phrase("Tiền thừa:                                                      " + tienthua.getText() + "vnđ", f));
        addEmptyLine(preface, 1);   // thêm 2 dòng trống để xuống dòng
        preface.add(new Phrase("\t\t=========================================================================="));
        addEmptyLine(preface, 1);   // thêm 2 dòng trống để xuống dòng
        preface.add(new Phrase("                                                         Cảm ơn Quý Khách", f));
        //document.addSubject("PDF : " + title);  // k hiểu code này 
        preface.setAlignment(Element.ALIGN_CENTER);  // căn chỉnh
        document.add(preface);  // thêm paragraph vừa taho vào document
        //document.newPage();  // tạo ra 1 trang mới
    }

    // Tạo hóa đơn pdf
    public void Fill_to_HoaDon(JDateChooser ngaygiaohang, JLabel infoKH, JLabel sdt, String diachiNhanHang, JTable tabledonhang, JTextField tongtienhang, JTextField phivanchuyen, JTextField tonggiamgia, JTextField khachcandua, JTextField khachdua, JTextField tienthua, JTextField NhanVien) throws DocumentException, IOException {
//       // f.setColor(Color.BLUE);
//        f.setSize(10);
//        f.setStyle(Font.NORMAL);
        String ten_file = infoKH.getText().toLowerCase()+"_"+sdt.getText();
        
        Document document = null;
        try {
            //Document is not auto-closable hence need to close it separately
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(
                    new File("D:\\HK1_Năm 3\\Neatbeans OOP\\Hoa_Dơn\\" + ten_file + ".pdf")));
//            HeaderFooter event = new HeaderFooter();
//            event.setHeader("Test Report");
//            writer.setPageEvent(event);
            document.open();
//            PDFCreater.addMetaData(document, TITLE);
            addTitlePage1(document, ngaygiaohang, infoKH, sdt, diachiNhanHang, NhanVien);
            addContent(document, tabledonhang);
            addTitlePage2(document, tongtienhang, phivanchuyen, tonggiamgia, khachcandua, khachdua, tienthua);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FileNotFoundException occurs.." + e.getMessage());
        } finally {
            if (null != document) {
                document.close();
            }
        }
    }

    public static void main(String[] args) throws DocumentException {

    }
// Hàm trả về thông báo khi chưa nhập đủ

    public void Thong_bao() {

    }
}
