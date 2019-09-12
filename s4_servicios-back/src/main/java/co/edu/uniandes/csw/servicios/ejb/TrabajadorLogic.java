/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.TrabajadorPersistence;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author c.otalora
 */
@Stateless
public class TrabajadorLogic {
    
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
    
     public TrabajadorEntity getAuthor(Long trabajadorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el trabajador con id = x", trabajadorId);
        TrabajadorEntity trabajadorEntity = persistence.find(trabajadorId);
        if (trabajadorEntity == null) {
            LOGGER.log(Level.SEVERE, "Trabajador con el id = x no existe", trabajadorId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el trabajador con id = x", trabajadorId);
        return trabajadorEntity;
    }
     
     public TrabajadorEntity updateAuthor(Long trabajadorId, TrabajadorEntity trabajadorEntity) throws BusinessLogicException {
        TrabajadorEntity trabajador = persistence.find(trabajadorId);
        if (trabajador != null) {
            throw new BusinessLogicException("Ya existe un trabajador con este id");
        }
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el autor con id = {0}", trabajadorId);
        TrabajadorEntity newAuthorEntity = persistence.update(trabajadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", trabajadorId);
        return newAuthorEntity;
    }
     
     public void deleteTrabajador(Long trabajadorId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el trabajador con id = {0}", trabajadorId);
        persistence.delete(trabajadorId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", trabajadorId);
    }
}
