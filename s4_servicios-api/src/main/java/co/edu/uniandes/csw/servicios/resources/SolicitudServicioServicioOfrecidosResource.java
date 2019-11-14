/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.ServicioOfrecidoDTO;
import co.edu.uniandes.csw.servicios.dtos.SolicitudServicioDTO;
import co.edu.uniandes.csw.servicios.dtos.SolicitudServicioDetailDTO;
import co.edu.uniandes.csw.servicios.ejb.ServicioOfrecidoLogic;
import co.edu.uniandes.csw.servicios.ejb.SolicitudServicioLogic;
import co.edu.uniandes.csw.servicios.ejb.SolicitudServicioServicioOfrecidosLogic;
import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author ca.torrese
 */
public class SolicitudServicioServicioOfrecidosResource {
    
    private static final Logger LOGGER = Logger.getLogger(SolicitudServicioServicioOfrecidosResource.class.getName());
    
    @Inject
    private SolicitudServicioServicioOfrecidosLogic soliciudServicioLogic;

    @Inject
    private ServicioOfrecidoLogic servicioLogic; 
    
     /**
     * Asocia un servicio existente con una solicitud existente
     *
     * @param solicitudId El ID de la solicitud a la cual se le va a asociar el servicio
     * @param servicioId El ID del servicio que se asocia
     * @return JSON {@link SolicitudServicioDTO} - La solicitud a la que fue asociado.
     */
    @POST
    @Path("{servicioId: \\d+}")
    public ServicioOfrecidoDTO addServicio(@PathParam("solicitudId") Long solicitudId, @PathParam("servicioId") Long servicioId) {
        LOGGER.log(Level.INFO, "SolicitudServicioServicioOfrecidosResource addServicio: input: solicitudId {0} , servicioId {1}", new Object[]{solicitudId, servicioId});
        ServicioOfrecidoDTO DTO = new ServicioOfrecidoDTO(soliciudServicioLogic.addServicio(solicitudId, servicioId));
        LOGGER.log(Level.INFO, "SolicitudServicioServicioOfrecidosResource addServicio: output: {0}", DTO);
        return DTO;
    }
    
     /**
     * Busca y devuelve todos los servicios que existen en una solicitud.
     *
     * @param solicitudId El ID de la solicitud de la cual se buscan los servicios
     * @return JSONArray {@link ServicioOfrecidoDTO} - Los servicios encontrados en la
     * solicitud. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public Collection<ServicioOfrecidoDTO> getServicios(@PathParam("solicitudId") Long solicitudId) {
        LOGGER.log(Level.INFO, "SolicitudServicioServicioOfrecidosResource getServicios: input: {0}", solicitudId);
        Collection<ServicioOfrecidoDTO> lista = serviciosListEntity2DTO(soliciudServicioLogic.getServicios(solicitudId));
        LOGGER.log(Level.INFO, "SolicitudServicioServicioOfrecidosResource getServicios: output: {0}", lista);
        return lista;
    }

     /**
     * Busca y devuelve el servicio con el ID recibido en la URL, relativo a una
     * solicitud.
     *
     * @param solicitudId El ID de la solicitud del cual se busca el servicio
     * @param servicioId El ID del servicio que se busca
     * @return {@link ServicioOfrecidoDTO} - El servicio encontrado en la solicitud.
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     * si el servicio no está asociado a la solicitud
     
     */
    @GET
    @Path("{servicioId: \\d+}")
    public ServicioOfrecidoDTO getServicio(@PathParam("solicitudId") Long solicitudId, @PathParam("servicioId") Long servicioId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudServicioServicioOfrecidosResource getServicio: input: solicitudId {0} , servicioId {1}", new Object[]{solicitudId, servicioId});
        ServicioOfrecidoDTO detailDTO = new ServicioOfrecidoDTO(soliciudServicioLogic.getServicio(solicitudId, servicioId));
        LOGGER.log(Level.INFO, "SolicitudServicioServicioOfrecidosResource getServicio: output: {0}", detailDTO);
        return detailDTO;
    }

     /**
     * Actualiza la lista de servicios de una solicitud con la lista que se recibe en el
     * cuerpo
     *
     * @param solicitudId El ID de la solicitud al cual se le va a asociar el servicio
     * @param servicios JSONArray {@link ServicioOfrecidoDTO} - La lista de servicios que se
     * desea guardar.
     * @return JSONArray {@link ServicioOfrecidoDTO} - La lista actualizada.
     */
    @PUT
    public Collection<ServicioOfrecidoDTO> replaceServicios(@PathParam("solicitudId") Long solicitudId, List<ServicioOfrecidoDTO> servicios) {
        LOGGER.log(Level.INFO, "SolicitudServicioServicioOfrecidosResource replaceServicios: input: solicitudId {0} , servicios {1}", new Object[]{solicitudId, servicios});
        Collection<ServicioOfrecidoDTO> lista = serviciosListEntity2DTO(soliciudServicioLogic.replaceServicios(solicitudId, serviciosListDTO2Entity(servicios)));
        LOGGER.log(Level.INFO, "SolicitudServicioServicioOfrecidosResource replaceServicios: output: {0}", lista);
        return lista;
    }

     /**
     * Elimina la conexión entre el servicio y la solicitud recibidos en la URL.
     *
     * @param solicitudId El ID de la solicitud al cual se le va a desasociar el servicio
     * @param servicioId El ID del servicio que se desasocia
     */
    @DELETE
    @Path("{servicioId: \\d+}")
    public void removeServicio(@PathParam("solicitudId") Long solicitudId, @PathParam("servicioId") Long servicioId) {
        LOGGER.log(Level.INFO, "SolicitudServicioServicioOfrecidosResource removeServicio: input: solicitudId {0} , servicioId {1}", new Object[]{solicitudId, servicioId});
        soliciudServicioLogic.removeServicio(solicitudId, servicioId);
        LOGGER.info("SolicitudServicioServicioOfrecidosResource removeServicio: output: void");
    }
    
     /**
     * Convierte una lista de ServicioOfrecidoEntity a una lista de ServicioOfrecidoDTO.
     *
     * @param entityList Lista de ServicioOfrecidoEntity a convertir.
     * @return Lista de ServicioOfrecidolDTO convertida.
     */
    private Collection<ServicioOfrecidoDTO> serviciosListEntity2DTO(Collection<ServicioOfrecidoEntity> entityList) {
        Collection<ServicioOfrecidoDTO> list = new ArrayList<>();
        for (ServicioOfrecidoEntity entity : entityList) {
            list.add(new ServicioOfrecidoDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ServicioOfrecidoDTO a una lista de SevicioOfrecidoEntity.
     *
     * @param dtos Lista de ServicioOfrecidoDTO a convertir.
     * @return Lista de ServicioOfrecidoEntity convertida.
     */
    private Collection<ServicioOfrecidoEntity> serviciosListDTO2Entity(Collection<ServicioOfrecidoDTO> dtos) {
        Collection<ServicioOfrecidoEntity> list = new ArrayList<>();
        for (ServicioOfrecidoDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

}
