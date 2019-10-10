/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.TrabajadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author c.otalora
 */
@Stateless
public class TrabajadorLogic {
    
    private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());

    @Inject
    private TrabajadorPersistence persistence;
    
    public TrabajadorEntity crearTrabajador (TrabajadorEntity trabajador) throws BusinessLogicException{
        
        if(persistence.find(trabajador.getId())!=null){
            throw new BusinessLogicException("Ya existe un trabajador con este id");
        }
        
        trabajador=persistence.create(trabajador);
        return trabajador;
    }
    
    public List<TrabajadorEntity> getTrabajadores() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los trabajadores");
        List<TrabajadorEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
    }
    
     public TrabajadorEntity getTrabajador(Long trabajadorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el trabajador con id = x", trabajadorId);
        TrabajadorEntity trabajadorEntity = persistence.find(trabajadorId);
        if (trabajadorEntity == null) {
            LOGGER.log(Level.SEVERE, "Trabajador con el id = x no existe", trabajadorId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el trabajador con id = x", trabajadorId);
        return trabajadorEntity;
    }
     
     public TrabajadorEntity updateTrabajador(Long trabajadorId, TrabajadorEntity trabajadorEntity) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el trabajador con id = {0}", trabajadorId);
        TrabajadorEntity newEntity = persistence.update(trabajadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el trabajador con id = {0}", trabajadorId);
        return newEntity;
    }
     
     public void deleteTrabajador(Long trabajadorId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el trabajador con id = {0}", trabajadorId);
        persistence.delete(trabajadorId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el trabajador con id = {0}", trabajadorId);
    }
}
