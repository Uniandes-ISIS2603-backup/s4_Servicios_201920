/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.SolicitudServicioPersistence;
import co.edu.uniandes.csw.servicios.persistence.TrabajadorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
@Stateless
public class TrabajadorSolicitudServicioLogic {
     @Inject
    private SolicitudServicioPersistence  solicitudServicioPersistence;

    @Inject
    private TrabajadorPersistence trabajadorPersistence;
    
       /**
     * Asocia un SolicitudServicio existente a un Trabajador
     *
     * @param TrabajadorId Identificador de la instancia de Trabajador
     * @param SolicitudServicio Identificador de la instancia de SolicitudServicio
     * @return Instancia de SolicitudServicioEntity que fue asociada a Trabajador
     */
    public SolicitudServicioEntity addSolicitudServicio(Long trabajadorId, Long solicitudServicioId) {
        
        TrabajadorEntity  trabajadorEntity = trabajadorPersistence.find(trabajadorId);
        SolicitudServicioEntity solicitudServicioEntity = solicitudServicioPersistence.find(solicitudServicioId);
        trabajadorEntity.getSolicitudes().add(solicitudServicioEntity);
        
        return solicitudServicioPersistence.find(solicitudServicioId);
    }
    
     /**
     * Obtiene una colección de instancias de ServiciosEntity asociadas a una
     * instancia de Author
     *
     * @param TrabajadorId Identificador de la instancia de Trabajador
     * @return Colección de instancias SolicitudServiciokEntity asociadas a la instancia de
     * Trabajador
     */
    public List<SolicitudServicioEntity> getServiciosOfrecidos(Long trabajadorId) {
        return (List<SolicitudServicioEntity>) trabajadorPersistence.find(trabajadorId).getSolicitudes();
    }
    
    /**
     * Obtiene una instancia de SolicitudServicioEntity asociada a una instancia de Author
     *
     * @param trabajadorId Identificador de la instancia de Author
     * @param solicitudServicioId Identificador de la instancia de Book
     * @return La entidadd de Libro del autor
     * @throws BusinessLogicException Si el libro no está asociado al autor
     */
    public SolicitudServicioEntity getSolicitudServicio   (Long trabajadorId, Long solicitudServicioId) throws BusinessLogicException {
      
        List<SolicitudServicioEntity> servicios = (List<SolicitudServicioEntity>) trabajadorPersistence.find(trabajadorId).getSolicitudes();
        SolicitudServicioEntity solicitudServicioEntity = solicitudServicioPersistence.find(solicitudServicioId);
        int index = servicios.indexOf(solicitudServicioEntity);
        if (index >= 0) {
            return servicios.get(index);
        }
        throw new BusinessLogicException("El trbajador no ofrece el servicio");
        
    }
    
    /**
     * Desasocia un solicitudServicio existente de un Trabajador existente
     *
     * @param authorsId Identificador de la instancia de Author
     * @param booksId Identificador de la instancia de Book
     */
    public void removeSolicitudServicio(Long trabajadorId, Long solicitudServicioId) {
        TrabajadorEntity trabajadorEntity = trabajadorPersistence.find(trabajadorId);
        SolicitudServicioEntity solicitudServicioEntity = solicitudServicioPersistence.find(solicitudServicioId);
        trabajadorEntity.getSolicitudes().remove(solicitudServicioEntity); 
    }
        
        
    public List<SolicitudServicioEntity> replaceSolicitudServicio(Long trabajadorId, List<SolicitudServicioEntity> solicitud )
    {
        TrabajadorEntity trabajadorEntity = trabajadorPersistence.find(trabajadorId);
        trabajadorEntity.setSolicitudes(solicitud);
        return (List<SolicitudServicioEntity>) trabajadorEntity.getSolicitudes();
    }
   
     
   
}

