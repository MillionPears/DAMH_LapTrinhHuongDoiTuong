/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import com.sun.source.tree.ContinueTree;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.hssf.record.PageBreakRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author ASUS
 */
public class DataExcel {
    public static ArrayList<String> readExcelFile(String filePath ) throws IOException {
    ArrayList<String> list=new ArrayList<>();
    Workbook workbook = WorkbookFactory.create(new File(filePath));
    Sheet sheet = workbook.getSheetAt(0);
    DataFormatter dataFormatter = new DataFormatter();   
    sheet.forEach(row -> {
        list.add(row.getCell(1).toString());      
    });
     list.remove(0);
     workbook.close();
     return list;  
    }   
}
