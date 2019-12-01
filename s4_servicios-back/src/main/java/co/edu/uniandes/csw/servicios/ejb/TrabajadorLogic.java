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
        
         List<TrabajadorEntity> workers = persistence.findAll();
         
         for (TrabajadorEntity i: workers)
         {
            if(trabajador.getUsuario().equals(i.getUsuario())  || trabajador.getCorreo().equals(i.getCorreo()))
            {
            throw new BusinessLogicException("Ya existe un trabajador con este correo o con este usuario");
            } 
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
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el trabajador con id = {0}", trabajadorId);
        TrabajadorEntity trabajadorEntity = persistence.find(trabajadorId);
        if (trabajadorEntity == null) {
            LOGGER.log(Level.SEVERE, "Trabajador con el id = {0} no existe", trabajadorId);
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
     
     /**
     * Obtiene los datos de una instancia de Trabajador a partir de su Usuario y Contrasena.
     *
     * @param trabajadorUsuario Usuario de la instancia a consultar
     * @param trabajadorContrasena Contrasena de la instancia a consultar
     * @throws BusinessLogicException
     * @return Instancia de TrabajadorEntity con los datos del Trabajador consultado.
     */
    public TrabajadorEntity getTrabajadorPorUsuario(String trabajadorUsuario, String trabajadorContrasena) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el trabajador con usuario = {0}", trabajadorUsuario);
        TrabajadorEntity trabajadorEntity = persistence.findByUsuario(trabajadorUsuario);
        if (trabajadorEntity == null) {
            LOGGER.log(Level.SEVERE, "El trabajador con el usuario = {0} no existe", trabajadorUsuario);
            throw new BusinessLogicException("El trabajador con el usuario = " + trabajadorUsuario + " no existe");
        }
        else if(!trabajadorEntity.getContrasena().equals(trabajadorContrasena))
        {
            LOGGER.log(Level.SEVERE, "La contrasena no coincide");
            throw new BusinessLogicException("El usuario o contrasena no son correctos. Por favor intente de nuevo.");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el trabajador con usuario = {0}", trabajadorUsuario);
        return trabajadorEntity;
    }
}
