/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.seta.fca.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import so.seta.fca.db.DB_FCA;
import static so.seta.fca.db.DB_FCA.LOGGER;
import static so.seta.fca.util.Utility.estraiEccezione;
import so.seta.fca.entity.Result;
/**
 *
 * @author Roberto Federico
 */
public class Exports {
    public static Result Excel_ExportLavorazioni(String data_start, String data_end, List<String[]> values) {
         Result ris = new Result();
          try {
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
            String ts = date.format(formatter);
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sh = wb.createSheet("Lavorazioni");    
            int rownum = 0;
            Row headerRow = sh.createRow(0);
            String[] headers = {"NumeroPratica","Esito","Data Lavorazione","Operatore","Utente","Nota Sospensione"};
            for(int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
//              cell.setCellStyle(headerCellStyle);
            }
            rownum++;

            for (String[] value : values) {
                Row row = sh.createRow(rownum);
                for (int k = 0; k < value.length; k++) {
                    Cell cell = row.createCell(k);
                    cell.setCellValue(value[k]);
                }
                rownum++;
            }
            
             for (int c = 1; c < 7; c++) {
                sh.autoSizeColumn(c);
            }
             
//            Row headerRow = sh.getRow(rownum);
             // Create a row and put some cells in it. Rows are 0 based.
           
//            Cell c = headerRow.getCell(1);
//            c.setCellValue(data_start + " / " + data_end);
//
//            rownum += 2;
//            for (String[] record : values) {
//                headerRow = sh.getRow(rownum);
//                c = headerRow.getCell(0);
//                c.setCellValue(record[0]);
//
//                c = headerRow.getCell(1);
//                c.setCellValue(record[1]);
//
//                c = headerRow.getCell(2);
//                c.setCellValue(record[2]);
//
//                c = headerRow.getCell(3);
//                c.setCellValue(record[3]);
//
//                c = headerRow.getCell(4);
//                c.setCellValue(record[4]);
//
//                c = headerRow.getCell(5);
//                c.setCellValue(record[5]);
//                
//                c = headerRow.getCell(6);
//                c.setCellValue(record[6]);
//
//                rownum++;
//            }
            FileOutputStream fos = new FileOutputStream(new File("ExportLavorazioniFCABank " + ts + ".xlsx"));
            wb.write(fos);
            ris.setEsito(true);
            ris.setMessaggio("File creato con successo!");
        } catch (IOException e) {
            LOGGER.log.severe(estraiEccezione(e));
            ris.setEsito(false);
            ris.setMessaggio("Errore durante la creazione del file!");
        }
          return ris;
    }
    public static List<String[]> Excel_ExportLavorazioni(String date_start, String date_end) {
        DB_FCA db_fca = new DB_FCA();
        List<String[]> rs = db_fca.rs_PraticheLavorate(date_start, date_end);
        return rs;
    }
}
