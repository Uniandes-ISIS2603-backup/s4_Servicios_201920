/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import co.edu.uniandes.csw.servicios.podam.CorreoStrategy;
import co.edu.uniandes.csw.servicios.podam.TelefonoStrategy;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Estudiante
 */
        
@Entity            
public class ClienteEntity extends BaseEntity implements Serializable{
    
   
    @PodamExclude
    @OneToMany( mappedBy = "cliente")
    private List<SolicitudServicioEntity> servicios;
   
    /**
     * El nombre del cliente
     */
    private String nombre; 
    
    @PodamExclude
    @OneToOne
    private PagoTarjetaEntity tarjeta;
    
    /**
     * El teléfono del cliente
     */
    @PodamStrategyValue(TelefonoStrategy.class)
    private Integer telefono;
    
    /**
     * El correo electrónico del cliente
     */
    @PodamStrategyValue(CorreoStrategy.class)
    private String mail;
    
    
    /**
     * El usuario con el que el cliente ingresa al sistema
     */
    private String usuario;
    
    /**
     * La contraseña del cliente
     */
    private String contrasena;
    
    /**
     * La dirección del cliente
     */
    private String direccion;
    
    public ClienteEntity()
    {
        //Constructor vacio
    }
    
    @Override
    public boolean equals(Object object){
        ClienteEntity aComparar = (ClienteEntity) object;
        boolean respuesta = false;
        if(this.usuario.equals(aComparar.usuario)){
            respuesta = true;
        }
        return respuesta;
    }
    
    /**
     * Retorna la dirección del cliente
     * @return la dirección
     */
    public String getDireccion(){
        return direccion;
    }
    
    /**
     * Cambia la dirección actual del cliente por una dada por parámetro
     * @param pDireccion - la nueva dirección del cliente. 
     */
    public void setDireccion(String pDireccion){
        direccion = pDireccion;
    }
    
    /**
     * Retorna una lista con los servicios del cliente
     * @return los servicios
     */
    public List<SolicitudServicioEntity> getServicios(){
        return servicios;
    }
    
    /**
     * Cambia los servicios del cliente por unos dados por parámetro
     * @param pServicios - La nueva lsita de servicios del cliente
     */
    public void setServicios(List<SolicitudServicioEntity> pServicios){
        servicios = pServicios;
    }
    
    /**
     * Retorna el nombre del cliente
     * @return el nombre
     */
    public String getNombre(){
        return nombre;
    }
    
    /**
     * Cambia el nombre del cliente por uno dado por parámetro
     * @param pNombre - El nuevo nombre del cliente
     */
    public void setNombre(String pNombre){
        nombre = pNombre;
    }
    
    /**
     * Retorna número de teléfono del cliente
     * @return el teléfono
     */
    public Integer getTelefono(){
        return telefono;
    }
    
    /**
     * Cambia el número de teléfono del cliente por uno dado por parámetro
     * @param pTelefono - El nuevo teléfono
     */
    public void setTelefono(Integer pTelefono){
        telefono = pTelefono;
    }
    
    /**
     * Retorna la información de la tarjeta del cliente
     * @return el teléfono
     */
    public PagoTarjetaEntity getTarjeta(){
        return tarjeta;
    }
    
    /**
     * Cambia la información de la tarjeta del cliente por una dada por parámetro. 
     * @param pTelefono - El nuevo teléfono
     */
    public void setTarjeta(PagoTarjetaEntity pTarjeta){
        tarjeta = pTarjeta;
    }
    
    /**
     * Retorna el correo electrónico del cliente
     * @return el email
     */
    public String getMail(){
        return mail;
    }
    
    /**
     * Cambia el correo electrónico del cliente por uno dado por parámetro
     * @param pMail - el nuevo correo del cliente
     */
    public void setMail(String pMail){
        mail = pMail;
    }
    
    /**
     * Retorna el usuario del cliente
     * @return - el usuario
     */
    public String getUsuario(){
        return usuario;
    }
    
    /**
     * Cambia el usuario del cliente por uno dado por parámetro
     * @param pUsuario - el nuevo usuario del cliente
     */
    public void setUsuario(String pUsuario){
        usuario = pUsuario;
    }
    
    /**
     * Retorna la contraseña del usuario
     * @return  - la contraseña
     */
    public String getContrasena(){
        return contrasena;
    }
    
    /**
     * Cambia la contraseña del cliente por una dada por parámetro
     * @param pContrasena . la nueva contraseña
     */
    public void setContrasena(String pContrasena){
        contrasena = pContrasena;
    }  
    
}
