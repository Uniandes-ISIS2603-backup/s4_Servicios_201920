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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    
    
    /**
     * El Entity Manager de la clase Cliente
     */
     @PersistenceContext(unitName = "serviciosPU")
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
        TypedQuery q = em.createQuery("select u from ClienteEntity u", ClienteEntity.class);
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
     * @param tID
     */
    public void delete(Long tID){
        ClienteEntity clienteEntity = em.find(ClienteEntity.class, tID);
        em.remove(clienteEntity);
    }
    
    /**
     * Busca si hay algun cliente con el usuario que se envía de argumento
     *
     * @param usuario: Usuario del cliente que se está buscando
     * @return null si no existe ningun cliente con el usuario del argumento. Si
     * existe alguno devuelve el primero.
     */
     public ClienteEntity findByUsuario(String usuario) {
        LOGGER.log(Level.INFO, "Consultando clientes por usuario ", usuario);
        TypedQuery query = em.createQuery("Select e From ClienteEntity e where e.usuario = :usuario", ClienteEntity.class);
        query = query.setParameter("usuario", usuario);
        List<ClienteEntity> sameUsuario = query.getResultList();
        ClienteEntity result;
        if (sameUsuario == null) {
            result = null;
        } else if (sameUsuario.isEmpty()) {
            result = null;
        } else {
            result = sameUsuario.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar libros por usuario ", usuario);
        return result;
    }
}
