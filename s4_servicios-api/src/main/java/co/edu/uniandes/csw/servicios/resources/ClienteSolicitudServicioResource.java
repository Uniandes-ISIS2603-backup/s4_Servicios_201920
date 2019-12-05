package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.SolicitudServicioDTO;
import co.edu.uniandes.csw.servicios.ejb.ClienteSolicitudServicioLogic;
import co.edu.uniandes.csw.servicios.ejb.SolicitudServicioLogic;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.mappers.WebApplicationExceptionMapper;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "clientes/{id}/servicios".
 *
 * @author estudiante
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteSolicitudServicioResource {

    private static final Logger LOGGER = Logger.getLogger(ClienteSolicitudServicioResource.class.getName());

    @Inject
    private ClienteSolicitudServicioLogic clienteSolicitudServicioLogic;

    @Inject
    private SolicitudServicioLogic solicitudServicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.


    /**
     * Busca y devuelve todos los servicios que existen en un cliente.
     *
     * @param clientesId El ID del cliente del cual se buscan los servicios
     * @return JSONArray {@link SolicitudServicioDTO} - Los servicios encontrados en el
     * cliente. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<SolicitudServicioDTO> getServicios(@PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "ClienteSolicitudServicioResource getServicios: input: {0}", clientesId);
        List<SolicitudServicioDTO> lista = serviciosListEntity2DTO(clienteSolicitudServicioLogic.getServicios(clientesId));
        LOGGER.log(Level.INFO, "ClienteSolicitudServicioResource getServicios: output: {0}", lista);
        return lista;
    }

    @GET
    @Path("{serviciosId: \\d+}")
    public SolicitudServicioDTO getServicio(@PathParam("clientesId") Long clientesId, @PathParam("serviciosId") Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteSolicitudServicioResource getServicio: input: authorsId {0} , booksId {1}", new Object[]{clientesId, serviciosId});
        if (solicitudServicioLogic.getSolicitudServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        SolicitudServicioDTO servicioDTO = new SolicitudServicioDTO(clienteSolicitudServicioLogic.getSolicitudServicio(clientesId, serviciosId));
        LOGGER.log(Level.INFO, "ClienteSolicitudServicioResource getServicio: output: {0}", servicioDTO);
        return servicioDTO;
    }

    
    @PUT
    public List<SolicitudServicioDTO> replaceSolicitudesServicio(@PathParam("clientesId") Long clientesId, List<SolicitudServicioDTO> solicitudesServicios) {
        LOGGER.log(Level.INFO, "ClienteSolicitudServicioResource replaceSolicitudesServicio: input: clientesId {0} , solicitudesServicios {1}", new Object[]{clientesId, solicitudesServicios});
        for (SolicitudServicioDTO servicio : solicitudesServicios) {
            if (solicitudServicioLogic.getSolicitudServicio(servicio.getId()) == null) {
                throw new WebApplicationException("El recurso /servicios/" + servicio.getId() + " no existe.", 404);
            }
        }
        List<SolicitudServicioDTO> lista = serviciosListEntity2DTO(clienteSolicitudServicioLogic.replaceSolicitudServicios(clientesId, serviciosListDTO2Entity(solicitudesServicios)));
        LOGGER.log(Level.INFO, "ClienteSolicitudServicioResource replaceSolicitudesServicio: output: {0}", lista);
        return lista;
    }
    
    /*
    * Elimina la conexión entre el servicio y el cliente recibidos en la URL.
     */
   /* @DELETE
    @Path("{serviciosId: \\d+}")
    public void removeSolicitudServicio(@PathParam("clientesId") Long clientesId, @PathParam("serviciosId") Long serviciosId) {
        LOGGER.log(Level.INFO, "ClienteSolicitudServicioResource deleteServicio: input: clientesId {0} , serviciosId {1}", new Object[]{clientesId, serviciosId});
        if (solicitudServicioLogic.getSolicitudServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
       }
        clienteSolicitudServicioLogic.removeSolicitudServicio(clientesId, serviciosId);
        LOGGER.info("AuthorBooksResource deleteBook: output: void");
    }*/


    private List<SolicitudServicioDTO> serviciosListEntity2DTO(List<SolicitudServicioEntity> entityList) {
        List<SolicitudServicioDTO> list = new ArrayList<>();
        for (SolicitudServicioEntity entity : entityList) {
            list.add(new SolicitudServicioDTO(entity));
        }
        return list;
    }

   
    private List<SolicitudServicioEntity> serviciosListDTO2Entity(List<SolicitudServicioDTO> dtos) {
        List<SolicitudServicioEntity> list = new ArrayList<>();
        for (SolicitudServicioDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
