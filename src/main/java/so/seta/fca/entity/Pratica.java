/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.seta.fca.entity;

/**
 *
 * @author Roberto Federico
 */
public class Pratica {
    Integer idPratica; 
    String num_pra; 
    String stato;    
    String dataCaricamento; 
    
    public Integer getIdPratica() {
        return idPratica;
    }
    
    public void setIdPratica(Integer value) {
        this.idPratica = value;
    }
    public String getNum_pra() {
        return num_pra;
    }
    public void setNum_pra(String value) {
        this.num_pra = value;
    }
    public String getStato() {
        return stato;
    }
    public void setStato(String value) {
        this.stato = value;
    }
    public String getDataCaricamento() {
        return dataCaricamento;
    }
    public void setDataCaricamento(String value) {
        this.dataCaricamento = value;
    } 
}
