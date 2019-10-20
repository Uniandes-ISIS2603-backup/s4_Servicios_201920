/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.ServicioOfrecidoPersistence;
import co.edu.uniandes.csw.servicios.persistence.SolicitudServicioPersistence;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ca.torrese
 */
@Stateless
public class SolicitudServicioServicioOfrecidosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(SolicitudServicioServicioOfrecidosLogic.class.getName());

    @Inject
    private SolicitudServicioPersistence solicitudPersistence;

    @Inject
    private ServicioOfrecidoPersistence servicioPersistence;

    /**
     * Asocia un ServicioOfrecido existente a un SolicitudServicio
     *
     * @param solicitudId Identificador de la instancia de SolicitudServicio
     * @param servicioId Identificador de la instancia de ServicioOfrecido
     * @return Instancia de SolicitudServicioEntity a la que fue asociada el ServicioOfrecido
     */
    public SolicitudServicioEntity addServicio(Long solicitudId, Long servicioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un servicio a la solicitud con id = {0}",solicitudId);
        SolicitudServicioEntity solicitudEntity = solicitudPersistence.find(solicitudId);
        ServicioOfrecidoEntity servicioEntity = servicioPersistence.find(servicioId);
        solicitudEntity.getServicios().add(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un servicio a la solicitud con id = {0}", solicitudId);
        return solicitudPersistence.find(solicitudId);
    }
    
    /**
     * Obtiene una colección de instancias de ServicioOfrecidoEntity asociadas a una
     * instancia de SolicitudServicio
     *
     * @param solicitudId Identificador de la instancia de SolicitudServicio
     * @return Colección de instancias de ServicioOfrecidoEntity asociadas a la instancia de
     * SolicitudServicio
     */
    public Collection<ServicioOfrecidoEntity> getServicios(Long solicitudId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los servicios de la solicitud con id = {0}", solicitudId);
        return solicitudPersistence.find(solicitudId).getServicios();
    }
    
    /**
     * Obtiene una instancia de ServicioOfrecidoEntity asociada a una instancia de SolicitudServicio
     *
     * @param solicitudId Identificador de la instancia de SolicitudServicio
     * @param servicioId Identificador de la instancia de ServicioOfrecido
     * @return La entidad de servicio de la solicitud
     * @throws BusinessLogicException Si el servicio no está asociado a la solicitud
     */
    public ServicioOfrecidoEntity getServicio(Long solicitudId, Long servicioId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el servicio con id = {0} de la solicitud con id = " + solicitudId, servicioId);
        Collection<ServicioOfrecidoEntity> servicios = solicitudPersistence.find(solicitudId).getServicios();
        ServicioOfrecidoEntity servicioEntity = servicioPersistence.find(servicioId);
        boolean contiene = servicios.contains(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el servicio con id = {0} de la solicitud con id = " + solicitudId, servicioId);
        if (contiene == true) {
            return servicioEntity;
        }
        throw new BusinessLogicException("El servicio no está asociado a la solicitud");
    }
    
    /**
     * Remplaza las instancias de ServicioOfrecido asociadas a una instancia de SolicitudServicio
     *
     * @param solicitudId Identificador de la instancia de SolicitudServicio
     * @param servicios Colección de instancias de ServicioOfrecidoEntity a asociar a instancia
     * de SolicitudServicio
     * @return Nueva colección de ServicioOfrecidoEntity asociada a la instancia de SolicitudServicio
     */
    public Collection<ServicioOfrecidoEntity> replaceServicios(Long solicitudId, Collection<ServicioOfrecidoEntity> servicios) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los servicios asocidos a la solicitud con id = {0}", solicitudId);
        SolicitudServicioEntity solicitudEntity = solicitudPersistence.find(solicitudId);
        solicitudEntity.setServicios(servicios);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los servicios asocidos a la solicitud con id = {0}", solicitudId);
        return solicitudEntity.getServicios();
    }
    
     /**
     * Desasocia un ServicioOfrecido existente de un SolicitudServicio existente
     *
     * @param solicitudId Identificador de la instancia de SolicitudServicio
     * @param servicioId Identificador de la instancia de ServicioOfrecido
     */
    public void removeServicio(Long solicitudId, Long servicioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un servicio de la solicitud con id = {0}", solicitudId);
        SolicitudServicioEntity solicitudEntity = solicitudPersistence.find(solicitudId);
        ServicioOfrecidoEntity servicioEntity = servicioPersistence.find(servicioId);
        solicitudEntity.getServicios().remove(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un servicio de la solicitud con id = {0}",solicitudId);
    }
    
}
