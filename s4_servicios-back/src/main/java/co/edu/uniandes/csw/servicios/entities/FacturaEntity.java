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
public class FacturaEntity extends BaseEntity{
    
    private int duracion;
    
    private double precioMateriales;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    private boolean pagada;
    
    private boolean primerPago;
    
    @PodamExclude
    @OneToOne(cascade = CascadeType.ALL)
    private SolicitudServicioEntity solicitud;
    
    @PodamExclude
    @OneToOne(cascade = CascadeType.ALL)
    private PagoTarjetaEntity tarjetaPago;
    
    public FacturaEntity(){
        // No est{a el constructor porque no es necesario.
    }
    
    public boolean equals(FacturaEntity aComparar){
        boolean respuesta = false;
        if(this.solicitud == aComparar.solicitud){
            respuesta = true;
        }
        return respuesta;
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

    public boolean isPagada() {
        return pagada;
    }
    
    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public boolean isPrimerPago() {
        return primerPago;
    }

    public void setPrimerPago(boolean primerPago) {
        this.primerPago = primerPago;
    }

    public SolicitudServicioEntity getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudServicioEntity solicitud) {
        this.solicitud = solicitud;
    }

    public PagoTarjetaEntity getTarjetaPago() {
        return tarjetaPago;
    }

    public void setTarjetaPago(PagoTarjetaEntity tarjetaPago) {
        this.tarjetaPago = tarjetaPago;
    }
    
    
    
    
    
}
