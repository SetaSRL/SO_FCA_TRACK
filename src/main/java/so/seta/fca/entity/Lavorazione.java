/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.seta.fca.entity;

/**
 *
 * @author Roberto Federico
 */
public class Lavorazione {
    Integer idLavorazione; 
    String num_pra; 
    String esito;
    String nota;
    String dataLavorazione; 
    String utente; 
    
    public Integer getIdLavorazione() {
        return idLavorazione;
    }
    
    public void setIdLavorazione(Integer value) {
        this.idLavorazione = value;
    }
    public String getNum_pra() {
        return num_pra;
    }
    public void setNum_pra(String value) {
        this.num_pra = value;
    }
    public String getEsito() {
        return esito;
    }
    public void setEsito(String value) {
        this.esito = value;
    }
    public String getNota() {
        return nota;
    }
    public void setNota(String value) {
        this.nota = value;
    }
    public String getDataLavorazione() {
        return dataLavorazione;
    }
    public void setDataLavorazione(String value) {
        this.dataLavorazione = value;
    }
     public String getUtente() {
        return utente;
    }
    public void setUtente(String value) {
        this.utente = value;
    }
}
