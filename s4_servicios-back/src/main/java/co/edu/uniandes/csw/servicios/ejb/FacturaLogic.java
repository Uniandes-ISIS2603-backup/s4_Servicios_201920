/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.FacturaEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.FacturaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Lucas Ibarra
 */
@Stateless
public class FacturaLogic {

    @Inject
    private FacturaPersistence persistence;

    public FacturaEntity createFactura(FacturaEntity factura) throws BusinessLogicException {
        //Duracion > 0
        if (factura.getDuracion() <= 0) {
            throw new BusinessLogicException("La duracion del servicio debe ser mayor a 0.");
        }

        //PrecioMateriales > 0
        else if (factura.getPrecioMateriales() < 0) {
            throw new BusinessLogicException("El precio de los materiales no puede ser negativo.");
        }

        // primerPago = true
        else if (!factura.isPrimerPago()) {
            throw new BusinessLogicException("No se puede crear la factura sin recibir el primer pago.");
        }
        // pagada = false
        else if (factura.isPagada()) {
            throw new BusinessLogicException("La factura no puede estar pagada al momento de su creacion.");
        }
        factura = persistence.create(factura);
        return factura;

        /**
         * // fecha != null if(factura.getFecha()==null){ throw new
         * BusinessLogicException("La factura debe tener una fecha de
         * finalizacion."); } // solicitud != null
         * if(factura.getSolicitud()==null){ throw new
         * BusinessLogicException("La factura debe tener una solicitud
         * asociada."); } // tarjetaPago != null if(factura.getTarjetaPago() ==
         * null){ throw new BusinessLogicException("Debe poseer una tarjeta"); }          *
         */
    }

    public List<FacturaEntity> getFacturas() {
        return persistence.findAll();
    }

    public FacturaEntity getFactura(Long idFactura) {
        return persistence.find(idFactura);
    }

    public FacturaEntity updateFactura(Long idFactura, FacturaEntity facturaEntity) throws BusinessLogicException {
        //Si servicio finalizo
        if (facturaEntity.getSolicitud().getEstado().equals("Finalizado")) {
            throw new BusinessLogicException("No se puede modificar una factura de un servicio finalizado.");
        }
        return persistence.update(facturaEntity);
    }

    public void deleteFactura(Long idFactura) {
        persistence.remove(idFactura);
    }
}
