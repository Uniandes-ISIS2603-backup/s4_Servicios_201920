/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.adapters.DateAdapter;
import co.edu.uniandes.csw.servicios.entities.PagoTarjetaEntity;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Estudiante
 */
public class PagoTarjetaDTO {

    private Long id;

    private int noTarjeta;

    private String banco;

    private int csv;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaV;

    public PagoTarjetaDTO() {

    }

    public PagoTarjetaDTO(PagoTarjetaEntity pagoTarjetaEntity) {
        if (pagoTarjetaEntity != null) {
            this.id = pagoTarjetaEntity.getId();
            this.noTarjeta = pagoTarjetaEntity.getNumTarjeta();
            this.banco = pagoTarjetaEntity.getBanco();
            this.csv = pagoTarjetaEntity.getCsv();
            this.fechaV = pagoTarjetaEntity.getFechaV();
        }
    }

   

    public PagoTarjetaEntity toEntity() {
        PagoTarjetaEntity pagoTarjetaEntity = new PagoTarjetaEntity();
        pagoTarjetaEntity.setId(this.getId());
        pagoTarjetaEntity.setNumTarjeta(this.getNoTarjeta());
        pagoTarjetaEntity.setBanco(this.getBanco());
        pagoTarjetaEntity.setCsv(this.getCsv());
        pagoTarjetaEntity.setFechaV(this.getFechaV());
        return pagoTarjetaEntity;
    }

    public String getBanco() {
        return banco;
    }

    public int getCsv() {
        return csv;
    }

    public Date getFechaV() {
        return fechaV;
    }

    public Long getId() {
        return id;
    }

    public int getNoTarjeta() {
        return noTarjeta;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public void setCsv(int csv) {
        this.csv = csv;
    }

    public void setFechaV(Date fechaV) {
        this.fechaV = fechaV;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNoTarjeta(int noTarjeta) {
        this.noTarjeta = noTarjeta;
    }

}
