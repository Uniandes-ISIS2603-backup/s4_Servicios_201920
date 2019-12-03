/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.ClienteDTO;
import co.edu.uniandes.csw.servicios.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.servicios.ejb.ClienteLogic;
import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
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
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Estudiante
 */
@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ClienteResource {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());

    @Inject
    private ClienteLogic clienteLogic;

    /**
     * Crea un nuevo cliente con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param cliente {@link ClienteDTO} - EL autor que se desea guardar.
     * @return JSON {@link ClienteDTO} - El autor guardado con el atributo id
     * autogenerado.
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @POST
    public ClienteDTO createCliente(ClienteDTO cliente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteResource createCliente: input: {0}", cliente);
        ClienteDTO clienteDTO = new ClienteDTO(clienteLogic.createCliente(cliente.toEntity()));
        LOGGER.log(Level.INFO, "ClienteResource createCliente: output: {0}", clienteDTO);
        return cliente;
    }

    /**
     * Busca y devuelve todos los clientes que existen en la aplicacion.
     *
     * @return JSONArray {@link ClienteDetailDTO} - Los clientes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ClienteDetailDTO> getClientes() {
        LOGGER.info("ClienteResource getClientes: input: void");
        List<ClienteDetailDTO> listaClientes = listEntity2DTO(clienteLogic.getClientes());
        LOGGER.log(Level.INFO, "ClienteResource getCliente: output: {0}", listaClientes);
        return listaClientes;
    }

    /**
     * Busca el cliente con el id asociado recibido en la URL y lo devuelve.
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente.
     */
    @GET
    @Path("{clientesId: \\d+}")
    public ClienteDetailDTO getCliente(@PathParam("clientesId") Long clientesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienterResource getCliente: input: {0}", clientesId);
        ClienteEntity clienteEntity = clienteLogic.getCliente(clientesId);
        if (clienteEntity == null) {
            throw new WebApplicationException("El recurso /cliente/" + clientesId + " no existe.", 404);
        }
        ClienteDetailDTO detailDTO = new ClienteDetailDTO(clienteEntity);
        LOGGER.log(Level.INFO, "ClienteResource getCliente: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza el cliente con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param clientesId Identificador del cliente que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param cliente {@link ClienteDetailDTO} cliente que se desea guardar.
     * @return JSON {@link ClienteDetailDTO} - El cliente guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente a
     * actualizar.
     */
    @PUT
    @Path("{clientesId: \\d+}")
    public ClienteDetailDTO updateCliente(@PathParam("clientesId") Long clientesId, ClienteDetailDTO cliente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteResource updateCliente: input: clientesId: {0} , cliente: {1}", new Object[]{clientesId, cliente});
        cliente.setId(clientesId);
        if (clienteLogic.getCliente(clientesId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        }
        ClienteDetailDTO detailDTO = new ClienteDetailDTO(clienteLogic.updateCliente(clientesId, cliente.toEntity()));
        LOGGER.log(Level.INFO, "ClienteResource updateCliente: output: {0}", detailDTO);
        return detailDTO;
    }


    @DELETE
    @Path("{clientesId: \\d+}")
    public void deleteCliente(@PathParam("clientesId") Long clientesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "AuthorResource deleteAuthor: input: {0}", clientesId);
        if (clienteLogic.getCliente(clientesId) == null) {
            throw new WebApplicationException("El recurso /authors/" + clientesId + " no existe.", 404);
        }
        clienteLogic.deleteCliente(clientesId);
        LOGGER.info("ClienteResource deleteCliente: output: void");
    }

    /*
     * @param clientesId El ID del cliente con respecto al cual se accede al
     * servicio.
     * @return El servicio de Servicios para ese autor en paricular.
     */
    @Path("{clientesId: \\d+}/solicitudes")
    public Class<ClienteSolicitudServicioResource> getClienteSolicitudesResource(@PathParam("clientesId") Long clientesId) throws BusinessLogicException {
        if (clienteLogic.getCliente(clientesId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        }
        return ClienteSolicitudServicioResource.class;
    }
    
    /**
     * Busca el cliente con el usuario y contrasena asociado recibido en la URL y lo devuelve.
     *
     * @param clientesUs Usuario del cliente que se esta buscando. Este debe
     * ser una cadena de caracteres y/o digitos.
     * @param clientesPs Contrasena del cliente que se esta buscando. Este debe
     * ser una cadena de caracteres y/o digitps.
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente.
     * @throws BusinessLogicException
     */
    @GET
    @Path("{clientesUs: [A-Za-z0-9][A-Za-z0-9]*}/{clientesPs: [A-Za-z0-9][A-Za-z0-9]*}")
    public ClienteDetailDTO getClientePorUsuario(@PathParam("clientesUs") String clientesUs, @PathParam("clientesPs") String clientesPs) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienterResource getClientePorUsuario: input: {0}", clientesUs);
        ClienteEntity clienteEntity = clienteLogic.getClientePorUsuario(clientesUs, clientesPs);
        if (clienteEntity == null) {
            throw new WebApplicationException("El recurso /cliente/" + clientesUs + "/" + clientesPs + " no existe.", 404);
        }
        ClienteDetailDTO detailDTO = new ClienteDetailDTO(clienteEntity);
        LOGGER.log(Level.INFO, "ClienteResource getClientePorUsuario: output: {0}", detailDTO);
        return detailDTO;
    }


    /**
     * Convierte una lista de ClienteEntity a una lista de ClienteDetailDTO.
     *
     * @param entityList Lista de ClienteEntity a convertir.
     * @return Lista de ClienteDetailDTO convertida.
     */
    private List<ClienteDetailDTO> listEntity2DTO(List<ClienteEntity> entityList) {
        List<ClienteDetailDTO> list = new ArrayList<>();
        for (ClienteEntity entity : entityList) {
            list.add(new ClienteDetailDTO(entity));
        }
        return list;
    }
}
