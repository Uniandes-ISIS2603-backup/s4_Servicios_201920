/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import co.edu.uniandes.csw.servicios.podam.TipoServicioStrategy;
import java.io.Serializable;
import javax.persistence.Entity;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Violeta Rodríguez
 */
@Entity
public class ServicioOfrecidoEntity extends BaseEntity implements Serializable {

    /**
     * Tipo del serivicio ofrecido, según las constantes definidas.
     */
    @PodamStrategyValue(TipoServicioStrategy.class)
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

    public ServicioOfrecidoEntity() {
        //Es un constructor vacio.
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the precio
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

}
