/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Juan Lucas Ibarra
 */
@Entity
public class FacturaEntity extends BaseEntity{
    
    private int duracion;
    
    private double precioMateriales;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    public FacturaEntity(){
        
    }
    
    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double getPrecioMateriales() {
        return precioMateriales;
    }

    public void setPrecioMateriales(double precioMateriales) {
        this.precioMateriales = precioMateriales;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
