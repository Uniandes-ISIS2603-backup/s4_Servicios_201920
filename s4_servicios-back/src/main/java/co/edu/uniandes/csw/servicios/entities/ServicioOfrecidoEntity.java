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
 * @author Violeta Rodríguez
 */
@Entity
public class ServicioOfrecidoEntity extends BaseEntity implements Serializable
{
    public static final int PLOMERIA =2;
   
    public static final int PINTURA =3;
    
    public static final int ELECTRICIDAD =4;
    
    public static final int CERRAJERIA =5;
    
    public static final int VENTANERIA=6;
    
  
    
    
    /**
     * Tipo del serivicio ofrecido, según las constantes definidas. 
     */
    private int tipo;
    
    /**
     * Decripción del servicio ofrecido
     */
    private String descripcion;
    
    /**
     * Precio por hora del servicio ofrecido. 
     */
    private double precio;
    
    public  ServicioOfrecidoEntity ()
    {
        
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
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
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
}
