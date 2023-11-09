/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
/**
 *
 * @author trieu
 */
public class Ghi_TV {
    public Ghi_TV (){}

    public static String readUTF8Text(String file) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        FileInputStream fi = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fi, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String sR = "", sNewLine;

        while ((sNewLine = br.readLine()) != null){
            sR += sNewLine + "\n";
        }
        fi.close();
        return sR;
    }

    public static void writeUTF8Text(String file, String text) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        writeUTF8Text(file, text, false);
    }

    public static void writeUTF8Text(String file, String text, boolean append) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        FileOutputStream fo = new FileOutputStream(file, append);
        OutputStreamWriter osw = new OutputStreamWriter(fo, "utf-8");
        osw.write(text);
        osw.flush();
        fo.close();
    }
}
