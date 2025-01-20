/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.seta.fca.util;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Roberto Federico
 */
public class LoggerNew {
     public Logger log;
    FileHandler fileTxt;
    
    public LoggerNew(String appl,String logpath) {
        try {
            Date d = new Date();
            String dataOdierna = (new SimpleDateFormat("ddMMyyyy")).format(d);
            String ora = (new SimpleDateFormat("HHmmss")).format(d);
            File dir1 = new File(logpath);
            dir1.mkdirs();
            File dir2 = new File(dir1.getPath() + File.separator + dataOdierna);
            dir2.mkdirs();
            this.log = Logger.getLogger(appl);
            SimpleFormatter formatterTxt;
            this.fileTxt = new FileHandler(dir2.getPath() + File.separator + appl + "_" + ora + ".log");
            formatterTxt = new SimpleFormatter();
            this.fileTxt.setFormatter(formatterTxt);
            this.log.addHandler(fileTxt);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }
}
