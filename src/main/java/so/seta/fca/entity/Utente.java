/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.seta.fca.entity;

/**
 *
 * @author Roberto Federico
 */
public class Utente {
  String nome; 
  String cognome;
  String user;
  String password;
  String ruolo;
  
  
    public String getNome() {
        return nome;
    }

 
    public void setNome(String value) {
        this.nome = value;
    }

    
  public String getCognome() {
        return cognome;
    }

 
    public void setCognome(String value) {
        this.cognome = value;
    }
    
     public String getUser() {
        return user;
    }

 
    public void setUser(String value) {
        this.user = value;
    }
    
    public String getRuolo() {
        return ruolo;
    }

 
    public void setRuolo(String value) {
        this.ruolo = value;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String value) {
        this.password = value;
    }

 
  
//  public Utente(String nome,String cognome,String user,String password,String ruolo) {
//      this.nome = nome;
//      this.cognome = cognome;
//      this.user= user;
//      this.password = password;
//      this.ruolo = ruolo;
//  }
}
