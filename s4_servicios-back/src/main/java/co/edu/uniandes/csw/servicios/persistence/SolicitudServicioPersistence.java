/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.persistence;

import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ca.torrese
 */
@Stateless
public class SolicitudServicioPersistence {
    
    @PersistenceContext(unitName = "serviciosPU")
    protected EntityManager em;
    
    public SolicitudServicioEntity create (SolicitudServicioEntity solicitudServicioEntity)
    {
        em.persist(solicitudServicioEntity);
        return solicitudServicioEntity;
    }
    
    public SolicitudServicioEntity find (Long solicitudServicioId)
    {
        return em.find(SolicitudServicioEntity.class, solicitudServicioId);
    }
    
    public List<SolicitudServicioEntity> findAll()
    {
        TypedQuery<SolicitudServicioEntity> query = em.createQuery("select u from SolicitudServicioEntity u", SolicitudServicioEntity.class);
        return query.getResultList();
    }
    
    public SolicitudServicioEntity update(SolicitudServicioEntity solicitudServicioEntity)
    {
        return em.merge(solicitudServicioEntity);
                
    }
    
    public void delete(Long solicitudServicioId)
    {
        SolicitudServicioEntity solicitudServicioEntity = em.find(SolicitudServicioEntity.class, solicitudServicioId);
        em.remove(solicitudServicioEntity);
    }
}
