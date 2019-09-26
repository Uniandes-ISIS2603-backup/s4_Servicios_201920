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
import javax.inject.Inject;

/**
 * La clase que implementa la conexion con la persitencia para la relacion entre 
 * las entidades Trabajador y ServicioOfrecido. 
 * @Athor Violeta Rodriguez
 */
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
        //trabajadorEntity. Falta meotod de agregar trabajador. 
        
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
    public List<ServicioOfrecidoEntity> getBooks(Long trabajadorId) {
        return (List<ServicioOfrecidoEntity>) trabajadorPersistence.find(trabajadorId).getServicios();
    }
    
    /**
     * Obtiene una instancia de BookEntity asociada a una instancia de Author
     *
     * @param authorsId Identificador de la instancia de Author
     * @param booksId Identificador de la instancia de Book
     * @return La entidadd de Libro del autor
     * @throws BusinessLogicException Si el libro no está asociado al autor
     */
    public ServicioOfrecidoEntity getBook(Long authorsId, Long booksId) throws BusinessLogicException {
      
        List<ServicioOfrecidoEntity> servicios = (List<ServicioOfrecidoEntity>) trabajadorPersistence.find(authorsId).getServicios();
        ServicioOfrecidoEntity servicioOfrecidoEntity = servicioOfrecidoPersistence.find(booksId);
        int index = servicios.indexOf(servicioOfrecidoEntity);
        if (index >= 0) {
            return servicios.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado al autor");
        
    }
    
    /**
     * Desasocia un Book existente de un Author existente
     *
     * @param authorsId Identificador de la instancia de Author
     * @param booksId Identificador de la instancia de Book
     */
    public void removeBook(Long trabajadorId, Long servicioOfrecidoId) {
        TrabajadorEntity trabajadorEntity = trabajadorPersistence.find(trabajadorId);
        ServicioOfrecidoEntity servicioOfrecidoEntity = servicioOfrecidoPersistence.find(servicioOfrecidoId);
        //trabajadorEntity.remove (servicioEntity); No est'a en la calse remove. 
    }
        
        
       
   
     
   
}
