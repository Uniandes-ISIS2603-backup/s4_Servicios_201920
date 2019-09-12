/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Estudiante
 */
        
@Entity            
public class ClienteEntity extends BaseEntity implements Serializable{
    
    @Id
    /**
     * El identificador del cliente en la base de datos
     */
    private Long id;
    
    //TODO: Meter clase AdministradorEntity para que funcione el código
    /**
     * Atributo que representa el administrador de los clientes
     * Se hace la anotación ManyToOne porque MUCHOS(Many) clientes tienen UN(One) sólo administrador
     */
    //@ManyToOne()
    //AdministradorEntity administrador;
    
    
    //TODO: Meter clase SolicitudServicioEntity para que funcione el código. 
    /**
     * Atributo que representa todos los servicios que ha pedido el cliente
     * Se hace la anotación OneToMany porque UN(One) cliente tiene MUCHOS(Many) servicios
     * En  la clase SolicitudServicio se refiere al cliente como "cliene"
     */
    @PodamExclude
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)

    List<SolicitudServicioEntity> servicios;

    //Collection<SolicitudServicioEntity> servicios;
   
    /**
     * El nombre del cliente
     */
    private String nombre; 
    
    @PodamExclude
    @OneToOne(cascade = CascadeType.ALL)
    private PagoTarjetaEntity tarjeta;
    
    /**
     * El teléfono del cliente
     */
    private int telefono;
    
    /**
     * El correo electrónico del cliente
     */
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
    
    @Override
    public Long getId(){
        return id;
    }
    
    @Override
    public void setId(Long pId){
        id = pId;
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
    public int getTelefono(){
        return telefono;
    }
    
    /**
     * Cambia el número de teléfono del cliente por uno dado por parámetro
     * @param pTelefono - El nuevo teléfono
     */
    public void setTelefono(int pTelefono){
        telefono = pTelefono;
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
