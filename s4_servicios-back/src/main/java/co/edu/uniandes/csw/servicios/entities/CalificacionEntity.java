/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import co.edu.uniandes.csw.servicios.podam.PuntajeStrategy;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author ca.torrese
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable{
    
    @PodamStrategyValue(PuntajeStrategy.class)
    private int puntaje;
    
    private String comentario;
    
    @PodamExclude
    @OneToOne
    private SolicitudServicioEntity solicitud;
    
    public CalificacionEntity()
    {
        // Como no se neceista inicializar ninguna variable entonces permanece vacío el constructor. 
    }
    
    
    
    /**
     * @return the puntaje
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * @param puntaje the puntaje to set
     */
    public void setPuntaje(int puntaje) {
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
