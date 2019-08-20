/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author ca.torrese
 */
@Entity
public class SolicitudServicioEntity extends BaseEntity implements Serializable{

    private String descripcion;

    private Date fechaInicio;

    private String estado;

    private String foto;
    
    public SolicitudServicioEntity()
    {

    }
    
    public SolicitudServicioEntity( String pDescripcion, Date pFechaInicio, String pEstado, String pFoto)
    {
        descripcion = pDescripcion;
        fechaInicio = pFechaInicio;
        estado = pEstado;
        foto = pFoto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getEstado() {
        return estado;
    }
  
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    
    
    
}
