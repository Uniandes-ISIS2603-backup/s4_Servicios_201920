/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.CalificacionDTO;
import co.edu.uniandes.csw.servicios.ejb.CalificacionLogic;
import co.edu.uniandes.csw.servicios.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
 * @author ca.torrese
 */
@Path("/calificaciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class CalificacionResource {
    
        private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());

         @Inject
    private CalificacionLogic calificacionLogic;

    /**
     * Crea un nuevo calificacion con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param calificacion {@link CalificacionDTO} - EL autor que se desea guardar.
     * @return JSON {@link CalificacionDTO} - El autor guardado con el atributo id
     * autogenerado.
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @POST
    public CalificacionDTO createCalificacion(CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0}", calificacion);
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.createCalificacion(calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    
     /**
     * Busca y devuelve todas las calificaciones que existen en la aplicacion.
     *
     * @return JSONArray {@link CalificacionDTO} - Las calificaciones
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CalificacionDTO> getCalificaciones() {
        LOGGER.info("CalificacionResource getCalificaciones: input: void");
        List<CalificacionDTO> listaCalificaciones = listEntity2DTO(calificacionLogic.getCalificaciones());
        LOGGER.log(Level.INFO, "CalificacionResource getCalificaciones: output: {0}", listaCalificaciones);
        return listaCalificaciones;
    }
    
    /**
     * Busca la calificacion con el id asociado recibido en la URL y la devuelve.
     *
     * @param calificacionesId Identificador de la calificacion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@linkCalificacionDTO} - La calificacion buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("calificacionesId") Long calificacionesId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionesId);
        CalificacionEntity calificacionEntity =calificacionLogic.getCalificacion(calificacionesId);
        if (calificacionEntity == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDTO DTO = new CalificacionDTO(calificacionEntity);
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", DTO);
        return DTO;
    }
    
    /**
     * Actualiza la calificacion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param calificacionId Identificador de la calificacion que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param calificacion {@link CalificacionDTO} La calificacion que se desea
     * guardar.
     * @return JSON {@link CalificacionlDTO} - La calificacion guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar la calificacion.
     */
    @PUT
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: id:{0} , calificacion: {1}", new Object[]{calificacionId, calificacion});
        calificacion.setId(calificacionId);
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /calificacion/" + calificacionId + " no existe.", 404);
        }
        CalificacionDTO DTO = new CalificacionDTO(calificacionLogic.updateCalificacion(calificacionId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output: {0}", DTO);
        return DTO;

    }
    
    /**
     * Borra la calificacion con el id asociado recibido en la URL.
     *
     * @param calificacionesId Identificador de la calificacion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la calificacion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacion(@PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource deleteCalificacion: input: {0}", calificacionesId);
        if (calificacionLogic.getCalificacion(calificacionesId) == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionesId + " no existe.", 404);
        }
        calificacionLogic.deleteCalificacion(calificacionesId);
        LOGGER.info("CalificacionResource deleteCalificacion: output: void");
    }
    
     /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CalificacionEntity a una lista de
     * objetos CalificacionlDTO (json)
     *
     * @param entityList corresponde a la lista de calificaciones de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de calificaciones en forma DTO (json)
     */
    private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
    
}
