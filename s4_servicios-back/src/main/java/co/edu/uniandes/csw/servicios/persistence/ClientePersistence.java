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
    
    /**
     * Logger que permite llevar un registro de las operaciones que se hacen en la base de datos
     */
    private static final Logger LOGGER = Logger.getLogger(ClientePersistence.class.getName());
    
    @PersistenceContext(unitName = "serviciosPU")
    /**
     * El Entity Manager de la clase Cliente
     */
    protected EntityManager em;

    /**
     * Metodo que se encarga de crear un nuevo cliente en la base de datos
     * Corresponde a la C de CRUD en los requisitos de bases de datos
     * @param pClienteEntity - El cliente que se va a agregar
     * @return - El cliente que se agregó
     */
    public ClienteEntity create(ClienteEntity pClienteEntity){
        LOGGER.log(Level.INFO, "Se está creando un Cliente nuevo");
        em.persist(pClienteEntity);
        LOGGER.log(Level.INFO, "Un cliente nuevo fue creado");
        return pClienteEntity;
    }
    
    /**
     * Metodo que devuelve una lista con todos los clientes que hay en la base de datos
     * Hace un "Read" de toda la base de datos 
     * @return - una lista con todos los clientes
     */
    public List<ClienteEntity> findAll(){
        LOGGER.log(Level.INFO, "Se están consultando todos los clientes");
        Query q = em.createQuery("select u from ClienteEntity u");
        return q.getResultList();
    }
    
    /**
     * Método que devuele un cliente específico de la base de datos
     * Corresponde a la R de CRUD en los requisitos de bases de datos
     * @param clienteId - El cliente que se quiere buscar en la base de datos
     * @return - El cliente encontrado
     */
    public ClienteEntity find(long clienteId){
        LOGGER.log(Level.INFO, "Se está buscando el cliente con id", clienteId);
        return em.find(ClienteEntity.class, clienteId);
    }
    
    /**
     * Método que se encarga de actualizar los datos de un cliente en la base de datos
     * Corresponde a la U de CRUD en los requisitos de bases de datos
     * @param pClienteEntity - El cliente del que se quieren actualizar los datos. 
     * @return - el cliente con los datos ya actualizados. 
     */
    public ClienteEntity update(ClienteEntity pClienteEntity){
        LOGGER.log(Level.INFO, "Se está actualizando el libro con id", pClienteEntity.getId());
        return em.merge(pClienteEntity);
    }
    
    /**
     * Método que borra uno de los clientes de la base de datos 
     * Corresponde a la D de CRUD en los requisitos de bases de datos
     * @param pClienteEntity - el cliente que se desea eliminar de la base de datos
     */
    public void delete(ClienteEntity pClienteEntity){
        LOGGER.log(Level.INFO, "Se quiere borrar el objeo con id", pClienteEntity.getId());
        ClienteEntity clienteABorrar = em.find(ClienteEntity.class, pClienteEntity.getId());
        em.remove(clienteABorrar);
    }
}
