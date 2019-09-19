/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.FacturaDTO;
import co.edu.uniandes.csw.servicios.dtos.PagoTarjetaDTO;
import co.edu.uniandes.csw.servicios.ejb.PagoTarjetaLogic;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author Estudiante
 */
public class PagoTarjetaResource
{

    @Inject
    private PagoTarjetaLogic pagoTarjetaLogic;

    @POST
    public PagoTarjetaDTO createPagoTarjeta(PagoTarjetaDTO pagoTarjeta) {
        return null;
    }

    @PUT
    @Path("{pagoTarjetaId: \\d+}")
    public PagoTarjetaDTO updatePagoTarjeta(@PathParam("pagoTarjetaId") Long pagoTarjetaId, PagoTarjetaDTO pagoTarjeta) {
        return null;
    }

    @GET
    public List<FacturaDTO> getPagoTarjetas() {
        return null;
    }

    @GET
    @Path("{pagoTarjetaId: \\d+}")
    public FacturaDTO getPagoTarjeta(@PathParam("pagoTarjetaId") Long pagoTarjetaId, PagoTarjetaDTO pagoTarjeta) {
        return null;
    }

    @DELETE
    @Path("{pagoTarjetaId: \\d+}")
    public FacturaDTO deletePagoTarjeta(@PathParam("pagoTarjetaId") Long pagoTarjetaId, PagoTarjetaDTO pagoTarjeta) {
        return null;
    }
}
