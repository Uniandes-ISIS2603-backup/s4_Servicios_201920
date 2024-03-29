/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import java.io.Serializable;

/**
 *
 * @author Violeta Rodríguez
 */
public class ServicioOfrecidoDTO implements Serializable
{
    
    
    /**
     * Identificador único del serivicio ofrecido
     */
    
    private Long id;
    
    /**
     * Tipo del serivicio ofrecido, según las constantes definidas. 
     */
    private String tipo;
    
    /**
     * Decripción del servicio ofrecido
     */
    private String descripcion;
    
    /**
     * Precio por hora del servicio ofrecido. 
     */
    private Double precio;
    
    /**
     * Nombre del servio a ofrecer
     */
    
    private String nombre;

    public ServicioOfrecidoDTO() {
    }
    
    
    /**
     * Constructor a partir de una entidad
     * @param entity entidad correspondiente.
     */
     public ServicioOfrecidoDTO (ServicioOfrecidoEntity entity)
    {
        if (entity!=null)
        {
            this.descripcion=entity.getDescripcion();
            this.nombre = entity.getNombre();
            this.tipo= entity.getTipo();
            this.id=entity.getId();
            this.precio=entity.getPrecio();
            
        }
    }
    
     public ServicioOfrecidoEntity toEntity(){
         ServicioOfrecidoEntity entity = new ServicioOfrecidoEntity();
         entity.setId(this.id);
         entity.setDescripcion(this.descripcion);
         entity.setNombre(this.nombre);
         entity.setPrecio(this.precio);
         entity.setTipo(this.tipo);
         return entity;   
     }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
     
     
}
