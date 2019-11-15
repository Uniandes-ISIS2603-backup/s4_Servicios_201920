/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import java.io.Serializable;

/**
 *
 * @author Estudiante
 */
public class ClienteDTO implements Serializable {
    
    private Long id;
    /**
     * El nombre del cliente
     */
    private String nombre; 
    
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
    
    public ClienteDTO(){
    }
    
    public ClienteDTO(ClienteEntity clienteEntity){
        if(clienteEntity != null){
            id = clienteEntity.getId();
            contrasena = clienteEntity.getContrasena();
            direccion = clienteEntity.getDireccion();
            mail = clienteEntity.getMail();
            nombre = clienteEntity.getNombre();
            telefono = clienteEntity.getTelefono();
            usuario = clienteEntity.getUsuario();
        }
    }
    
    public ClienteEntity toEntity(){
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(id);
        clienteEntity.setContrasena(contrasena);
        clienteEntity.setDireccion(direccion);
        clienteEntity.setMail(mail);
        clienteEntity.setNombre(nombre);
        clienteEntity.setTelefono(telefono);
        clienteEntity.setUsuario(usuario);
        return clienteEntity;
    }
    
    
    public Long getId(){
        return id;
    }
    
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
    
}
