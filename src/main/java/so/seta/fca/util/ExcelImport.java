/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package so.seta.fca.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
 import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import so.seta.fca.db.DB_FCA;
import so.seta.fca.entity.Result;
import static so.seta.fca.gui.ImportFrame.LOGGER;
import static so.seta.fca.util.Utility.estraiEccezione;
/**
 *
 * @author Roberto Federico
 */
 
public class ExcelImport  {
   public static void ReadFromExcel(String excelFilePath) throws IOException {
         try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath))) {
             Workbook workbook = new XSSFWorkbook(inputStream);
             Sheet firstSheet = workbook.getSheetAt(0);
             Iterator<Row> rows = firstSheet.iterator();
             for (Row nextRow : firstSheet) {
                 if (nextRow.getRowNum()!=0) {
                    Iterator<Cell> cellIterator = nextRow.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                     
                        switch (cell.getCellType()){
                            case STRING:
                                System.out.print(cell.getStringCellValue());
                                break;
                            case BOOLEAN:
                                System.out.print(cell.getBooleanCellValue());
                                break;
                            case NUMERIC:
                                 System.out.print(cell.getNumericCellValue());
                             break;
                     }
                     System.out.print(" - ");
                    }
                } else {
                     
                     continue;
                 }
                 
                 System.out.println();
             }
             
             workbook.close();
         } catch (Exception e) {
             
         }
       }
     
    public static Result NewReadFromExcel(String excelFilePath) throws IOException {
        Result ritorno = new Result(); 
        int contaPratiche = 0;    
       
        try ( Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {
            DB_FCA dbf = new DB_FCA();
            Sheet sheet = workbook.getSheetAt(1);
            int startRow = 0;
            DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
             
            for (Row row : sheet) {
                if (row.getRowNum() != startRow) {
                    for (Cell cell : row) {
                        if (cell.getColumnIndex()== 0){
                            String j_codpratica = formatter.formatCellValue(cell);
                            if (!checkCell(j_codpratica))
                                    continue;                           
                            dbf.insPratiche(j_codpratica);
                            contaPratiche++;
                        }
                    }
                }
            }
            
        } catch (Exception ex) {
             LOGGER.log.severe(estraiEccezione(ex));
        }
        ritorno.setEsito(true);
        String messaggio = "Pratiche inserite: " + contaPratiche;
        ritorno.setMessaggio(messaggio);
        return ritorno;
     }
    private static boolean checkCell(String val){
        if (val.isEmpty())
            return false;
        if (!StringUtils.isNumeric(val)) {
            return false;
        } else {
             return !val.contains("/");
        }       
    }
    public static List<String> ReadExcelAsList(String excelFilePath) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(excelFilePath);
        List<String> listaPratiche = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
 
            Sheet firstSheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = firstSheet.iterator();
           
             DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
             while (rowIterator.hasNext()) {
                 Row nextRow = rowIterator.next();
                 Iterator<Cell> cellIterator = nextRow.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell nextCell = cellIterator.next();
                        int columnIndex = nextCell.getColumnIndex();
                        if (columnIndex == 0) {
                           String numPratica  = formatter.formatCellValue(nextCell);
                            if (checkCell(numPratica)) {
                                listaPratiche.add(numPratica);
                            }
                        }   
                     }
                  
                
                 
                            
             }
          
              workbook.close();
             
            
  
           
            long end = System.currentTimeMillis();
        }  catch (IOException ex1) {
             LOGGER.log.severe(estraiEccezione(ex1));
        }
        return listaPratiche;
    } 
    public static Result ReadAndInsertFromExcel(String excelFilePath) {
          DB_FCA dbf = new DB_FCA();
          return dbf.insPraticheBatch(excelFilePath);
    }
    
    public static Result InsertListaPratiche(List<String> listaPratiche) {
         DB_FCA dbf = new DB_FCA();
         return dbf.insPraticheBatchFromList(listaPratiche);
    }
}  

   

     
    