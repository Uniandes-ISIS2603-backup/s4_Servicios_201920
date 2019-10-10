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
import co.edu.uniandes.csw.servicios.persistence.SolicitudServicioPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Cliente y Servicio.
 *
 * @author ISIS2603
 */
@Stateless
public class ClienteSolicitudServicioLogic {

    private static final Logger LOGGER = Logger.getLogger(ClienteSolicitudServicioLogic.class.getName());

    @Inject
    private SolicitudServicioPersistence solicitudServicioPersistence;

    @Inject
    private ClientePersistence clientePersistence;

    
    public SolicitudServicioEntity addSolicitudServicio(Long clientesId, Long solicitudServiciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una solicitud de servicio al cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        SolicitudServicioEntity solicitudServicioEntity = solicitudServicioPersistence.find(solicitudServiciosId);
        solicitudServicioEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una solicitud de servicio a un cliente con id = {0}", clientesId);
        return solicitudServicioPersistence.find(solicitudServiciosId);
    }


    public List<SolicitudServicioEntity> getServicios(Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los servicios del cliente con id = {0}", clientesId);
        return clientePersistence.find(clientesId).getServicios();
    }

    
    public SolicitudServicioEntity getSolicitudServicio(Long clientesId, Long solicitudServiciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el servicio con id = {0} del cliente con id = " + clientesId, solicitudServiciosId);
        List<SolicitudServicioEntity> servicios = clientePersistence.find(clientesId).getServicios();
        SolicitudServicioEntity servicioEntity = solicitudServicioPersistence.find(solicitudServiciosId);
        int index = servicios.indexOf(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el servicio con id = {0} del cliente con id = " + clientesId, solicitudServiciosId);
        if (index >= 0) {
            return servicios.get(index);
        }
        throw new BusinessLogicException("El servicio no está asociado al cliente");
    }

    public List<SolicitudServicioEntity> replaceSolicitudServicios(Long clientesId, List<SolicitudServicioEntity> servicios) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los servicios asocidos al clente con id = {0}", clientesId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        List<SolicitudServicioEntity> serviciosList = solicitudServicioPersistence.findAll();
        clienteEntity.setServicios(servicios);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los servicios asocidos al cliente con id = {0}", clientesId);
        return clienteEntity.getServicios();
    }

}