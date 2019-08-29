/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author c.otalora
 */
@Entity
public class TrabajadorEntity extends BaseEntity implements Serializable {
    
    //estos van en la clase Cuenta
    private String nombre;
    private String usuario;
    private String contrasena;
    private Integer telefono;
    private String correo;
    private String foto;
    
    //atributos
    private boolean disponibilidad;
    private String hojaVida;
    private boolean esApto;
    private String seguroSocial;
    private String riesgos;

    /**
     * constructor
     */
    public TrabajadorEntity(){
        
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
     * @return the disponibilidad
     */
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    /**
     * @param disponibilidad the disponibilidad to set
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
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
     * @return the esApto
     */
    public boolean isEsApto() {
        return esApto;
    }

    /**
     * @param esApto the esApto to set
     */
    public void setEsApto(boolean esApto) {
        this.esApto = esApto;
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
