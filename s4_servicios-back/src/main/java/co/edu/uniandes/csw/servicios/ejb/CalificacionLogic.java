/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.CalificacionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Calificacion.
 *
 * @author ca.torrese
 */

@Stateless
public class CalificacionLogic {
    
    private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());

    
    @Inject
    private CalificacionPersistence persistence;
    
     /**
     * Crea una calificacion en la persistencia.
     *
     * @param CalificacionEntity La entidad que representa la calificacion a
     * persistir.
     * @return La entidad de la calificacion luego de persistirla.
     * @throws BusinessLogicException Si la calificacion a persistir ya existe.
     */
    public CalificacionEntity createCalificacion(CalificacionEntity calificacion) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la calificacion");
        
        if(calificacion.getPuntaje() < 1 || calificacion.getPuntaje() > 5)
        {
            throw new BusinessLogicException( "El puntaje no es válido. Debe ser un número entre 1 y 5.");
        }
       calificacion = persistence.create(calificacion);
       LOGGER.log(Level.INFO, "Termina proceso de creación de la calificacion");
        return calificacion;
    }
    
     /**
     *
     * Obtener todas las calificaciones existentes en la base de datos.
     *
     * @return una lista de calificaciones.
     */
    public List<CalificacionEntity> getCalificaciones() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las calificaciones");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<CalificacionEntity> calificaciones = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las calificaciones");
        return calificaciones;
    }
    
     /**
     *
     * Obtener una calificacion por medio de su id.
     *
     * @param calificacionId: id de la calificacion para ser buscada.
     * @return la calificacion solicitada por medio de su id.
     */
    public CalificacionEntity getCalificacion(Long calificacionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0}", calificacionId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
       CalificacionEntity calificacionEntity = persistence.find(calificacionId);
        if (calificacionEntity == null) {
            LOGGER.log(Level.SEVERE, "La calificacion con el id = {0} no existe", calificacionId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la calificacion con id = {0}", calificacionId);
        return calificacionEntity;
    }
    
    /**
     *
     * Actualizar una calificacion.
     *
     * @param calificacionId: id de la calificacion para buscarla en la base de
     * datos.
     * @param calificacionEntity: calificacion con los cambios para ser actualizada,
     * por ejemplo el puntaje.
     * @return la calificacion con los cambios actualizados en la base de datos.
     */
    public CalificacionEntity updateCalificacion(Long calificacionId, CalificacionEntity calificacionEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la calificacion con id = {0}", calificacionId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        CalificacionEntity newEntity = persistence.update(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la calificacion con id = {0}", calificacionEntity.getId());
        return newEntity;
    }
    
    /**
     * Borrar un calificacion
     *
     * @param calificacionsId: id de la calificacion a borrar

     */
    public void deleteCalificacion(Long calificacionsId)  {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la calificacion con id = {0}", calificacionsId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
       
        persistence.delete(calificacionsId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la calificacion con id = {0}", calificacionsId);
    }
}
