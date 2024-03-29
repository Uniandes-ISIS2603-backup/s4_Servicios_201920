/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import java.io.Serializable;

/**
 *
 * @author c.otalora
 */
public class TrabajadorDTO implements Serializable{
    
    private Long id;
private String nombre;

private String contrasena;

private Integer telefono;

private String usuario;

private String foto;

private String correo;
  
private Boolean esApto;

private String hojaVida;

private Boolean disponibilidad;

private String riesgos;

private String seguroSocial;        


public TrabajadorDTO(){
        
        
}
    
public TrabajadorDTO( TrabajadorEntity trabajadorEntity ){
    if(trabajadorEntity != null){
        this.id = trabajadorEntity.getId();
        this.nombre = trabajadorEntity.getNombre();
        this.usuario = trabajadorEntity.getUsuario();
        this.contrasena = trabajadorEntity.getContrasena();
        this.telefono = trabajadorEntity.getTelefono();
        this.correo = trabajadorEntity.getCorreo();
        this.foto = trabajadorEntity.getFoto();
        this.disponibilidad = trabajadorEntity.isDisponibilidad();
        this.hojaVida = trabajadorEntity.getHojaVida();
        this.esApto = trabajadorEntity.isEsApto();
        this.seguroSocial = trabajadorEntity.getSeguroSocial();
        this.riesgos = trabajadorEntity.getRiesgos();
        }
    }
        
public TrabajadorEntity toEntity(){
        TrabajadorEntity trabajadorEntity = new TrabajadorEntity();
        trabajadorEntity.setId(this.id);
        trabajadorEntity.setNombre(this.nombre);
        trabajadorEntity.setUsuario(this.usuario);
        trabajadorEntity.setContrasena(this.contrasena);
        trabajadorEntity.setTelefono(this.telefono);
        trabajadorEntity.setCorreo(this.correo);
        trabajadorEntity.setFoto(this.foto);
        trabajadorEntity.setDisponibilidad(this.disponibilidad);
        trabajadorEntity.setHojaVida(this.hojaVida);
        trabajadorEntity.setEsApto(this.esApto);
        trabajadorEntity.setSeguroSocial(this.seguroSocial);
        trabajadorEntity.setRiesgos(this.riesgos);
        return trabajadorEntity;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the telefono
     */
    public Integer getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * @return the hojaVida
     */
    public String getHojaVida() {
        return hojaVida;
    }

    /**
     * @param hojaVida the hojaVida to set
     */
    public void setHojaVida(String hojaVida) {
        this.hojaVida = hojaVida;
    }

    /**
     * @param disponibilidad the disponibilidad to set
     */
    public void setDisponibilidad(Boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     * @return the seguroSocial
     */
    public String getSeguroSocial() {
        return seguroSocial;
    }

    /**
     * @param seguroSocial the seguroSocial to set
     */
    public void setSeguroSocial(String seguroSocial) {
        this.seguroSocial = seguroSocial;
    }

    /**
     * @param esApto the esApto to set
     */
    public void setEsApto(Boolean esApto) {
        this.esApto = esApto;
    }

    public Boolean getEsApto() {
        return esApto;
    }

    public Boolean getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * @return the riesgos
     */
    public String getRiesgos() {
        return riesgos;
    }

    /**
     * @param riesgos the riesgos to set
     */
    public void setRiesgos(String riesgos) {
        this.riesgos = riesgos;
    }

}
