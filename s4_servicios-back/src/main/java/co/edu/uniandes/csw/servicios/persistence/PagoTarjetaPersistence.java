/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.persistence;
import co.edu.uniandes.csw.servicios.entities.PagoTarjetaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Estudiante
 */
@Stateless
public class PagoTarjetaPersistence {
        
        @PersistenceContext(unitName = "serviciosPU")
    protected EntityManager em;
    
    public PagoTarjetaEntity create(PagoTarjetaEntity pagoTarjetaEntity){
        em.persist(pagoTarjetaEntity);
        return pagoTarjetaEntity;
    }
    
    public PagoTarjetaEntity find(Long pagoTarjetaId){
        return em.find(PagoTarjetaEntity.class, pagoTarjetaId);
    }
    
    public List<PagoTarjetaEntity> findAll(){
        TypedQuery<PagoTarjetaEntity> query = em.createQuery("select u from PagoTarjetaEntity u", PagoTarjetaEntity.class);
        return query.getResultList();
    }
    
    public PagoTarjetaEntity update(PagoTarjetaEntity pagoTarjetaEntity){
        return em.merge(pagoTarjetaEntity);
    }
    
    public void remove(Long pagoTarjetaId){
        PagoTarjetaEntity x = em.find(PagoTarjetaEntity.class, pagoTarjetaId);
        em.remove(x);
}
}
