/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.FacturaDTO;
import co.edu.uniandes.csw.servicios.ejb.FacturaLogic;
import java.util.List;
import java.util.logging.Level;
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
 *
 * @author Juan Lucas Ibarra
*/
@Path("/facturas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FacturaResource {
    
    @Inject
    private FacturaLogic facturaLogic;
    
    @POST
    public FacturaDTO createFactura(FacturaDTO factura){
        return null;
    }
  
    @PUT
    @Path("{facturaId: \\d+}")
    public FacturaDTO updateFactura(@PathParam("facturaId") Long facturaId, FacturaDTO factura) {
        return null;
    }
    
    @GET
    public List<FacturaDTO> getFacturas() {
        return null;
    }
    
    @GET
    @Path("{facturaId: \\d+}")
    public FacturaDTO getFactura(@PathParam("facturaId") Long facturaId, FacturaDTO factura) {
        return null;
    }
    
    @DELETE
    @Path("{facturaId: \\d+}")
    public FacturaDTO deleteFactura(@PathParam("facturaId") Long facturaId, FacturaDTO factura) {
        return null;
    }
    
    
}
