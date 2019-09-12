/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.persistence;

import co.edu.uniandes.csw.servicios.entities.ReporteMensualEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Violeta Rodríguez
 */
public class ReporteMensualPersistence 
{
 @PersistenceContext(unitName = "serviciosPU")
    protected EntityManager em;

    public ReporteMensualEntity create(ReporteMensualEntity reporteMensual) {
        em.persist(reporteMensual);
        return reporteMensual;

    }

    public ReporteMensualEntity find(Long reporteMensualId) {
        return em.find(ReporteMensualEntity.class, reporteMensualId);
    }

    public List<ReporteMensualEntity> findAll() {
        
        TypedQuery<ReporteMensualEntity> query= em.createQuery("select u from ServicioOfrecidoEntity u", ReporteMensualEntity.class);
        
        return query.getResultList();
    }
   
    
    public ReporteMensualEntity update(ReporteMensualEntity reporteMensual)
    {
        return em.merge(reporteMensual);
    }
    
    public void delete(Long reporteMensualId){
        
        em.remove(em.find(ReporteMensualEntity.class, reporteMensualId));
        
    }

    
}
