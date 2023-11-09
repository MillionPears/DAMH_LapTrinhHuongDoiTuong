/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ASUS
 */
public class DataValidator {
    public static boolean empty_Data(JTextField txt,StringBuilder sb,String mess )
    {
        if(txt.getText().equals(""))
        {
            sb.append(mess).append("\n");
            txt.setBackground(Color.red);
            txt.requestFocus();
            return true;
        } else {
            txt.setBackground(Color.white);
            return false;
        }
    }

    public static boolean empty_Data(JPasswordField txt,StringBuilder sb,String mess )
    {
        String password=new String (txt.getPassword());
        if(password.equals(""))
        {
            sb.append(mess).append("\n");
            txt.setBackground(Color.red);
            txt.requestFocus();
            return true;
        } else {
            txt.setBackground(Color.white);
            return false;
        }
    }
    public static boolean email_check(JTextField txt,StringBuilder sb )
    {

       if(empty_Data(txt, sb, "Email chưa được nhập!")) {
           return false;
       }
        Pattern pattern= Pattern.compile("\\w+@\\w+.\\w+");
        Matcher matcher=pattern.matcher(txt.getText());
        if(!matcher.find()){
            sb.append("Email chưa nhập đúng định dạng\n");
            txt.setBackground(Color.red);
            return false;
        }
        else{
            txt.setBackground(Color.white );
              return false;
        }
    }
     public static boolean numberPhone_Check(JTextField txt,StringBuilder sb )
    {

       if(empty_Data(txt, sb, "Số Điện Thoại chưa được nhập!")) {
           return false;
       }
        Pattern pattern= Pattern.compile("0\\d{9}");
        Matcher matcher=pattern.matcher(txt.getText());
        if(!matcher.find()){
            sb.append("Số Điện Thoại chưa nhập đúng định dạng\n");
            txt.setBackground(Color.red);
            return false;
        }
        else{
            txt.setBackground(Color.white );
              return false;
        }
    }
     public static boolean cmnd_Check(JTextField txt,StringBuilder sb )
    {

       if(empty_Data(txt, sb, "CMND chưa được nhập!")) {
           return false;
       }
        Pattern pattern= Pattern.compile("\\d{10}");
        Matcher matcher=pattern.matcher(txt.getText());
        if(!matcher.find()){
            sb.append("CMND chưa nhập đúng định dạng\n");
            txt.setBackground(Color.red);
            return false;
        }
        else{
            txt.setBackground(Color.white );
              return false;
        }
    }
     public static boolean kitu_check(String txt)
     {
       for(int i=0;i<txt.length();i++)
           {
               if(txt.charAt(i)=='0'||txt.charAt(i)=='1'||txt.charAt(i)=='2'||txt.charAt(i)=='3'||txt.charAt(i)=='5'||txt.charAt(i)=='6'||txt.charAt(i)=='7'||txt.charAt(i)=='8'||txt.charAt(i)=='9') return false;
           }
       return true;
     }
      public static boolean name_Check(JTextField txt,StringBuilder sb )
    {

       if(empty_Data(txt, sb, "Họ Tên chưa được nhập!")) {
           return false;
       }
        Pattern pattern= Pattern.compile("\\D[a-z]");
        Matcher matcher=pattern.matcher(txt.getText().toLowerCase());
        if(!matcher.find() ||kitu_check(txt.getText())==false){
            sb.append("Họ Tên chưa nhập đúng định dạng\n");
            txt.setBackground(Color.red);
            return false;
        }
        else{
            txt.setBackground(Color.white );
              return false;
        }
    }
      public static boolean passWorkCheck(JPasswordField txt1,JPasswordField txt2){
           String password=new String (txt1.getPassword());
           String passwordcheck=new String (txt2.getPassword());
           if(password.equals(passwordcheck))
           {
               txt1.setBackground(Color.white);
              txt2.setBackground(Color.white);
               return true;
           }
           else
           {
               txt1.setBackground(Color.red);
              txt2.setBackground(Color.red);

               return false;
           }


      }
    public static boolean datecheck(JDateChooser date){
           if(date.getDate()==null) return false;
           return true;
      }
    public static boolean giacheck(JTextField txt,StringBuilder sb,String mess){
        if(empty_Data(txt, sb, mess+" chưa được Nhập!")) {
           return false;
       }
        try {
            long tmp=Long.parseLong(txt.getText());

               txt.setBackground(Color.white );

              return true;
        } catch (Exception e) {
            sb.append(mess+" không đúng định dạng\n");
            txt.setBackground(Color.red);
            return false;
        }

    }
    public static boolean tenSPCheck(JTextField txt,StringBuilder sb){
         if(empty_Data(txt, sb, "Tên Sản Phẩm chưa được nhập")) {
           return false;
       }
        Pattern pattern= Pattern.compile("\\D[a-z]");
        Matcher matcher=pattern.matcher(txt.getText().toLowerCase());
        if(!matcher.find()){
            sb.append("Tên Sản Phẩm  chưa nhập đúng định dạng(a-z)(0_9)\n");
            txt.setBackground(Color.red);
            return false;
        }
        else{
            txt.setBackground(Color.white );
              return false;
        }

    }
     public static boolean input_cbx_check(JComboBox<String> cbx,StringBuilder sb,String mess)
    {
        if(cbx.getSelectedItem()==null || cbx.getSelectedItem().toString().equals("")||cbx.getSelectedItem().toString().equals(" "))
        {
            sb.append(mess).append("\n");
            cbx.setBackground(Color.red);
            cbx.requestFocus();
            return true;
        } else {
            cbx.setBackground(Color.white);
            return false;
        }
    }
     public  static String xuLiChuoi(String str)
             {
                 
                 String strtmp="";
               
               String [] tmp=str.split("\\s+");
              
                 for (String txt : tmp) {
                     txt=txt.trim();
                    String f = txt.substring(0, 1).toUpperCase();
                    String l = txt.substring(1, txt.length());
                    strtmp += f + l+" ";
                     
                 }
                 return strtmp.substring(0,strtmp.length()-1);
                
                
             }
    public   String xuLiChuoiGiamGia(String str)
             {
                 
                
               
               String [] tmp=str.split("\\s+");
              
                 if(tmp[1].equalsIgnoreCase("percent"))
                 {
                     try {
                         int x=Integer.parseInt(tmp[0]);
                         return x+"";
                         
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
                 
                 if(tmp[1].equalsIgnoreCase("vnd"))
                 {
                     try {
                         int x=Integer.parseInt(tmp[0]);
                         return x+"";
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
                 
                return 0+"";
                
                
             }

}
