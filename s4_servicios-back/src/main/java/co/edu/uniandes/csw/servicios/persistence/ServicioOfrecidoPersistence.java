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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
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
        
        Query query= em.createQuery("select u from ServicioOfrecidoEntity u");
        
        return query.getResultList();
    }
    
    /**
     * Busca en la base de datos el servicio ofrecido conle nombre dado
     * @param nombre nombre del servicio a buscar
     * @return retona la entidad de el servicio ofrecido con le nombre dado. Si no encuentra el servicio, retorna null. 
     */
    public ServicioOfrecidoEntity findByName(String nombre)
    {
       //Creamos un query para buscar el servicio con el nombre. Aquí voy a gaurdar los resultados de la búsqueda.
        TypedQuery query= em.createQuery("Select e from ServicioOfrecidoEntity e where e.nombre = :nombre", ServicioOfrecidoEntity.class);
        
        query = query.setParameter("nombre", nombre );
        
        ServicioOfrecidoEntity sameName = (ServicioOfrecidoEntity) query.getSingleResult();
        
        return sameName;      
    }
    
    public List<ServicioOfrecidoEntity>  findByType(String tipo)
    {
       //Creamos un query para buscar el servicio con el nombre. Aquí voy a gaurdar los resultados de la búsqueda.
        TypedQuery query= em.createQuery("Select e From ServicioOfrecidoEntity e where e.tipo = :tipo", ServicioOfrecidoEntity.class);
        
        query = query.setParameter("tipo", tipo );
        
        return query.getResultList();      
    }
    
    public ServicioOfrecidoEntity update(ServicioOfrecidoEntity servicioOfrecido)
    {
        return em.merge(servicioOfrecido);
    }
    
    public void delete(Long servicioOfrecidoId){
        
        em.remove(em.find(ServicioOfrecidoEntity.class, servicioOfrecidoId));
        
    }
    
   
}
