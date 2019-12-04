/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.SolicitudServicioDTO;
import co.edu.uniandes.csw.servicios.dtos.TrabajadorDetailDTO;
import co.edu.uniandes.csw.servicios.ejb.SolicitudServicioLogic;
import co.edu.uniandes.csw.servicios.ejb.TrabajadorSolicitudServicioLogic;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author c.otalora
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrabajadorSolicitudServicioResource {
    

    @Inject
    private TrabajadorSolicitudServicioLogic trabajadorSolicitudServicioLogic;
             
    @Inject
    private SolicitudServicioLogic solicitudServicioLogic ;
    
    /**
     * Asocia un solicitudServicio existente a un trabjador existente
     * @param trabajadorId identificador del trabajadoral que se le desea asociar el solicitudServicio
     * @param SolicitudServicioLogic identificador del serivcioOfrecido que se desea asociar.
     * @return JSON - solicitudServicio asociado
     */
    @POST
    @Path("{solicitudServicioId: \\d+}")
    public SolicitudServicioDTO addSolicitudServicio(@PathParam("trabajadoresId") Long trabajadorId, @PathParam("solicitudServicioId") Long solicitudServicioId) {
        
        if (solicitudServicioLogic.getSolicitudServicio(solicitudServicioId) == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudServicioId + " no existe.", 404);
        }
       SolicitudServicioDTO DTO = new SolicitudServicioDTO(trabajadorSolicitudServicioLogic.addSolicitudServicio(trabajadorId, solicitudServicioId));
        return DTO;
    }
    
    /**
     * Devuelve todos los seriviciosOfrecidos de un trabajador existente
     * @param trabajadorId id del trabajador del que se quieren los serviciosOfrecidos
     * @return JSONArray - los libros encontrados para el trabjador. 
     */
    @GET
    public List<SolicitudServicioDTO> getServiciosOfrecidos(@PathParam("trabajadoresId") Long trabajadorId)
    {
       List<SolicitudServicioDTO> list=solicitudServicioEntityToDTO(trabajadorSolicitudServicioLogic.getServiciosOfrecidos(trabajadorId));  
       
       return list;
    }
    
    /**
     * Devuelve el servicio ofrecido con el id dado para el id del trajbajador dado.
     * @param trabajadorId indentificador del trabajador
     * @param solicitudServicioId indetificador del solicitudServicio
     * @return el serivicio ofrecido pr el trbajador 
     * @throws BusinessLogicException no cumples las reh;as de negocio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el serivicioOfrecido.
     */
    @GET
    @Path("{solicitudServicioId: \\d+}")
    public SolicitudServicioDTO getSolicitudServicio(@PathParam("trabajadoresId") Long trabajadorId, @PathParam("solicitudServicioId") Long solicitudServicioId) throws BusinessLogicException
    {
        if(solicitudServicioLogic.getSolicitudServicio(solicitudServicioId)==null)
        {
         throw new WebApplicationException("El recurso /solicitudes/" + solicitudServicioId    + " no existe.", 404);   
        }
        
        SolicitudServicioDTO dto = new SolicitudServicioDTO(trabajadorSolicitudServicioLogic.getSolicitudServicio(trabajadorId, solicitudServicioId));
        return dto;   
    }
    
    /**
     * Actualiza la lista de solicitudServicios por el trabajador
     * @param trabjadorId identificador del trabajador al que se le quieren actulizar los servicios
     * @param servicios 
     * @return JSONArray - la lista actualziada
     */
    @PUT 
    public List<SolicitudServicioDTO> replaceServiciosOfrecidos(@PathParam("trabajadoresId") Long trabjadorId, List<SolicitudServicioDTO> servicios)
    {
        for (SolicitudServicioDTO servicio : servicios) {
            if (solicitudServicioLogic.getSolicitudServicio(servicio.getId()) == null) {
                throw new WebApplicationException("El recurso /solicitudes/" + servicio.getId() + " no existe.", 404);
            }
        }
        List<SolicitudServicioDTO> lista = solicitudServicioEntityToDTO(trabajadorSolicitudServicioLogic.replaceSolicitudServicio(trabjadorId, solicitudServicioDTOToEntity(servicios)));
        return lista;
    }
    
    @DELETE
    @Path("{solicitudServicioId:\\d+}")
    public void removeSolicitudServicio(@PathParam("trabajadoresId") Long trabajadoresId, @PathParam("solicitudServicioId") Long solicitudServicioId)
    {
        if(solicitudServicioLogic.getSolicitudServicio(solicitudServicioId)==null)
        {
             throw new WebApplicationException("El recurso /solicitudServicio/" + solicitudServicioId+ " no existe.", 404);
        }
        
        trabajadorSolicitudServicioLogic.removeSolicitudServicio(trabajadoresId, solicitudServicioId);
    }
    
    
    
    private List<SolicitudServicioEntity> solicitudServicioDTOToEntity(List<SolicitudServicioDTO> dtos)
    {
             List<SolicitudServicioEntity> lista = new ArrayList<>();
        
        for (SolicitudServicioDTO dto:dtos)
        {
            lista.add(dto.toEntity());
        }
        
            return lista;
            
    }
        
        
    private List<SolicitudServicioDTO> solicitudServicioEntityToDTO( List<SolicitudServicioEntity> entityList)
    {
        List<SolicitudServicioDTO> lista = new ArrayList<>();
        
        for (SolicitudServicioEntity entity:entityList)
        {
            lista.add(new SolicitudServicioDTO(entity));
        }
        
        return lista;     
    
    }
}
    