/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author trieu
 */
public class check_input {

    public check_input() {
    }
    // Kiểm tra Tên không được có số, xóa khoảng trắng và in hoa chữ cái đầu
    public String check_Ten(String s) {
        
        s.trim();
        
        for (int i = 0; i < s.length() -1; i++) {   
            
            if(Character.isDigit(s.charAt(i)))
            {
                JOptionPane.showMessageDialog(null, "Tên Không chứa số!");
            }
            else if (s.charAt(i) == ' ' && s.charAt(i + 1) == ' ') {
                s = s.substring(0, i) + s.substring(i + 1, s.length());
                i--;
            }
        }
        s=s.toLowerCase();
        if(Character.isDigit(s.charAt(s.length()-1)))
            {
                JOptionPane.showMessageDialog(null, "Tên Không chứa số!");
            }
        char[]tmp = s.toCharArray();
        for(int i=1;i<tmp.length-1;i++)
        {
            if(tmp[i]==' ') tmp[i+1]-=32;
        }
        tmp[0]-=32;
        s=String.valueOf(tmp);
        return s;
    }

    // Ktra coi có phải là 1 Jtxt trống hay không
    public boolean isEmpty(JTextField txt) {

        String s = txt.getText();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                return false;
            }
        }return true;
    }

    public void check_So(JTextField txt) {
        check_input ci = new check_input();
        String tmp = txt.getText();
        if (ci.isEmpty(txt)) {
            JOptionPane.showMessageDialog(null, "không được để trống!");
            txt.requestFocus();
        } else {
            try {
                Integer.parseInt(tmp);
            } catch (Exception e) {
                System.out.println("ERROR: include Character!");
                txt.setText(tmp);
                JOptionPane.showMessageDialog(null, "chỉ nhập số!");
                txt.requestFocus();
                //return false;
            }
            //return true;
        }
    }

    public static void main(String[] args) {
        check_input ci = new check_input();
        String s1 = ci.check_Ten("  Lê   Tuấn  Triệu  ");
         String s2 = ci.check_Ten("  lê   tuấn  triệu  ");
         boolean t = s1.equals(s2);
         System.out.println(t);
    }
}
