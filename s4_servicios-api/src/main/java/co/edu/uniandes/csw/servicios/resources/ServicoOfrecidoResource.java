/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.ServicioOfrecidoDTO;
import co.edu.uniandes.csw.servicios.ejb.ServicioOfrecidoLogic;
import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *Clase que implementa el recurso "ServicioOfrecido"
 * 
 * @author Violeta Rodríguez
 */
@Path("serviciosOfrecidos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ServicoOfrecidoResource 
{
    @Inject
    private ServicioOfrecidoLogic servicioLogic;
    
    
    /**
     * Crea un servicioOfrecido con la iformación dada
     * @param servicio servicio que se desea crear. 
     * @return rerona el serivcio que se ha almacenda en la base de datos con su espectivo ID. 
     * @throws BusinessLogicException  Lanza excepción cuando no se ha cumplido alguno de las reglas de negocio establecidas. 
     */
    @POST
    public ServicioOfrecidoDTO createServicioOfrecido(ServicioOfrecidoDTO servicio) throws BusinessLogicException {
    
        ServicioOfrecidoDTO newService = new ServicioOfrecidoDTO (servicioLogic.createServicioOfrecido(servicio.toEntity()));
        return newService;
    }
    
    @GET 
    public List<ServicioOfrecidoDTO> getServiciosOfrecidos() {
       
        List<ServicioOfrecidoDTO> listaServicioOfrecidos = toDTO(servicioLogic.getServiciosOfrecidos());
       
        return listaServicioOfrecidos;
    }
    
    /**
     * Busca el servicioOfrecido con el id asociado recibido en la URL y lo devuelve.
     *
     * @param servicioOfrecidoId Identificador del servicioOfrecido que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON - El servicio ofrecido buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @GET
   @Path("{servicioOfrecidoId: \\d+}")
    public ServicioOfrecidoDTO getServicioOfrecido(@PathParam("servicioOfrecidoId") Long servicioOfrecidoId)
    {
        ServicioOfrecidoEntity entity = servicioLogic.getServicioOfrecido(servicioOfrecidoId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /serviciosOfrecidos/" + servicioOfrecidoId + " no existe.", 404);
        }
        
        ServicioOfrecidoDTO servicioDTO = new ServicioOfrecidoDTO(entity);
        return servicioDTO;
    }
    
    /**
     * Busca los servicioOfrecido del tipo recibido en la URL y los devuelve.
     *
     * @param servicioOfrecidoId Identificador del servicioOfrecido que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON - El servicio ofrecido buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @GET
   @Path("{servicioOfrecidoTipo:[a-zA-Z]+}")
    public List<ServicioOfrecidoDTO> getServiciosOfrecidosType(@PathParam("servicioOfrecidoTipo") String servicioOfrecidoType)
    {
        List<ServicioOfrecidoEntity> entities = servicioLogic.getServiciosOfrecidosByType(servicioOfrecidoType) ;
        if (entities == null) {
            throw new WebApplicationException("El recurso /serviciosOfrecidos/" + servicioOfrecidoType + " no existe.", 404);
        }
        
        List<ServicioOfrecidoDTO> serviciosDTO = toDTO(entities);
        return serviciosDTO;
    }
    
    
    /**
     * Actualiza el servicioOfrecido con el id recibido en la URL con la 
     * informaci'on que recibe el el cuepo de la perticion
     * @param servicioId identificador del servicioOfrecido que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @param servicio El libro que se desea guardar.
     * @return JSON  - El libro guardada.
      * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el libro.
     */
    @PUT
    @Path("{servicioOfrecidoId: \\d+}")
    public ServicioOfrecidoDTO updateServicioOfrecido(@PathParam("servicioOfrecidoId") Long servicioId, ServicioOfrecidoDTO servicio) throws BusinessLogicException
    {
        servicio.setId(servicioId);
        
        if (servicioLogic.getServicioOfrecido(servicioId) == null) {
            throw new WebApplicationException("El recurso /serviciosOfrecidos/" + servicioId + " no existe.", 404);
        }
        
        ServicioOfrecidoDTO servicioDTO = new ServicioOfrecidoDTO(servicioLogic.updateServicioOfrecido(servicioId, servicio.toEntity()));
        return servicioDTO;
    }
    
    
    @DELETE
    @Path("{servicioOfrecidoId: \\d+}")
    public void deleteServicioOfrecido (@PathParam("servicioOfrecidoId") Long servicioId) throws BusinessLogicException
    {
       ServicioOfrecidoEntity entity = servicioLogic.getServicioOfrecido(servicioId);
       if (entity == null) {
            throw new WebApplicationException("El recurso /serviciosOfrecidos/" + servicioId + " no existe.", 404);
        }
       servicioLogic.deleteServicioOfrecido(servicioId);
    }
    
   private List<ServicioOfrecidoDTO> toDTO (List<ServicioOfrecidoEntity> entityList)
   {
        List<ServicioOfrecidoDTO> list = new ArrayList<>();
        for (ServicioOfrecidoEntity entity : entityList) {
            list.add(new ServicioOfrecidoDTO(entity));
        }
        return list;
        
       
       
   }
}
