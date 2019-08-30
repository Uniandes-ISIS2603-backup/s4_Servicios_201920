/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.persistence;

import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author c.otalora
 */

@Stateless
public class TrabajadorPersistence {
    
    @PersistenceContext(unitName = "serviciosPU")
    protected EntityManager em;
    
    public TrabajadorEntity create(TrabajadorEntity trabajadorEntity){
        
     em.persist(trabajadorEntity);
     return trabajadorEntity;
    }
    
    public TrabajadorEntity find(Long trabajadorId){
        return em.find(TrabajadorEntity.class, trabajadorId);
    }
    
    public List<TrabajadorEntity> findAll(){
        TypedQuery query = em.createQuery("select u from TrabajadorEntity", TrabajadorEntity.class);
        return query.getResultList();
    }

    public void delete(Long tID){
        TrabajadorEntity trabajadorEntity = em.find(TrabajadorEntity.class, tID);
        em.remove(trabajadorEntity);
    }

    public TrabajadorEntity update(TrabajadorEntity t) {
        return em.merge(t);
    }
}
