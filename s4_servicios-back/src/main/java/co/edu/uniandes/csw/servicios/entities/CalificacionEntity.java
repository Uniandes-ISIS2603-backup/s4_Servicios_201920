/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ca.torrese
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable{
    
    
    private double puntaje;
    
    private String comentario;
    
    @PodamExclude
    @OneToOne(mappedBy = "calificacion", fetch = FetchType.LAZY)
    private SolicitudServicioEntity solicitud;
    
    //@PodamExclude
    //@ManyToOne
    //private TrabajadorEntity trabajador;
    
    public CalificacionEntity()
    {
        
    }
    
    /**
     * @return the puntaje
     */
    public double getPuntaje() {
        return puntaje;
    }

    /**
     * @param puntaje the puntaje to set
     */
    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the solicitud
     */
    public SolicitudServicioEntity getSolicitud() {
        return solicitud;
    }

    /**
     * @param solicitud the solicitud to set
     */
    public void setSolicitud(SolicitudServicioEntity solicitud) {
        this.solicitud = solicitud;
    }
    
}
