/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.TrabajadorDTO;
import co.edu.uniandes.csw.servicios.ejb.TrabajadorLogic;
import java.util.List;
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
import javax.ws.rs.core.MediaType;

/**
 * @author c.otalora
 *
 */
@Path("/trabajadores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TrabajadorResourse {
    
    @Inject
    private TrabajadorLogic trabajadorLogic;
    
    @POST
    public TrabajadorDTO createTrabajador(TrabajadorDTO trabajador){
        return trabajador;
    }
  
    @PUT
    @Path("{trabajadorId: \\d+}")
    public TrabajadorDTO updateTrabajador(@PathParam("trabajadorId") Long facturaId, TrabajadorDTO factura) {
        return null;
    }
    
    @GET
    public List<TrabajadorDTO> getTrabajador() {
        return null;
    }
    
    @GET
    @Path("{trabajadorId: \\d+}")
    public TrabajadorDTO getTrabajador(@PathParam("trabajadorId") Long facturaId, TrabajadorDTO factura) {
        return null;
    }
    
    @DELETE
    @Path("{trabajadorId: \\d+}")
    public TrabajadorDTO deleteTrabajador(@PathParam("trabajadorId") Long facturaId, TrabajadorDTO factura) {
        return null;
    }
}
