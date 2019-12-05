/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.SolicitudServicioDTO;
import co.edu.uniandes.csw.servicios.dtos.SolicitudServicioDetailDTO;
import co.edu.uniandes.csw.servicios.ejb.SolicitudServicioLogic;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
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
@Path("/solicitudes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class SolicitudServicioResource {
    
    private static final Logger LOGGER = Logger.getLogger(SolicitudServicioResource.class.getName());

         @Inject
    private SolicitudServicioLogic solicitudServicioLogic;

    /**
     * Crea un nuevo solicitudServicio con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param solicitudServicio {@link SolicitudServicioDTO} - EL autor que se desea guardar.
     * @return JSON {@link SolicitudServicioDTO} - El autor guardado con el atributo id
     * autogenerado.
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @POST
    public SolicitudServicioDTO createSolicitudServicio(SolicitudServicioDTO solicitudServicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudServicioResource createSolicitudServicio: input: {0}", solicitudServicio);
        SolicitudServicioDTO solicitudServicioDTO = new SolicitudServicioDTO(solicitudServicioLogic.createSolicitudServicio(solicitudServicio.toEntity()));
        LOGGER.log(Level.INFO, "SolicitudServicioResource createSolicitudServicio: output: {0}", solicitudServicioDTO);
        return solicitudServicioDTO;
    }
    
     /**
     * Busca y devuelve todas las solicitudes que existen en la aplicacion.
     *
     * @return JSONArray {@link SolicitudServicioDTO} - Las solicitudes
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<SolicitudServicioDTO> getSolicitudServicioes() {
        LOGGER.info("SolicitudServicioResource getSolicitudServicioes: input: void");
        List<SolicitudServicioDTO> listaSolicitudServicioes = listEntity2DTO(solicitudServicioLogic.getSolicitudServicios());
        LOGGER.log(Level.INFO, "SolicitudServicioResource getSolicitudServicioes: output: {0}", listaSolicitudServicioes);
        return listaSolicitudServicioes;
    }
    
    /**
     * Busca la solicitudServicio con el id asociado recibido en la URL y la devuelve.
     *
     * @param solicitudesId Identificador de la solicitudServicio que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@linkSolicitudServicioDTO} - La solicitudServicio buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitudServicio.
     */
    @GET
    @Path("{solicitudesId: \\d+}")
    public SolicitudServicioDetailDTO getSolicitudServicio(@PathParam("solicitudesId") Long solicitudesId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "SolicitudServicioResource getSolicitudServicio: input: {0}", solicitudesId);
        SolicitudServicioEntity solicitudServicioEntity =solicitudServicioLogic.getSolicitudServicio(solicitudesId);
        if (solicitudServicioEntity == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudesId + " no existe.", 404);
        }
        SolicitudServicioDetailDTO DTO = new SolicitudServicioDetailDTO(solicitudServicioEntity);
        LOGGER.log(Level.INFO, "SolicitudServicioResource getSolicitudServicio: output: {0}", DTO);
        return DTO;
    }
    
    /**
     * Actualiza la solicitudServicio con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param solicitudesId Identificador de la solicitudServicio que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param solicitudServicio {@link SolicitudServicioDTO} La solicitudServicio que se desea
     * guardar.
     * @return JSON {@link SolicitudServiciolDTO} - La solicitudServicio guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitudServicio a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar la Solicitud.
     */
    @PUT
    @Path("{solicitudesId: \\d+}")
    public SolicitudServicioDetailDTO updateSolicitudServicio(@PathParam("solicitudesId") Long solicitudesId, SolicitudServicioDTO solicitudServicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudServicioResource updateSolicitudServicio: input: id:{0} , solicitudServicio: {1}", new Object[]{solicitudesId, solicitudServicio});
        solicitudServicio.setId(solicitudesId);
        if (solicitudServicioLogic.getSolicitudServicio(solicitudesId) == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudesId + " no existe.", 404);
        }
        SolicitudServicioDetailDTO DTO = new SolicitudServicioDetailDTO(solicitudServicioLogic.updateSolicitudServicio(solicitudesId, solicitudServicio.toEntity()));
        LOGGER.log(Level.INFO, "SolicitudServicioResource updateSolicitudServicio: output: {0}", DTO);
        return DTO;

    }
    
    /**
     * Borra la solicitudServicio con el id asociado recibido en la URL.
     *
     * @param solicitudesId Identificador de la solicitudServicio que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la solicitudServicio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitudServicio.
     */
    @DELETE
    @Path("{solicitudesId: \\d+}")
    public void deleteSolicitudServicio(@PathParam("solicitudesId") Long solicitudesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudServicioResource deleteSolicitudServicio: input: {0}", solicitudesId);
        if (solicitudServicioLogic.getSolicitudServicio(solicitudesId) == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudesId + " no existe.", 404);
        }
        solicitudServicioLogic.deleteSolicitudServicio(solicitudesId);
        LOGGER.info("SolicitudServicioResource deleteSolicitudServicio: output: void");
    }
    
    /**
     * Conexión con el servicio de servicios para una solicitud.
     * {@link SolicitudServicioServicioOfrecidosResource}
     *
     * Este método conecta la ruta de /solicitudes con las rutas de /servicios que
     * dependen de la solicitud, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los servicios.
     *
     * @param solicitudId El ID de la solicitud con respecto al cual se accede al
     * servicio.
     * @return El servicio de ServicioOfrecido para ese autor en paricular.
     */
    @Path("{solicitudId: \\d+}/servicios")
    public Class<SolicitudServicioServicioOfrecidosResource> getSolicitudServicioServicioOfrecidosResource(@PathParam("solicitudId") Long solicitudId) {
        if (solicitudServicioLogic.getSolicitudServicio(solicitudId) == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudId + " no existe.", 404);
        }
        return SolicitudServicioServicioOfrecidosResource.class;
    }
    
     /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SolicitudServicioEntity a una lista de
     * objetos SolicitudServiciolDTO (json)
     *
     * @param entityList corresponde a la lista de solicitudes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de solicitudes en forma DTO (json)
     */
    private List<SolicitudServicioDTO> listEntity2DTO(List<SolicitudServicioEntity> entityList) {
        List<SolicitudServicioDTO> list = new ArrayList<>();
        for (SolicitudServicioEntity entity : entityList) {
            list.add(new SolicitudServicioDTO(entity));
        }
        return list;
    }
    
}
