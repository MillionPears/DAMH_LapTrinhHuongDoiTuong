       /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Check;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author ACER
 */
public class TestInputData {
    public static boolean isEmpty(JTextField txtField, StringBuilder str, String msg){
        String strField = txtField.getText().trim();
        if (strField.equals("")){
            str.append(msg).append("\n");
            txtField.setBackground(Color.red);
            txtField.requestFocus();
            return false;
        }
        txtField.setBackground(Color.white);
        return true;
    }
    public static boolean isNumber(JTextField txtField, StringBuilder str, String msg){
        if (!isEmpty(txtField, str, msg)){
            return false;
        }else{
            try {
                Integer.parseInt(txtField.getText());
                txtField.setBackground(Color.white);
            } catch (Exception e) {
                str.append(msg).append("\n");
                txtField.setBackground(Color.red);
                txtField.requestFocus();
                return false;                
            }
        }
        return true;
    }
    public static boolean isNumber(JTextField txtField, StringBuilder str, String msg, int type){
        if (!isEmpty(txtField, str, msg)){
            return false;
        }else{
            try {
                switch (type) {
                    case 0:
                        Integer.parseInt(txtField.getText());
                        txtField.setBackground(Color.white);
                        break;
                    case 1:
                        Double.parseDouble(txtField.getText());
                        txtField.setBackground(Color.white);
                        break;                        
                }
            } catch (Exception e) {
                str.append(msg).append("\n");
                txtField.setBackground(Color.red);
                txtField.requestFocus();
                return false;                
            }
        }
        return true;
    }    

    public static String xuLiChuoi(String str) {

        String strtmp = "";

        String[] tmp = str.split("\\s+");

        for (String txt : tmp) {
            txt = txt.trim();
            String f = txt.substring(0, 1).toUpperCase();
            String l = txt.substring(1, txt.length());
            strtmp += f + l + " ";

        }
        return strtmp.substring(0, strtmp.length() - 1);

    }    
}
