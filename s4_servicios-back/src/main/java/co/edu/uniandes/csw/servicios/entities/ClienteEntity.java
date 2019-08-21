/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import javax.persistence.Entity;

/**
 *
 * @author Estudiante
 */
        
@Entity            
public class ClienteEntity extends BaseEntity {
    
    private int id;
    private String nombre; 
    private int telefono;
    private String mail;
    private String usuario;
    private String contrasena;
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String pNombre){
        nombre = pNombre;
    }
    
    public int getTelefono(){
        return telefono;
    }
    
    public void setTelefono(int pTelefono){
        telefono = pTelefono;
    }
    
    public String getMail(){
        return mail;
    }
    
    public void setMail(String pMail){
        mail = pMail;
    }
    
    public String getUsuario(){
        return usuario;
    }
    
    public void setUsuario(String pUsuario){
        usuario = pUsuario;
    }
    
    public String getContrasena(){
        return contrasena;
    }
    
    public void setContrasena(String pContrasena){
        contrasena = pContrasena;
    }
    
    
}
