/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.TrabajadorDTO;
import co.edu.uniandes.csw.servicios.dtos.TrabajadorDetailDTO;
import co.edu.uniandes.csw.servicios.ejb.TrabajadorLogic;
import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
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

/**
 * @author c.otalora
 *
 */
@Path("/trabajadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class TrabajadorResource {
    
        
    private static final Logger LOGGER = Logger.getLogger(TrabajadorResource.class.getName());

    
    @Inject
    private TrabajadorLogic trabajadorLogic;
    
    @POST
    public TrabajadorDTO createTrabajador(TrabajadorDTO trabajador) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "TrabajadorResource createTrabajador: input: {0}", trabajador);
        TrabajadorDTO trabajadorDTO = new TrabajadorDTO(trabajadorLogic.crearTrabajador(trabajador.toEntity()));
        LOGGER.log(Level.INFO, "TrabajadorResource createTrabajador: output: {0}", trabajadorDTO);
        
        return trabajadorDTO;
    }
  
    @PUT
    @Path("{trabajadorId: \\d+}")
    public TrabajadorDTO updateTrabajador(@PathParam("trabajadorId") Long trabajadorId, TrabajadorDetailDTO trabajador) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TrabajadorResource updateTrabajador: input: trabajadorsId: {0} , trabajador: {1}", new Object[]{trabajadorId, trabajador});
        trabajador.setId(trabajadorId);
        if (trabajadorLogic.getTrabajador(trabajadorId) == null) {
            throw new WebApplicationException("El recurso /trabajadores/" + trabajadorId + " no existe.", 404);
        }
        TrabajadorDetailDTO detailDTO = new TrabajadorDetailDTO(trabajadorLogic.updateTrabajador(trabajadorId, trabajador.toEntity()));
        LOGGER.log(Level.INFO, "TrabajadorResource updateTrabajador: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @GET
    public List<TrabajadorDetailDTO> getTrabajadores() {
        LOGGER.info("TrabajadorResource getTrabajadors: input: void");
        List<TrabajadorDetailDTO> listaTrabajadors = listEntity2DTO(trabajadorLogic.getTrabajadores());
        LOGGER.log(Level.INFO, "TrabajadorResource getTrabajador: output: {0}", listaTrabajadors);
        return listaTrabajadors;
    }
    
    @GET
    @Path("{trabajadorId: \\d+}")
    public TrabajadorDetailDTO getTrabajador(@PathParam("trabajadorId") Long trabajadorId) {
        LOGGER.log(Level.INFO, "TrabajadorrResource getTrabajador: input: {0}", trabajadorId);
        TrabajadorEntity trabajadorEntity = trabajadorLogic.getTrabajador(trabajadorId);
        if (trabajadorEntity == null) {
            throw new WebApplicationException("El recurso /trabajadores/" + trabajadorId + " no existe.", 404);
        }
        TrabajadorDetailDTO detailDTO = new TrabajadorDetailDTO(trabajadorEntity);
        LOGGER.log(Level.INFO, "TrabajadorResource getTrabajador: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @DELETE
    @Path("{trabajadorId: \\d+}")
    public void deleteTrabajador(@PathParam("trabajadorId") Long trabajadorId) {
        LOGGER.log(Level.INFO, "AuthorResource deleteAuthor: input: {0}", trabajadorId);
        if (trabajadorLogic.getTrabajador(trabajadorId) == null) {
            throw new WebApplicationException("El recurso /trabajadores /" + trabajadorId + " no existe.", 404);
        }
        trabajadorLogic.deleteTrabajador(trabajadorId);
        LOGGER.info("TrabajadorResource deleteTrabajador: output: void");
    }
    
    
     @Path("{trabajadoresId: \\d+}/servicios")
    public Class<TrabajadorServicioOfrecidoResource> getTrabajadorResource(@PathParam("trabajadoresId") Long trabajadoresId) {
        if (trabajadorLogic.getTrabajador(trabajadoresId) == null) {
            throw new WebApplicationException("El recurso /trabajadores/" + trabajadoresId + " no existe.", 404);
        }
        return TrabajadorServicioOfrecidoResource.class;
    }
    
    
    private List<TrabajadorDetailDTO> listEntity2DTO(List<TrabajadorEntity> entityList) {
        List<TrabajadorDetailDTO> list = new ArrayList<>();
        for (TrabajadorEntity entity : entityList) {
            list.add(new TrabajadorDetailDTO(entity));
        }
        return list;
    }
}
