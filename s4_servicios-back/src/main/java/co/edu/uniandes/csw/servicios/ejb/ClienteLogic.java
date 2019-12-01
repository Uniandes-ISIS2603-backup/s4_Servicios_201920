/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.ClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
@Stateless
public class ClienteLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());
    
    @Inject
    private ClientePersistence persistence;
    
    /**
     * Se encarga de crear un Cliente en la base de datos.
     *
     * @param clienteEntity Objeto de ClienteEntity con los datos nuevos
     * @return Objeto de ClienteEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    public ClienteEntity createCliente (ClienteEntity clienteEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cliente");
        String error = "No se puede crear un cliente con id";
        if(clienteEntity.getNombre() == null){
            throw new BusinessLogicException(error + clienteEntity.getId() + " porque el nombre es null");
        }
        else if(clienteEntity.getNombre().equals("")){
            throw new BusinessLogicException(error + clienteEntity.getId() + "porque el nombre es vacío");
        }
        else if(clienteEntity.getMail() == null){
            throw new BusinessLogicException(error + clienteEntity.getId() + " porque el correo es null");
        }
        else if(!clienteEntity.getMail().matches("^(.+)@(.+)$")){
            throw new BusinessLogicException(error + clienteEntity.getId() + " porque el correo es inválido");
        }
        else if(clienteEntity.getUsuario() == null){
            throw new BusinessLogicException(error + clienteEntity.getId() + " porque el usuario es null");
        }
        else if( clienteEntity.getUsuario().equals("")){
            throw new BusinessLogicException(error + clienteEntity.getId() + " porque el usuario es vacío");
        }
        else if (persistence.findByUsuario(clienteEntity.getUsuario()) != null)
        {
            throw new BusinessLogicException(error + clienteEntity.getId() + " porque ya existe un cliente con ese usuario");
        }
        else if(clienteEntity.getTelefono() == null){
            throw new BusinessLogicException(error + clienteEntity.getId() + " porque el número telefónico es null");
        }
        else if(clienteEntity.getTelefono() < 0){
            throw new BusinessLogicException(error + clienteEntity.getId() + " porque el número telefónico es inválido");
        }
        
            clienteEntity = persistence.create(clienteEntity);
        
        LOGGER.log(Level.INFO, "Termina proceso de creación del cliente");
        return clienteEntity;
    }
    
    /**
     * Obtiene la lista de los registros de Cliente.
     *
     * @return Colección de objetos de ClienteEntity.
     */
    public List<ClienteEntity> getClientes() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los clientes");
        List<ClienteEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los clientes");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Cliente a partir de su ID.
     *
     * @param clienteId Identificador de la instancia a consultar
     * @throws BusinessLogicException si el cliente tiene servicios asociados.
     * @return Instancia de ClienteEntity con los datos del cliente consultado.
     */
    public ClienteEntity getCliente(Long clienteId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente con id = {0}", clienteId);
        ClienteEntity clienteEntity = persistence.find(clienteId);
        if (clienteEntity == null) {
            LOGGER.log(Level.SEVERE, "El cliente con el id = {0} no existe", clienteId);
            throw new BusinessLogicException("El cliente con el id =" + clienteId + "no existe");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente con id = {0}", clienteId);
        return clienteEntity;
    }

    /**
     * Actualiza la información de una instancia de Cliente.
     *
     * @param clienteId Identificador de la instancia a actualizar
     * @param clienteEntity Instancia de clienteEntity con los nuevos datos.
     * @return Instancia de clienteEntity con los datos actualizados.
     */
    public ClienteEntity updateCliente(Long clienteId, ClienteEntity clienteEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cliente con id = {0}", clienteId);
        ClienteEntity newAuthorEntity = persistence.update(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", clienteId);
        return newAuthorEntity;
    }

    /**
     * Elimina una instancia de Cliente de la base de datos.
     *
     * @param clienteId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el cliente tiene servicios asociados.
     */
    public void deleteCliente(Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cliente con id = {0}", clienteId);
        persistence.delete(clienteId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", clienteId);
    }
    
    /**
     * Obtiene los datos de una instancia de Cliente a partir de su Usuario y Contrasena.
     *
     * @param clienteUsuario Usuario de la instancia a consultar
     * @param clienteContrasena Contrasena de la instancia a consultar
     * @throws BusinessLogicException 
     * @return Instancia de ClienteEntity con los datos del cliente consultado.
     */
    public ClienteEntity getClientePorUsuario(String clienteUsuario, String clienteContrasena) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente con usuario = {0}", clienteUsuario);
        ClienteEntity clienteEntity = persistence.findByUsuario(clienteUsuario);
        if (clienteEntity == null) {
            LOGGER.log(Level.SEVERE, "El cliente con el usuario = {0} no existe", clienteUsuario);
            throw new BusinessLogicException("El cliente con el usuario = " + clienteUsuario + " no existe");
        }
        else if(!clienteEntity.getContrasena().equals(clienteContrasena))
        {
            LOGGER.log(Level.SEVERE, "La contrasena no coincide");
            throw new BusinessLogicException("El usuario o contrasena no son correctos. Por favor intente de nuevo.");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente con usuario = {0}", clienteUsuario);
        return clienteEntity;
    }
}
