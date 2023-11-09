/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class MessageDialogHelper {
    public static void showMessageDialog(Component component ,String content,String title)
    {
        JOptionPane.showMessageDialog(component, content, title, JOptionPane.INFORMATION_MESSAGE);
       
    }
     public static void showErrorDialog(Component component ,String content,String title)
    {
        JOptionPane.showMessageDialog(component, content, title, JOptionPane.ERROR_MESSAGE);
       
    }
      public static int showConfirmDialog(Component component ,String content,String title)
    {
       int choose= JOptionPane.showConfirmDialog(component, content, title, JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
       return choose;
    }
    
}
