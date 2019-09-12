/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import co.edu.uniandes.csw.servicios.podam.DateStrategy;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author ca.torrese
 */
@Entity
public class SolicitudServicioEntity extends BaseEntity implements Serializable{

    private String descripcion;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaInicio;

    private String estado;

    private String foto;
    
   @PodamExclude
   @ManyToOne
   private ClienteEntity cliente;
    
   @PodamExclude
   @ManyToOne
   private TrabajadorEntity trabajador;
    
   @PodamExclude
   @OneToMany(mappedBy = "solicitudServicio", fetch = FetchType.LAZY)
   private Collection<ServicioOfrecidoEntity> servicios;
    
    @PodamExclude
    @OneToOne
    private FacturaEntity factura;
    
    @PodamExclude
    @OneToOne
    private CalificacionEntity calificacion;

    
    public SolicitudServicioEntity()
    {
        
    }
    
    
    
    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
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
     * @return the calificacion
     */
    public CalificacionEntity getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(CalificacionEntity calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the servicios
     */
    public Collection<ServicioOfrecidoEntity> getServicios() {
        return servicios;
    }

    /**
     * @param servicios the servicios to set
     */
    public void setServicios(Collection<ServicioOfrecidoEntity> servicios) {
        this.servicios = servicios;
    }

    /**
     * @return the cliente
     */
    public ClienteEntity getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the trabajador
     */
    public TrabajadorEntity getTrabajador() {
        return trabajador;
    }

    /**
     * @param trabajador the trabajador to set
     */
    public void setTrabajador(TrabajadorEntity trabajador) {
        this.trabajador = trabajador;
    }

    /**
     * @return the factura
     */
    public FacturaEntity getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(FacturaEntity factura) {
        this.factura = factura;
    }
    
    
    
   
}
