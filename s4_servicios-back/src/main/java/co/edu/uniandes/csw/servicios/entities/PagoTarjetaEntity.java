/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.entities;

import java.util.Date;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Juan Lucas Ibarra
 */
@Entity
public class PagoTarjetaEntity extends BaseEntity{
        
    private int numTarjeta;
    
    private int csv;
    
    @Temporal(TemporalType.DATE)
    private Date fechaV;
    
    @PodamExclude
    @OneToOne(cascade = CascadeType.ALL)
    private FacturaEntity factura;
    
    
    @PodamExclude
    @OneToOne(cascade = CascadeType.ALL)
    private ClienteEntity cliente;
    
    public PagoTarjetaEntity(){
        
    }

    public int getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(int numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public int getCsv() {
        return csv;
    }

    public void setCsv(int csv) {
        this.csv = csv;
    }

    public Date getFechaV() {
        return fechaV;
    }

    public void setFechaV(Date fechaV) {
        this.fechaV = fechaV;
    }
    
    public FacturaEntity getFactura() {
        return factura;
    }

    public void setFactura(FacturaEntity factura) {
        this.factura = factura;
    }
    
    
    
    
}
