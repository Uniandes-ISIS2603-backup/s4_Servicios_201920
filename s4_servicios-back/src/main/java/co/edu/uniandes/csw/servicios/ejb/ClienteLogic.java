/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.ClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
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
//        boolean valido = true;
//        if(clienteEntity.getNombre().equals(null) 
//                ||!clienteEntity.getMail().matches("^(.+)@(.+)$")
//                /*|| Integer.toString(clienteEntity.getTelefono()).matches("^\+(?:[0-9] ?){6,14}[0-9]$")*/
//                ){
//            valido = false;
//            throw new BusinessLogicException("No se puede borrar el autor con id = " + clienteEntity.getId() + " porque tiene books asociados");
//        }
        
            LOGGER.log(Level.INFO, "Inicia proceso de creación del cliente");
            ClienteEntity newClienteEntity = persistence.create(clienteEntity);
            LOGGER.log(Level.INFO, "Termina proceso de creación del cliente");
            return newClienteEntity;
        
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
     * @return Instancia de ClienteEntity con los datos del cliente consultado.
     */
    public ClienteEntity getCliente(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente con id = {0}", clienteId);
        ClienteEntity clienteEntity = persistence.find(clienteId);
        if (clienteEntity == null) {
            LOGGER.log(Level.SEVERE, "El cliente con el id = {0} no existe", clienteId);
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
        List<SolicitudServicioEntity> servicios = getCliente(clienteId).getServicios();
//        if (servicios.size() != 0) {
//            throw new BusinessLogicException("No se puede borrar el cliente con id = " + clienteId + " porque tiene servicios asociados");
//        }
        persistence.delete(getCliente(clienteId));
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", clienteId);
    }
}
