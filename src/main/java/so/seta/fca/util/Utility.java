/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.seta.fca.util;

import java.util.ResourceBundle;
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
