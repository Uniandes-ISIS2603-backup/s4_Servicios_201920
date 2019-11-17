/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.adapters.DateAdapter;
import co.edu.uniandes.csw.servicios.entities.ReporteMensualEntity;
import java.io.Serializable;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Violeta Rodríguez
 */
public class ReporteMensualDTO implements Serializable
{
   /**
     * Mes al que corresponde el reporte
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
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

    private Long id;


    public ReporteMensualDTO()
    {
        
    }
    
     public ReporteMensualDTO(ReporteMensualEntity entity)
    {
        if (entity !=null)
        {
        this.egresos=entity.getEgresos();
        this.id=entity.getId();
        this.ingresos=entity.getIngresos();
        this.numSerivico= entity.getNumSerivico();
        this.mes= entity.getMes();
        }
               
    }
     
     public ReporteMensualEntity toEntity()
     {
         ReporteMensualEntity entity = new ReporteMensualEntity();
         
         entity.setId(this.id);
         entity.setEgresos(this.egresos);
         entity.setIngresos(this.ingresos);
         entity.setMes(this.mes);
         entity.setNumSerivico(this.numSerivico);
         
         return entity;
     }
    
    public Double getIngresos() {
        return ingresos;
    }

    public void setIngresos(Double ingresos) {
        this.ingresos = ingresos;
    }

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    public Integer getNumSerivico() {
        return numSerivico;
    }

    public void setNumSerivico(Integer numSerivico) {
        this.numSerivico = numSerivico;
    }

    public Double getEgresos() {
        return egresos;
    }

    public void setEgresos(Double egresos) {
        this.egresos = egresos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
     
    
    
    
    
}
