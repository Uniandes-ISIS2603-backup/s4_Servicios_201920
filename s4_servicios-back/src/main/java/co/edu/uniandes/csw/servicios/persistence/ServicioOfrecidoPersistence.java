/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.persistence;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Violeta Rodríguez
 */
@Stateless
public class ServicioOfrecidoPersistence {

    @PersistenceContext(unitName = "serviciosPU")
    protected EntityManager em;

    public ServicioOfrecidoEntity create(ServicioOfrecidoEntity servicioOfrecido) {
        em.persist(servicioOfrecido);
        return servicioOfrecido;

    }

    public ServicioOfrecidoEntity find(Long servicioOfrecidoId) {
        return em.find(ServicioOfrecidoEntity.class, servicioOfrecidoId);
    }

    public List<ServicioOfrecidoEntity> findAll() {
        
        TypedQuery<ServicioOfrecidoEntity> query= em.createQuery("select u from ServicioOfrecidoEntity u", ServicioOfrecidoEntity.class);
        
        return query.getResultList();
    }
}
