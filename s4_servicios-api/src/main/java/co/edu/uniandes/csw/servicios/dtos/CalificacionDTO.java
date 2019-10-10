/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.entities.CalificacionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ca.torrese
 */
public class CalificacionDTO implements Serializable{
    
    private Long id;
    
    private String comentario;
    
    private int puntaje;
    
     /*
    * Relación a un trabajador  
    * dado que esta tiene cardinalidad 1.
     */
    private TrabajadorDTO trabajador;
    
    /*
    * Relación a una solicitud  
    * dado que esta tiene cardinalidad 1.
    */
    private SolicitudServicioDTO solicitud;
    
    /**
     * Constructor por defecto
     */
    public CalificacionDTO()
    {
        
    }
    
    /**
     * Convertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param calificacionEntity: Es la entidad que se va a convertir a DTO
     */
    public CalificacionDTO(CalificacionEntity calificacionEntity) {
        if (calificacionEntity != null) {
            this.id = calificacionEntity.getId();
            this.comentario = calificacionEntity.getComentario();
            this.puntaje = calificacionEntity.getPuntaje();
            if (calificacionEntity.getTrabajador() != null) {
            this.trabajador = new TrabajadorDTO(calificacionEntity.getTrabajador());
            }
            else{
            this.trabajador = null;
            }
            if (calificacionEntity.getSolicitud() != null) {
            this.solicitud= new SolicitudServicioDTO(calificacionEntity.getSolicitud());
            }
            else{
            this.solicitud = null;
            }
        }
    }
    
     /**
     * Devuelve el ID de la calificacion.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la calificacion.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el comentario de la calificacion.
     *
     * @return the puntaje
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Modifica el comentario de la calificacion.
     *
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    
    /**
     * Devuelve el puntaje de la editorial.
     *
     * @return the puntaje
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Modifica el puntaje de la editorial.
     *
     * @param puntaje the puntaje to set
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    /**
     * Devuelve el trabajador de la calificacion.
     *
     * @return the trabajador
     */
    public TrabajadorDTO getTrabajador() {
        return trabajador;
    }

    /**
     * Modifica el comentario de la calificacion.
     *
     * @param trabajador the trabajador to set
     */
    public void setTrabajador(TrabajadorDTO trabajador) {
        this.trabajador = trabajador;
    }
    
     /**
     * Devuelve la solicitud de la calificacion.
     *
     * @return the solicitud
     */
     public SolicitudServicioDTO getSolicitud() {
        return solicitud;
     }

    /**
     * Modifica la solicitud de la calificacion.
     *
     * @param solicitud the solicitud to set
     */
     public void setSolicitud(SolicitudServicioDTO solicitud) {
        this.solicitud = solicitud;
     }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public CalificacionEntity toEntity() {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setId(this.id);
        calificacionEntity.setPuntaje(this.puntaje);
        calificacionEntity.setComentario(this.comentario);
        if (this.trabajador != null) {
        calificacionEntity.setTrabajador(this.trabajador.toEntity());
        }
        if (this.solicitud != null) {
            calificacionEntity.setSolicitud(this.solicitud.toEntity());
        }
        return calificacionEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    
}
