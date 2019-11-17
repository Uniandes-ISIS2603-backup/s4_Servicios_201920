/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.FacturaDTO;
import co.edu.uniandes.csw.servicios.ejb.FacturaLogic;
import co.edu.uniandes.csw.servicios.entities.FacturaEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import java.util.ArrayList;
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
    public FacturaDTO createTarjeta(FacturaDTO factura) throws BusinessLogicException {
        FacturaEntity facturaEntity = factura.toEntity();
        FacturaEntity nuevaFacturaEntity = facturaLogic.createFactura(facturaEntity);
        FacturaDTO nuevaFacturaDTO = new FacturaDTO(nuevaFacturaEntity);
        return nuevaFacturaDTO;
    }

    @PUT
    @Path("{facturaId: \\d+}")
    public FacturaDTO updateFactura(@PathParam("facturaId") Long facturaId, FacturaDTO factura) throws BusinessLogicException {
        factura.setId(facturaId);
        if (facturaLogic.getFactura(facturaId) == null) {
            throw new WebApplicationException("El recurso /facturas/" + facturaId + " no existe.", 404);
        }
        FacturaDTO detailDTO = new FacturaDTO(facturaLogic.updateFactura(facturaId, factura.toEntity()));
        return detailDTO;
    }

    @GET
    public List<FacturaDTO> getFacturas() {
        List<FacturaDTO> listaFacturas = listEntity2DTO(facturaLogic.getFacturas());
        return listaFacturas;
    }

    @GET
    @Path("{facturaId: \\d+}")
    public FacturaDTO getFactura(@PathParam("facturaId") Long facturaId) {
        FacturaEntity facturaEntity = facturaLogic.getFactura(facturaId);
        if (facturaEntity == null) {
            throw new WebApplicationException("El recurso /facturas/" + facturaId + " no existe.", 404);
        }
        FacturaDTO detailDTO = new FacturaDTO(facturaEntity);
        return detailDTO;
    }

    @DELETE
    @Path("{facturaId: \\d+}")
    public void deleteFactura(@PathParam("facturaId") Long facturaId, FacturaDTO factura) {
        if (facturaLogic.getFactura(facturaId) == null) {
            throw new WebApplicationException("El recurso /facturas/" + facturaId + " no existe.", 404);
        }
        facturaLogic.deleteFactura(facturaId);
    }

    private List<FacturaDTO> listEntity2DTO(List<FacturaEntity> entityList) {
        List<FacturaDTO> list = new ArrayList<>();
        for (FacturaEntity entity : entityList) {
            list.add(new FacturaDTO(entity));
        }
        return list;
    }

}
