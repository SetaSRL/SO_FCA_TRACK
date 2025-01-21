/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.seta.fca.db;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import so.seta.fca.entity.Utente;
import so.seta.fca.util.LoggerNew;
import static so.seta.fca.util.Utility.estraiEccezione;


import java.util.logging.Level;
import so.seta.fca.entity.Lavorazione;
import so.seta.fca.entity.Pratica;

/**
 *
 * @author Roberto Federico
 */
public class DB_FCA {
    public static final ResourceBundle conf = ResourceBundle.getBundle("conf.conf");   
    public static final String PATH = conf.getString("path.log");
    public static LoggerNew LOGGER = new LoggerNew("FCA_TRACK",PATH);
    
    Connection conn = null;
    private final String user = conf.getString("db.seta.user");
    private final String pwd = conf.getString("db.seta.ps");
    private final String host = conf.getString("db.seta.ip") + ":3306/fca";
    private final String drivername = conf.getString("db.seta.driver");
    private final String typedb = conf.getString("db.seta.tipo");
      public DB_FCA() {
        try {
            Class.forName(drivername).newInstance();
            Properties p = new Properties();
            p.put("user", user);
            p.put("password", pwd);
            p.put("useUnicode", "true");
            p.put("characterEncoding", "UTF-8");
            this.conn = DriverManager.getConnection("jdbc:" + typedb + "://" + host, p);
        } catch (Exception ex) {
            this.conn = null;
            LOGGER.log.severe(estraiEccezione(ex));            
        }
    }
     public DB_FCA(Connection conn) {
        try {
            this.conn = conn;
        } catch (Exception ex) {
            LOGGER.log.severe(estraiEccezione(ex));
        }
    }

    public Connection getConnectionDB() {
        return conn;
    }

    public void closeDB() {
        try {
            if (conn != null) {
                this.conn.close();
            }
        } catch (SQLException ex) {
           LOGGER.log.severe(estraiEccezione(ex));
        }
    }

    public boolean login(String user, String pwd) {
        try {
            Statement stmt = null;
            ResultSet rs = null;

            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select user From operatore Where user='" + user + "'" + "And password='" + pwd + "'");
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            LOGGER.log.severe(estraiEccezione(ex));
        }
        return false;

    }

    public Utente getUser(String user) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            Utente usrOut = null;
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery("Select nome,cognome,user,ruolo From operatore Where user='" + user + "'");
            if (rs.next()) {
                usrOut = new Utente();
                usrOut.setNome(rs.getString("nome") );
                usrOut.setCognome(rs.getString("cognome") );
                usrOut.setUser(rs.getString("user") );
                usrOut.setRuolo(rs.getString("ruolo") );                
            }
            return usrOut;
        } catch (SQLException ex) {
            LOGGER.log.severe(estraiEccezione(ex));
        }
        return null;
    }
    
    public List<String[]> rs_PraticheLavorate(String date_start, String date_end) {
        List<String[]> out = new ArrayList<>();
        try {
            String query = "SELECT LAV.NUM_PRA, CASE WHEN LAV.ESITO='CH' THEN 'CHIUSURA' ELSE CASE WHEN LAV.ESITO = 'SO' THEN 'SOSPENSIONE' ELSE 'NON LAVORABILE' END END AS Esito," ;
            query += "DATE_FORMAT(LAV.DATALAVORAZIONE, '%d-%m-%Y %H:%i') DATALAV , ";
            query += "CONCAT(OP.nome,' ', OP.cognome) AS Operatore, OP.user AS Utente,LAV.NOTA, LAV.ID ";
            query += "FROM operatore OP INNER JOIN lavorazioni LAV ON OP.user = LAV.Utente "; 
            query += "WHERE DATE_FORMAT(LAV.DATALAVORAZIONE,'%Y-%m-%d')  BETWEEN STR_TO_DATE(?, '%d-%m-%Y') AND STR_TO_DATE(?, '%d-%m-%Y') order by LAV.ID";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, date_start);
            ps.setString(2, date_end);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] arr = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6)};
                out.add(arr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
     
    public int insPratiche(String codicePratica) {
        try {
            String sql = "INSERT INTO pratiche (NUM_PRA,STATO,data) values ('" + codicePratica + "','S',Now())";            
            PreparedStatement ps = this.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log.severe(estraiEccezione(ex));
        }
        return 0;
    }
              
    public Pratica getPraticaDaLavorare() {
        try {
            Pratica prOut = new Pratica();
            prOut.setIdPratica(0);
            String idPratica = "";
            try (PreparedStatement ps1 = this.conn.prepareStatement("SELECT ID,NUM_PRA,STATO,data FROM pratiche Where STATO = 'S' order by data limit 0,1")) {
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) {
                    idPratica = rs.getString("ID");
                    prOut.setIdPratica(Integer.valueOf(idPratica));
                    prOut.setNum_pra(rs.getString("NUM_PRA"));
                }
            }
            if (!idPratica.equals("")) {
                try (PreparedStatement ps2 = this.conn.prepareStatement("UPDATE pratiche set STATO = 'L' where ID = ? ")) {
                    ps2.setString(1, idPratica);
                    ps2.executeUpdate();
                }
            }
            return prOut;
        } catch (SQLException ex) {
            LOGGER.log.severe(estraiEccezione(ex));
        }
        return null;
    }
   
    public List<Lavorazione> VerificaPratica(String numeroPratica) {                       
            List<Lavorazione> out = new ArrayList();    
            try {
              String query = "SELECT ID," +
                             "NUM_PRA," +
                             "ESITO," +
                             "UTENTE," +
                             "DATE_FORMAT(DATALAVORAZIONE, '%d %m %Y %H:%i') DATALAV, " +
                             "NOTA " +
                             "FROM lavorazioni WHERE NUM_PRA = ? ORDER BY DATALAVORAZIONE DESC";
               PreparedStatement ps = this.conn.prepareStatement(query);
               ps.setString(1, numeroPratica);
               ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Lavorazione lav = new Lavorazione();
                                lav.setIdLavorazione(rs.getInt(1));
                                lav.setNum_pra(rs.getString(2));
                                lav.setEsito(rs.getString(3));
                                lav.setUtente(rs.getString(4));
                                lav.setDataLavorazione(rs.getString(5));
                                lav.setNota(rs.getString(6));                              
                        out.add(lav);
                    }
            }   catch (SQLException ex) {
                LOGGER.log.severe(estraiEccezione(ex));
            }
            return out;
    }
    
     public void setStatoPratica(String idPratica, String stato) {
        try (PreparedStatement ps = this.conn.prepareStatement("update pratiche set stato = ? Where id = ?")) {
            ps.setString(1, stato);
            ps.setString(2, idPratica);
            ps.executeUpdate();
        } catch (Exception ex) {
            LOGGER.log.severe(estraiEccezione(ex));
        }

    }
     
     public int sbloccaPratiche() {
        try (Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate("update pratiche set stato='S' where stato='L'");
        } catch (Exception e) {
             LOGGER.log.severe(estraiEccezione(e));
        }
        return 0;
    } 
    
     public boolean insLavorazione(Lavorazione lav) {
       
        String ins = "insert into lavorazioni (NUM_PRA,ESITO,NOTA,DATALAVORAZIONE,UTENTE) VALUES (?,?,?,CURRENT_TIMESTAMP,?)";
        try (PreparedStatement ps = this.conn.prepareStatement(ins)) {
            ps.setString(1, lav.getNum_pra());
            ps.setString(2, lav.getEsito());
            ps.setString(3, lav.getNota());
            ps.setString(4, lav.getUtente());
            
            ps.execute();
            return true;
        } catch (Exception ex) {
            LOGGER.log.severe(estraiEccezione(ex));
        }
        return false;
    }
 
     
      public void getTables() {
         try {
                DatabaseMetaData md = this.conn.getMetaData();
                ResultSet rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
                    System.out.println(rs.getString(3));
                }
            } catch (SQLException ex) {
                LOGGER.log.severe(estraiEccezione(ex));
            }
    }
}
