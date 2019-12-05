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
    
    private String banco;
    
    private int numTarjeta;
    
    private int csv;
    
    @Temporal(TemporalType.DATE)
    private Date fechaV;
    
    @PodamExclude
    @OneToOne
    private FacturaEntity factura;
    
    
    @PodamExclude
    @OneToOne
    private ClienteEntity cliente;
    
    public PagoTarjetaEntity(){
        //El constructor es vacío porque no se necesita inicializar parámetros
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

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
    
    
    
}
