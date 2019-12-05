/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.adapters.DateAdapter;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ca.torrese
 */
public class SolicitudServicioDTO implements Serializable{
    
    private String descripcion;
     
    private Long id;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaInicio;

    private String foto;
    
     private String estado;
    /*
    * Relación a un cliente  
    * dado que esta tiene cardinalidad 1.
    */
    private ClienteDTO cliente;
    
    /*
    * Relación a un trabajador  
    * dado que esta tiene cardinalidad 1.
    */
    private TrabajadorDTO trabajador;
    
    /*
    * Relación a una factura  
    * dado que esta tiene cardinalidad 1.
    */
    private FacturaDTO factura;
    
    /*
    * Relación a un calificacion  
    * dado que esta tiene cardinalidad 1.
    */
    private CalificacionDTO calificacion;
    
    /**
     * Constructor por defecto
     */
    public SolicitudServicioDTO()
    {
        
    }
    
    /**
     * Convertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param solicitudServicioEntity: Es la entidad que se va a convertir a DTO
     */
    public SolicitudServicioDTO(SolicitudServicioEntity solicitudServicioEntity) {
        if (solicitudServicioEntity != null) {
            this.id = solicitudServicioEntity.getId();
            this.descripcion = solicitudServicioEntity.getDescripcion();
            this.fechaInicio = solicitudServicioEntity.getFechaInicio();
            this.estado = solicitudServicioEntity.getEstado();
            this.foto = solicitudServicioEntity.getFoto();

            if (solicitudServicioEntity.getTrabajador() != null) {
            this.trabajador = new TrabajadorDTO(solicitudServicioEntity.getTrabajador());
            }
            else{
            this.trabajador = null;
            }

            if (solicitudServicioEntity.getTrabajador() != null) {
            this.trabajador = new TrabajadorDTO(solicitudServicioEntity.getTrabajador());
            }
            else{
            this.trabajador = null;
            }

            if (solicitudServicioEntity.getCliente() != null) {
            this.cliente = new ClienteDTO(solicitudServicioEntity.getCliente());
            }
            else{
            this.cliente = null;
            }
            if (solicitudServicioEntity.getFactura() != null) {
            this.factura = new FacturaDTO(solicitudServicioEntity.getFactura());
            }
            else{
            this.factura = null;
            }
            if (solicitudServicioEntity.getCalificacion()!= null) {
            this.calificacion= new CalificacionDTO(solicitudServicioEntity.getCalificacion());
            }
            else{
            this.calificacion = null;
            }
        }
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
     * @return the cliente
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(ClienteDTO cliente) {
       this.cliente = cliente;
    }
    
    /**
     * @return the calificacion
     */
    public CalificacionDTO getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(CalificacionDTO calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the factura
     */
    public FacturaDTO getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }
    

     /**
     * @return the trabajador
     */
    public TrabajadorDTO getTrabajador() {
        return trabajador;
    }

    /**
     * @param trabajador the trabajador to set
     */
    public void setTrabajador(TrabajadorDTO trabajador) {
        this.trabajador = trabajador;
    }

    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public SolicitudServicioEntity toEntity() {
        SolicitudServicioEntity solicitudEntity = new SolicitudServicioEntity();
        solicitudEntity.setId(this.id);
        solicitudEntity.setDescripcion(this.descripcion);
        solicitudEntity.setFechaInicio(this.fechaInicio);
        solicitudEntity.setEstado(this.estado);
        solicitudEntity.setFoto(this.foto);

         if (this.trabajador != null) {
        solicitudEntity.setTrabajador(this.trabajador.toEntity());
        }

         if (this.getTrabajador() != null) {
        solicitudEntity.setTrabajador(this.getTrabajador().toEntity());
        }

         if (this.getCliente() != null) {
        solicitudEntity.setCliente(this.getCliente().toEntity());
        }
         if (this.getFactura() != null) {
        solicitudEntity.setFactura(this.getFactura().toEntity());
        }
        if (this.calificacion != null) {
            solicitudEntity.setCalificacion(this.calificacion.toEntity());
        }
        return solicitudEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


}
