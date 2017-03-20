/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Thushan;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Design
 */
public class ReadFromFile { 
    private String inputFile;
    private JFrame frame;
    private final ArrayList<Double> data = new ArrayList<Double>();  

    public void setInputFile(JFrame frame, String inputFile) {
        this.inputFile = inputFile;
        this.frame = frame;
    }

    public ArrayList<Double> readXLS() {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            for(int x=0;x<w.getNumberOfSheets();x++){
                
                int result = JOptionPane.showConfirmDialog(frame, "Use Page "+w.getSheetNames()[x]+" At the index "+x,"Page Selection",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                
                if(JOptionPane.OK_OPTION != result){
                    continue;
                }

                Sheet sheet = w.getSheet(x);
                int index = 0;
                for (int j = 0; j < sheet.getColumns(); j++) {
                    for (int i = 0; i < sheet.getRows(); i++) {
                        
                        Cell cell = sheet.getCell(j, i);
                        CellType type = cell.getType();

                        if (type == CellType.NUMBER) {
                            String check = cell.getContents();

                            while(true) {
                                try {  
                                    data.add(index,Double.parseDouble(check));
                                    index++;
                                    break;
                                } catch(NumberFormatException e){  
                                    String wordx = JOptionPane.showInputDialog(frame,
                                    "Word " + check + " is not in a recognizable format, replace value or cancel dialog to skip",
                                    "Non numerical value found", JOptionPane.QUESTION_MESSAGE);

                                    if(wordx == null){
                                        break;
                                    }else{
                                        check = wordx;
                                    }
                                }
                            } 
                        }
                    }
                }
            }
        } catch (IOException e1) {
        } catch (BiffException e2) {}
        
        return data;
    }
  
    public ArrayList<Double> readANY(String splitBy) {  
        
        String csvFileToRead = this.inputFile;  
        BufferedReader br = null;  
        String line;  
  
        ArrayList<String> all = new ArrayList<String>();
        
        try {  
            br = new BufferedReader(new FileReader(csvFileToRead));  
   
            while ((line = br.readLine()) != null) {  
                String[] allArray = line.split(splitBy);
                all.addAll(Arrays.asList(allArray));
            }  
   
            String regex = "^[-+]?\\d+(\\.\\d+)?$";
            int index = 0;
   
            for(String s : all) {
                
                if(s.matches(regex)){  
                    String check = s;
                   
                    while(true) {
                        try {  
                            data.add(index,Double.parseDouble(check));
                            index++;
                            break;
                        } catch(NumberFormatException e){  
  
                            String wordx = JOptionPane.showInputDialog(frame,
                            "Word " + check + " is not in a recognizable format, replace value or cancel dialog to skip",
                            "Non numerical value found", JOptionPane.QUESTION_MESSAGE);
        
                            if(wordx == null){
                                break;
                            }else{
                                check = wordx;
                            }
                        }
                    }
                    
                }else{
                    String check = s;
                   
                    while(true){
                        
                        try {  
                            data.add(index,Double.parseDouble(check));
                            index++;
                            break;
                        } catch(NumberFormatException e){  

                            String wordx = JOptionPane.showInputDialog(frame,
                            "Word " + check + " is not numerical, replace value or cancel dialog to skip",
                            "Non numerical value found", JOptionPane.QUESTION_MESSAGE);

                            if(wordx == null){
                                break;
                            }else{
                                check = wordx;
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e1) {  
        } catch (IOException e2) {  
        } finally {  
            if (br != null) {  
                try {  
                    br.close();  
                } catch (IOException e) {}  
            }  
        }  
        return data;
    }  


  public static void main(String[] args) throws IOException {
    ReadFromFile test = new ReadFromFile();
    test.setInputFile(new JFrame(),"lars.xls");
    
    ArrayList<Double> alllist = test.readANY(" ");//test.readXLS();;//
              for(Double word :alllist){
            System.out.println(word);
       }
         
   // System.out.println(Long.toString(5,2));   
 
    
  }

}
