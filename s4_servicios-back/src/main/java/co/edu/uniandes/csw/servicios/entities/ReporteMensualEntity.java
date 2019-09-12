package co.edu.uniandes.csw.servicios.entities;

import co.edu.uniandes.csw.servicios.podam.DateStrategy;
import java.util.Date;
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
public class ReporteMensualEntity 
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
    private double ingresos;
    
    /**
     * Gasto que se generaron en el mes.
     */
    private double egresos;
    
    /**
     * Número de servicios prestados en el mes.
     */
    private int numSerivico;


    public ReporteMensualEntity()
    {
        
    }
    
    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public double getEgresos() {
        return egresos;
    }

    public void setEgresos(double egresos) {
        this.egresos = egresos;
    }

    public int getNumSerivico() {
        return numSerivico;
    }

    public void setNumSerivico(int numSerivico) {
        this.numSerivico = numSerivico;
    }

    
    
}
