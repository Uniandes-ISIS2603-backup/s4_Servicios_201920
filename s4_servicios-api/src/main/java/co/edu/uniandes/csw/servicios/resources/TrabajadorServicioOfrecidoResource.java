/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.ejb.ServicioOfrecidoLogic;
import co.edu.uniandes.csw.servicios.ejb.TrabajadorServicioOfrecidoLogic;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
    
    @POST
    @Path("{booksId: \\d+}")
    public TrabajadorDetailDTO addServicio(@PathParam("trabajadorId") Long trabajadorId, @PathParam("servicioOfrecidoId") Long servicioOfrecidoId) {
        
        if (servicioOfrecidoLogic.getServicioOfrecido(servicioOfrecidoId) == null) {
            throw new WebApplicationException("El recurso /books/" + servicioOfrecidoId + " no existe.", 404);
        }
        TrabajadorDetailDTO detailDTO = new TrabajadorDetailDTO(trabajadorServicioOfrecidoLogic.addBook(trabajadorId, servicioOfrecidoId));
        return detailDTO;
    }
    
    
}
