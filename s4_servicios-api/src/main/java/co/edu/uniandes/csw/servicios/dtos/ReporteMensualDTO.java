/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.adapters.DateAdapter;
import co.edu.uniandes.csw.servicios.entities.ReporteMensualEntity;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Violeta Rodríguez
 */
public class ReporteMensualDTO 
{
   /**
     * Mes al que corresponde el reporte
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
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

    private Long id;


    public ReporteMensualDTO()
    {
        
    }
    
     public ReporteMensualDTO(ReporteMensualEntity entity)
    {
        this.egresos=entity.getEgresos();
        this.id=entity.getId();
        this.ingresos=entity.getIngresos();
        this.numSerivico= entity.getNumSerivico();
        this.mes= entity.getMes();
               
    }
     
     public ReporteMensualEntity toEntity()
     {
         ReporteMensualEntity entity = new ReporteMensualEntity();
         
         entity.setEgresos(this.egresos);
         entity.setIngresos(this.ingresos);
         entity.setId(this.id);
         entity.setMes(this.mes);
         entity.setNumSerivico(this.numSerivico);
         
         return entity;
     }
    
    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    public int getNumSerivico() {
        return numSerivico;
    }

    public void setNumSerivico(int numSerivico) {
        this.numSerivico = numSerivico;
    }

    public double getEgresos() {
        return egresos;
    }

    public void setEgresos(double egresos) {
        this.egresos = egresos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
     
    
    
    
    
}
