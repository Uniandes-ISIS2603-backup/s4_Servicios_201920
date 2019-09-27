/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.SolicitudServicioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * SolicitudServicio.
 * 
 * @author ca.torrese
 */
@Stateless
public class SolicitudServicioLogic {
    
     private static final Logger LOGGER = Logger.getLogger(SolicitudServicioLogic.class.getName());

    
    @Inject
    private SolicitudServicioPersistence persistence;
    
     /**
     * Crea una solicitud en la persistencia.
     *
     * @param solicitud La entidad que representa la solicitud a
     * persistir.
     * @return La entidad de la solicitud luego de persistirla.
     * @throws BusinessLogicException Si la solicitud a persistir ya existe.
     */
    public SolicitudServicioEntity createSolicitudServicio(SolicitudServicioEntity solicitud) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la solicitud");
        
       
       solicitud = persistence.create(solicitud);
       LOGGER.log(Level.INFO, "Termina proceso de creación de la solicitud");
        return solicitud;
    }
    
    
     /**
     *
     * Obtener todas las solicitudes existentes en la base de datos.
     *
     * @return una lista de solicitudes.
     */
    public List<SolicitudServicioEntity> getSolicitudServicios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las solicitudes");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<SolicitudServicioEntity> solicitudes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las solicitudes");
        return solicitudes;
    }
    
     /**
     *
     * Obtener una solicitud por medio de su id.
     *
     * @param solicitudId: id de la solicitud para ser buscada.
     * @return la solicitud solicitada por medio de su id.
     */
    public SolicitudServicioEntity getSolicitudServicio(Long solicitudId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la solicitud con id = {0}", solicitudId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
      SolicitudServicioEntity solicitudEntity = persistence.find(solicitudId);
        if (solicitudEntity == null) {
            LOGGER.log(Level.SEVERE, "La solicitud con el id = {0} no existe", solicitudId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la solicitud con id = {0}", solicitudId);
        return solicitudEntity;
    }
    
    /**
     *
     * Actualizar una solicitud.
     *
     * @param  solicitudId: id de la solicitud para buscarla en la base de
     * datos.
     * @param solicitudEntity: solicitud con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la solicitud con los cambios actualizados en la base de datos.
     */
    public SolicitudServicioEntity updateSolicitudServicio(Long solicitudId,SolicitudServicioEntity solicitudEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la solicitud con id = {0}", solicitudId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        SolicitudServicioEntity newEntity = persistence.update(solicitudEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la solicitud con id = {0}", solicitudEntity.getId());
        return newEntity;
    }
    
     /**
     * Borrar un solicitud.
     *
     * @param solicitudId: id de la solicitud a borrar

     */
    public void deleteSolicitudServicio(Long solicitudId)  {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la solicitud con id = {0}", solicitudId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
       
        persistence.delete(solicitudId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la solicitud con id = {0}", solicitudId);
    }
    
}
