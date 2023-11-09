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
    
    public static String checkTen(String s, String j) {
        s = s.toLowerCase();
        s = s.trim();
        for (int i = 0; i < s.length() - 1; i++) {   
            
            if(Character.isDigit(s.charAt(i)))
            {
                JOptionPane.showMessageDialog(null, j+" không chứa số!");
            }
            else if (s.charAt(i) == ' ' && s.charAt(i + 1) == ' ') {
                s = s.substring(0, i) + s.substring(i + 1, s.length());
                i--;
            }
        }
        
        if(Character.isDigit(s.charAt(s.length()-1)))
            {
                JOptionPane.showMessageDialog(null, j+" không chứa số!");
            }
        
        char[]tmp = s.toCharArray();
        for (int i = 1; i < tmp.length - 1; i++) {
            if (tmp[i] == ' ') {
                tmp[i + 1] -= 32;
            }
        }
        tmp[0] -= 32;
        s = String.valueOf(tmp);
        
        return s;
    }
    
    public static boolean isNumberMinMax(JTextField txtField, StringBuilder str, String msg, double min, double max, int type){
        if (!isNumber(txtField, str, msg, type)){
            return false;
        }else{
            switch (type) {
                case 0: 
                    int numberInt = Integer.parseInt(txtField.getText());
                    if (numberInt < min || numberInt > max){
                        str.append(msg).append("\n");
                        txtField.setBackground(Color.red);
                        txtField.requestFocus();
                        return false;
                    }
                    break;
                case 1:
                    double number = Double.parseDouble(txtField.getText());
                    if (number < min || number > max){
                        str.append(msg).append("\n");
                        txtField.setBackground(Color.red);
                        txtField.requestFocus();
                        return false;
                    }
                    break;    
            }
        }
        txtField.setBackground(Color.white);
        return true;
    }
    
}
