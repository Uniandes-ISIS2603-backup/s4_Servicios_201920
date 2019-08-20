/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.persistence;

import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ca.torrese
 */
@Stateless
public class SolicitudServicioPersistence {
    
    @PersistenceContext(unitName = "ServiciosPU")
    protected EntityManager em;
    
    public SolicitudServicioEntity create (SolicitudServicioEntity solicitudServicioEntity)
    {
        em.persist(solicitudServicioEntity);
        return solicitudServicioEntity;
    }
    
    
}
