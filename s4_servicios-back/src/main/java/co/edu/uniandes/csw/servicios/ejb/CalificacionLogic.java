/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.CalificacionPersistence;
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
     * @throws BusinessLogicException Si la editorial a persistir ya existe.
     */
    public CalificacionEntity createCalificacion(CalificacionEntity calificacion) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la calificacion");
        if(persistence.findAll().size() != 0)
        {
            throw new BusinessLogicException( "Ya existe una calificación para este servicio.");
        }
        if(calificacion.getPuntaje() < 1 || calificacion.getPuntaje() > 5)
        {
            throw new BusinessLogicException( "El puntaje no es válido. Debe ser un número entre 1 y 5.");
        }
       calificacion = persistence.create(calificacion);
       LOGGER.log(Level.INFO, "Termina proceso de creación de la calificacion");
        return calificacion;
    }
    
}
