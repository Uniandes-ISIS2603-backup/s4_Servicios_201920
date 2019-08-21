/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.persistence;

import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.*;

/**
 *
 * @author Estudiante
 */
@Stateless
public class ClientePersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ClientePersistence.class.getName());
    
    @PersistenceContext(unitName = "serviciosPU")
    protected EntityManager em;

    public ClienteEntity create(ClienteEntity pClienteEntity){
        LOGGER.log(Level.INFO, "Se está creando un Cliente nuevo");
        em.persist(pClienteEntity);
        LOGGER.log(Level.INFO, "Un cliente nuevo fue creado");
        return pClienteEntity;
    }
    
    public List<ClienteEntity> findAll(){
        LOGGER.log(Level.INFO, "Se están consultando todos los libros");
        Query q = em.createQuery("select u from ClienteEntity u");
        return q.getResultList();
    }
    
    public ClienteEntity find(ClienteEntity pClienteEntity){
        LOGGER.log(Level.INFO, "Se está buscando el cliente con id", pClienteEntity.getId());
        return em.find(ClienteEntity.class, pClienteEntity.getId());
    }
    
    public ClienteEntity update(ClienteEntity pClienteEntity){
        LOGGER.log(Level.INFO, "Se está actualizando el libro con id", pClienteEntity.getId());
        return em.merge(pClienteEntity);
    }
    
    public void delete(ClienteEntity pClienteEntity){
        LOGGER.log(Level.INFO, "Se quiere borrar el objeo con id", pClienteEntity.getId());
        ClienteEntity clienteABorrar = em.find(ClienteEntity.class, pClienteEntity.getId());
        em.remove(clienteABorrar);
    }
}
