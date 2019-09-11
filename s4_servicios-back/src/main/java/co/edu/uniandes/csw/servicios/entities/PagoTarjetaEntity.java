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
    
    private String banco; 
    
    @PodamExclude
    @OneToOne(cascade = CascadeType.ALL)
    private FacturaEntity factura;
    
    public PagoTarjetaEntity(){
        
    }

    public int getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(int numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public FacturaEntity getFactura() {
        return factura;
    }

    public void setFactura(FacturaEntity factura) {
        this.factura = factura;
    }
    
    
    
    
}
