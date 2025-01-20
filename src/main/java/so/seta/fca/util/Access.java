/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.seta.fca.util;
import so.seta.fca.db.DB_FCA;
/**
 *
 * @author Roberto Federico
 */
 
public class Access {
  
   public static boolean getLogin(String user, String pwd) {
       DB_FCA db = new DB_FCA();
       return db.login(user,pwd);
   }
}
