/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.ServicioOfrecidoDTO;
import co.edu.uniandes.csw.servicios.dtos.TrabajadorDetailDTO;
import co.edu.uniandes.csw.servicios.ejb.ServicioOfrecidoLogic;
import co.edu.uniandes.csw.servicios.ejb.TrabajadorServicioOfrecidoLogic;
import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
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
 * @author Violeta Rodriguez
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrabajadorServicioOfrecidoResource 
{
    @Inject
    private TrabajadorServicioOfrecidoLogic trabajadorServicioOfrecidoLogic;
             
    @Inject
    private ServicioOfrecidoLogic servicioOfrecidoLogic ;
    
    /**
     * Asocia un servicioOfrecido existente a un trabjador existente
     * @param trabajadorId identificador del trabajadoral que se le desea asociar el servicioOfrecido
     * @param servicioOfrecidoId identificador del serivcioOfrecido que se desea asociar.
     * @return JSON - servicioOfrecido asociado
     */
    @POST
    @Path("{servicioOfrecidoId: \\d+}")
    public ServicioOfrecidoDTO addServicio(@PathParam("trabajadoresId") Long trabajadorId, @PathParam("servicioOfrecidoId") Long servicioOfrecidoId) {
        
        if (servicioOfrecidoLogic.getServicioOfrecido(servicioOfrecidoId) == null) {
            throw new WebApplicationException("El recurso /books/" + servicioOfrecidoId + " no existe.", 404);
        }
       ServicioOfrecidoDTO DTO = new ServicioOfrecidoDTO(trabajadorServicioOfrecidoLogic.addServicioOfrecido(trabajadorId, servicioOfrecidoId));
        return DTO;
    }
    
    /**
     * Devuelve todos los seriviciosOfrecidos de un trabajador existente
     * @param trabajadorId id del trabajador del que se quieren los serviciosOfrecidos
     * @return JSONArray - los libros encontrados para el trabjador. 
     */
    @GET
    public List<ServicioOfrecidoDTO> getServiciosOfrecidos(@PathParam("trabajadoresId") Long trabajadorId)
    {
       List<ServicioOfrecidoDTO> list=servicioOfrecidoEntityToDTO(trabajadorServicioOfrecidoLogic.getServiciosOfrecidos(trabajadorId));  
       
       return list;
    }
    
    /**
     * Devuelve el servicio ofrecido con el id dado para el id del trajbajador dado.
     * @param trabajadorId indentificador del trabajador
     * @param servicioOfrecidoId indetificador del servicioOfrecido
     * @return el serivicio ofrecido pr el trbajador 
     * @throws BusinessLogicException no cumples las reh;as de negocio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el serivicioOfrecido.
     */
    @GET
    @Path("{servicioOfrecidoId: \\d+}")
    public ServicioOfrecidoDTO getServicioOfrecido(@PathParam("trabajadoresId") Long trabajadorId, @PathParam("servicioOfrecidoId") Long servicioOfrecidoId) throws BusinessLogicException
    {
        if(servicioOfrecidoLogic.getServicioOfrecido(trabajadorId)==null)
        {
         throw new WebApplicationException("El recurso /SeriviciosOfrecidos/" + servicioOfrecidoId    + " no existe.", 404);   
        }
        
        ServicioOfrecidoDTO dto = new ServicioOfrecidoDTO(trabajadorServicioOfrecidoLogic.getServicioOfrecido(trabajadorId, trabajadorId));
        return dto;   
    }
    
    /**
     * Actualiza la lista de servicioOfrecidos por el trabajador
     * @param trabjadorId identificador del trabajador al que se le quieren actulizar los servicios
     * @param servicios 
     * @return JSONArray - la lista actualziada
     */
    @PUT 
    public List<ServicioOfrecidoDTO> replaceServiciosOfrecidos(@PathParam("trabajadoresId") Long trabjadorId, List<ServicioOfrecidoDTO> servicios)
    {
        for (ServicioOfrecidoDTO servicio : servicios) {
            if (servicioOfrecidoLogic.getServicioOfrecido(servicio.getId()) == null) {
                throw new WebApplicationException("El recurso /serviciosOfrecidos/" + servicio.getId() + " no existe.", 404);
            }
        }
        List<ServicioOfrecidoDTO> lista = servicioOfrecidoEntityToDTO(trabajadorServicioOfrecidoLogic.replaceServicioOfrecido(trabjadorId, servicioOfrecidoDTOToEntity(servicios)));
        return lista;
    }
    
    @DELETE
    @Path("{servicioOfrecidoId:\\d+}")
    public void removeServicioOfrecido(@PathParam("trabajadoresId") Long trabajadoresId, @PathParam("servicioOfrecidoId") Long servicioOfrecidoId)
    {
        if(servicioOfrecidoLogic.getServicioOfrecido(servicioOfrecidoId)==null)
        {
             throw new WebApplicationException("El recurso /servicioOfrecido/" + servicioOfrecidoId+ " no existe.", 404);
        }
        
        trabajadorServicioOfrecidoLogic.removeServicioOfrecido(trabajadoresId, servicioOfrecidoId);
    }
    
    
    
    private List<ServicioOfrecidoEntity> servicioOfrecidoDTOToEntity(List<ServicioOfrecidoDTO> dtos)
    {
             List<ServicioOfrecidoEntity> lista = new ArrayList<>();
        
        for (ServicioOfrecidoDTO dto:dtos)
        {
            lista.add(dto.toEntity());
        }
        
            return lista;
            
    }
        
        
    private List<ServicioOfrecidoDTO> servicioOfrecidoEntityToDTO( List<ServicioOfrecidoEntity> entityList)
    {
        List<ServicioOfrecidoDTO> lista = new ArrayList<>();
        
        for (ServicioOfrecidoEntity entity:entityList)
        {
            lista.add(new ServicioOfrecidoDTO(entity));
        }
        
        return lista;     
    
    }
}
    