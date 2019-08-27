/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.persistence;

/**
 *
 * @author ca.torrese
 */

import co.edu.uniandes.csw.servicios.entities.CalificacionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CalificacionPersistence {
    
    @PersistenceContext( unitName = "serviciosPU")
    protected EntityManager em;
    
    public CalificacionEntity create(CalificacionEntity calificacionEntity)
    {
        em.persist(calificacionEntity);
        return calificacionEntity;
    }
    
    public CalificacionEntity find(Long calificacionId)
    {
        return em.find(CalificacionEntity.class, calificacionId);
    }
    
    public List<CalificacionEntity> findAll()
    {
       TypedQuery<CalificacionEntity> query = em.createQuery("select u from CalificacionEntity u", CalificacionEntity.class);
       return query.getResultList();
    }
    
    public CalificacionEntity update(CalificacionEntity calificacionEntity)
    {
        return em.merge(calificacionEntity);
    }
    
    public void delete(Long calificacionId)
    {
        CalificacionEntity calificacionEntity = em.find(CalificacionEntity.class, calificacionId);
        em.remove(calificacionEntity);
    }
}
