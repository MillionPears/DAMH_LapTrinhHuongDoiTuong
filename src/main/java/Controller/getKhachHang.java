/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import model.KhachHang;
/**
 *
 * @author trieu
 */
public class getKhachHang {
    public KhachHang getKH(JTextField txt1,JTextField txt2,JTextField txt3,JTextField txt4,JTextField txt5,JTextField txt6,JTextField txt7,JTextField txt8) {
        KhachHang kh = new KhachHang();
        kh.setMaKH(txt1.getText());
        kh.getMaKH();
        kh.setHoKH(txt2.getText());
        kh.getHoKH();
        kh.setTenKH(txt3.getText());
        kh.getTenKH();
        kh.setSoDT(txt4.getText());
        kh.getSoDT();
        kh.setCmnd(txt5.getText());
        kh.getCmnd();
        kh.setEmail(txt6.getText());
        kh.getEmail();
        kh.setGioiTinh(txt7.getText());
        kh.getGioiTinh();
        kh.setDiaChi(txt8.getText());
        kh.getDiaChi();
        //kh.setGhiChu(txt.getText());
        return kh;
    }
}
