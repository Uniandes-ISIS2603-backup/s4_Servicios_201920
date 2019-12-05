/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.PagoTarjetaDTO;
import co.edu.uniandes.csw.servicios.ejb.PagoTarjetaLogic;
import co.edu.uniandes.csw.servicios.entities.PagoTarjetaEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import java.util.ArrayList;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Estudiante
 */
@Path("/tarjetas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PagoTarjetaResource {

    @Inject
    private PagoTarjetaLogic pagoTarjetaLogic;

    @POST
    public PagoTarjetaDTO createPagoTarjeta(PagoTarjetaDTO pagoTarjeta) throws BusinessLogicException {
        PagoTarjetaEntity tarjetaEntity = pagoTarjeta.toEntity();
        PagoTarjetaEntity nuevaTarjetaEntity = pagoTarjetaLogic.createPagoTarjeta(tarjetaEntity);
        return new PagoTarjetaDTO(nuevaTarjetaEntity);
    }

    @PUT
    @Path("{pagoTarjetaId: \\d+}")
    public PagoTarjetaDTO updatePagoTarjeta(@PathParam("pagoTarjetaId") Long pagoTarjetaId, PagoTarjetaDTO pagoTarjeta) throws BusinessLogicException {
        pagoTarjeta.setId(pagoTarjetaId);
        if (pagoTarjetaLogic.getTarjeta(pagoTarjetaId) == null) {
            throw new WebApplicationException("El recurso /pagoTarjetas/" + pagoTarjetaId + " no existe.", 404);
        }
        return new PagoTarjetaDTO(pagoTarjetaLogic.updatePagoTarjeta(pagoTarjeta.toEntity()));
    }

    @GET
    public List<PagoTarjetaDTO> getPagoTarjetas() {
        return listEntity2DTO(pagoTarjetaLogic.getTarjetas());
    }

    @GET
    @Path("{pagoTarjetaId: \\d+}")
    public PagoTarjetaDTO getPagoTarjeta(@PathParam("pagoTarjetaId") Long pagoTarjetaId) {
        PagoTarjetaEntity tarjetaEntity = pagoTarjetaLogic.getTarjeta(pagoTarjetaId);
        if (tarjetaEntity == null) {
            throw new WebApplicationException("El recurso /pagoTarjetas/" + pagoTarjetaId + " no existe.", 404);
        }
        return new PagoTarjetaDTO(tarjetaEntity);
    }

    @DELETE
    @Path("{pagoTarjetaId: \\d+}")
    public void deletePagoTarjeta(@PathParam("pagoTarjetaId") Long pagoTarjetaId, PagoTarjetaDTO pagoTarjeta) {
                if (pagoTarjetaLogic.getTarjeta(pagoTarjetaId) == null) {
            throw new WebApplicationException("El recurso /pagoTarjetas/" + pagoTarjetaId + " no existe.", 404);
        }
        pagoTarjetaLogic.deletePagoTarjeta(pagoTarjetaId);
    }

    private List<PagoTarjetaDTO> listEntity2DTO(List<PagoTarjetaEntity> tarjetas) {
        List<PagoTarjetaDTO> list = new ArrayList<>();
        for (PagoTarjetaEntity entity : tarjetas) {
            list.add(new PagoTarjetaDTO(entity));
        }
        return list;
    }
}
