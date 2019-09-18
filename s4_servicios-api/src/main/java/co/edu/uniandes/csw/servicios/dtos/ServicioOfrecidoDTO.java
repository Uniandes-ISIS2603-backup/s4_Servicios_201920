/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;

/**
 *
 * @author Estudiante
 */
public class ServicioOfrecidoDTO {
    
    
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
    private double precio;
    
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
     
     
}
