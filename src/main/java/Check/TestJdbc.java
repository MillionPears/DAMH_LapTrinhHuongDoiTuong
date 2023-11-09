package Check;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Connection;
import Check.TestDatabase;
import model.KhachHang;
import BanHang_HoaDon.KhachHangDAO;

/**
 *
 * @author ACER
 */
public class TestJdbc {

    public static void main(String[] args) {
        Connection con = TestDatabase.getDBConnect();
        KhachHangDAO dao = new KhachHangDAO();
        if (con != null){
            System.out.println("Connect thành công");
        }else{
            System.out.println("Connect thất bại");
        }
    }
}
