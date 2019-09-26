/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.CalificacionDTO;
import co.edu.uniandes.csw.servicios.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.servicios.ejb.CalificacionLogic;
import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
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
    
    
}
