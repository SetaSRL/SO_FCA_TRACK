/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.seta.fca.util;
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

/**
 *
 * @author Roberto Federico
 */
public class Utility {
     public static final ResourceBundle conf = ResourceBundle.getBundle("conf.conf");
    
     
      public static String estraiEccezione(Exception ec1) {
        try {
            return ec1.getStackTrace()[0].getMethodName() + " - " + getStackTrace(ec1);
        } catch (Exception e) {            
        }
        return ec1.getMessage();

    }    
}
