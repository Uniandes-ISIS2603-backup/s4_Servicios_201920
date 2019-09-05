/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import co.edu.uniandes.csw.servicios.persistence.ClientePersistence;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
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
     */
    public ClienteEntity createCliente (ClienteEntity clienteEntity) {
        boolean valido = true;
        if(clienteEntity.getNombre().equals(null)){
            valido = false;
        }
        if(valido = true)
        {
            LOGGER.log(Level.INFO, "Inicia proceso de creación del autor");
            ClienteEntity newAuthorEntity = persistence.create(clienteEntity);
            LOGGER.log(Level.INFO, "Termina proceso de creación del autor");
            return newAuthorEntity;
        }
        else
            return null;
    }
}
