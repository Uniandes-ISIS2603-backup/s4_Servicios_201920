/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.ServicioOfrecidoPersistence;
import co.edu.uniandes.csw.servicios.persistence.TrabajadorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * La clase que implementa la conexion con la persitencia para la relacion entre 
 * las entidades Trabajador y ServicioOfrecido. 
 * @Athor Violeta Rodriguez
 */
@Stateless
public class TrabajadorServicioOfrecidoLogic 
{
    @Inject
    private ServicioOfrecidoPersistence  servicioOfrecidoPersistence;

    @Inject
    private TrabajadorPersistence trabajadorPersistence;
    
   /**
     * Asocia un ServicioOfrecido existente a un Trabajador
     *
     * @param TrabajadorId Identificador de la instancia de Trabajador
     * @param ServicioOfrecidosId Identificador de la instancia de ServicioOfrecido
     * @return Instancia de ServicioOfrecidoEntity que fue asociada a Trabajador
     */
    public ServicioOfrecidoEntity addServicioOfrecido(Long trabajadorId, Long servicioOfrecidoId) {
        
        TrabajadorEntity  trabajadorEntity = trabajadorPersistence.find(trabajadorId);
        
        ServicioOfrecidoEntity servicioOfrecidoEntity = servicioOfrecidoPersistence.find(servicioOfrecidoId);
        trabajadorEntity.getServicios().add(servicioOfrecidoEntity);
        
        return servicioOfrecidoPersistence.find(servicioOfrecidoId);
    }
    
     /**
     * Obtiene una colección de instancias de ServiciosEntity asociadas a una
     * instancia de Author
     *
     * @param TrabajadorId Identificador de la instancia de Trabajador
     * @return Colección de instancias ServicioOfrecidokEntity asociadas a la instancia de
     * Trabajador
     */
    public List<ServicioOfrecidoEntity> getServiciosOfrecidos(Long trabajadorId) {
        return (List<ServicioOfrecidoEntity>) trabajadorPersistence.find(trabajadorId).getServicios();
    }
    
    /**
     * Obtiene una instancia de ServicioOfrecidoEntity asociada a una instancia de Author
     *
     * @param trabajadorId Identificador de la instancia de Author
     * @param servicioOfrecidoId Identificador de la instancia de Book
     * @return La entidadd de Libro del autor
     * @throws BusinessLogicException Si el libro no está asociado al autor
     */
    public ServicioOfrecidoEntity getServicioOfrecido   (Long trabajadorId, Long servicioOfrecidoId) throws BusinessLogicException {
      
        List<ServicioOfrecidoEntity> servicios = (List<ServicioOfrecidoEntity>) trabajadorPersistence.find(trabajadorId).getServicios();
        for (ServicioOfrecidoEntity i: servicios)
        {
            if(i.getId().equals(servicioOfrecidoId))
            {
                return i;
            }
        }

        throw new BusinessLogicException("El trbajador no ofrece el servicio "+ servicioOfrecidoId);
        
    }
    
    /**
     * Desasocia un servicioOfrecido existente de un Trabajador existente
     *
     * @param authorsId Identificador de la instancia de Author
     * @param booksId Identificador de la instancia de Book
     */
    public void removeServicioOfrecido(Long trabajadorId, Long servicioOfrecidoId) {
        TrabajadorEntity trabajadorEntity = trabajadorPersistence.find(trabajadorId);
        ServicioOfrecidoEntity servicioOfrecidoEntity = servicioOfrecidoPersistence.find(servicioOfrecidoId);
        trabajadorEntity.getServicios().remove(servicioOfrecidoEntity); 
    }
        
        
    public List<ServicioOfrecidoEntity> replaceServicioOfrecido(Long trabajadorId, List<ServicioOfrecidoEntity> servicios ) throws BusinessLogicException
    {
        TrabajadorEntity trabajadorEntity = trabajadorPersistence.find(trabajadorId);
        for (ServicioOfrecidoEntity servicio:servicios )
        {
            if(servicioOfrecidoPersistence.find(servicio.getId())==null)
            {
               throw new BusinessLogicException("El recurso serviciosOfrecidos/"+ servicio.getId() + " no existe");
            }
        }
          trabajadorEntity.setServicios(servicios);
          
        return (List<ServicioOfrecidoEntity>) trabajadorPersistence.update(trabajadorEntity).getServicios();
    }
   
     
   
}
