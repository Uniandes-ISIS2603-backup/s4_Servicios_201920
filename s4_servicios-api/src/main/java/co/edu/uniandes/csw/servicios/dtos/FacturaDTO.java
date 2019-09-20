/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.adapters.DateAdapter;
import co.edu.uniandes.csw.servicios.entities.FacturaEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Estudiante
 */
public class FacturaDTO implements Serializable {

    private Long id;

    private int duracion;

    private double precioMateriales;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fecha;

    private boolean pagada;

    private boolean primerPago;

    private PagoTarjetaDTO pagoTarjeta;

    public FacturaDTO() {

    }

    public FacturaDTO(FacturaEntity facturaEntity) {
        if (facturaEntity != null) {
            this.id = facturaEntity.getId();
            this.duracion = facturaEntity.getDuracion();
            this.precioMateriales = facturaEntity.getPrecioMateriales();
            this.fecha = facturaEntity.getFecha();
            this.pagada = facturaEntity.isPagada();
            this.primerPago = facturaEntity.isPrimerPago();
            if (facturaEntity.getTarjetaPago() != null) {
                this.pagoTarjeta = new PagoTarjetaDTO(facturaEntity.getTarjetaPago());
            } else {
                this.pagoTarjeta = null;
            }
        }
    }

    public FacturaEntity toEntity() {
        FacturaEntity facturaEntity = new FacturaEntity();
        facturaEntity.setId(this.getId());
        facturaEntity.setDuracion(this.getDuracion());
        facturaEntity.setPrecioMateriales(this.getPrecioMateriales());
        facturaEntity.setFecha(this.getFecha());
        facturaEntity.setPagada(this.isPagada());
        facturaEntity.setPrimerPago(this.isPrimerPago());
        return facturaEntity;
    }

    public int getDuracion() {
        return duracion;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getPrecioMateriales() {
        return precioMateriales;
    }

    public boolean isPagada() {
        return pagada;
    }

    public boolean isPrimerPago() {
        return primerPago;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public void setPrecioMateriales(double precioMateriales) {
        this.precioMateriales = precioMateriales;
    }

    public void setPrimerPago(boolean primerPago) {
        this.primerPago = primerPago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PagoTarjetaDTO getPagoTarjeta() {
        return pagoTarjeta;
    }

    public void setPagoTarjeta(PagoTarjetaDTO pagoTarjeta) {
        this.pagoTarjeta = pagoTarjeta;
    }

}
