package co.edu.uniandes.csw.servicios.entities;

import co.edu.uniandes.csw.servicios.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamStrategyValue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Estudiante
 */
@Entity
public class ReporteMensualEntity extends BaseEntity implements Serializable
{
    /**
     * Mes al que corresponde el reporte
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date mes;
    
    /**
     * Ingresos que serecibieron en el mes
     */
    private Double ingresos;
    
    /**
     * Gasto que se generaron en el mes.
     */
    private Double egresos;
    
    /**
     * Número de servicios prestados en el mes.
     */
    private Integer numSerivico;
    @Id
    private Long id;


    public ReporteMensualEntity()
    {
        //Es un constructor vacio.
    }
    
    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    public Double getIngresos() {
        return ingresos;
    }

    public void setIngresos(Double ingresos) {
        this.ingresos = ingresos;
    }

    public Double getEgresos() {
        return egresos;
    }

    public void setEgresos(Double egresos) {
        this.egresos = egresos;
    }

    public Integer getNumSerivico() {
        return numSerivico;
    }

    public void setNumSerivico(Integer numSerivico) {
        this.numSerivico = numSerivico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
}
